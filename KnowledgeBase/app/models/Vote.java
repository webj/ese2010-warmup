package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Vote extends Model {
	
	public Boolean result;

	@ManyToOne
	public User author;
	
	@ManyToOne
	public Answer answer;

	@ManyToOne
	public Question question;

	public Vote(Question quesiton, User author, Boolean result2) {

		this.question = quesiton;
		this.author = author;
		this.result = result2;
		author.addVote(this);

	}
	

	public Vote(Answer answer, User author, Boolean result) {

		this.answer = answer;
		this.author = author;
		this.result = result;
		author.addVote(this);

	}
}
