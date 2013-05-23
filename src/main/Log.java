package main;
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
	private static ArrayList<LogEntry> result;
	private static Date lastUpdate;
	private static DB database;
	
	private static int numEntries;
	
	public static void setup() {
		result = new ArrayList<LogEntry>();
		lastUpdate = new Date();
		// set numEntries to the number of log entries in
		// the database.
		database = Driver.getAccessTracker().getDatabase();
		numEntries = (int) database.getCollection("LogEntries").count();
	}
	
	public static void printLog() {
		
	}
	
	public static void extractLog(Date startDate, Date endDate) {
		
	}
	
	public static void extractLog(User user) {
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("userCWID", user.getCWID()));
		while(cursor.hasNext()) {
			DBObject result = cursor.next();
			int id = (int) result.get("ID");
			User u = Driver.getAccessTracker().loadUser((int) result.get("CWID"));
			
			ArrayList<Machine> machinesUsed = new ArrayList<Machine>();
			
			DBCollection machinesColl = database.getCollection("Machines");
			ArrayList<BasicDBObject> machines = (ArrayList<BasicDBObject>)result.get("machinesUsed");
			if (machines == null) {
				user.loadCertifiedMachines(new ArrayList<Machine>());
			} else {
				for(BasicDBObject embedded : machines){ 
					String i = (String)embedded.get("id"); 
					DBCursor machine = machinesColl.find(new BasicDBObject("ID", id));
					if (machine.hasNext()) {
						machinesUsed.add(new Machine((String) machine.next().get("name"), i));
					}
				} 
				user.loadCertifiedMachines(machinesUsed);
			}
			
			
//			ArrayList<Tool> toolsCheckedOut
//			Date timeOut
//			Date timeIn
//			ArrayList<Tool> toolsReturned
		}
	}
	
	public static void extractLogCheckedOutTool(Tool checkedOutTool) {
		
	}
	
	public static void extractLogReturnedTool(Tool returnedTool) {
		
	}
	
	public static void extractLog(Machine machine) {
		
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
	
	/******************************************* GETTERS AND SETTERS ******************************************/
	

	public static ArrayList<LogEntry> getResult() {
		return result;
	}
	
	public static int getNumEntries() {
		return numEntries;
	}
	
}
