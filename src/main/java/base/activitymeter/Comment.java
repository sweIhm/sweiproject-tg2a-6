package base.activitymeter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long activityID;
	
	private String comment;
	
	private Date postTime;
	
	public Comment()
	{
		
	}
	
	public Comment( Long activityID, String comment )
	{
		this.activityID = activityID;
		this.comment = comment;
		this.postTime = new Date();
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivityID() {
		return activityID;
	}

	public void setActivityID(Long activityID) {
		this.activityID = activityID;
	}
}
