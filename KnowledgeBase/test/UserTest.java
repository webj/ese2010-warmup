import org.junit.*;

import java.util.*;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {

    @Test
    public void shouldCreateAndRetrieveUser(){
    	
    	//Create a new User
    	new User("Bob", "hallo").save();
    	new User("Brayn", "velo").save();
    	
    	
    	//Retrieve the user with some keywords
    	User bob = User.find("byPassword", "hallo").first();
    	User brayn = User.find("byName", "Brayn").first();
    	User zero = User.find("byPassword", "Housi").first();
    	
    	assertEquals("Bob", bob.name);
    	assertEquals("hallo", bob.password);
    	
    	assertEquals("Brayn", brayn.name);
    	assertEquals("velo", brayn.password);    	
    	
    	assertNull(zero);
    }
    
    @Before
    public void setup(){
    	Fixtures.deleteAll();
    } 
        
    @Test
    public void shouldDeleteUser(){
    	
    	User bob = new User("Bob", "hallo").save();
    	bob.delete();
    	assertEquals(0, User.count());    
    }
    
    @Test
    public void shouldUseTheQuestionRelation(){
    	User bob = new User("Bob", "hallo").save();
    	User brayn = new User("Brayn", "velo").save();
    	
    	bob.addQuestion("What's going on?", "Hey guys, What's going on?").save();
    	bob.addQuestion("Why rise the sun every mornig?", "The question is above").save();
    	
    	//all Questions
    	assertEquals(2, Question.count());
    	
    	//Retrieves the questions from bob
    	List<Question> bobQuestion = Question.find("byAuthor", bob).fetch();    	
    	assertEquals(2, bobQuestion.size());
    	
    	List<Question> braynQuestion = Question.find("byAuthor", brayn).fetch();
    	assertEquals(0, braynQuestion.size());
    }
    
    public void shouldDeleteUserAndEveryDependencies(){
    	
    	assertEquals(0, Question.count());
    	
    	//create user
    	User bob = new User("Bob", "hallo").save();
    	User brayn = new User("Brayn", "velo").save();
    	
    	//create questions
    	Question firstQuestion = bob.addQuestion("What's going on?", "Hey guys, What's going on?").save();
    	Question secondQuestion = brayn.addQuestion("Why doesen't snwo smell?", "Hey, yesterday I was in the mountains. " +
    			"I was very confused when I detected that snow doesen't smell. Pleas help me!").save();
    	
    	firstQuestion.addAnswer(brayn, "A lot").save();
    	secondQuestion.addAnswer(bob, "Brayn, you are an idiot").save();
    	firstQuestion.addAnswer(bob, "Oh, ok").save();
    	
    	assertEquals(2, Question.count());
    	assertEquals(2, User.count());
    	assertEquals(3, Answer.count());
    	
    	//delete bob
    	bob.delete();
    	
    	//All dependencies should be deleted
    	assertEquals(1, User.count());
    	assertEquals(1, Answer.count());
    	assertEquals(1, Question.count()); 
    }

}