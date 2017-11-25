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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

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

	//evtl. Exception expecten
	
	@Test
	public void ensurePostRequestWithoutJSONBodyFails() throws Exception {
		//Causes WARN, which is OK, as no JSON is sent.
		this.mockMvc.perform(post("/post"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
    public void ensurePostRequestWithIncorrectJSONBodyFails() throws Exception {
		//Causes WARN, which is OK, as wrong JSON is sent.
    	String JSON = "{\"title\":" + TITLE + "}";
    	this.mockMvc.perform(post("/post")
    			.contentType(MediaType.APPLICATION_JSON)
                .content(JSON)
                .accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().is4xxClientError());  
    }

	@Test
	public void ensurePostRequestWithCorrectJSONBodyWorksAndCheckJSONResponse() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.text").value(TEXT))
				.andExpect(jsonPath("$.tags").value(TAG))
				.andExpect(jsonPath("$.title").value(TITLE))
				.andExpect(jsonPath("$.uni").value(UNI))
				.andExpect(jsonPath("$.faculty").value(FAC))
				.andExpect(jsonPath("$.image").value(IMG))
				.andExpect(jsonPath("$.published").value("false"))
				.andExpect(jsonPath("$.id").value("1"));
	}

	@Test
	public void ensureActivityIsStoredCorrectly() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE2, EMAIL, UNI, FAC, IMG);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.text").value(TEXT))
				.andExpect(jsonPath("$.tags").value(TAG))
				.andExpect(jsonPath("$.title").value(TITLE2))
				.andExpect(jsonPath("$.uni").value(UNI))
				.andExpect(jsonPath("$.faculty").value(FAC))
				.andExpect(jsonPath("$.image").value(IMG))
				.andExpect(jsonPath("$.published").value("false"))
				.andExpect(jsonPath("$.id").value("2"));

		Activity a = activityRepository.findOne((long)2);

		assertEquals(a.getText(), TEXT);
		assertEquals(a.getTags(), TAG);
		assertEquals(a.getTitle(), TITLE2);
		assertEquals(a.geteMail(), EMAIL);
		assertEquals(a.getUni(), UNI);
		assertEquals(a.getFaculty(), FAC);
		assertEquals(a.getImage(), IMG);
		assertEquals(a.isPublished(), false);
		assertEquals(String.valueOf(a.getId()), "2");
		assertEquals(a.getSecretKey().length(), 60);
		
	}
	
	@Test
	public void ensureSecretKeyAndEMailAreNotRevealedFromPostResponse() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.secretKey").isEmpty())
				.andExpect(jsonPath("$.eMail").isEmpty());

	}
	
	/*
	@Test
	public void ensureEmailIsNotRevealedFromPostResponse() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.eMail").isEmpty());
	}*/

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
