import java.util.List;

import models.Answer;
import models.Question;
import models.User;
import models.Vote;

import org.junit.Test;

import play.test.UnitTest;

public class TestTest extends UnitTest {

	@Test
	public void shouldAddAnVote() {

		User bob = new User("Bob", "hallo").save();
		User brayn = new User("Brayn", "velo").save();

		bob.addQuestion("Whatever", "blabla").save();
		brayn.addQuestion("Another question", "this is the question").save();

		List<Question> listbobQuestion = Question.find("byAuthor", bob).fetch();
		Question bobQuestion = listbobQuestion.get(0);

		List<Question> listbraynQuestion = Question.find("byAuthor", brayn)
				.fetch();
		Question braynQuestion = listbraynQuestion.get(0);

		bobQuestion.addAnswer(brayn, "A lot").save();
		braynQuestion.addAnswer(bob, "Brayn, you are an idiot").save();
		bobQuestion.addAnswer(bob, "Oh, ok").save();

		List<Answer> listbobAnswer = Answer.find("byAuthor", bob).fetch();
		Answer bobAnswer = listbobAnswer.get(0);

		List<Answer> listbraynAnswer = Answer.find("byAuthor", brayn).fetch();
		Answer braynAnswer = listbraynAnswer.get(0);

		braynAnswer.addVote(bob, "like").save();
		bobAnswer.addVote(bob, "like").save();
		bobAnswer.addVote(brayn, "like").save();

		List<Vote> votes = Vote.find("byResult", "like").fetch();
		
		assertEquals(3, votes.size());

	}
}

