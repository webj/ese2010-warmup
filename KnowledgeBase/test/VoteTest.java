import models.*;

import org.junit.Test;

import play.test.UnitTest;


public class VoteTest extends UnitTest{

	@Test
	public void shouldAddAnVote(){
		
		User bob = new User("Bob", "hallo").save();
    	User brayn = new User("Brayn", "velo").save();
    	
    	Question firstQuestion = bob.addQuestion("What's going on?", "Hey guys, What's going on?").save();
    	firstQuestion.addAnswer(brayn, "A lot").save();
    	
    	firstQuestion.addVote(brayn, dislike).save;
    	firstAnser.addVote(bob, dislike).save;
	}
}