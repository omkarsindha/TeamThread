package ca.sheridancollege.sindhao.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.sindhao.bean.Topic;
import ca.sheridancollege.sindhao.bean.Thread;

@Repository
public class DatabseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	public List<Topic> getAllTopics() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String q = "SELECT * FROM topics";
		return jdbc.query(q, namedParameters, new BeanPropertyRowMapper<Topic>(Topic.class));
	}

	public List<Thread> getAllThreads() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String q = "SELECT * FROM threads";
		return jdbc.query(q, namedParameters, new BeanPropertyRowMapper<Thread>(Thread.class));
	}

	public List<Thread> getThreadsByTopicID(int id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("topicid", id);
		String q = "SELECT * FROM threads WHERE topicid = :topicid";
		System.out.println(jdbc.query(q, namedParameters, new BeanPropertyRowMapper<Thread>(Thread.class)));
		return jdbc.query(q, namedParameters, new BeanPropertyRowMapper<Thread>(Thread.class));
	}

	public void postComment(Thread comment) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("topicid", comment.getTopicid());
		namedParameters.addValue("username", comment.getUsername());
		namedParameters.addValue("comment", comment.getComment());
		namedParameters.addValue("role", comment.getRole());
		String query = "INSERT INTO threads(topicid,username,comment,role,cmttime) VALUES(:topicid,:username,:comment,:role,NOW())";
		jdbc.update(query, namedParameters);
	}

	public int deleteTopicByID(int id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		String query = "DELETE FROM topics where id = :id";
		String q = "DELETE FROM threads where topicid = :id";
	    jdbc.update(q, namedParameters);
		return jdbc.update(query, namedParameters);
	}
	public int deleteThreadByID(int id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		String query = "DELETE FROM threads where id = :id";
		return jdbc.update(query, namedParameters);
	}

	public void addTopic(Topic topic) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("title", topic.getTitle());
		namedParameters.addValue("description", topic.getDescription());
		String query = "INSERT INTO topics(title,description) VALUES(:title,:description)";
		jdbc.update(query, namedParameters);
		
	}
}