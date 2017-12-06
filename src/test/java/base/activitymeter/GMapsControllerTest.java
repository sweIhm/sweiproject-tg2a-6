package base.activitymeter;

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

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GMapsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	static final String TEXT = "sampletxt";
	static final String TAG = "#tag, #tag2";
	static final String TITLE = "sampletitle1";
	static final String TITLE2 = "sampletitle2";
	static final String EMAIL = "a@hm.edu";
	static final String UNI = "MUAS";
	static final String UNI2 = "CalPoly";
	static final String FAC = "7";
	static final String IMG = "data:image/jpeg;base64,someimgdata";
	static final String ZIPCODE ="80331";
	static final String ZIPCODE2 ="80336";

	
	@Autowired
	private ActivityRepository activityRepository;

	@Test
	public void ensureGetRequestReturnsCorrectJSONIfRequestedUniExists() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity)));
	 
		activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI2, FAC, IMG, ZIPCODE2);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity)));
		
		activity = activityRepository.findOne((long)1);
		String key = activity.getSecretKey();
		this.mockMvc.perform(get("/rest/verify/" + key)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		activity = activityRepository.findOne((long)2);
		key = activity.getSecretKey();
		this.mockMvc.perform(get("/rest/verify/" + key)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/rest/mapdata/" + UNI))
				.andDo(print())
				.andExpect(jsonPath("$[0].id").value("1"))
				.andExpect(jsonPath("$[0].published").value(true))
				.andExpect(jsonPath("$[0].zipcode").value(ZIPCODE))
				.andExpect(jsonPath("$[1]").doesNotExist());
	}
	
	@Test
	public void ensureGetRequestReturnsCorrectJSONIfRequestedUniDoesNotExist() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI2, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity)));
		
		activity = activityRepository.findOne((long)1);
		String key = activity.getSecretKey();
		this.mockMvc.perform(get("/rest/verify/" + key)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/rest/mapdata/" + UNI))
				.andDo(print())
				.andExpect(content().string("[]"));
	}
	
	@Test
	public void ensureGetRequestDoesNotReturnUnpublishedActivities() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity)));
		
		this.mockMvc.perform(get("/rest/mapdata/" + UNI))
				.andDo(print())
				.andExpect(content().string("[]"));
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
