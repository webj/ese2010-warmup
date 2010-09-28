package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hsqldb.lib.Iterator;

import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String name;
	public String password;
	private ArrayList<Answer> answers;
	private ArrayList<Vote> votes;
	
	@OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	public List<Question> questions;
	
	

	public User(String name, String password) {
		
		this.votes = new ArrayList<Vote>();
		this.questions = new ArrayList<Question>();
		this.answers = new ArrayList<Answer>();
		this.name = name;
		this.password = password;
	}

	public User addQuestion(String title, String content) {
		Question question = new Question(this, title, content).save();
		this.questions.add(question);
		this.save();
		return this;
	}
	
	public void addAnswer(Answer answer){
		
		this.answers.add(answer);
		this.save();
		
	}

	public void addVote(Vote vote) {
		this.votes.add(vote);
		this.save();
		
	}
}
