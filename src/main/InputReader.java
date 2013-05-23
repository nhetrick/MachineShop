package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import GUI.MainGUI;

public class InputReader implements KeyListener {
	
	private String start = ";984000017";
	private String error = "E?";
	private String CWID = "";
	private String input;
	private MainGUI gui;
	private static int errorCount = 0;
	
	public InputReader() {
		input = "";
	}
	
	public InputReader(String input) {
		this.input = input;
	}
	
	public InputReader(MainGUI gui)	{
		input = "";
		this.gui = gui;
	}
	
	public void strip() throws InputReaderException {
		if (input.startsWith(start)) {
			CWID = input.substring(10, 18);
//			System.out.println("Your CWID is " + CWID);
		}
		else if (input.contains(error)) {
			++errorCount;
			throw new InputReaderException("Card read error. Please try again.");
		} else if ( input.length() < 10 ) {
			throw new InputReaderException("Error. Please swipe your blastercard.");
		} else if ( input.equals("machineshop")) {
			CWID = "22222222";
		}
		else
			throw new InputReaderException("The card is not a blastercard.");
	}
	
	public String getCWID(){
		return CWID;
	}
	
	public String getInput() {
		return input;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if ( e.getKeyChar() != '\n') {
			input += e.getKeyChar();
//			System.out.println(input);
		}
		
		else {
			gui.enterPressed();
			input = "";
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public static int getErrorCount() {
		return errorCount;
	}
	
	public static void resetErrorCount() {
		errorCount = 0;
	}

}
