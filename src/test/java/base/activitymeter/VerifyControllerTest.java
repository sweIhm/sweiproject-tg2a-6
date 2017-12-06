package base.activitymeter;

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

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class VerifyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	static final String TEXT = "sampletxt";
	static final String TAG = "#tag, #tag2";
	static final String TITLE = "sampletitle1";
	static final String TITLE2 = "sampletitle2";
	static final String EMAIL = "a@hm.edu";
	static final String UNI = "hm";
	static final String FAC = "7";
	static final String IMG = "data:image/jpeg;base64,someimgdata";
	static final String FAKE_KEY = "XXXX3333";
	static final String ZIPCODE ="80331";

	
	@Autowired
	private ActivityRepository activityRepository;
	
	
	@Test
	public void ensureSuccesslessVerificationNotPublishes() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/rest/verify/" + FAKE_KEY))
				.andDo(print())
				.andExpect(status().isOk());
		
		activity = activityRepository.findOne((long)1);
		assertFalse(activity.isPublished());
	}
	
	@Test
	public void ensureSuccessfullVerificationPublishesActivity() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE2, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
				
		activity = activityRepository.findOne((long)1);
		String key = activity.getSecretKey();
		
		this.mockMvc.perform(get("/rest/verify/" + key))
				.andDo(print())
				.andExpect(status().isOk());
		
		activity = activityRepository.findOne((long)1);
		assertTrue(activity.isPublished());
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