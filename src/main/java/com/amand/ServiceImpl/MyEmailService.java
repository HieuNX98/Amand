package com.amand.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MyEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String [] tos, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(tos);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
