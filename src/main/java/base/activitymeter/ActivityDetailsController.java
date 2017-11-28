package base.activitymeter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details/{activityID}")
public class ActivityDetailsController
{
	@Autowired
	private ActivityRepository activityRepository;
	  
	@GetMapping
	public Activity getActivityDetails(@PathVariable Long activityID) throws Exception
	{
		Activity ret =  activityRepository.findOne(activityID);
		
		if( ret != null && ret.isPublished() )
		{
			ret.setSecretKey( null );
			ret.seteMail( null );
			return ret;
		}
		
		return null;
	}
}