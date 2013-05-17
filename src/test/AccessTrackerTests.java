package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.AccessTracker;
import main.LogEntry;
import main.Tool;
import main.User;

import org.junit.Test;

public class AccessTrackerTests {
	
	@Test
	public void loadUserTest(){
		AccessTracker tracker = new AccessTracker();
		User testUser = new User("John", "Smith", 10539477);
		tracker.loadUser(testUser.getCWID());
		
		assertTrue(tracker.getCurrentUsers().contains(testUser));
	}
	
	@Test
	public void createUserTest(){
		AccessTracker tracker = new AccessTracker();
		tracker.createUser("John", "Smith", 10539477);
		User testUser = new User("", "", 10539477);
		
		assertTrue(tracker.getCurrentUsers().contains(testUser));
	}
	
	@Test
	public void processLogInTest(){
		fail("Not yet implemented");
	}
	
	@Test
	public void addLogEntryTest(){
		AccessTracker tracker = new AccessTracker();
		tracker.createUser("John", "Smith", 10539477);
		tracker.addLogEntry(10539477, "KYON");
		LogEntry testEntry = new LogEntry(tracker.getUser(10539477), "KYON");
		
		assertTrue(tracker.getLog().getEntries().contains(testEntry));
	}
	
	@Test
	public void processLogOutTest(){
		fail("Not yet implemented");
	}
	
	@Test
	public void checkLegitimacyTest(){
		fail("Not yet implemented");
	}
	
	@Test
	public void loadNameTest(){
		fail("Not yet implemented");
	}
	
	@Test
	public void updateToolsTest() {
		AccessTracker tracker = new AccessTracker();
		Tool testTool1 = new Tool("HITCOO", 15);
		Tool testTool2 = new Tool("EVA", 01);
		Tool testTool3 = new Tool("PROGKNIFE", 6);
		Tool testTool5 = new Tool("4WASNEVERTHERE", 5);
		
		tracker.getTools().add(testTool1);
		tracker.getTools().add(testTool2);
		tracker.getTools().add(testTool3);
		tracker.getTools().add(testTool5);
		
		testTool3.checkoutTool();
		tracker.updateTools();
		
		assertTrue(tracker.getAvailableTools().contains(testTool1));
		assertTrue(tracker.getAvailableTools().contains(testTool2));
		assertFalse(tracker.getAvailableTools().contains(testTool3));
		assertTrue(tracker.getAvailableTools().contains(testTool5));
	}
}
