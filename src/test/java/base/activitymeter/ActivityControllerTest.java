package base.activitymeter;


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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


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
	static final String EMAIL = "a@b.de";
	static final String UNI = "hm";
	static final String FAC = "7";
	static final String IMG = "data:image/jpeg;base64,someimgdata";

	@Autowired
	private ActivityRepository activityRepository;

	@Test
	public void ensureThatUnpublishedActivitiesAreNotShown() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		this.mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("[]"));

	}

	@Test
	public void ensureThatPublishedActivitiesAreShown() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);

		this.mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		activity = activityRepository.findOne((long) 1);
		activity.setPublished(true);
		activityRepository.save(activity);
		String expectedString = "[{\"id\":1,\"text\":\"sampletxt\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"nope\",\"published\":true,\"secretKey\":\"nope\",\"uni\":\"hm\",\"faculty\":\"7\",\"image\":\"data:image/jpeg;base64,someimgdata\"}]";

		mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(expectedString));

	}
	
	@Test
	public void ensureThatActivitiesAreBeingDeleted() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);

		this.mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON).content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		activity = activityRepository.findOne((long) 1);
		activity.setPublished(true);
		activityRepository.save(activity);
		String expectedString = "[{\"id\":1,\"text\":\"sampletxt\",\"tags\":\"#tag, #tag2\",\"title\":\"sampletitle1\",\"eMail\":\"nope\",\"published\":true,\"secretKey\":\"nope\",\"uni\":\"hm\",\"faculty\":\"7\",\"image\":\"data:image/jpeg;base64,someimgdata\"}]";

		mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(expectedString));
		mockMvc.perform(delete("/activity/1"));
		mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("[]"));

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
