package GUI;
import java.awt.Font;
import java.io.IOException;

import main.AccessTracker;

public class Driver {
	
	private static AccessTracker tracker = new AccessTracker();
	private static MainGUI mainGui;
	private static Process dbServer;
	public static boolean isLogInScreen = false;
	
	public static void main(String[] args) throws IOException {
		//Runtime r = Runtime.getRuntime();
		//dbServer = r.exec("C:/mongodb/bin/mongod.exe --dbpath C:/data/bin");
		mainGui = new MainGUI();
	}
	
	public static void exit() {
		dbServer.destroy();
		System.exit(1);
	}

	public static MainGUI getMainGui() {
		return mainGui;
	}
	
	public static AccessTracker getAccessTracker() {
		return tracker;
	}
	
}
