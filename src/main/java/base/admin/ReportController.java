package base.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.activitymeter.Activity;
import base.activitymeter.ActivityRepository;

@RestController
@RequestMapping("/rest/report/")
public class ReportController {

	@Autowired
	private ActivityRepository activityRepository;

	@GetMapping("{ordering}")
	public ArrayList<Activity> listAllreportedActivities(@PathVariable Long ordering) {
		ArrayList<Activity> activities = new ArrayList<>();

		for (Activity a : activityRepository.findAll()) {
			// if (!a.isReported()) { ->>das war deins vorher
			if (a.getReportCounter() == 0) {
				continue;
			}
			a.setSecretKey("");
			a.seteMail("");

			a.setText("");
			a.setImage("");
			a.setFaculty("");
			activities.add(a);

		}
		
		// Sorting
		Collections.sort(activities, new Comparator<Activity>() {
			@Override
			public int compare(Activity act1, Activity act2) {
				if (ordering == 1) {
					return act1.getReportCounter() - act2.getReportCounter();
				} else {
					return act2.getReportCounter() - act1.getReportCounter();
				}
			}
		});

		return activities;
	}

}
