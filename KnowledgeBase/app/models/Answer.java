package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;




@Entity
public class Answer extends Model {
	
	public User author;
	public Date timestamp;
	
	
	@Lob
	public String content;

	@ManyToOne
	public Question question;	
	
	public Answer(Question question, User author, String content){
		this.question = question;
		this.author = author;
		this.content = content;
		this.timestamp = new Date();		
	}
	

}