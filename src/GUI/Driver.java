package GUI;

import main.AccessTracker;

public class Driver {
	
	private static AccessTracker tracker;
	private static MainGUI mainGui;
	public static boolean isLogInScreen = false;
	
	public Driver() {
		tracker = new AccessTracker();
	}
	
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
}
