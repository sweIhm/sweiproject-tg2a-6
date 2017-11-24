package base.activitymeter;

//using javax.mail.jar for sending eMails over google mail
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class MailVerification {

	private final static String eMailFrom = "activitrackerhm@gmail.com";
	private final static String eMailSubject = "ActiviTracker Verification";
	private final static String eMailText = "Attention! Do NOT answer on this eMail!\n\nPlease klick on the following link to post your activity: \n\n";
	
	//default constructor for eMail class
	public MailVerification(){};
	
	public boolean sendMail(String eMailTo, String verificationID) {
		
		//creating unique link for verification and posting activity
		String verificationLink = "http://..........." + verificationID;
		
		//setting properties for sending email over Simple Mail Transfer Protocol with port 465 with g-mail as host
		Properties properties = new Properties();    
        properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    
        properties.put("mail.smtp.port", "465");    
        
        //authenticating accessing AciviTracker Mail account with mail address and password
        Session session = Session.getDefaultInstance(properties,    
        					new javax.mail.Authenticator() {    
        						protected PasswordAuthentication getPasswordAuthentication() {    
        							return new PasswordAuthentication(eMailFrom, "password");  
        						}    
        					});    

        try {    
        	//creating a message with sender, receiver, subject and text
        	MimeMessage message = new MimeMessage(session);    
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(eMailTo));    
        	message.setSubject(eMailSubject);    
        	message.setText(eMailText + verificationLink);   
        	System.out.println("");
        	//sending the email  
        	Transport.send(message);    
        } 
        catch (MessagingException e) {
        	e.getStackTrace();
        	//returning false if email could not be sent
        	return false;
        } 
        //returning true if verification email was sent successfully
		return true;
	}
}
