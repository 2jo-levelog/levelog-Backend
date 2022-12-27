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

    // 메일 포트 설정
    @Value("${mail.smtp.port}")
    private int port;


    @Value("${mail.smtp.socketFactory.port}")
    private int socketPort;


    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.smtp.starttls.required}")
    private boolean startlls_required;
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;

    //
    @Bean
    public JavaMailSender javaMailService() {

        // Mime메세지와 simpleMail메세지를 서포트하는 구현체
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        // 메일 호스트 설정
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }

    // properties로 부터 mail 설정을 가져와 적용
    private Properties getMailProperties()
    {
        Properties pt = new Properties();
        pt.put("mail.smtp.socketFactory.port", socketPort);
        pt.put("mail.smtp.auth", auth);                                 // 인증 필요 여부
        pt.put("mail.smtp.starttls.enable", starttls);
        pt.put("mail.smtp.starttls.required", startlls_required);
        pt.put("mail.smtp.socketFactory.fallback",fallback);
        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return pt;
    }
}
