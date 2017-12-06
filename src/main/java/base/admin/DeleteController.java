package base.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.activitymeter.Activity;
import base.activitymeter.ActivityRepository;

@RestController
@RequestMapping("/delete/{activityID}")
public class DeleteController {

	@Autowired
	private EMailRepository emailRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@GetMapping
	public void deleteActivityBlockEmail(@PathVariable Long activityID) {
		Activity activityToDelete = activityRepository.findOne(activityID);
		
		String eMail = activityToDelete.geteMail();
		emailRepository.save(new EMail(eMail));
		
		activityRepository.delete(activityToDelete);
	}
}
