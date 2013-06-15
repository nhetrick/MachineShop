package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Pattern;

import GUI.Driver;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

// This class is a static class that only accesses the log entries
// in the database and puts them in the "result" ArrayList for access
// by other classes. It also persists new log entries to the database.

public class Log {
	private static ArrayList<LogEntry> results;
	private static DB database;
	
	private static int numEntries;
	
	public static void setup() {
		results = new ArrayList<LogEntry>();
		new Date();
		// set numEntries to the number of log entries in
		// the database.
		database = Driver.getAccessTracker().getDatabase();
		numEntries = (int) database.getCollection("LogEntries").count();
	}
	
	private static boolean containsIgnoreCase(String findIn, String toFind) {
		try {
			boolean found = Pattern.compile(toFind, Pattern.CASE_INSENSITIVE).matcher(findIn).find();
			return found;
		}catch (Exception e){
			return false;
		}
	}
	
	public static void printLog() {
		System.out.println();
		for (LogEntry entry:results) {
			System.out.println();
			entry.print();
		}
	}
	
	public static void printLogTable() {
		System.out.println();
		System.out.format("%5s%20s%30s%30s%30s%30s%30s", "ID", "User", "Time In", "Time Out", "Machines Used", "Tools Checked Out", "Tools Returned");
		for (LogEntry entry:results) {
			System.out.println();
			entry.printTable();
		}
	}
	
	public static void extractLog(Date startDate, Date endDate, boolean clear) {
		if (clear)
			results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		BasicDBObject dateRange = new BasicDBObject ("$gte",startDate);
		dateRange.put("$lte", endDate);
		BasicDBObject query = new BasicDBObject("timeIn", dateRange);
		DBCursor cursor = logEntries.find(query);
		retrieveEntryData(cursor);
	}
	
	public static void extractResultsByTool(String name) {
		ArrayList<LogEntry> newResults = new ArrayList<LogEntry>();
		for (LogEntry entry : results) {
			for (Tool t : entry.getToolsCheckedOut()) {
				if (t.getName().contains(name)) {
					newResults.add(entry);
				}
			}
			for (Tool t : entry.getToolsReturned()) {
				if (containsIgnoreCase(t.getName(), name)) {
					newResults.add(entry);
				}
			}
		}
		results = newResults;
	}
	
	public static void extractResultsByMachine(String name) {
		ArrayList<LogEntry> newResults = new ArrayList<LogEntry>();
		for (LogEntry entry : results) {
			for (Machine m : entry.getMachinesUsed()) {
				if (containsIgnoreCase(m.getName(), name)) {
					newResults.add(entry);
				}
			}
		}
		results = newResults;
	}
	
	public static void extractResultsByUser(String cwid) {
		ArrayList<LogEntry> newResults = new ArrayList<LogEntry>();
		for (LogEntry entry : results) {
			if (containsIgnoreCase(entry.getCwid(), cwid)) {
				newResults.add(entry);
			}
		}
		results = newResults;
	}
	
