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
	static final String ADMIN_PASS_0 = "admin";
	static final String ADMIN_NAME_1 = "admin2";
	static final String ADMIN_PASS_1 = "admin2";
	
	static final String WRONG_ADMIN_NAME = "wrongadmin";
	static final String WRONG_ADMIN__PASS = "wrongadmin";
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
		Admin a =  new Admin( ADMIN_NAME_0, ADMIN_PASS_0 );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME_0 +"\",\"password\":\"" + ADMIN_PASS_0 + "\",\"forever\":" + SESSION_ONLY +"}";
		this.mockMvc.perform(post("/rest/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("true"));
		
		assertTrue( ((Boolean) mockSession.getAttribute("login")).booleanValue() );
		assertEquals( mockSession.getAttribute("adminId"), a.getId() );
	}
	
	@Test
	public void testWrongPassword() throws Exception
	{
		Admin a =  new Admin( ADMIN_NAME_0, ADMIN_PASS_0 );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME_0 +"\",\"password\":\"" + WRONG_ADMIN_NAME + "\",\"forever\":" + SESSION_ONLY +"}";
		this.mockMvc.perform(post("/rest/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("false"));
		
		assertEquals( mockSession.getAttribute("login"), null );
		
	}
	
	@Test
	public void testWrongUser() throws Exception
	{
		Admin a =  new Admin( ADMIN_NAME_0, ADMIN_PASS_0 );
		adminRepository.save( a );
		String login = "{\"name\":\""+ WRONG_ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS_0 + "\",\"forever\":" + SESSION_ONLY +"}";
		this.mockMvc.perform(post("/rest/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("false"));
		
		assertEquals( mockSession.getAttribute("login"), null );
	}
	
	@Test
	public void testCorrectPostLoginDataWithForever() throws Exception
	{
		Admin a =  new Admin( ADMIN_NAME_0, ADMIN_PASS_0 );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME_0 +"\",\"password\":\"" + ADMIN_PASS_0 + "\",\"forever\":" + FOREVER +"}";
		this.mockMvc.perform(post("/rest/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("true"))
				.andExpect(cookie().exists("JSESSIONID"))
				.andExpect(cookie().value("JSESSIONID", mockSession.getId()))
				.andExpect(cookie().maxAge("JSESSIONID", Integer.MAX_VALUE));
		
		assertTrue( ((Boolean) mockSession.getAttribute("login")).booleanValue() );
		assertEquals( mockSession.getAttribute("adminId"), a.getId() );
	}
	
	@Test
	public void testLogout() throws Exception
	{
		System.out.println("bookmark");
		Admin a =  new Admin( ADMIN_NAME_0, ADMIN_PASS_0 );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME_0 +"\",\"password\":\"" + ADMIN_PASS_0 + "\",\"forever\":" + FOREVER +"}";
		this.mockMvc.perform(post("/rest/admin").contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON));
		
		this.mockMvc.perform(delete("/rest/admin").session(mockSession))
		.andExpect(cookie().exists("JSESSIONID"))
		.andExpect(cookie().value("JSESSIONID", ""))
		.andExpect(cookie().maxAge("JSESSIONID", 0));;
		assertTrue(mockSession.isInvalid());
		
	}
}
