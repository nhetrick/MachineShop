package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class LogEntry {
	private User user;
	private ArrayList<Machine> machinesUsed;
	private ArrayList<Tool> toolsCheckedOut;
	private Date timeOut;
	private Date timeIn;
	private ArrayList<Tool> toolsReturned;
	private Calendar calendar;
	
	public LogEntry(User user) {
		calendar = Calendar.getInstance();
		this.user = user;
		machinesUsed = new ArrayList<Machine>();
		toolsCheckedOut = new ArrayList<Tool>();
		timeOut = null;
		timeIn = calendar.getTime();
		toolsReturned = new ArrayList<Tool>();
	}
	
	public void persistEntry() {
		
	}
}
