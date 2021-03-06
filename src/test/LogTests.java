package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import GUI.Driver;


import main.*;

public class LogTests {
	
	static Driver driver;
	static SystemAdministrator admin;
	
	static User testUser1;
	static User testUser2;

	static LogEntry testEntry1;
	static LogEntry testEntry2;
	static LogEntry testEntry3;
	static LogEntry testEntry4;
	static LogEntry testEntry5;
	static LogEntry testEntry6;

	static Machine testMachine;
	static ArrayList<Machine> testMachines;

	static Machine testMachine2;
	static ArrayList<Machine> testMachines2;

	static Tool testTool1;
	static ArrayList<Tool> testTools1;

	static Tool testTool2;
	static ArrayList<Tool> testTools2;
	
	static Tool testTool3;
	static ArrayList<Tool> testTools3;

	static Tool testTool4;
	static Tool testTool5;
	static ArrayList<Tool> testTools4;
	
	static Tool testTool6;
	
	static Date dateIn;
	static Date dateOut;
	
	static ArrayList<Integer> entryIds;
	static ArrayList<String> machineIds;
	static ArrayList<String> toolIds;
	static ArrayList<User> users;

	@BeforeClass
	public static void setup() {
		
		driver = new Driver();
		admin = new SystemAdministrator("", "", "0", "admin@password.com", "ADMN");
		
		testUser1 = new User("", "", "2", "rayanami2@nerv.net", "YCLN");
		testUser2 = new User("", "", "3", "rayanami3@nerv.net", "YCLN");
		admin.addUser(testUser1);
		admin.addUser(testUser2);
		users = new ArrayList<User>();
		users.add(testUser1);
		users.add(testUser2);
		
		testEntry1 = new LogEntry();
		testEntry2 = new LogEntry();
		testEntry3 = new LogEntry();
		testEntry4 = new LogEntry();
		testEntry5 = new LogEntry();
		testEntry6 = new LogEntry();		

		testMachine = new Machine("TEST Lathe", "11A11");
		admin.addMachine(testMachine);
		testMachines = new ArrayList<Machine>();
		testMachines.add(testMachine);

		testMachine2 = new Machine("TEST Metal Press", "22A22");
		admin.addMachine(testMachine2);
		testMachines2 = new ArrayList<Machine>();
		testMachines2.add(testMachine2);

		testTool1 = new Tool("TEST Hammer", "111");
		admin.addTool(testTool1);
		testTools1 = new ArrayList<Tool>();
		testTools1.add(testTool1);

		testTool2 = new Tool("TEST Crowbar", "222");
		admin.addTool(testTool2);
		testTools2 = new ArrayList<Tool>();
		testTools2.add(testTool2);
		
		testTool3 = new Tool("TEST Sponge", "333");
		admin.addTool(testTool3);
		testTools3 = new ArrayList<Tool>();
		testTools3.add(testTool3);

		testTool4 = new Tool("TEST Driver", "444");
		admin.addTool(testTool4);
		testTool5 = new Tool("TEST Handsaw", "555");
		admin.addTool(testTool5);
		testTools4 = new ArrayList<Tool>();
		testTools4.add(testTool4);
		testTools4.add(testTool5);
		
		testTool6 = new Tool("TEST NotCheckedOutByAnyone", "777");
		admin.addTool(testTool6);

		testEntry1.startEntry(testUser1, testMachines2, testTools1, testTools2);
		testEntry2.startEntry(testUser2, testMachines2, testTools1, testTools2);
		testEntry3.startEntry(testUser1, testMachines2, testTools3, testTools2);
		testEntry4.startEntry(testUser1, testMachines2, testTools2, testTools4);
		testEntry5.startEntry(testUser1, testMachines, testTools2, testTools1);
		
		testEntry1.finishEntry();
		testEntry2.finishEntry();
		testEntry3.finishEntry();
		testEntry4.finishEntry();
		testEntry5.finishEntry();
		
		testEntry6.startEntry(testUser2, testMachines, testTools4, testTools3);
		testEntry6.finishEntry();
		
		entryIds = new ArrayList<Integer>();
		entryIds.add(testEntry1.getID());
		entryIds.add(testEntry2.getID());
		entryIds.add(testEntry3.getID());
		entryIds.add(testEntry4.getID());
		entryIds.add(testEntry5.getID());
		entryIds.add(testEntry6.getID());
		
		machineIds = new ArrayList<String>();
		machineIds.add(testMachine.getID());
		machineIds.add(testMachine2.getID());
		
		toolIds = new ArrayList<String>();
		toolIds.add(testTool1.getUPC());
		toolIds.add(testTool2.getUPC());
		toolIds.add(testTool3.getUPC());
		toolIds.add(testTool4.getUPC());
		toolIds.add(testTool5.getUPC());
		toolIds.add(testTool6.getUPC());
		
	}
	
