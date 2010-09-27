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

	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
	public List<Question> questions;

	public User(String name, String password) {

		this.questions = new ArrayList<Question>();
		this.name = name;
		this.password = password;
	}

	public User addQuestion(String title, String content) {
		Question question = new Question(this, title, content).save();
		questions.add(question);
		this.save();
		return this;
	}

}
