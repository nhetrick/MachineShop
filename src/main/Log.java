package main;
import java.util.ArrayList;
import java.util.Date;


public class Log {
	private String ID;
	private ArrayList<LogEntry> entries;
	private ArrayList<LogEntry> result;
	private Date lastUpdate;
	
	public Log() {
		entries = new ArrayList<LogEntry>();
	}
	
	public void printLog() {
		
	}
	
	public void extractLog(Date startDate, Date endDate) {
		
	}
	
	public void extractLog(User user) {
		
	}
	
	public void extractLogCheckedOutTool(Tool tool) {
		
	}
	
	public void extractLogReturnedTool(Tool tool) {
		
	}
	
	public void extractLog(Machine machine) {
		
	}

	public ArrayList<LogEntry> getEntries() {
		return entries;
	}

	public ArrayList<LogEntry> getResult() {
		return result;
	}
	
}
