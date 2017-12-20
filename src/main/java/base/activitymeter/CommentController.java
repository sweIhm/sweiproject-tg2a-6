package base.activitymeter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rest/comment")
public class CommentController {

	@Autowired
	private ActivityRepository activityRepository;

	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@GetMapping("{id}")
	public List<Comment> getForActivity(@PathVariable Long id)
	{

		List<Comment> result = new ArrayList<>();
		for (Comment a : commentRepository.findAll())
		{
			if(a.getActivityID().equals(id))
				result.add(a);
		}
		return result;
	}
	
	@PostMapping
	public void comment( @RequestBody String json )
	{
		Map<String, Object> jsonMap;
		try {
			jsonMap = new ObjectMapper().readValue(json, Map.class);

			Long activityID = ((Integer)jsonMap.get("activityID")).longValue();
			String comment = (String)jsonMap.get("comment");
			if(comment.equals(""))
				return;
			if(activityRepository.findOne(activityID) == null )
				return;
			commentRepository.save( new Comment(activityID, comment));
		} 
		catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}
	}
	
}
