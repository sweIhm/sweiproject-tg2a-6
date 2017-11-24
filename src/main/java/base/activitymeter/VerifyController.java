package base.activitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;

@RestController
@RequestMapping("/verify")
public class VerifyController {
		
	  @Autowired
	  private ActivityRepository activityRepository;
	  
	  
	  @GetMapping("{key}")
	  public String find(@PathVariable String key) {
		  System.out.println(key);
		  return System.getenv("password");
	  }
}