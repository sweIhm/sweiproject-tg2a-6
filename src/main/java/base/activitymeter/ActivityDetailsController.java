package base.activitymeter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details/{activityID}")
public class ActivityDetailsController
{
	@Autowired
	private ActivityRepository activityRepository;
	  
	@GetMapping
	public Activity getActivityDetails( @PathVariable Long activityID ) throws Exception
	{
		Activity result =  activityRepository.findOne( activityID );
		
		if( result != null && result.isPublished() )
		{
			result.setSecretKey( null );
			result.seteMail( null );
			return result;
		}
		
		return null;
	}
}