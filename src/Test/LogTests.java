package test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import main.*;

public class LogTests {
	
	@Before
	public void setup() {
		Log testLog = new Log();
		User testUser = new User("", "", 2);
		Machine testMachine = new Machine("TPDD", "T-1");
		ArrayList<Machine> testMachines = new ArrayList<Machine>();
		testMachines.add(testMachine);
		Tool testTool1 = new Tool("HITCOO", 15);
		ArrayList<Tool> testTools1 = new ArrayList<Tool>();
		testTools1.add(testTool1);
		Tool testTool2 = new Tool("Crowbar", 1);
		ArrayList<Tool> testTools2 = new ArrayList<Tool>();
		testTools1.add(testTool2);
		Date dateIn = new Date(2009, 9, 9);
		Date dateOut = new Date(2010, 9, 9);
		
		LogEntry testEntry1 = new LogEntry(testUser, "1");
		LogEntry testEntry2 = new LogEntry(testUser, "2");
		LogEntry testEntry3 = new LogEntry(testUser, "3");
		LogEntry testEntry4 = new LogEntry(testUser, "4");
		LogEntry testEntry5 = new LogEntry(testUser, "5");
		
		testLog.getEntries().add(new LogEntry(testUser,testMachines, testTools1, dateIn, dateOut, testTools2, "1"));
	}
	
	@Test
	public void extractLogByDateTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void extractLogByUserTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void extractLogByToolTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void extractLogByMachineTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void persistEntryTest() {
		fail("Not yet implemented");
	}
}
