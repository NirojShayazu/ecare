package com.example.demo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String toEmail, String genPassword){
		MimeMessage mimeMsg = javaMailSender.createMimeMessage();
		MimeMessageHelper msg = new MimeMessageHelper(mimeMsg);
		try {
			msg.setTo(toEmail);
			msg.setSubject("Account Login Details");
			String html = "Dear sir/madam,<br><br> "
					+ "Thanks so much for reaching out! Your request has been verified and "
					+ "looking forward to be wroking with you.<br><br>"
					+ "Please login into our website with your email and given password.<br>"
					+ "Password: <b>"+genPassword+"</b><br><br>"
					+ "Note that after first login, you can change your credentials as per your requirement.<br><br>"
					+ "Thank you! Regards,<br>"
					+ "<b>Ecare Medical Group</b>";
			msg.setText(html,true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		javaMailSender.send(mimeMsg);

	}

}
