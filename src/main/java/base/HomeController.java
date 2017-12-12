package base;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.admin.Admin;
import base.admin.AdminRepository;

@Controller // so framework can recognize this as a controller class
@RequestMapping("/")
public class HomeController {

	@Autowired
	private AdminRepository adminRepository;

    @GetMapping
    public String index(Map<String, Object> model, HttpSession session) { 
    	if( session.getAttribute("login") != null && (Boolean)session.getAttribute("login") == true )
    	{
    		model.put( "login", (Boolean) session.getAttribute("login") );
    		model.put( "admin", adminRepository.findOne((long)session.getAttribute("adminId") ) );
    	}
    	return "index"; 
    }

}