package com.tinqinacademy.authentication.core.coms;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailSender;

    public void emailActivation(String toEmail,String code) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailSender);
        helper.setTo(toEmail);
        helper.setSubject("Activate account message");
        String htmlMsg = "Dear client,<br><br>Your activation code is <b>"+code+"</b><br><br>Yours sincerely,<br>The Authentication Support team";
        helper.setText(htmlMsg,true);
        javaMailSender.send(mimeMessage);
    }

    public void emailRecovery(String toEmail,String code) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailSender);
        helper.setTo(toEmail);
        helper.setSubject("Recover password");
        String htmlMsg = "Dear client,<br><br>Your recovery code is <b>"+code+"</b><br><br>Yours sincerely,<br>The Authentication Support team";
        helper.setText(htmlMsg,true);
        javaMailSender.send(mimeMessage);
    }
}
