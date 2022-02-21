package com.team7.propertypredict.service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class MailServiceImpl implements MailService {
	
	@Resource
	private JavaMailSender mailSender;
	
    private String from = "sa53.team7@gmail.com";
    
    @Async("taskExecutor")
	@Override
	public void sendHtmlMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();
        
		try {
			Thread.sleep(1000);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            
            mailSender.send(message);
            System.out.println("Email Sent");
        } catch (Exception e) {
        	e.printStackTrace();
        }

	}

}
