package main;
import java.util.ArrayList;
import java.util.Date;


public class Log {
	private static ArrayList<LogEntry> entries;
	private static ArrayList<LogEntry> result;
	private static Date lastUpdate;
	
	private static int numEntries = 0;
	
	public Log() {
		entries = new ArrayList<LogEntry>();
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
	
	public static void addEntry(LogEntry entry) {
		entries.add(entry);
	}
	
	public static void finishEntry(LogEntry entry) {
		// The entry being updated just needs a time out. So we get
		// the time out from "entry" and update the corresponding entry
		// in the log with the correct time out.
		entries.get(entry.getID()).setTimeOut(entry.getTimeOut());
	}

	public static ArrayList<LogEntry> getEntries() {
		return entries;
	}

	public static ArrayList<LogEntry> getResult() {
		return result;
	}
	
	public static int getNumEntries() {
		return numEntries;
	}
	
	public static void incrementNumEntries() {
		numEntries++;
	}
	
}
