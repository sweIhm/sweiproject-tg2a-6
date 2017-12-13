package base.activitymeter;


import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



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
public class ActivityControllerTest {
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
	static final String ZIPCODE ="80331";

	@Autowired
	private ActivityRepository activityRepository;

	@Test
	public void ensureThatUnpublishedActivitiesAreNotShown() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/rest/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("[]"));

	}

	@Test
	public void ensureThatPublishedActivitiesAreShown() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);

		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		activity = activityRepository.findOne((long) 1);
		activity.setPublished(true);
		activityRepository.save(activity);
		String expectedString = "[{\"id\":1,\"text\":\"\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"nope\",\"secretKey\":\"nope\",\"uni\":\"hm\",\"faculty\":\"\",\"zipcode\":\"80331\",\"published\":true,\"reportCounter\":0,\"image\":\"\"}]";

		mockMvc.perform(get("/rest/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(expectedString));

	}
	
	@Test
	public void testReportingActivity() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		activity = activityRepository.save(activity);
		
		activity = activityRepository.findOne((long)1);
		assertEquals(activity.getReportCounter(), 0);
		
		long id = activity.getId();
		mockMvc.perform(get("/rest/activity/report/" + id)).andDo(print()).andExpect(status().isOk());
		
		activity = activityRepository.findOne(id);
		assertEquals(activity.getReportCounter(), 1);
	}
	
	/* Disabeld for sprint 1
	@Test
	public void ensureThatActivitiesAreBeingDeleted() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);

		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		activity = activityRepository.findOne((long) 1);
		activity.setPublished(true);
		activityRepository.save(activity);
		String expectedString = "[{\"id\":1,\"text\":\"sampletxt\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"nope\",\"published\":true,\"secretKey\":\"nope\",\"uni\":\"hm\",\"faculty\":\"7\",\"image\":\"data:image/jpeg;base64,someimgdata\"}]";

		mockMvc.perform(get("/rest/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(expectedString));
		mockMvc.perform(delete("/rest/activity/1"));
		mockMvc.perform(get("/rest/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("[]"));

	}
	
	@Test
	public void ensureThatActivitiesAreBeingUpdated() throws Exception {
		Activity activityOld = new Activity(TEXT + TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		Activity activitynew = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);

		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activityOld))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		activityOld = activityRepository.findOne((long) 1);
		activityOld.setPublished(true);
		activityRepository.save(activityOld);
		
		String expectedStringOld = "[{\"id\":1,\"text\":\"sampletxtsampletxt\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"nope\",\"published\":true,\"secretKey\":\"nope\",\"uni\":\"hm\",\"faculty\":\"7\",\"image\":\"data:image/jpeg;base64,someimgdata\"}]";
		String expectedStringNew = "[{\"id\":1,\"text\":\"sampletxt\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"nope\",\"published\":true,\"secretKey\":\"nope\",\"uni\":\"hm\",\"faculty\":\"7\",\"image\":\"data:image/jpeg;base64,someimgdata\"}]";

		mockMvc.perform(get("/rest/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(expectedStringOld));
		mockMvc.perform(put("/rest/activity/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activitynew))
		.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());;
		mockMvc.perform(get("/rest/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(expectedStringNew));

		
	}
	*/

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
