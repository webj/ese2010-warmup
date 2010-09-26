package models;

import java.util.*;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Question extends Model {
		
	public Date timestamp;
	public String title;
	
	@Lob
	public String content;
	
	@ManyToOne
	public User author;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	public List<Answer> answers;

	
	public Question(User user, String title, String content){
		
		this.answers = new ArrayList<Answer>();
		this.author = user;
		this.title = title;
		this.content = content;
		this.timestamp = new Date();		

	}
	
	public Question addAnswer(User user, String content){
		Answer answer = new Answer(this, user, content).save();
		answers.add(answer);
		this.save();
		return this;
	}
}