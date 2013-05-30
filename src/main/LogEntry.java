package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import GUI.Driver;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class LogEntry {
	private int ID;
	private User user;
	private ArrayList<Machine> machinesUsed;
	private ArrayList<Tool> toolsCheckedOut;
	private Date timeOut;
	private Date timeIn;
	private ArrayList<Tool> toolsReturned;
	private Calendar calendar;
	private DB database;
	
	public LogEntry() {
		calendar = Calendar.getInstance();
		timeOut = null;
		database = Driver.getAccessTracker().getDatabase();
	}
	
	
	
	public LogEntry(int iD, User user, ArrayList<Machine> machinesUsed,
			ArrayList<Tool> toolsCheckedOut, Date timeOut, Date timeIn,
			ArrayList<Tool> toolsReturned) {
		super();
		ID = iD;
		this.user = user;
		this.machinesUsed = machinesUsed;
		this.toolsCheckedOut = toolsCheckedOut;
		this.timeOut = timeOut;
		this.timeIn = timeIn;
		this.toolsReturned = toolsReturned;
		calendar = Calendar.getInstance();
		database = Driver.getAccessTracker().getDatabase();
	}



	// FOR TESTING PURPOSES ONLY
	public void startEntry(User user, ArrayList<Machine> machinesUsed, ArrayList<Tool> toolsCheckedOut, ArrayList<Tool> toolsReturned) {
		// AUTO-GENERATE ID
		this.ID = Log.getNumEntries();
		this.timeIn = calendar.getTime();
		this.user = user;
		this.machinesUsed = machinesUsed;
		this.toolsCheckedOut = toolsCheckedOut;
		this.toolsReturned = toolsReturned;
		Log.incrementNumEntries();
		
		user.setCurrentEntry(this);
		
		BasicDBObject logEntry = new BasicDBObject();
		logEntry.put("ID", ID);
		logEntry.put("timeIn", timeIn);
		logEntry.put("userCWID", user.getCWID());
		
		DBCollection logEntries = database.getCollection("LogEntries");
		logEntries.insert(logEntry);
		
		addMachinesUsed(machinesUsed);
		addToolsCheckedOut(toolsCheckedOut);
		addToolsReturned(toolsReturned);
		
	}
	
	public void startEntry(User user) {
		// AUTO-GENERATE ID
		this.ID = Log.getNumEntries();
		this.timeIn = calendar.getTime();
		this.user = user;
		this.machinesUsed = new ArrayList<Machine>();
		this.toolsCheckedOut = new ArrayList<Tool>();
		this.toolsReturned = new ArrayList<Tool>();
		Log.incrementNumEntries();
		
		this.user.setCurrentEntry(this);
		
		BasicDBObject logEntry = new BasicDBObject();
		logEntry.put("ID", ID);
		logEntry.put("timeIn", timeIn);
		logEntry.put("userCWID", user.getCWID());
		
		DBCollection logEntries = database.getCollection("LogEntries"); 
		logEntries.insert(logEntry);
	}
	
	public void addMachinesUsed(ArrayList<Machine> used) {
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("ID", ID));
		DBObject result = cursor.next();
		
		BasicDBList machines = new BasicDBList();
		for(Machine m : used) {
			machines.add(new BasicDBObject("id", m.getID()));
		}
		result.put("machinesUsed", machines);
		
		logEntries.update(new BasicDBObject("ID", ID), result);
	}
	
	public void addToolsCheckedOut(ArrayList<Tool> checkedOut) {
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("ID", ID));
		DBObject result = cursor.next();
		
		BasicDBList tools = new BasicDBList();
		for(Tool t : checkedOut) {
			tools.add(new BasicDBObject("upc", t.getUPC()));
		}
		result.put("toolsCheckedOut", tools);
		
		logEntries.update(new BasicDBObject("ID", ID), result);
	}
	
	public void addToolsReturned( ArrayList<Tool> returned) {
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("ID", ID));
		DBObject result = cursor.next();
		
		BasicDBList tools = new BasicDBList();
		for(Tool t : returned) {
			tools.add(new BasicDBObject("upc", t.getUPC()));
		}
		result.put("toolsReturned", tools);
		
		logEntries.update(new BasicDBObject("ID", ID), result);
	}
	
	public void finishEntry() {
		this.timeOut = calendar.getTime();
		
		DBCollection logEntries = database.getCollection("LogEntries");
		DBCursor cursor = logEntries.find(new BasicDBObject("ID", ID));
		DBObject result = cursor.next();
		
		result.put("timeOut", timeOut);
		
		logEntries.update(new BasicDBObject("ID", ID), result);
	}
	
	public Date getTimeIn() {
		return timeIn;
	}
	
	public Date getTimeOut() {
		return timeOut;
	}
	
	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LogEntry))
			return false;
		LogEntry obj = (LogEntry) o;
		return (this.ID == obj.getID());
	}

	public int getID() {
		return ID;
	}
	
	public void print() {
		System.out.println("Log Entry: " + ID);
		System.out.println("User: " + user);
		System.out.println("Time In: " + timeIn);
		System.out.println("Time Out: " + timeOut);
		System.out.println("Machines Used: " + machinesUsed);
		System.out.println("Tools Checked Out: " + toolsCheckedOut);
		System.out.println("Tools Returned: " + toolsReturned);
	}
	
	public void printTable() {
		System.out.format("%5d%20s%30s%30s%30s%30s%30s", ID, user, timeIn, timeOut, machinesUsed, toolsCheckedOut, toolsReturned);
	}
}
