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

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest
{

	static final String ADMIN_NAME = "admin";
	static final String ADMIN_PASS = "admin";
	
	static final String WRONG_ADMIN_NAME = "wrongadmin";
	static final String WRONG_ADMIN_PASS = "wrongadmin";
	static final String FOREVER = "true";
	static final String SESSION_ONLY = "false";
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MockHttpSession mockSession;
	

	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void testCorrectPostLoginDataWithoutForever() throws Exception
	{
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS + "\",\"forever\":" + SESSION_ONLY +"}";
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
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME +"\",\"password\":\"" + WRONG_ADMIN_NAME + "\",\"forever\":" + SESSION_ONLY +"}";
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
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ WRONG_ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS + "\",\"forever\":" + SESSION_ONLY +"}";
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
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS + "\",\"forever\":" + FOREVER +"}";
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
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS + "\",\"forever\":" + FOREVER +"}";
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
	
	@Test
	public void testGetWithoutSession() throws Exception
	{
		this.mockMvc.perform(get("/rest/admin")).andExpect(content().string(""));
	}
	
	@Test
	public void testGetWithSession() throws Exception
	{
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS + "\",\"forever\":" + FOREVER +"}";
		this.mockMvc.perform(post("/rest/admin").contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON));
		
		this.mockMvc.perform(get("/rest/admin").session(mockSession))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value(ADMIN_NAME))
		.andExpect(jsonPath("$.id").value(mockSession.getAttribute("adminId")));
		
	}
}
