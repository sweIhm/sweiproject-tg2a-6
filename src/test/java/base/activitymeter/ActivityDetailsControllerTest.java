package base.activitymeter;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.hamcrest.Matchers.*;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ActivityDetailsControllerTest
{
	@Autowired
	private MockMvc mockMvc;
	
	static final String TEXT = "sampletxt";
	static final String TAG = "#tag, #tag2";
	static final String TITLE = "sampletitle";
	static final String EMAIL = "a@b.de";
	static final boolean PUBLISHED = true;
	static final String UNI = "hm";
	static final String FAC = "7";
	static final String IMG = "data:image/jpeg;base64,someimgdata";
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void testExistingActivity() throws Exception
	{	
		Activity a = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		a.setPublished(true);
		
		a = activityRepository.save(a);
		
		mockMvc.perform(get("/details/" + a.getId() ))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.text", is(TEXT)))
		.andExpect(jsonPath("$.tags", is(TAG)))
		.andExpect(jsonPath("$.title", is(TITLE)))
		.andExpect(jsonPath("$.eMail").doesNotExist())
		.andExpect(jsonPath("$.published", is(true)))
		.andExpect(jsonPath("$.secretKey").doesNotExist())
		.andExpect(jsonPath("$.uni", is(UNI)))
		.andExpect(jsonPath("$.faculty", is(FAC)))
		.andExpect(jsonPath("$.image", is(IMG)));
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void testNonExistingActivity() throws Exception
	{	
		Activity a = new Activity(TEXT, TAG, TITLE, EMAIL, UNI, FAC, IMG);
		a.setPublished(true);
		
		a = activityRepository.save(a);
		
		mockMvc.perform(get("/details/" + a.getId() + 1 ))
		.andExpect(status().isOk())
		.andExpect(content().string(""));
	}
	
	

}

