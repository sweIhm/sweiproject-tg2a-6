package base.admin;

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

import base.activitymeter.Activity;
import base.activitymeter.ActivityRepository;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ActivityRepository activityRepository;


	static final String TEXT = "sampletxt";
	static final String TAG = "#tag, #tag2";
	static final String TITLE = "sampletitle1";
	static final String TITLE2 = "sampletitle2";
	static final String EMAIL = "a@hm.edu";
	static final String UNI = "hm";
	static final String FAC = "7";
	static final String IMG = "data:image/jpeg;base64,someimgdata";
	static final String ZIPCODE = "80331";

	// test for ascending ordering
	@Test
	public void ensureThatUnreportedActivitiesAreNotShownAsc() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/rest/report/0")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("[]"));

	}

	// test for descending ordering
	@Test
	public void ensureThatUnreportedActivitiesAreNotShownDesc() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/rest/report/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("[]"));

	}
	
	// ensure that reported activities are shown, both asc and desc
	@Test
	public void ensureThatReportedActivitiesAreNotShownAscDesc() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/rest/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
		activity = activityRepository.findOne((long) 1);
		activity.setReportCounter(1);
		activityRepository.save(activity);
		String expect = "[{\"id\":1,\"text\":\"\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"\",\"secretKey\":\"\",\"uni\":\"hm\",\"faculty\":\"\",\"zipcode\":\"80331\",\"published\":false,\"reportCounter\":1,\"image\":\"\"}]";
		mockMvc.perform(get("/rest/report/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(expect));
		
		mockMvc.perform(get("/rest/report/0")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(expect));

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
