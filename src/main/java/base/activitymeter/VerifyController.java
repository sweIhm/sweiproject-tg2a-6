package base.activitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
@RequestMapping("/verify")
public class VerifyController {
		
	  @Autowired
	  private ActivityRepository activityRepository;
	  
	  
	  @GetMapping("{key}")
	  public String publish(@PathVariable String key) {
		  ArrayList<Activity> activities = new ArrayList<>();
	      activityRepository.findAll().forEach(activity -> activities.add(activity));
		  for (Activity activity : activities) {
			  if (activity.getSecretKey().equals(key)) {
				  activity.setPublished(true);
				  activityRepository.save(activity);
				  try {
					  String indexHTML = "";
					  BufferedReader getIndexHTML = new BufferedReader(
							  							new InputStreamReader(
							  									new FileInputStream("src/main/resources/static/verificationSuccess.html")));
					  
					  String line;
					  while ((line = getIndexHTML.readLine()) != null) {
						  indexHTML += line;
					  }
					  getIndexHTML.close();
					  return indexHTML;
				  } 
				  catch (IOException e) {
					  e.getStackTrace();
					  return "Successfull Verification!";
				  }
			  }
		  }
		  return "Post-Verification failed! Please try again!";
	  }
}