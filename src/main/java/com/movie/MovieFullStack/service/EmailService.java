package com.movie.MovieFullStack.service;

import com.movie.MovieFullStack.Dto.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
//   this class will give us the template to send the mail
    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage (MailBody mailBody){
        SimpleMailMessage newMessage = new SimpleMailMessage();
        newMessage.setTo(mailBody.to());
        newMessage.setFrom("amancore4482@gmail.com");
        newMessage.setSubject(mailBody.subject());
        newMessage.setText(mailBody.text());
        javaMailSender.send(newMessage);
    }
}

