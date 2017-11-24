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
    private String tags;
    private String title;
    private String eMail;
    private boolean published;
    private String secretKey;
    private String uni;
    private String faculty;

    public Activity (){};

    public Activity(String text, String tags, String title, String eMail, String uni, String faculty) {
        this.text = text;
        this.tags = tags;
        this.title = title;
        this.published = false;
        this.eMail = eMail;
        this.uni = uni;
        this.faculty = faculty;
        
        char[] chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 60; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        secretKey = sb.toString();
    }

    public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getUni() {
		return uni;
	}

	public void setUni(String uni) {
		this.uni = uni;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public void setPublished(boolean published) {
		this.published = published;
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
    
    public String getTags() {
      return tags;
    }

    public void setTags(String tags) {
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
    	//default
    	new MailVerification().sendMail(eMail,id.toString());
    }
}