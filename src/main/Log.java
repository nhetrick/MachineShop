package main;
import java.util.ArrayList;


public class Log {
	private ArrayList<LogEntry> entries;
	private ArrayList<LogEntry> result;
	
	public Log() {
		entries = new ArrayList<LogEntry>();
	}
	
	public void printLog() {
		
	}
	
	public void extractLog(String startDate, String endDate) {
		
	}
	
	public void extractLog(User user) {
		
	}
	
	public void extractLog(Tool tool) {
		
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
