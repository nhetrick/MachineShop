package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.AccessTracker;
import main.Log;
import main.LogEntry;
import main.Machine;
import main.SystemAdministrator;
import main.Tool;
import main.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import GUI.Driver;

public class AccessTrackerTests {
	
	static Calendar calendar;
	static Driver driver;
	static SystemAdministrator admin;
	static AccessTracker tracker;
	static User testUser1;
	static User testUser2;
	static ArrayList<User> users;
	static ArrayList<Integer> entryIds;
	
	@BeforeClass
	public static void setup() {
		driver = new Driver();
		tracker = Driver.getAccessTracker();
		admin = new SystemAdministrator("", "", "12", "@mines.edu", "ADMN");
		calendar = Calendar.getInstance();
		users = new ArrayList<User>();
		entryIds = new ArrayList<Integer>();
		
		testUser1 = new User("Test1", "User1", "88888888", "tuser1@mines.edu", "TEST");
		testUser2 = new User("Test2", "User2", "99999999", "tuser2@mines.edu", "TEST");
	}
	
	@Test
	public void createAndLoadUserTest() {
		
		//user not in system
		assertFalse(tracker.getCurrentUsers().contains(testUser1));
		
		// Query the database for a user with this CWID.
		tracker.createUser("Test1", "User1", "88888888", "tuser1@mines.edu", "TEST");
		users.add(testUser1);
		
		//user now in system
		assertTrue(tracker.getCurrentUsers().contains(testUser1));
	}
	
	@Test
	public void processLogInAndLogOutTest() {
		
		Date startTime = calendar.getTime();
		
		admin.addUser(testUser2);
		users.add(testUser2);
		tracker.processLogIn(testUser2.getCWID());
		
		//entryIds.add(testUser2.getCurrentEntry().getID());
		
		// Ensure this user is now in the list of current users
		assertTrue(tracker.getCurrentUsers().contains(testUser2));
		
		// Ensures the entry was added to the log
		Log.extractLog(testUser2, true);
		
		int latestEntryIndex = Log.getResults().size() - 1;
		LogEntry entry = Log.getResults().get(latestEntryIndex);
		entryIds.add(entry.getID());
		
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
		tools.add(new Tool("TEST TOOL", "3333"));
		
		entry.addMachinesUsed(machines);
		entry.addToolsCheckedOut(tools);
		entry.addToolsReturned(tools);
		
		tracker.processLogOut(testUser2.getCWID());
		
		// Ensure the user has been removed from the list of current users
		assertFalse(tracker.getCurrentUsers().contains(testUser2));
		
		//Ensures the entry was added to the log
		Log.clearResults();
		Log.extractLog(testUser2, true);
		entry = Log.getResults().get(latestEntryIndex);
			
		// Get the time from this log entry? Ensure that it is
		// after the "currentTime" and after the log in time
	
		assertTrue(entry.getTimeOut().after(currentTime));
		assertTrue(entry.getTimeOut().compareTo(entry.getTimeIn())>=0);
	}
	
	@Test
	public void updateToolsTest() {
		
		Tool testTool1 = new Tool("Test Tool 1", "818");
		Tool testTool2 = new Tool("Test Tool 2", "828");
		Tool testTool3 = new Tool("Test Tool 3", "838");
		Tool testTool5 = new Tool("Test Tool 4", "848");
		
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
	
	@AfterClass
	public static void cleanup() {
		tracker.clearUsers(users);
		admin.removeUsers(users);
		for (int i : entryIds) {
			Log.deleteEntry(i);
		}
	}
}
