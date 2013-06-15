package GUI;

import java.awt.Font;

import main.AccessTracker;

public class Driver {
	
	private static AccessTracker tracker = new AccessTracker();
	private static MainGUI mainGui;
	private static String password = "1234";
	public static boolean isLogInScreen = false;
	
	public static void main(String[] args) {
		tracker = new AccessTracker();
		mainGui = new MainGUI();
	}
	
	public static void exit() {
		System.exit(1);
	}

	public static MainGUI getMainGui() {
		return mainGui;
	}
	
	public static AccessTracker getAccessTracker() {
		return tracker;
	}
	
	public static String getPassword() {
		return password;
	}
	
}
