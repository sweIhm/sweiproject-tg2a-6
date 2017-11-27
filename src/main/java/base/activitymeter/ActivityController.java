package base.activitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/activity")
public class ActivityController {
  private final String fakeValue = "nope";
	
  @Autowired
  private ActivityRepository activityRepository;
  
  
  
  @GetMapping
  public ArrayList<Activity> listAll() {
      ArrayList<Activity> activities = new ArrayList<>();
      
      activityRepository.findAll().forEach(activity -> activities.add(activity));
      for (Activity a: activities) {
    	  a.setSecretKey(fakeValue);
    	  a.seteMail(fakeValue);
      }
      return activities;
  }

  @GetMapping("{id}")
  public Activity find(@PathVariable Long id) {
	  Activity a = activityRepository.findOne(id);
	  a.setSecretKey(fakeValue);
	  a.seteMail(fakeValue);
      return a;
  }

  //DARF MICHT MEHR VERWENDET WERDEN -> use PostController
  /*@PostMapping 
  public Activity create(@RequestBody Activity input) {
      return activityRepository.save(new Activity(input.getText(), input.getTags(), input.getTitle(), input.geteMail(), input.getUni(), input.getFaculty()));
  }*/

  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) {
      activityRepository.delete(id);
  }

  @PutMapping("{id}")
  public Activity update(@PathVariable Long id, @RequestBody Activity input) {
      Activity activity = activityRepository.findOne(id);
      if (activity == null) {
          return null;
      } else {
          activity.setText(input.getText());
          activity.setTags(input.getTags());
          activity.setTitle(input.getTitle());
          activity.seteMail(input.geteMail());
          activity.setUni(input.getUni());
          activity.setFaculty(input.getFaculty());


          return activityRepository.save(activity);
      }
  }

}
