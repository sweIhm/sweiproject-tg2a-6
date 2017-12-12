package base;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController 
{
	

	@Value("${welcome.message:test}")
	private String message = "Hello World";
	
	@RequestMapping("/hello")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		System.out.println("test");
		return "hell";
	}
}