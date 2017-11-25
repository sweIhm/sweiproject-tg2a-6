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
	  
	  input.verify();
	  //BUG!!!!!!!! Posten nicht moeglich
	  
      return activityRepository.save(new Activity(input.getText(), input.getTags(), input.getTitle(), input.geteMail(), input.getUni(), input.getFaculty(), input.getImage()));
  }

}
	