package main;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

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
	private static Date lastUpdate;
	private static DB database;
	
	private static int numEntries;
	
	public static void setup() {
		results = new ArrayList<LogEntry>();
		lastUpdate = new Date();
		// set numEntries to the number of log entries in
		// the database.
		database = Driver.getAccessTracker().getDatabase();
		numEntries = (int) database.getCollection("LogEntries").count();
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
	
	public static void extractLog(Date startDate, Date endDate) {
		results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		BasicDBObject dateRange = new BasicDBObject ("$gte",startDate);
		dateRange.put("$lte", endDate);

		BasicDBObject query = new BasicDBObject("timeIn", dateRange);
		DBCursor cursor = logEntries.find(query);
		retrieveEntryData(cursor);
	}
	
	public static void extractLog(User user) {
		results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("userCWID", user.getCWID()));
		retrieveEntryData(cursor);
	}
	
	public static void extractLogCheckedOutTool(Tool checkedOutTool) {
		results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("toolsCheckedOut", new BasicDBObject("upc", checkedOutTool.getUPC())));
		retrieveEntryData(cursor);
	}
	
	public static void extractLogReturnedTool(Tool returnedTool) {
		results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("toolsReturned", new BasicDBObject("upc", returnedTool.getUPC())));
		retrieveEntryData(cursor);
	}
	
	public static void extractLog(Machine machine) {
		results.clear();
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("machinesUsed", new BasicDBObject("id", machine.getID())));
		retrieveEntryData(cursor);
	}
	
	public static void retrieveEntryData(DBCursor cursor) {
		while(cursor.hasNext()) {
			//next log entry
			DBObject result = cursor.next();
			
			//get ID
			int id = (int) result.get("ID");
			
			//get CWID
			User u = Driver.getAccessTracker().loadUser((int) result.get("userCWID"));
			
			//get timeIn
			Date timeIn = (Date) result.get("timeIn");
			
			//get machinesUsed
			ArrayList<Machine> machinesUsed = new ArrayList<Machine>();
			DBCollection machinesColl = database.getCollection("Machines");
			ArrayList<BasicDBObject> machines = (ArrayList<BasicDBObject>) result.get("machinesUsed");
			if (!(machines == null)) {
				for(BasicDBObject embedded : machines){ 
					String i = (String)embedded.get("id"); 
					DBCursor machine = machinesColl.find(new BasicDBObject("ID", i));
					if (machine.hasNext()) {
						machinesUsed.add(new Machine((String) machine.next().get("name"), i));
					}
				} 
			}
			
			//get toolsCheckedOut			
			ArrayList<Tool> toolsCheckedOut = new ArrayList<Tool>();
			DBCollection toolsColl = database.getCollection("Tools");
			ArrayList<BasicDBObject> toolsOut = (ArrayList<BasicDBObject>) result.get("toolsCheckedOut");
			if (!(toolsOut == null)) {
				for(BasicDBObject embedded : toolsOut){ 
					String upc = (String) embedded.get("upc"); 
					DBCursor tool = toolsColl.find(new BasicDBObject("upc", upc));
					if (tool.hasNext()) {
						toolsCheckedOut.add(new Tool((String) tool.next().get("name"), upc));
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
					DBCursor tool = toolsColl.find(new BasicDBObject("upc", upc));
					if (tool.hasNext()) {
						toolsReturned.add(new Tool((String) tool.next().get("name"), upc));
					}
				} 
			}
			
			LogEntry entry = new LogEntry(id, u, machinesUsed, toolsCheckedOut, timeOut, timeIn, toolsReturned);
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
	
	/******************************************* GETTERS AND SETTERS ******************************************/
	

	public static ArrayList<LogEntry> getResults() {
		return results;
	}
	
	public static int getNumEntries() {
		return numEntries;
	}
	
}
