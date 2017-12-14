package base.admin;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import base.activitymeter.Activity;
import base.activitymeter.ActivityRepository;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteControllerTest {

	private static final String TEXT = "sampletxt";
	private static final String TAG = "#tag, #tag2";
	private static final String TITLE = "sampletitle1";
	private static final String EMAIL = "a@hm.edu";
	private static final String UNI = "hm";
	private static final String FAC = "7";
	private static final String IMG = "data:image/jpeg;base64,someimgdata";
	private static final String ZIPCODE ="80331";
	
	private static final String ADMIN_NAME = "admin";
	private static final String ADMIN_PASS = "admin";
	private static final String SESSION_ONLY = "false";
	
	@Autowired
	private EMailRepository emailRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MockHttpSession mockSession;
	

	@Autowired
	private AdminRepository adminRepository;
	
	
	@Test
	public void ensurePostIsDeletedAndEMailBlocked() throws Exception {
		
		Admin a =  new Admin( ADMIN_NAME, ADMIN_PASS );
		adminRepository.save( a );
		String login = "{\"name\":\""+ ADMIN_NAME +"\",\"password\":\"" + ADMIN_PASS + "\",\"forever\":" + SESSION_ONLY +"}";
		this.mockMvc.perform(post("/rest/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("true"));
		
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		activity = activityRepository.findOne((long)1);
		activity.setReportCounter(activity.getReportCounter() + 1);
		long id = activity.getId();
		String eMailAddress = activity.geteMail();
		activityRepository.save(activity);
		
		this.mockMvc.perform(get("/rest/delete/" + id)				
				.session(mockSession))
				.andDo(print())
				.andExpect(status().isOk());
		
		activity = activityRepository.findOne(id);

		EMail email = emailRepository.findOne((long)1);
		String eMailBlocked = email.geteMailAddress();
		
		String hash = GenerateHash.getHash(eMailAddress);
		
		assertEquals(activity, null);
		assertEquals(hash, eMailBlocked);
	}
	
	
	

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}