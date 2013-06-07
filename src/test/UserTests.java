package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.*;
import org.junit.Before;
import org.junit.Test;

public class UserTests {
	
	AccessTracker tracker;
	
	@Before
	public void setup() {
		tracker = new AccessTracker();
	}
	
	@Test
	public void checkoutAndReturnToolTest() {
		User testUser = new User("", "", "12345678", "missongno@po.ke", "PKMN");
		Tool testTool = new Tool("HITCOO", "15");
		ArrayList<Tool> tools = new ArrayList<Tool>();
		tools.add(testTool);
		
		testUser.checkoutTool(testTool);
		
		tracker.loadTools();
		
		// Ensures that once a tool has been checked out by a user, the database
		// and the system recognize that it has actually been checked out.
		assertTrue(testTool.isCheckedOut());
		
		// Ensures that the user actually has the tool
		assertTrue(testUser.getToolsCheckedOut().contains(testTool));
		
		testUser.returnTools(tools);
		
		tracker.loadTools();
		
		// Ensures that once a tool has been returned, the database
		// and the system recognize that it has actually been returned.
		assertFalse(testTool.isCheckedOut());
		
		// Ensures that the user no longer has the tool		
		assertFalse(testUser.getToolsCheckedOut().contains(testTool));
	}
	
}
