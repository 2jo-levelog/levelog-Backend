package com.team2.levelog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

// 1. 기능 : 메일 properties를 받아 SMTP 설정
// 2. 작성자 : 조소영
@Configuration
@PropertySource("classpath:application-local.properties")   // 클래스 찾기 위한 경로
public class EmailConfig {

    // SMTP(Simple Mail Transfer Protocol / 발신메일), IMAP(Internet Messaging Access Protocol / 수신메일)

    @Value("${mail.smtp.port}")
    private int port;                       // SMTP 포트 : TLS 587, SSL 465 (SSL3.0부터 TLS라고 부름)
    @Value("${mail.smtp.socketFactory.port}")
    private int socketPort;                 // 소켓 포트 설정
    @Value("${mail.smtp.auth}")
    private boolean auth;                   // 인증 필요 여부
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.smtp.starttls.required}")
    private boolean startlls_required;      // 메일 발송시 STARTTLS를 지원하는 서버면 STARTTLS를 이용해서 TLS 암호화 사용
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;               // fallback(대체)
    @Value("${AdminMail.id}")     // 편지를 보내는 주체의 gmail주소 (example@gmail.com)
    private String id;
    @Value("${AdminMail.password}")             // 구글 메일 2단계 인증 앱 비밀번호 (일반 비밀번호 아님)
    private String password;

    //
    @Bean
    public JavaMailSender javaMailService() {

        // Mime메세지와 simpleMail메세지를 서포트하는 구현체
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        // 메일 호스트 설정
        javaMailSender.setHost("smtp.gmail.com");                       // 메일 Host 설정
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");                     // 인코딩 형식 설정
        return javaMailSender;
    }

    // properties로 부터 mail 설정을 가져와 적용
    private Properties getMailProperties()
    {
        Properties pt = new Properties();
        pt.put("mail.smtp.socketFactory.port", socketPort);
        pt.put("mail.smtp.auth", auth);
        pt.put("mail.smtp.starttls.enable", starttls);
        pt.put("mail.smtp.starttls.required", startlls_required);
        pt.put("mail.smtp.socketFactory.fallback",fallback);
        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  // 소켓 생성 클래스
        return pt;
    }
}
