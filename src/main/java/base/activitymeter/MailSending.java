package base.activitymeter;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class MailSending {

	private String eMailTo;
	
	private final static String eMailSubjectVerification = "ActiviTracker Verification"; 
	private final static String eMailBodyVerification = "Attention! Do NOT answer on this eMail!\n\nPlease klick on the following link to post your activity: \n\n";
	
	private final static String eMailSubjectBlocked = "ActiviTracker Information";
	private final static String eMailBodyFirstBlocked = "Hello ActiviTracker-User,\n\nSadly we have to tell you that you are not allowed to post activities anymore!\n"
														+ "Reason for that was a post with following name: ";
	private final static String eMailBodySecondBlocked = "\n\nYou got blocked on: ";
	private final static String eMailBodyThirdBlocked = "\n\nPlease do NOT answer on this EMail!";
												
	
	public MailSending(String eMailTo){
		this.eMailTo = eMailTo;
	};
	
	public void sendVerificationMail(String verificationID) {

		
		String verificationLink = System.getenv("URL_VERIFY") + verificationID;
		
		System.out.println(verificationID);    
		
        try {    
        	MimeMessage message = new MimeMessage(getSession());   
        	InternetAddress mailAdressReceiver = new InternetAddress(eMailTo);
        	message.addRecipient(Message.RecipientType.TO, mailAdressReceiver);    
        	message.setSubject(eMailSubjectVerification);    
        	message.setText(eMailBodyVerification + verificationLink);   

        	Transport.send(message);  
        } 
        catch (MessagingException e) {
        	e.getStackTrace();
        } 
	}
	
	public void sendBlockedMail(String activityName, String date) {
		try {    
        	MimeMessage message = new MimeMessage(getSession());   
        	InternetAddress mailAdressReceiver = new InternetAddress(eMailTo);
        	message.addRecipient(Message.RecipientType.TO, mailAdressReceiver);    
        	message.setSubject(eMailSubjectBlocked);  
        	
        	String body = eMailBodyFirstBlocked + activityName + eMailBodySecondBlocked + date + eMailBodyThirdBlocked;
        	message.setText(body); 

        	Transport.send(message);  
        } 
        catch (MessagingException e) {
        	e.getStackTrace();
        } 
	}
	
	
	public Session getSession() {
		Properties properties = new Properties();    
        properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    
        properties.put("mail.smtp.port", "465");
        
		PasswordAuthentication passwordAuthenticator = new PasswordAuthentication(System.getenv("EMAIL_ADDRESS"), System.getenv("EMAIL_PASSWORD"));
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {    
        															protected PasswordAuthentication getPasswordAuthentication() {    
        																return passwordAuthenticator;  
        															}    
        														});  
        
        return session;
	}
}