package base.admin;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/admin")
public class AdminController
{
	@GetMapping
	public String admin(HttpSession s)
	{
		if(s.getAttribute("login") == null)
		{
			s.setAttribute("login", "true");

			return "You are now logged in as admin";
		}
		return "You are already logged in as admin";
	}
}
