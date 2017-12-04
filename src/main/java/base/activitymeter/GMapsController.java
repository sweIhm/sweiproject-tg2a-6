package base.activitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/mapdata")
public class GMapsController {

	@Autowired
	private ActivityRepository activityRepository;

	@GetMapping("{uni}")
	public ArrayList<Activity> find(@PathVariable String uni) {
		ArrayList<Activity> activities = new ArrayList<>();

		for (Activity a : activityRepository.findAll()) {
			if (!a.isPublished() || !a.getUni().equals(uni)) {
				continue;
			}
			a.seteMail(null);
			a.setFaculty(null);
			a.setImage(null);
			a.setSecretKey(null);
			a.setTags(null);
			a.setText(null);
			a.setTitle(null);
			a.setUni(null);
			
			activities.add(a);
		}

		return activities;
	}

}
