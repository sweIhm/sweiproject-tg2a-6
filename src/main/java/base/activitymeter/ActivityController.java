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
      
      for(Activity a: activityRepository.findAll()) {
    	  if (!a.isPublished()) {
    		  continue;
    	  }
    	  a.setSecretKey(fakeValue);
    	  a.seteMail(fakeValue);
    	  activities.add(a);
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


  /* Disabeld for sprint 1
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
  */

}
