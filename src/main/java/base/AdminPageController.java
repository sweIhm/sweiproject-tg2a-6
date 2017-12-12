package base;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping
    public String index(HttpSession session) { 
    	if( session.getAttribute("login") != null && (Boolean)session.getAttribute("login") == true )
    		return "redirect:/"; 
    	return "admin/index";
    }

}