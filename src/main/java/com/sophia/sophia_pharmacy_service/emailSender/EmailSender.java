package com.sophia.sophia_pharmacy_service.emailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Value("${FRONTEND_URL}")
    private String frontLink;

    @Autowired
    private JavaMailSender mailSender;

    public void sendInviteEmail(String to, String token,String name) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Convite para acessar farmácia");
        message.setText(
                "Você foi convidado para acessar a farmácia: " + name+"\n\n" +
                        "Clique no link:\n" + frontLink+"/entrar?token="+token
        );


        mailSender.send(message);
    }
}
