package models;

import java.util.*;

import javax.persistence.*;

import org.junit.Before;

import play.db.jpa.*;
import play.test.Fixtures;


@Entity
public class User extends Model {
	
	public String name;
	public String password;
	
	@OneToMany(mappedBy="author", cascade=CascadeType.REMOVE)
	public List<Question> questions;

	public User(String name, String password) {
		
		this.questions = new ArrayList<Question>();
		this.name = name;
		this.password = password;
	}
	
    @Before
    public void setup(){
    	Fixtures.deleteAll();
    } 
	
	public Question addQuestion(String title, String content){
		Question question = new Question(this, title, content).save();
		questions.add(question);
		this.save();
		return question;
	}

}
