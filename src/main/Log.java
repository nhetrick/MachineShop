package main;
import java.util.ArrayList;
import java.util.Date;

import com.mongodb.DB;

// This class is a static class that only accesses the log entries
// in the database and puts them in the "result" ArrayList for access
// by other classes. It also persists new log entries to the database.

public class Log {
	private static ArrayList<LogEntry> result;
	private static Date lastUpdate;
	private static DB database;
	
	private static int numEntries;
	
	public static void setup() {
		result = new ArrayList<LogEntry>();
		lastUpdate = new Date();
		// set numEntries to the number of log entries in
		// the database.
		database = AccessTracker.getDatabase();
		numEntries = (int) database.getCollection("LogEntries").count();
	}
	
	public static void printLog() {
		
	}
	
	public static void extractLog(Date startDate, Date endDate) {
		
	}
	
	public static void extractLog(User user) {
		
	}
	
	public static void extractLogCheckedOutTool(Tool checkedOutTool) {
		
	}
	
	public static void extractLogReturnedTool(Tool returnedTool) {
		
	}
	
	public static void extractLog(Machine machine) {
		
	}
	
	public static void startEntry(User user) {
		LogEntry entry = new LogEntry(database);
		entry.startEntry(user);
	}
	
	public static void finishEntry(LogEntry entry) {
		entry.finishEntry();
	}

	public static void incrementNumEntries() {
		numEntries++;
	}
	
	/******************************************* GETTERS AND SETTERS ******************************************/
	

	public static ArrayList<LogEntry> getResult() {
		return result;
	}
	
	public static int getNumEntries() {
		return numEntries;
	}
	
}
