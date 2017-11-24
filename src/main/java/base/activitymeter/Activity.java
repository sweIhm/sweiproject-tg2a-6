package base.activitymeter;

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

    public Activity (){};

    public Activity(String text, String tags, String title) {
        this.text = text;
        this.tags = tags;
        this.title = title;
        this.published = false;
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
    	boolean mailSent = new MailVerification().sendMail(eMail,id.toString());
    	if (mailSent == true) {
    		//say sent successfully
    	}
    	else {
    		//say error sending email
    	}
    }
}