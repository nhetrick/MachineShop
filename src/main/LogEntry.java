package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class LogEntry {
	private String ID;
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
		ID = ""; // auto generate
	}
	
	public LogEntry(User user, String ID) {
		calendar = Calendar.getInstance();
		this.user = user;
		machinesUsed = new ArrayList<Machine>();
		toolsCheckedOut = new ArrayList<Tool>();
		timeOut = null;
		timeIn = calendar.getTime();
		toolsReturned = new ArrayList<Tool>();
		this.ID = ID;
	}
	
	// for test purpose
	public LogEntry(User user, ArrayList<Machine> machinesUsed, ArrayList<Tool> toolsCheckedOut, Date timeIn, Date timeOut, ArrayList<Tool> toolsReturned, String ID) {
		calendar = Calendar.getInstance();
		this.user = user;
		this.machinesUsed = machinesUsed;
		this.toolsCheckedOut = toolsCheckedOut;
		this.timeOut = timeOut;
		this.timeIn = timeIn;
		this.toolsReturned = toolsReturned;
		this.ID = ID;
	}
	
	public void persistEntry() {
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LogEntry))
			return false;
		LogEntry obj = (LogEntry) o;
		return (this.ID == obj.getID());
	}

	public String getID() {
		return ID;
	}
}
