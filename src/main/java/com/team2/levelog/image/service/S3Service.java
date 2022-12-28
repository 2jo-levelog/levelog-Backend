package com.team2.levelog.image.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team2.levelog.image.entity.PostImage;
import com.team2.levelog.image.repository.ImageRepository;
import com.team2.levelog.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(file.getSize());
//            objectMetadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucket+"/post/image", fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            String splitUrl = "https://s3." + region + ".amazonaws.com/" + bucket + "/post/image/";
            String imagePath = s3Client.getUrl(bucket+"/post/image", fileName).toString().split(splitUrl)[1];
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
        return s3Client.getUrl(bucket+"/profile/image", fileName).toString().split(splitUrl)[1];
    }

    public void delete(String fileName) {
        boolean isExistObject = s3Client.doesObjectExist(bucket, fileName);
        if(isExistObject == true) {
            s3Client.deleteObject(bucket, "/post/image/" + fileName);
        }
    }

}
