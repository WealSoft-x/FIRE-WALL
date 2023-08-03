package com.example.demo.application.config;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.demo.application.domain.model.User;

public class Mail {
	

	public void sentMail(User user) {
		
		try {
		
		Properties property = new Properties();
		property.put("mail.smtp.auth", "true");
		property.put("mail.smtp.starttls.enable", "true");
		property.put("mail.smtp.host", "smtp.gmail.com");
		property.put("mail.smtp.port", "587");
		property.put("mail.smtp.debug", "true");
		
		
		Session session = Session.getInstance(property, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("info.wealsoft@gmail.com", "fojpnjmqxbglgerc");
			}
		});
		
		
		MimeMessage mimeMessage = new MimeMessage(session);
		InternetAddress toAddress = new InternetAddress(user.getMail(), user.getName());
		mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
		InternetAddress fromAddress = new InternetAddress("info.wealsoft@gmail.com", "TEST認証");
		mimeMessage.setFrom(fromAddress);
		mimeMessage.setSubject("認証コード" + user.getToken(), "ISO-2022-JP");
		mimeMessage.setText(user.getToken(), "ISO-2022-JP");
		Transport.send(mimeMessage);
		System.out.println("メール送信が完了しました。");
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

}
