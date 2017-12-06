package base.activitymeter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void ensureIndexIsReturned() throws Exception {

		String indexHTML = "";

		try (BufferedReader getIndexHTML = new BufferedReader(
				new InputStreamReader(new FileInputStream("src/main/resources/static/index.html")));) {
			String line;
			while ((line = getIndexHTML.readLine()) != null) {
				indexHTML += line;
				indexHTML += "\r\n";
			}
			getIndexHTML.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(forwardedUrl("index.html"));

		this.mockMvc.perform(get("/index.html")).andExpect(status().isOk()).andExpect(content().string(indexHTML));

	}

}
