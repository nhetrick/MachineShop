package main;
import java.util.ArrayList;

public class User {
	private ArrayList<Machine> certifiedMachines;
	private ArrayList<Tool>	toolsCheckedOut;
	private int CWID;
	private String firstName;
	private String lastName;
	private LogEntry currentEntry;
	private boolean isLocked;
	protected boolean isAdmin = false;
	
	
	public User(String firstName, String lastName, int CWID) {
		// needs to be extracted from data base
		certifiedMachines = new ArrayList<Machine>();
		toolsCheckedOut = new ArrayList<Tool>();
		this.CWID = CWID;
		this.firstName = firstName;
		this.lastName = lastName;
		isLocked = false;
	}
	
	public void checkoutTool(Tool t) {
		t.checkoutTool();	
		toolsCheckedOut.add(t);
	}
	
	public void returnTool(Tool t) {
		t.returnTool();
		toolsCheckedOut.remove(t);
	}
	
	public void useMachine(Machine m) {
		m.use();
	}
	
	public void stopUsingMachine(Machine m) {
		m.stopUsing();
	}

	public int getCWID() {
		return CWID;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public void setLockedStatus(boolean lock) {
		isLocked = lock;
	}

	public ArrayList<Machine> getCertifiedMachines() {
		return certifiedMachines;
	}
	
	public void loadCertifiedMachines(ArrayList<Machine> machines) {
		certifiedMachines = machines;
	}
	
	public ArrayList<Tool> getToolsCheckedOut() {
		return toolsCheckedOut;
	}
	
	public void loadCheckedOutTools(ArrayList<Tool> tools) {
		toolsCheckedOut = tools;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User))
			return false;
		
		User obj = (User) o;
		return (this.CWID == obj.getCWID());
	}
	
	public String toString() {
		return firstName + " " + lastName;
	}
	
	public LogEntry getCurrentEntry() {
		return currentEntry;
	}

	public void setCurrentEntry(LogEntry currentEntry) {
		this.currentEntry = currentEntry;
	}

}
