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

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
	static final String EMAIL = "a@hm.edu";
	static final String EMAIL2 = "a@cpp.edu";
	static final String UNI = "hm";
	static final String FAC = "7";
	static final String IMG = "data:image/jpeg;base64,someimgdata";
	static final String ZIPCODE ="80331";
	
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
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
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
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.zipcode").value(ZIPCODE));

	}

	@Test
	public void ensureActivityIsStoredCorrectly() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE2, EMAIL, UNI, FAC, IMG, ZIPCODE);
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
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.zipcode").value(ZIPCODE));


		Activity a = activityRepository.findOne((long)1);

		assertEquals(TEXT, a.getText());
		assertEquals(TAG, a.getTags());
		assertEquals(TITLE2, a.getTitle());
		assertEquals(EMAIL, a.geteMail());
		assertEquals(UNI, a.getUni());
		assertEquals(FAC, a.getFaculty());
		assertEquals(IMG, a.getImage());
		assertEquals(false, a.isPublished());
		assertEquals("1", String.valueOf(a.getId()));
		assertEquals(60, a.getSecretKey().length());
		assertEquals(ZIPCODE, a.getZipcode());

		
	}
	
	@Test
	public void ensureSecretKeyIsNotRevealedFromPostResponse() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.secretKey").doesNotExist());
	}
	
	
	@Test
	public void ensureEmailIsNotRevealedFromPostResponse() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.eMail").doesNotExist());
	}

	@Test
	public void ensureActivityIsNotStoredIfWrongEmail() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, "a@b.de", UNI, FAC, IMG, ZIPCODE);
		this.mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(activity))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}
	
	@Test
	public void ensureHMEmailIsAccepted() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG, ZIPCODE);
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
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.zipcode").value(ZIPCODE));
	}
	
	@Test
	public void ensureCPPEmailIsAccepted() throws Exception {
		Activity activity = new Activity(TEXT, TAG, TITLE, EMAIL2, UNI, FAC, IMG, ZIPCODE);
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
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.zipcode").value(ZIPCODE));
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
