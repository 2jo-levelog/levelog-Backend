package com.team2.levelog.image.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.image.repository.entity.PostImage;
import com.team2.levelog.image.repository.ImageRepository;
import com.team2.levelog.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// 1. 기능   : 이미지 첨부 비즈니스 로직
// 2. 작성자 : 박소연
@Service
@RequiredArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.cloud_front.file_url_format}")
    private String CLOUD_FRONT_DOMAIN_NAME;

    private final ImageRepository imageRepository;


    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public List<String> upload(Post post, List<MultipartFile> multipartFiles) throws IOException {
        List<String> imageUrlList = new ArrayList<>();

        for(MultipartFile file : multipartFiles) {
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(bucket+"/post/image", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }

            String splitUrl = "https://s3." + region + ".amazonaws.com/" + bucket + "/post/image/";
            String originalFileName = s3Client.getUrl(bucket+"/post/image", fileName).toString().split(splitUrl)[1];

            String imagePath = "https://" + CLOUD_FRONT_DOMAIN_NAME + "/post/image/" + originalFileName;
            imageUrlList.add(imagePath);

            PostImage image = new PostImage(post, imagePath);
            imageRepository.save(image);
        }
        return imageUrlList;
    }

    public String uploadOne(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        s3Client.putObject(new PutObjectRequest(bucket+"/profile/image", fileName, file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        String splitUrl = "https://s3." + region + ".amazonaws.com/" + bucket + "/profile/image/";
        String originalFileName = s3Client.getUrl(bucket+"/profile/image", fileName).toString().split(splitUrl)[1];

        return "https://" + CLOUD_FRONT_DOMAIN_NAME + "/profile/image/" + originalFileName;
    }

    public void delete(String fileName) {
        boolean isExistObject = s3Client.doesObjectExist(bucket, fileName);
        if(isExistObject == true) {
            s3Client.deleteObject(bucket, "/post/image/" + fileName);
        }
    }
}
