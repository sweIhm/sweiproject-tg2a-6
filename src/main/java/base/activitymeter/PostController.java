package base.activitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
  
  @Autowired
  private ActivityRepository activityRepository;
  

  @PostMapping
  public Activity create(@RequestBody Activity input) {
	  
	  Activity activity = new Activity(input.getText(), input.getTags(), input.getTitle(), input.geteMail(), input.getUni(), input.getFaculty(), input.getImage());
	  activity.verify();
	  
	  Activity a = activityRepository.save(activity);
	  a.setSecretKey(null);
	  a.seteMail(null);
      return a;
  }

}
	