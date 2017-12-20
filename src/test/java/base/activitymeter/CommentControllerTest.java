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
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

	private final static String COMMENT1 = "test comment";
	private final static String COMMENT2 = "test comment2";
	private final static String COMMENT3 = "test comment3";
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	public void testNoJSON() throws Exception
	{
		this.mockMvc.perform(post("/rest/comment")).andExpect(content().string(""));
		assertEquals(commentRepository.count(), 0);
	}
	
	@Test
	public void testNoCorrespondingActivity() throws Exception
	{
		this.mockMvc.perform(post("/rest/comment")
    			.contentType(MediaType.APPLICATION_JSON)
                .content("{\"activityID\": 1, \"comment\": \"" + COMMENT1 + "\"}")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string(""));
		assertEquals(commentRepository.count(), 0);
		
	}
	
	@Test
	public void testNoComment() throws Exception
	{
		
		Activity a = new Activity("", "", "", "", "", "", "", "");
		
		activityRepository.save(a);
		
		this.mockMvc.perform(post("/rest/comment")
    			.contentType(MediaType.APPLICATION_JSON)
                .content("{\"activityID\": " + a.getId() + ", \"comment\": \"\"}")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string(""));
		assertEquals(commentRepository.count(), 0);
		
	}
	
	@Test
	public void testCorrectComment() throws Exception
	{
		
		Activity a = new Activity("", "", "", "", "", "", "", "");
		
		activityRepository.save(a);
		
		this.mockMvc.perform(post("/rest/comment")
    			.contentType(MediaType.APPLICATION_JSON)
                .content("{\"activityID\": " + a.getId() + ", \"comment\": \"" + COMMENT1 + "\"}")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string(""));
		assertEquals(commentRepository.count(), 1);
		assertEquals(commentRepository.findOne(1L).getComment(), COMMENT1);
		
	}
	
	@Test
	public void testGetWithoutComment() throws Exception
	{
		this.mockMvc.perform(get("/rest/comment/1")).andExpect(content().string("[]"));
	}
	
	@Test
	public void testGetWithComment() throws Exception
	{
		commentRepository.save(new Comment(1L, COMMENT1));
		this.mockMvc.perform(get("/rest/comment/1"))
		.andExpect(jsonPath("$[0].activityID").value(1))
		.andExpect(jsonPath("$[0].comment").value(COMMENT1))
		.andExpect(jsonPath("$", hasSize(1)));
		
	}
	
	@Test
	public void testGetWithMultipleComments() throws Exception
	{
		System.out.println("bookmark");
		commentRepository.save(new Comment(1L, COMMENT1));
		commentRepository.save(new Comment(1L, COMMENT2));
		commentRepository.save(new Comment(1L, COMMENT3));
		
		this.mockMvc.perform(get("/rest/comment/1"))
		.andExpect(jsonPath("$[0].activityID").value(1))
		.andExpect(jsonPath("$[0].comment").value(COMMENT1))
		.andExpect(jsonPath("$[1].activityID").value(1))
		.andExpect(jsonPath("$[1].comment").value(COMMENT2))
		.andExpect(jsonPath("$[2].activityID").value(1))
		.andExpect(jsonPath("$[2].comment").value(COMMENT3))
		.andExpect(jsonPath("$", hasSize(3)));
		
	}	
	
	@Test
	public void testGetWithDifferentActivity() throws Exception
	{
		System.out.println("bookmark");
		commentRepository.save(new Comment(1L, COMMENT1));
		commentRepository.save(new Comment(1L, COMMENT2));
		commentRepository.save(new Comment(1L, COMMENT3));
		commentRepository.save(new Comment(2L, COMMENT1));
		commentRepository.save(new Comment(2L, COMMENT2));
		commentRepository.save(new Comment(2L, COMMENT3));
		commentRepository.save(new Comment(3L, COMMENT1));
		commentRepository.save(new Comment(3L, COMMENT2));
		commentRepository.save(new Comment(3L, COMMENT3));
		
		
		this.mockMvc.perform(get("/rest/comment/1"))
		.andExpect(jsonPath("$[0].activityID").value(1))
		.andExpect(jsonPath("$[0].comment").value(COMMENT1))
		.andExpect(jsonPath("$[1].activityID").value(1))
		.andExpect(jsonPath("$[1].comment").value(COMMENT2))
		.andExpect(jsonPath("$[2].activityID").value(1))
		.andExpect(jsonPath("$[2].comment").value(COMMENT3))
		.andExpect(jsonPath("$", hasSize(3)));
		
	}
	
}