package main;
import java.util.ArrayList;


public class User {
	private ArrayList<Machine> certifiedMachines;
	private ArrayList<Tool>	toolsCheckedOut;
	private int CWID;
	private String firstName;
	private String lastName;
	private boolean isLocked;
	
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
		
	}
	
	public void returnTool(Tool t) {
		t.returnTool();
	}

	public int getCWID() {
		return CWID;
	}
	
	public boolean isLocked() {
		return isLocked;
	}

	public ArrayList<Machine> getCertifiedMachines() {
		return certifiedMachines;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User))
			return false;
		
		User obj = (User) o;
		return (this.CWID == obj.getCWID());
	}
}
