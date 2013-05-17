package test;

import static org.junit.Assert.*;
import main.*;
import org.junit.Test;

public class UserTests {

	@Test
	public void checkoutToolTest() {
		User testUser = new User("", "", 12345678);
		Tool testTool = new Tool("HITCOO", 15);
		testUser.checkoutTool(testTool);
		
		assertTrue(testTool.isCheckedOut());
	}
	
	@Test
	public void returnToolTest() {
		User testUser = new User("", "", 12345678);
		Tool testTool = new Tool("HITCOO", 15);
		testUser.checkoutTool(testTool);
		testUser.returnTool(testTool);
		
		assertFalse(testTool.isCheckedOut());
	}

}
