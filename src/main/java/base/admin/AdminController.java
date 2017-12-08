package base.admin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/rest/admin")
public class AdminController
{

	@Autowired
	private AdminRepository adminRepository;
	
	private void tempFillDataBase()
	{
		adminRepository.save(new Admin( "ottensme@hm.edu", "supersecretpassword" ));
		adminRepository.save(new Admin( "semlinger@hm.edu", "supersecretpassword" ));
		adminRepository.save(new Admin( "orendi@hm.edu", "supersecretpassword" ));
		adminRepository.save(new Admin( "scalet@hm.edu", "supersecretpassword" ));
	}
	
	
	@GetMapping
	public Admin getAdmin(HttpSession session)
	{
		
		if(session.getAttribute("adminId") != null)
		{
			Admin result = adminRepository.findOne((long)session.getAttribute("adminId") );
			result.prepareForREST();
			return result;
		}
		return null;
	}
	@PostMapping
	public Boolean login( @RequestBody String json , HttpSession session)
	{
		
		tempFillDataBase();
		
		if( session.getAttribute("login") != null && (Boolean)session.getAttribute("login") == true )
			return false;
		
		try {
			Map<String, Object> jsonMap = new ObjectMapper().readValue(json, Map.class);
			String name = (String)jsonMap.get("name");
			String pass = (String)jsonMap.get("password");
			Boolean forever = (Boolean)jsonMap.get("forever");
			if( pass != null )
			{
				for(Admin a : adminRepository.findAll())
				{
					if( name.equals( a.getName() ) && a.checkPassword(pass))
					{
						session.setAttribute("login", true);
						session.setAttribute("adminId", a.getId());
						return true;
					}
				}
			}
			
			
		} catch (IOException e) {
			return false;
		}

		return false;
		
		
	}
}
