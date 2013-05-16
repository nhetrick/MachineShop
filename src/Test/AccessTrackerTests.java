package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.AccessTracker;
import main.LogEntry;
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

}
