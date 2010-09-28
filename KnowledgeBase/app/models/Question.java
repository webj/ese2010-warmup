package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Question extends Model {

	public Date timestamp;
	public String title;

	@Lob
	public String content;

	@ManyToOne
	public User author;

	@OneToMany(mappedBy = "question", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	public List<Answer> answers;

	@OneToMany(mappedBy = "question", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	public List<Vote> votes;


	public Question(User user, String title, String content) {

		this.answers = new ArrayList<Answer>();
		this.votes = new ArrayList<Vote>();
		this.author = user;
		this.title = title;
		this.content = content;
		this.timestamp = new Date();

	}

	public Question addAnswer(User author, String content) {
		Answer answer = new Answer(this, author, content).save();
		this.answers.add(answer);
		this.save();
		return this;
	}

	public Question addVote(User author, boolean result) {
		Vote vote = new Vote(this, author, result).save();
		votes.add(vote);
		this.save();
		return this;
	}
}