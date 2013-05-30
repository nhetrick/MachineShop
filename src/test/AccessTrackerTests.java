package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.AccessTracker;
import main.Log;
import main.LogEntry;
import main.Machine;
import main.Tool;
import main.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import GUI.Driver;

public class AccessTrackerTests {
	
	static Calendar calendar;
	static Driver driver;
	static AccessTracker tracker;
	static User testUser;
	
	@BeforeClass
	public static void setup() {
		driver = new Driver();
		tracker = Driver.getAccessTracker();
		calendar = Calendar.getInstance();
	}
	
	@AfterClass
	public static void cleanup() {
		tracker.clearAllUsers();
	}
	
	@Test
	public void createAndLoadUserTest() {
		
		testUser = new User("Test", "User", 88888888);
		
		//user not in system
		assertFalse(tracker.getCurrentUsers().contains(testUser));
		
		// Query the database for a user with this CWID.
		tracker.createUser("Test", "User", 88888888);
		
		//user now in system
		assertTrue(tracker.getCurrentUsers().contains(testUser));
	}
	
	@Test
	public void processLogInAndLogOutTest() {
		
		Date startTime = calendar.getTime();
		
		testUser = new User(" ", " ", 10542318);
		
		tracker.processLogIn(10542318);
		
		// Ensure this user is now in the list of current users
		assertTrue(tracker.getCurrentUsers().contains(testUser));
		
		// Ensures the entry was added to the log
		Log.extractLog(testUser);
		
		int latestEntryIndex = Log.getResults().size() - 1;
		LogEntry entry = Log.getResults().get(latestEntryIndex);
		
		// Must ensure that THAT particular log in was logged.
		// Get the time from this log entry? Ensure that it is
		// after the "startTime" variable above
		assertTrue(entry.getTimeIn().after(startTime));
		
		// ADD ANOTHER TEST HERE TO MAKE SURE THE USER WAS
		// PERSISTED TO THE DATABASE -- TO DO --
		
		Date currentTime = calendar.getTime();
		
		ArrayList<Machine> machines = new ArrayList<Machine>();
		machines.add(new Machine("TEST MACHINE", "1KD01"));
		
		ArrayList<Tool> tools = new ArrayList<Tool>();
		tools.add(new Tool("TEST TOOL", 3333));
		
		entry.addMachinesUsed(machines);
		entry.addToolsCheckedOut(tools);
		entry.addToolsReturned(tools);
		
		tracker.processLogOut(10542318);
		
		// Ensure the user has been removed from the list of current users
		assertFalse(tracker.getCurrentUsers().contains(testUser));
		
		//Ensures the entry was added to the log
		Log.extractLog(testUser);
		
		latestEntryIndex = Log.getResults().size() - 1;
		entry = Log.getResults().get(latestEntryIndex);
		
		// Get the time from this log entry? Ensure that it is
		// after the "currentTime" and after the log in time
	
		assertTrue(entry.getTimeOut().after(currentTime));
		assertTrue(entry.getTimeOut().compareTo(entry.getTimeIn())>=0);
	}
		
	@Test
	public void checkLegitimacyTest() {
		// CAN'T DO YET BECAUSE WE DON'T HAVE ACCESS
		// TO THE BLASTERCARD DATABASE
		fail("Not yet implemented");
	}
	
	@Test
	public void loadNameTest() {
		// CAN'T DO YET BECAUSE WE DON'T HAVE ACCESS
		// TO THE BLASTERCARD DATABASE
		fail("Not yet implemented");
	}
	
	@Test
	public void updateToolsTest() {
		
		Tool testTool1 = new Tool("Test Tool 1", 818);
		Tool testTool2 = new Tool("Test Tool 2", 828);
		Tool testTool3 = new Tool("Test Tool 3", 838);
		Tool testTool5 = new Tool("Test Tool 4", 848);
		
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
