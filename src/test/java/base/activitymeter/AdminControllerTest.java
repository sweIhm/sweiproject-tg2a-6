package base.activitymeter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import base.admin.Admin;
import base.admin.AdminRepository;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import com.fasterxml.jackson.databind.ObjectMapper;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest
{

	static final String ADMIN_NAME_0 = "admin";
	static final String ADMIN__PASS_0 = "admin";
	
	
	@Autowired
	private MockMvc mockMvc;
	

	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void testCorrectPostLoginDataWithoutForever() throws Exception
	{/*
		System.out.println("bookmark");
		
		adminRepository.save( new Admin( ADMIN_NAME_0, ADMIN__PASS_0 ) );
		
		String login = "{\"name\":\""+ ADMIN_NAME_0 +"\",\"password\":\"" + ADMIN__PASS_0 + "\",\"forever\":false}";
		this.mockMvc.perform(post("/rest/admin").
				contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print());
				*/
	}
}
