package base.admin;

import javax.persistence.Column;
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
   
    @Column(columnDefinition="TEXT")
    private String eMailAddress;
    
    public EMail(){};
    
    public EMail(String eMail) {
    	this.eMailAddress = eMail;
    }
    
    public void seteMailAddress(String eMail) {
    	this.eMailAddress = eMail;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
    	return id;
    }

	public String geteMailAddress() {
		return eMailAddress;
	}
}