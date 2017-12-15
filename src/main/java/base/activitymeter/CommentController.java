package base.activitymeter;

import java.io.IOException;
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
	
	@GetMapping
	public String getAll()
	{
		return "hui";
	}
	
	@GetMapping("{id}")
	public Long getForActivity(@PathVariable Long id)
	{
		return id;
	}
	
	@PostMapping
	public void comment( @RequestBody String json )
	{
		Map<String, Object> jsonMap;
		try {
			jsonMap = new ObjectMapper().readValue(json, Map.class);

			Long activityID = (Long)jsonMap.get("activityID");
			String comment = (String)jsonMap.get("comment");
			
			commentRepository.save( new Comment(activityID, comment));
		} 
		catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}
	}
	
}
