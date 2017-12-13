package base.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import base.admin.Admin;
import base.admin.AdminRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import base.activitymeter.Activity;
import base.activitymeter.ActivityRepository;
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest
{

	static final String ADMIN_NAME_0 = "admin";
	static final String ADMIN__PASS_0 = "admin";
	static final String FOREVER = "true";
	static final String SESSION_ONLY = "false";
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	MockHttpSession mockSession;
	

	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void testCorrectPostLoginDataWithoutForever() throws Exception
	{
		
		System.out.println("bookmark");
		Admin a =  new Admin( ADMIN_NAME_0, ADMIN__PASS_0 );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME_0 +"\",\"password\":\"" + ADMIN__PASS_0 + "\",\"forever\":" + SESSION_ONLY +"}";
		this.mockMvc.perform(post("/rest/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("true"));
		
		assertTrue( ((Boolean) mockSession.getAttribute("login")).booleanValue() );
		assertEquals( mockSession.getAttribute("adminId"), a.getId() ) ;
		
	}
}