	@Test
	public void extractLogByDateTest() {
		
		dateIn = testEntry1.getTimeIn();
		dateOut = testEntry6.getTimeOut();
		
		Log.clearResults();
		Log.extractLog(dateIn, dateOut, true);
		
		// Ensure the results of the extraction are correct
		// All 5 entries should be returned by this query.
		
		assertEquals(6, Log.getResults().size());
		assertTrue(Log.getResults().contains(testEntry1));
		assertTrue(Log.getResults().contains(testEntry2));
		assertTrue(Log.getResults().contains(testEntry3));
		assertTrue(Log.getResults().contains(testEntry4));
		assertTrue(Log.getResults().contains(testEntry5));
		assertTrue(Log.getResults().contains(testEntry6));
		
	}

	@Test
	public void extractLogByUserTest() {
		Log.clearResults();
		Log.extractLog(testUser2, true);
		
		// Ensure that this query returns 2 log entries, and
		// that they are both for testUser2
		assertTrue(Log.getResults().contains(testEntry2));
		assertTrue(Log.getResults().contains(testEntry6));
		assertFalse(Log.getResults().contains(testEntry1));
		assertEquals(2, Log.getResults().size());
		
	}

	@Test
	public void extractLogByCheckedOutToolTest() {
		Log.clearResults();
		Log.extractLogCheckedOutTool(testTool3, true);
		
		// log entry 3 is the only one with testTool3
		// as a checked-out tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResults().contains(testEntry3));
		assertEquals(1, Log.getResults().size());
		
		Log.clearResults();
		Log.extractLogCheckedOutTool(testTool5, true);
		
		// log entry 6 is the only one with testTool5
		// as a checked-out tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResults().contains(testEntry6));
		assertEquals(1, Log.getResults().size());
		
		Log.clearResults();
		Log.extractLogCheckedOutTool(testTool6, true);
		
		// No one checked out testTool6, so this query should
		// return no results (An empty List)
		assertEquals(0, Log.getResults().size());
	}

	@Test public void extractLogByReturnedTool() {
		Log.clearResults();
		Log.extractLogReturnedTool(testTool4, true);
		
		// log entry 4 is the only one with testTool4
		// as a returned tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResults().contains(testEntry4));
		assertEquals(1, Log.getResults().size());
		
		Log.clearResults();
		Log.extractLogReturnedTool(testTool5, true);
		
		// log entry 4 is the only one with testTool5
		// as a returned tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResults().contains(testEntry4));
		assertEquals(1, Log.getResults().size());
		
		Log.clearResults();
		Log.extractLogReturnedTool(testTool6, true);
		
		// No one returned testTool6, so this query should
		// return no results (An empty List)
		assertEquals(0, Log.getResults().size());
		
	}

	@Test
	public void extractLogByMachineTest() {
		Log.clearResults();
		Log.extractLog(testMachine, true);
		
		// log entry 5 is the only one with testMachine
		// as a machine they used, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResults().contains(testEntry5));
		assertEquals(2, Log.getResults().size());
		
	}
	
	@AfterClass
	public static void cleanup() {
		for (int i : entryIds) {
			Log.deleteEntry(i);
		}
		for (String i : machineIds) {
			admin.removeMachine(i);
		}
		for (String i : toolIds) {
			admin.removeTool(i);
		}
		admin.removeUsers(users);
		Driver.getAccessTracker().clearUsers(users);
	}
	
}
