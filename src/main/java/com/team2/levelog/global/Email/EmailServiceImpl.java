package com.team2.levelog.global.Email;

import com.team2.levelog.global.GlobalResponse.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

import static com.team2.levelog.global.GlobalResponse.code.ErrorCode.FAIL_SEND_EMAIL;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired                                                      // 의존성 주입. 필요한 의존 객체의 “타입"에 해당하는 빈을 찾아 주입한다.
    JavaMailSender emailSender;

    public static final String ePw = createKey();                   // 인증코드 생성해서 대입

    // 메일 내용 설정
    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);                    // 로그로 서버에서 확인
        System.out.println("인증 번호 : "+ePw);

        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);        //  보내는 대상
        message.setSubject("Levelog 회원가입 이메일 인증");          //  메일 제목


        // MailSender는 SimpleMailMessage를 정의해 텍스트 메일을 발송 할 수있는 반면,
        // JavaMailSender에선 MimeMessage를 정의해 본문이 HTML로 이루어진 메일을 발송 가능
        // 보낼 메일 내용
        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 레벨로그입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>저희 사이트를 이용해주셔서 감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<strong>";
//        msgg+= "<a href='http://13.209.84.31:8080/api/auth/email/confirm/" + ePw + "'>"+ "클릭하여 인증을 완료해주세요" + "</a></strong><div><br/>";
        msgg+= "<a href='http://localhost:8080/api/auth/email/confirm/" + ePw + "'>"+ "클릭하여 인증을 완료해주세요" + "</a></strong><div><br/>";
        msgg+= "</div>";

        message.setText(msgg, "utf-8", "html");                                             // 내용 세팅
        message.setFrom(new InternetAddress("hellotesters000@gmail.com","hellotesters"));   // 보내는 사람

        return message;
    }

    // 인증코드 생성 및 반환
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    // 메일 전송 메소드
    @Override
    public String sendSimpleMessage (String to) throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new CustomException(FAIL_SEND_EMAIL);
        }
        return ePw;
    }

}
