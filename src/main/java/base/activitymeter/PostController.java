package base.activitymeter;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import base.admin.EMail;
import base.admin.EMailRepository;
import base.admin.GenerateHash;

@RestController
@RequestMapping("/rest/post")
public class PostController {
	

	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private EMailRepository emailRepository;

	@PostMapping
	public Activity create(@RequestBody Activity input) {
		
		String hash = GenerateHash.getHash(input.geteMail());
		
		ArrayList<EMail> eMails = new ArrayList<>();
	    emailRepository.findAll().forEach(eMail -> eMails.add(eMail));
		for (EMail eMail : eMails) {
			if (hash.equals(eMail.geteMailAddress())) {
				return null;
			}
		}

		Activity activity = new Activity(input.getText(), input.getTags(), input.getTitle(), input.geteMail(),
				input.getUni(), input.getFaculty(), input.getImage(), input.getZipcode());

		if (activity.geteMail().endsWith("@hm.edu") || activity.geteMail().endsWith("@cpp.edu")) {
			activity.verify();

			Activity a = activityRepository.save(activity);
			a.setSecretKey(null);
			a.seteMail(null);
			return a;
		} else {
			return null;
		}
	}

}
