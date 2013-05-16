package Test;

import static org.junit.Assert.*;

import main.InputReader;

import org.junit.Test;

public class InputReaderTests {
	
	@Test
	public void stripTest(){
		
		InputReader ir = new InputReader();
		String blasterCard = ";9840000171053947700?";
		ir.strip(blasterCard);
		assertEquals("length was different than expected", 8, ir.getCWID().length());
		
		ir = new InputReader();
		String invalidCard = "%6228=4968951479898261609?;6228=0000000000043611?";
		ir.strip(invalidCard);
		assertEquals("expected invalid CWID","", ir.getCWID());
		
		ir = new InputReader();
		String errorCard = "E?";
		ir.strip(errorCard);
		assertEquals("expected error CWID","", ir.getCWID());
	
	}

}
