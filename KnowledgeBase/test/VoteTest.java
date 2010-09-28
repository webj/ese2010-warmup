import java.util.List;

import models.Answer;
import models.Question;
import models.User;
import models.Vote;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class VoteTest extends UnitTest {
	
	@Before
	public void setup() {
		Fixtures.deleteAll();
	}
	
	@Test
	public void shouldAddAnVoteToAnswer() {
		
		//Create user
		User bob = new User("Bob", "hallo").save();
		User brayn = new User("Brayn", "velo").save();
		
		//Create questions
		bob.addQuestion("Whatever", "blabla").save();
		brayn.addQuestion("Another question", "this is the question").save();

		//fetch the first question
		List<Question> listbobQuestion = Question.find("byAuthor", bob).fetch();
		Question bobQuestion = listbobQuestion.get(0);
		
		//Check if right
		assertEquals("Whatever", bobQuestion.title);

		//fetch the second question
		List<Question> listbraynQuestion = Question.find("byAuthor", brayn).fetch();
		Question braynQuestion = listbraynQuestion.get(0);

		bobQuestion.addAnswer(brayn, "A lot").save();
		braynQuestion.addAnswer(bob, "Brayn, you are an idiot").save();
		bobQuestion.addAnswer(bob, "Oh, ok").save();

		List<Answer> listbobAnswer = Answer.find("byQuestion", bobQuestion).fetch();
		assertEquals(2, listbobAnswer.size());
		Answer bobAnswer = listbobAnswer.get(0);

		List<Answer> listbraynAnswer = Answer.find("byQuestion", braynQuestion).fetch();
		Answer braynAnswer = listbraynAnswer.get(0);

		braynAnswer.addVote(bob, true).save();
		bobAnswer.addVote(bob, true).save();
		bobAnswer.addVote(brayn, true).save();
		bobAnswer.addVote(bob, false).save();

		//check if the querys work
		List<Vote> votes = Vote.find("byResult", true).fetch();		
		assertEquals(3, votes.size());
		
		votes = Vote.find("byResult", false).fetch();
		assertEquals(1, votes.size());
		
		//check if the realtions deletes right
		bob.delete();
		
		assertEquals(0, Vote.count());
		votes = Vote.find("byResult", true).fetch();
		assertEquals(0, votes.size());

	}

	@Test
	public void shouldAddAnVoteQuestion(){
		
		//Create user
		User bob = new User("Bob", "hallo").save();
		User brayn = new User("Brayn", "velo").save();
		
		//Create questions
		bob.addQuestion("Whatever", "blabla").save();
		brayn.addQuestion("Another question", "this is the question").save();
		
		//fetch the first question
		List<Question> listbobQuestion = Question.find("byAuthor", bob).fetch();
		Question bobQuestion = listbobQuestion.get(0);
		
		bobQuestion.addVote(bob, true).save();
		bobQuestion.addVote(brayn, true).save();
		
		assertEquals(2, Vote.count());
		
		brayn.delete();
		
	}
	


}
