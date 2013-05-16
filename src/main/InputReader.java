package main;
//import java.io.Console;
import java.util.Scanner;

public class InputReader {
	private String start = ";984000017";
	private String error = "E?";
	private String CWID;

	public InputReader() {
		CWID = "";
	}

	public void strip(String input) throws InputReaderException {
		if (input.startsWith(start)) {
			CWID = input.substring(10, 18);
			System.out.println("Your CWID is " + CWID);
		}
		
		else if (input.contains(error))
			throw new InputReaderException("An error has occured. Please try again.");
		
		else if (input.length() == 8) {
			throw new InputReaderException("Your CWID is " + input);
		}
		
		else {
			throw new InputReaderException("The card is not a blastercard.");
		}
	}
	
////	public static void main(String[] args) {
//		InputReader reader = new InputReader();
//		Scanner scan = new Scanner(System.in);
//		while(true) {
//			System.out.println("Please scan your blaster card.");
//			String input = scan.nextLine();
//			reader.strip(input);
//		}
//	}
	
	public String getCWID(){
		return CWID;
	}

}
