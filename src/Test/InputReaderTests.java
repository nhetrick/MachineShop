package test;

import static org.junit.Assert.*;

import main.InputReader;
import main.InputReaderException;

import org.junit.Rule;
import org.junit.Test;

public class InputReaderTests {
	
	@Test
	public void stripTest(){
		
		// ensure CWID is stripped properly
		final int expectedlength = 8; 
		String lengthMsg = "length was different than expected";
		String cwidMsg = "CWID was different than expected";
		InputReader ir = new InputReader();
		String blasterCard = ";9840000171053947700?";
		String expectedCWID = "10539477";
		
		try {
			ir.strip(blasterCard);
		} catch (InputReaderException e) {
			fail("unexpected execption");
		}
		
		assertEquals(lengthMsg, expectedlength, ir.getCWID().length());
		assertEquals(cwidMsg, expectedCWID, ir.getCWID());
		
		// ensure handling of invalid card
		ir = new InputReader();
		String invalidCard = "%6228=4968951479898261609?;6228=0000000000043611?";
		try {
			ir.strip(invalidCard);
			fail("expected input reader execption");
		} catch (InputReaderException e) {
			assertEquals("expected invalid CWID","", ir.getCWID());
		}
				
		// ensure handling of error card
		ir = new InputReader();
		String errorCard = "E?";
		try {
			ir.strip(errorCard);
			fail("expected input reader execption");
		} catch (InputReaderException e) {
			assertEquals("expected error CWID","", ir.getCWID());
		}
	}
}
