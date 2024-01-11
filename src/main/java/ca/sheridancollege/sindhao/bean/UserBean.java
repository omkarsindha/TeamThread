package ca.sheridancollege.sindhao.bean;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {
	private String username;
	private String password;
	private String authority = "user";

}
