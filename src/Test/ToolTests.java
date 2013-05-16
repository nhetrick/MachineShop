package test;

import static org.junit.Assert.*;
import main.*;
import org.junit.Test;

public class ToolTests {
	
	@Test
	public void checkoutToolTest() {
		Tool testTool = new Tool("HITCOO", 15);
		testTool.checkoutTool();
		
		assertTrue(testTool.isCheckedOut());
	}
	
	@Test
	public void returnToolTest() {
		Tool testTool = new Tool("HITCOO", 15);
		testTool.returnTool();
		
		assertFalse(testTool.isCheckedOut());
	}

}
