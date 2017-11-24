package base.activitymeter;

import java.util.ArrayList;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String text;
    private ArrayList<String> tags;
    private String title;
    private String eMail;
    private boolean published;
    private String secretKey;
    private String uni;
    private int faculty;

    public Activity (){};

    public Activity(String text, ArrayList<String> tags, String title) {
        this.text = text;
        this.tags = tags;
        this.title = title;
        this.published = false;
        
        char[] chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 60; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        secretKey = sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public ArrayList<String> getTags() {
      return tags;
    }

    public void setTags(ArrayList<String> tags) {
      this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isPublished() {
    	return published;
    }

    public void publish() {
    	boolean mailSent = new MailVerification().sendMail(eMail,id.toString());
    	if (mailSent == true) {
    		//say sent successfully
    	}
    	else {
    		//say error sending email
    	}
    }
}