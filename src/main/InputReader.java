package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.io.Console;
import java.util.Scanner;

public class InputReader implements KeyListener {
	
	private String start = ";984000017";
	private String error = "E?";
	private String CWID = "";
	private String input;
	
	public InputReader() {
		input = "";
	}
	
	public InputReader(String input) {
		this.input = input;
	}
	
	public void strip() throws InputReaderException {
		if (input.startsWith(start)) {
			CWID = input.substring(10, 18);
			System.out.println("Your CWID is " + CWID);
		}
		else if (input.contains(error))
			throw new InputReaderException("An error has occured. Please try again.");
		else if (input.length() == 8) {
			CWID = input;
			System.out.println("Your CWID is " + input);
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
		if ( e.getKeyChar() != '\n' ) {
			input += e.getKeyChar();
			System.out.println(e.getKeyChar());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