	public static void extractLog(User user, boolean clear) {
		if (clear)
			results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("userCWID", user.getCWID()));
		retrieveEntryData(cursor);
	}
	
	public static void extractLogCheckedOutTool(Tool checkedOutTool, boolean clear) {
		if (clear)
			results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("toolsCheckedOut", new BasicDBObject("upc", checkedOutTool.getUPC())));
		retrieveEntryData(cursor);
	}
	
	public static void extractLogReturnedTool(Tool returnedTool, boolean clear) {
		if (clear)
			results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("toolsReturned", new BasicDBObject("upc", returnedTool.getUPC())));
		retrieveEntryData(cursor);
	}
	
	public static void extractLog(Machine machine, boolean clear) {
		if (clear)
			results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("machinesUsed", new BasicDBObject("id", machine.getID())));
		retrieveEntryData(cursor);
	}
	
	public static void extractLogByTool(String toolName, boolean clear) {
		if (clear)
			results.clear();
		ArrayList<DBObject> result = Driver.getAccessTracker().searchDatabase("Tools", "name", toolName);
		for (DBObject obj : result) {
			extractLogCheckedOutTool(Driver.getAccessTracker().getToolByUPC((String) obj.get("upc")), false);
			extractLogReturnedTool(Driver.getAccessTracker().getToolByUPC((String) obj.get("upc")), false);
		}
		Collections.sort(results, new LogEntryIDComparator());
		cleanResults();
	}
	
	public static void extractLogByMachine(String machineName, boolean clear) {
		if (clear)
			results.clear();
		ArrayList<DBObject> result = Driver.getAccessTracker().searchDatabase("Machines", "name", machineName);
		for (DBObject obj : result) {
			extractLog(Driver.getAccessTracker().getMachineByID((String) obj.get("ID")), false);
		}
		Collections.sort(results, new LogEntryIDComparator());
		cleanResults();
	}
	
	public static void retrieveEntryData(DBCursor cursor) {
		while(cursor.hasNext()) {
			//next log entry
			DBObject result = cursor.next();
			
			//get ID
			int id = (int) result.get("ID");
			
			//get CWID
			String cwid = (String) result.get("userCWID");
			
			//get timeIn
			Date timeIn = (Date) result.get("timeIn");
			
			//get department
			String department = (String) result.get("department");
			
			//get machinesUsed
			ArrayList<Machine> machinesUsed = new ArrayList<Machine>();
			ArrayList<BasicDBObject> machines = (ArrayList<BasicDBObject>) result.get("machinesUsed");
			if (!(machines == null)) {
				for(BasicDBObject embedded : machines){ 
					String i = (String)embedded.get("id"); 
					Machine machine = Driver.getAccessTracker().getMachineByID(i);
					if (machine != null) {
						machinesUsed.add(new Machine((String) machine.getName(), i));
					}
				} 
			}
			
			//get toolsCheckedOut			
			ArrayList<Tool> toolsCheckedOut = new ArrayList<Tool>();
			ArrayList<BasicDBObject> toolsOut = (ArrayList<BasicDBObject>) result.get("toolsCheckedOut");
			if (!(toolsOut == null)) {
				for(BasicDBObject embedded : toolsOut){ 
					String upc = (String) embedded.get("upc"); 
					Tool tool = Driver.getAccessTracker().getToolByUPC(upc);
					if (tool != null) {
						toolsCheckedOut.add(new Tool((String) tool.getName(), upc));
					}
				} 
			}
			
			//get timeOut
			Date timeOut = (Date) result.get("timeOut");
			
			//get toolsReturned
			ArrayList<Tool> toolsReturned = new ArrayList<Tool>();
			ArrayList<BasicDBObject> toolsBack = (ArrayList<BasicDBObject>) result.get("toolsReturned");
			if (!(toolsBack == null)) {
				for(BasicDBObject embedded : toolsBack){ 
					String upc = (String) embedded.get("upc"); 
					Tool tool = Driver.getAccessTracker().getToolByUPC(upc);
					if (tool != null) {
						toolsReturned.add(new Tool((String) tool.getName(), upc));
					}
				} 
			}
			
			LogEntry entry = new LogEntry(id, cwid, department, machinesUsed, toolsCheckedOut, timeOut, timeIn, toolsReturned);
			results.add(entry);
		}
	}
	
	public static void startEntry(User user) {
		LogEntry entry = new LogEntry();
		entry.startEntry(user);
	}
	
	public static void finishEntry(LogEntry entry) {
		entry.finishEntry();
	}
	
	public static void clearResults() {
		results.clear();
	}

	public static void incrementNumEntries() {
		numEntries++;
	}
	
	public static void deleteEntry(int id) {
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor result = logEntries.find(new BasicDBObject("ID", id));
		if (!(result == null)) {
			logEntries.remove(result.next());
		}
	}
	
	public static class LogEntryIDComparator implements Comparator<LogEntry> {
		@Override
	    public int compare(LogEntry entry1, LogEntry entry2) {
	        return entry1.getID() - entry2.getID();
	    }
	}
	
	public static void cleanResults() {
		for (int i = 0; i < results.size() - 1; i++) {
			if (results.get(i).getID() == results.get(i+1).getID()) {
				results.remove(i+1);
				i--;
			}
		}
	}
	
	/******************************************* GETTERS AND SETTERS ******************************************/
	

	public static ArrayList<LogEntry> getResults() {
		return results;
	}
	
	public static int getNumEntries() {
		return numEntries;
	}
	
}
