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

	@Test
	public void shouldAddAnVote() {
		
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

		braynAnswer.addVote(bob, "like").save();
		bobAnswer.addVote(bob, "like").save();
		bobAnswer.addVote(brayn, "like").save();
		bobAnswer.addVote(bob, "dis").save();

		//check if the querys work
		List<Vote> votes = Vote.find("byResult", "like").fetch();		
		assertEquals(3, votes.size());
		
		votes = Vote.find("byResult", "dis").fetch();
		assertEquals(1, votes.size());
		
		//check if the realtions deletes right
		bob.delete();
		
		votes = Vote.find("byResult", "like").fetch();
		assertEquals(1, votes.size());

	}
	
	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

}
