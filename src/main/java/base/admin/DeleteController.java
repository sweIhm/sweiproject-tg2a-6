package base.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.activitymeter.Activity;
import base.activitymeter.ActivityRepository;
import base.admin.EMailRepository;

@RestController
@RequestMapping("/rest/delete/{activityID}")
public class DeleteController {

	@Autowired
	private EMailRepository emailRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@DeleteMapping
	public void deleteActivityBlockEmail(@PathVariable Long activityID, HttpSession session) {
		
		if(session.getAttribute("login") != null && session.getAttribute("login").equals((Boolean)true)) {
			Activity activityToDelete = activityRepository.findOne(activityID);
			
			String hash = GenerateHash.getHash(activityToDelete.geteMail());

			emailRepository.save(new EMail(hash));
			activityRepository.delete(activityToDelete);
		}
	}
}
