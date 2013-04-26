package com.safdesk.comms;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.Authenticator;

import com.safdesk.util.Props;

import java.util.*;

public class SendMail {
	
	static Map<String, String> propMap = Props.get();
	
	public static synchronized void send(String subject, String to, String body){

		Properties sendProps = new Properties();
		sendProps.put("mail.smtp.host", propMap.get("sendHost"));
		sendProps.put("mail.smtp.port", propMap.get("sendPort"));
		sendProps.put("mail.smtp.user", propMap.get("mailAd"));

		sendProps.put("mail.smtp.auth", propMap.get("sendAUTH"));
		sendProps.put("mail.smtp.starttls.enable", propMap.get("sendTLS"));
		sendProps.put("mail.smtp.debug", propMap.get("sendDebug"));

		sendProps.put("mail.smtp.socketFactory.port", propMap.get("sendPort"));
		sendProps.put("mail.smtp.socketFactory.class", propMap.get("sendSF"));
		sendProps.put("mail.smtp.socketFactory.fallback", "false");
		
		Session gmailSend = Session.getInstance(sendProps, new Authenticator() {
	        protected PasswordAuthentication  getPasswordAuthentication() {
	            return new PasswordAuthentication(
	                        propMap.get("mailAd"), propMap.get("mailPass"));
	                    }
	        });
		gmailSend.setDebug(true);
		
		MimeMessage mailOut = new MimeMessage(gmailSend);
		try {
			mailOut.setSubject(subject);
			mailOut.setFrom(new InternetAddress(propMap.get("mailAd")));
			mailOut.setText(body);
			mailOut.addRecipient(RecipientType.TO, new InternetAddress(to));
			mailOut.saveChanges();
			
			Transport.send(mailOut);
			
		} catch (MessagingException e) {e.printStackTrace();}
		
	}
}
