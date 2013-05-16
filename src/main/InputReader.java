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

	public void strip(String input) {
		if (input.startsWith(start)) {
			CWID = input.substring(10, 18);
			System.out.println("Your CWID is " + CWID);
		}
		
		else if (input.contains(error))
			System.out.println("An error has occured. Please try again.");
		
		else if (input.length() == 8) {
			System.out.println("Your CWID is " + input);
		}
		
		else {
			System.out.println("The card is not a blastercard.");
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
