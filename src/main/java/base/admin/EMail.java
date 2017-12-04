package base.admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EMail {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
   
    private String eMail;
    
    public EMail (String eMail) {
    	this.eMail = eMail;
    }
    
    public long getId() {
    	return id;
    }

	public String geteMail() {
		return eMail;
	}
}