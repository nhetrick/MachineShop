package test;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;

import main.*;

public class LogTests {
	
	User testUser1;
	User testUser2;

	LogEntry testEntry1;
	LogEntry testEntry2;
	LogEntry testEntry3;
	LogEntry testEntry4;
	LogEntry testEntry5;
	LogEntry testEntry6;

	Machine testMachine;
	ArrayList<Machine> testMachines;

	Machine notAMachine;
	ArrayList<Machine> notTestMachines;

	Tool testTool1;
	ArrayList<Tool> testTools1;

	Tool testTool2;
	ArrayList<Tool> testTools2;
	
	Tool testTool3;
	ArrayList<Tool> testTools3;

	Tool testTool4;
	Tool testTool5;
	ArrayList<Tool> testTools4;
	
	Tool testTool6;
	
	Date dateIn;
	Date dateOut;

	@Before
	public void setup() {		
		testUser1 = new User("", "", 2);
		testUser2 = new User("", "", 3);
		
		testEntry1 = new LogEntry();
		testEntry2 = new LogEntry();
		testEntry3 = new LogEntry();
		testEntry4 = new LogEntry();
		testEntry5 = new LogEntry();
		testEntry6 = new LogEntry();		

		testMachine = new Machine("TPDD", "T-1");
		testMachines = new ArrayList<Machine>();
		testMachines.add(testMachine);

		notAMachine = new Machine("This it not a machine", "WINE");
		notTestMachines = new ArrayList<Machine>();
		notTestMachines.add(notAMachine);

		testTool1 = new Tool("HITCOO", 15);
		testTools1 = new ArrayList<Tool>();
		testTools1.add(testTool1);

		testTool2 = new Tool("Crowbar", 1);
		testTools2 = new ArrayList<Tool>();
		testTools2.add(testTool2);
		
		testTool3 = new Tool("Hammer", 103);
		testTools3 = new ArrayList<Tool>();
		testTools3.add(testTool3);

		testTool4 = new Tool("Driver", 56);
		testTool5 = new Tool("Handsaw", 40);
		testTools4 = new ArrayList<Tool>();
		testTools4.add(testTool4);
		testTools4.add(testTool5);
		
		testTool6 = new Tool("NotCheckedOutByAnyone", 0);

		testEntry1.startEntry(testUser1, notTestMachines, testTools1, testTools2);
		testEntry2.startEntry(testUser2, notTestMachines, testTools1, testTools2);
		testEntry3.startEntry(testUser1, notTestMachines, testTools3, testTools2);
		testEntry4.startEntry(testUser1, notTestMachines, testTools2, testTools4);
		testEntry5.startEntry(testUser1, testMachines, testTools2, testTools1);
		
		testEntry1.finishEntry();
		testEntry2.finishEntry();
		testEntry3.finishEntry();
		testEntry4.finishEntry();
		testEntry5.finishEntry();
		
		testEntry6.startEntry(testUser2, testMachines, testTools4, testTools3);
		testEntry6.finishEntry();
		
	}
	
	@Test
	public void extractLogByDateTest() {
		
		dateIn = testEntry1.getTimeIn();
		dateOut = testEntry5.getTimeOut();
		
		Log.extractLog(dateIn, dateOut);
		
		// Ensure the results of the extraction are correct
		// All 5 entries should be returned by this query.
		assertTrue(Log.getResult().contains(testEntry1));
		assertTrue(Log.getResult().contains(testEntry2));
		assertTrue(Log.getResult().contains(testEntry3));
		assertTrue(Log.getResult().contains(testEntry4));
		assertTrue(Log.getResult().contains(testEntry5));
		
	}

	@Test
	public void extractLogByUserTest() {
		Log.extractLog(testUser2);
		
		// Ensure that this query returns 2 log entries, and
		// that they are both for testUser2
		assertTrue(Log.getResult().get(0).equals(testEntry2));
		assertTrue(Log.getResult().get(1).equals(testEntry6));
		assertFalse(Log.getResult().contains(testEntry1));
		assertEquals(2, Log.getResult().size());
		
	}

	@Test
	public void extractLogByCheckedOutToolTest() {
		Log.extractLogCheckedOutTool(testTool3);
		
		// log entry 3 is the only one with testTool3
		// as a checked-out tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResult().contains(testEntry3));
		assertEquals(1, Log.getResult().size());
		
		Log.extractLogCheckedOutTool(testTool5);
		
		// log entry 6 is the only one with testTool5
		// as a checked-out tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResult().contains(testEntry6));
		assertEquals(1, Log.getResult().size());
		
		Log.extractLogCheckedOutTool(testTool6);
		
		// No one checked out testTool6, so this query should
		// return no results (An empty List)
		assertEquals(0, Log.getResult().size());
	}

	@Test public void extractLogByReturnedTool() {
		Log.extractLogReturnedTool(testTool4);
		
		// log entry 4 is the only one with testTool4
		// as a returned tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResult().contains(testEntry4));
		assertEquals(1, Log.getResult().size());
		
		Log.extractLogReturnedTool(testTool5);
		
		// log entry 4 is the only one with testTool5
		// as a returned tool, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResult().contains(testEntry4));
		assertEquals(1, Log.getResult().size());
		
		Log.extractLogReturnedTool(testTool6);
		
		// No one returned testTool6, so this query should
		// return no results (An empty List)
		assertEquals(0, Log.getResult().size());
		
	}

	@Test
	public void extractLogByMachineTest() {
		Log.extractLog(testMachine);
		
		// log entry 5 is the only one with testMachine
		// as a machine they used, so it is the only one
		// that should be returned by this query.
		assertTrue(Log.getResult().contains(testEntry5));
		assertEquals(1, Log.getResult().size());
		
	}
	
}
