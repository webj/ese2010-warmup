package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String name;
	public String password;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Question> questions;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Answer> answers;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Vote> votes;

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

	public User addAnswer(Answer answer) {

		this.answers.add(answer);
		this.save();
		return this;

	}

	public User addVote(Vote vote) {
		this.votes.add(vote);
		this.save();
		return this;
	}
}
