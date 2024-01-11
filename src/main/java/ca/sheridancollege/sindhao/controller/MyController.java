package ca.sheridancollege.sindhao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.sindhao.bean.UserBean;
import ca.sheridancollege.sindhao.bean.Thread;
import ca.sheridancollege.sindhao.bean.Topic;
import ca.sheridancollege.sindhao.database.DatabseAccess;
@Controller
public class MyController {
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	DatabseAccess da;
	@GetMapping("/")
	public String getIndex() {
		return "index.html";
	}
	
	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("topicList",da.getAllTopics());
		return "home.html";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login.html";
	}
	
	@GetMapping("/denied")
	public String getDenied() {
		return "denied.html";
	}
	
	
	@GetMapping("/register")
	public String getRegister() {
		return "register.html";
	}
	
	@GetMapping("/addTopic")
	public String getAddTopicPage(Model model) {
		model.addAttribute("topic", new Topic());
		return "addtopic.html";
	}
	
	@PostMapping("/addTopic")
	public String postAddTopic(@ModelAttribute Topic topic) {
		da.addTopic(topic);
		return "redirect:/home";
	}
	
	@GetMapping("/admin")
	public String getAdminPage(Model model) {
		model.addAttribute("topics",da.getAllTopics());
		model.addAttribute("threads",da.getAllThreads());
		return "admin.html";
	}
	
	@GetMapping("/thread/{id}")
	public String getThreadPage(Model model, @PathVariable int id) {
		model.addAttribute("topicid",id);
		model.addAttribute("threads", da.getThreadsByTopicID(id));
		model.addAttribute("comment", new Thread());
		return "thread.html";
	}
	
	@PostMapping("/postComment")
	public String postComment(@ModelAttribute Thread comment) {
		da.postComment(comment);
		return "redirect:/thread/" +comment.getTopicid();
	}
	@GetMapping("/rmtopic/{id}")
	public String deleteTopicByID(@PathVariable int id) {
		da.deleteTopicByID(id);
		return "redirect:/admin";
	}
	@GetMapping("/rmthread/{id}")
	public String deleteThreadByID(@PathVariable int id) {
		da.deleteThreadByID(id);
		return "redirect:/admin";
	}
	
	@PostMapping("/register")
	public String processRegister(@ModelAttribute UserBean user) {
		System.out.println(user);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getAuthority().equalsIgnoreCase("WORKER")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_WORKER"));
		} else if (user.getAuthority().equalsIgnoreCase("MANAGER")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		User newuser = new User(user.getUsername(), encodedPassword, authorities);
		jdbcUserDetailsManager.createUser(newuser);
		return "redirect:/";
	}
}
