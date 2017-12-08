package base.admin;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rest/admin")
public class AdminController
{
	@PostMapping
	public String admin( @RequestBody String json , HttpSession session)
	{
		
		String result = "session";
		
		if(session.getAttribute("login") == null)
		{

			result = "no-session";
		}
		try {
			Map<String, Object> jsonMap = new ObjectMapper().readValue(json, Map.class);
			String user = (String)jsonMap.get("name");
			String pass = (String)jsonMap.get("pass");
			Boolean forever = (Boolean)jsonMap.get("forever");
			
			session.setAttribute("login", true);
			session.setAttribute("name", user);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		return result;
		
	}
}
