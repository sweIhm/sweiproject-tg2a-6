package base.activitymeter;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(columnDefinition="TEXT")
    private String text;
    private String tags;
    private String title;
    private String eMail;
    private boolean published;
    private String secretKey;
    private String uni;
    private String faculty;
    
    @Lob
    private String image;

    public Activity (){};

    public Activity(String text, String tags, String title, String eMail, String uni, String faculty, String image) {
        this.text = text;
        this.tags = tags;
        this.title = title;
        this.published = false;
        this.eMail = eMail;
        this.uni = uni;
        this.faculty = faculty;
        this.image = image;
        
        char[] chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 60; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        secretKey = sb.toString();
    }

    


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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