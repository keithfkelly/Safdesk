package com.safdesk.comms;

import javax.mail.*;
import com.safdesk.util.DB;
import com.safdesk.util.Props;


import java.util.Map;
import java.util.Properties;

public class GetMail {

	static Properties mailProps = new Properties();
	static StringBuffer mailBuffer = new StringBuffer();


	public static void main(String[] args) throws Exception{

		Map<String, String> propMap = Props.get();
		String name = null;
		String email = null;

		mailProps.put("mail.host", propMap.get("mailHost"));
		mailProps.put("mail.store.protocol", propMap.get("mailProtocol"));
		mailProps.put("mail.pop3s.auth", propMap.get("mailAuth"));
		mailProps.put("mail.pop3s.port", propMap.get("mailPort"));

		Session gmailSession = Session.getDefaultInstance(mailProps);
		Store mailStore = gmailSession.getStore();
		mailStore.connect(propMap.get("mailAd"), propMap.get("mailPass"));
		Folder safMail = mailStore.getFolder("Inbox");

		safMail.open(Folder.READ_WRITE);
		Message mails[] = safMail.getMessages();

		for(Message mail:mails){
			Multipart mp = (Multipart) mail.getContent();
			BodyPart bp = mp.getBodyPart(0);
			String content = (String) bp.getContent();
			System.out.println(content);
			StringBuffer nAE = new StringBuffer(); 
			nAE.append(mail.getFrom()[0].toString());
			for(int i=0;i<nAE.length();i++){
				if(nAE.charAt(i)=='<'){
					name = nAE.substring(0, i-1);
					nAE.replace(0, i+1, "");
					nAE.replace(nAE.lastIndexOf(">"), nAE.length(), "");
					email = nAE.toString();
				}
			}
			String u = "CALL emailInsert('"+name+"','Via Email','"+email+"','"+mail.getSubject()+"','"+content+"')";
			System.out.println(u);
			new DB();
			DB.doU(u);
			mail.setFlag(Flags.Flag.DELETED, true);
		}
	}
}