package ca.sheridancollege.sindhao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import ca.sheridancollege.sindhao.bean.Topic;
import ca.sheridancollege.sindhao.database.DatabseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class MyTest {

	@Autowired
	DatabseAccess da;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLoadingIndexPage() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index.html"));
	}

	@Test
	public void testLoadingHome() throws Exception {
		this.mockMvc.perform(get("/home").with(user("user").roles("WORKER"))).andExpect(status().isOk())
				.andExpect(view().name("home.html"));
	}

	@Test
	public void testLoadingThreads() throws Exception {
		this.mockMvc.perform(get("/thread/{id}", 1).with(user("user").roles("WORKER"))).andExpect(status().isOk())
				.andExpect(view().name("thread.html"));
	}

	@Test
	public void testLoadingAdminPage() throws Exception {
		this.mockMvc.perform(get("/admin").with(user("user").roles("ADMIN"))).andExpect(status().isOk())
				.andExpect(view().name("admin.html"));
	}

	@Test
	public void testDeleteTopic() throws Exception {
		this.mockMvc.perform(get("/rmtopic/{id}", 1).with(user("user").roles("ADMIN"))).andExpect(status().isFound())
				.andExpect(redirectedUrl("/admin"));
	}

	@Test
	public void testDeleteThreads() throws Exception {
		this.mockMvc.perform(get("/rmthread/{id}", 1).with(user("user").roles("ADMIN"))).andExpect(status().isFound())
				.andExpect(redirectedUrl("/admin"));
	}

	@Test
	public void testPostComment() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("topipcid", "1");
		requestParams.add("username", "omkar");
		requestParams.add("role", "ROLE_ADMIN");
		requestParams.add("comment", "cmt example");
		this.mockMvc.perform(get("/postComment").params(requestParams)).andExpect(status().isFound());
	}

	@Test
	public void testAddTopic() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("title", "example title");
		requestParams.add("description", "example summary");
		this.mockMvc.perform(get("/addTopic").params(requestParams)).andExpect(status().isFound());
	}

	@Test
	public void testAddTopicToDatabase() {
		Topic topic = new Topic();
		topic.setTitle("title");
		topic.setDescription("description");
		int origSize = da.getAllTopics().size();
		da.addTopic(topic);
		int newSize = da.getAllTopics().size();
		assertThat(newSize).isEqualTo(origSize + 1);
	}
	

}
