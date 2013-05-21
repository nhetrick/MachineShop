package main;
import java.util.ArrayList;
import java.util.Date;

// This class is a static class that only accesses the log entries
// in the database and puts them in the "result" ArrayList for access
// by other classes. It also persists new log entries to the database.

public class Log {
	private static ArrayList<LogEntry> result;
	private static Date lastUpdate;
	
	private static int numEntries;
	
	public static void setup() {
		result = new ArrayList<LogEntry>();
		lastUpdate = new Date();
		// set numEntries to the number of log entries in
		// the database.
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
	
	public static void finishEntry(LogEntry entry) {
		// The entry being updated just needs a time out. So we get
		// the time out from "entry" and update the corresponding entry
		// in the database with the correct time out.
		// Something like this
		// db.get(LogEntries).get(entry.getID()).setTimeOut(entry.getTimeOut());
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
