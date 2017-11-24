package base.activitymeter;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

public class MailVerification {

	private final static String eMailFrom = "activitrackerhm@gmail.com";
	private final static String eMailSubject = "ActiviTracker Verification";
	private final static String eMailBody = "Attention! Do NOT answer on this eMail!\n\nPlease klick on the following link to post your activity: \n\n";
	
	public MailVerification(){};
	
	public void sendMail(String eMailTo, String verificationID) {
		
		String verificationLink = "http://..........." + verificationID;
		
		Properties properties = new Properties();    
        properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    
        properties.put("mail.smtp.port", "465");    

        PasswordAuthentication passwordAuthenticator = new PasswordAuthentication(eMailFrom, System.getenv("password"));
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {    
        															protected PasswordAuthentication getPasswordAuthentication() {    
        																return passwordAuthenticator;  
        															}    
        														});    

        try {    
        	MimeMessage message = new MimeMessage(session);   
        	InternetAddress mailAdressReceiver = new InternetAddress(eMailTo);
        	message.addRecipient(Message.RecipientType.TO, mailAdressReceiver);    
        	message.setSubject(eMailSubject);    
        	message.setText(eMailBody + verificationLink);   
        	
        	Transport.send(message);    
        } 
        catch (MessagingException e) {
        	e.getStackTrace();
        } 
	}
}
