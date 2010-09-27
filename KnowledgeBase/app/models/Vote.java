package models;

import javax.persistence.ManyToOne;

import play.db.jpa.Model;

public class Vote extends Model {

	public User author;
	public String result;

	@ManyToOne
	public Answer answer;

	@ManyToOne
	public Question question;

	public Vote(Question question, User author, String result) {

		this.question = question;
		this.author = author;
		this.result = result;

	}

	public Vote(Answer answer, User author, String result) {

		this.answer = answer;
		this.author = author;
		this.result = result;

	}
}