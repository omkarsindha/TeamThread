package ca.sheridancollege.sindhao.bean;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thread {
    int id;
	String topicid;
	String username;
	String role;
	String comment;
	Date cmttime;
}
