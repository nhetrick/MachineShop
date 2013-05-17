package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.BeforeClass;
import org.junit.Test;
import main.*;

public class LogTests {
	Log testLog;

	User testUser;
	User testUser2;

	LogEntry testEntry1;
	LogEntry testEntry2;
	LogEntry testEntry3;
	LogEntry testEntry4;
	LogEntry testEntry5;

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
	ArrayList<Tool> testTools4;

	Date dateIn;
	Date dateOut;

	@BeforeClass
	public void setup() {
		Log testLog = new Log();

		User testUser = new User("", "", 2);
		User testUser2 = new User("", "", 3);

		LogEntry testEntry1 = new LogEntry(testUser, "1");
		LogEntry testEntry2 = new LogEntry(testUser2, "2");
		LogEntry testEntry3 = new LogEntry(testUser, "3");
		LogEntry testEntry4 = new LogEntry(testUser, "4");
		LogEntry testEntry5 = new LogEntry(testUser, "5");

		Machine testMachine = new Machine("TPDD", "T-1");
		ArrayList<Machine> testMachines = new ArrayList<Machine>();
		testMachines.add(testMachine);

		Machine notAMachine = new Machine("This it not a machine", "WINE");
		ArrayList<Machine> notTestMachines = new ArrayList<Machine>();
		notTestMachines.add(notAMachine);

		Tool testTool1 = new Tool("HITCOO", 15);
		ArrayList<Tool> testTools1 = new ArrayList<Tool>();
		testTools1.add(testTool1);

		Tool testTool2 = new Tool("Crowbar", 1);
		ArrayList<Tool> testTools2 = new ArrayList<Tool>();
		testTools2.add(testTool2);
		
		Tool testTool3 = new Tool("Hammer", 15);
		ArrayList<Tool> testTools3 = new ArrayList<Tool>();
		testTools3.add(testTool3);

		Tool testTool4 = new Tool("Driver", 1);
		ArrayList<Tool> testTools4 = new ArrayList<Tool>();
		testTools4.add(testTool4);

		Date dateIn = new Date(2009, 9, 9);
		Date dateOut = new Date(2010, 9, 9);

		testLog.getEntries().add(new LogEntry(testUser,notTestMachines, testTools1, dateIn, dateIn, testTools2, "1"));
		testLog.getEntries().add(new LogEntry(testUser2,notTestMachines, testTools1, dateIn, dateOut, testTools2, "2"));
		testLog.getEntries().add(new LogEntry(testUser,notTestMachines, testTools3, dateIn, dateOut, testTools2, "3"));
		testLog.getEntries().add(new LogEntry(testUser,notTestMachines, testTools2, dateIn, dateOut, testTools4, "4"));
		testLog.getEntries().add(new LogEntry(testUser,testMachines, testTools2, dateIn, dateOut, testTools1, "5"));
	}

	@Test
	public void extractLogByDateTest() {
		testLog.extractLog(dateIn, dateIn);

		assertTrue(testLog.getResult().contains(testEntry1));
	}

	@Test
	public void extractLogByUserTest() {
		testLog.extractLog(testUser2);

		assertTrue(testLog.getResult().contains(testEntry2));
	}

	@Test
	public void extractLogByCheckedOutToolTest() {
		testLog.extractLogCheckedOutTool(testTool3);

		assertTrue(testLog.getResult().contains(testEntry3));
	}

	@Test public void extractLogByReturnedTool() {
		testLog.extractLogReturnedTool(testTool4);

		assertTrue(testLog.getResult().contains(testEntry4));
	}

	@Test
	public void extractLogByMachineTest() {
		testLog.extractLog(testMachine);

		assertTrue(testLog.getResult().contains(testEntry5));
	}

	@Test
	public void persistEntryTest() {
		testEntry1.persistEntry();
		testEntry2.persistEntry();
		testEntry3.persistEntry();
		testEntry4.persistEntry();
		testEntry5.persistEntry();
		
		assertTrue(testLog.getEntries().contains(testEntry1));
		assertTrue(testLog.getEntries().contains(testEntry2));
		assertTrue(testLog.getEntries().contains(testEntry3));
		assertTrue(testLog.getEntries().contains(testEntry4));
		assertTrue(testLog.getEntries().contains(testEntry5));
	}
}
