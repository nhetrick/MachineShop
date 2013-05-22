package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LogEntry {
	private int ID;
	private User user;
	private ArrayList<Machine> machinesUsed;
	private ArrayList<Tool> toolsCheckedOut;
	private Date timeOut;
	private Date timeIn;
	private ArrayList<Tool> toolsReturned;
	private Calendar calendar;
	
	public LogEntry() {
		calendar = Calendar.getInstance();
		timeOut = null;
	}
	
	public void startEntry(User user, ArrayList<Machine> machinesUsed, ArrayList<Tool> toolsCheckedOut, ArrayList<Tool> toolsReturned) {
		// AUTO-GENERATE ID
		this.ID = Log.getNumEntries();
		this.timeIn = calendar.getTime();
		this.user = user;
		this.machinesUsed = machinesUsed;
		this.toolsCheckedOut = toolsCheckedOut;
		this.toolsReturned = toolsReturned;
		Log.incrementNumEntries();
	}
	
	public void finishEntry() {
		this.timeOut = calendar.getTime();
		Log.finishEntry(this);
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
}
