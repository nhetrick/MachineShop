package main;
import java.util.ArrayList;

import GUI.Driver;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class User {
	private ArrayList<Machine> certifiedMachines;
	private ArrayList<Tool>	toolsCheckedOut;
	private String CWID;
	private String firstName;
	private String lastName;
	private LogEntry currentEntry;
	private boolean isLocked;
	protected boolean isAdmin = false;
	
	
	public User(String firstName, String lastName, String CWID) {
		// needs to be extracted from data base
		certifiedMachines = new ArrayList<Machine>();
		toolsCheckedOut = new ArrayList<Tool>();
		this.CWID = CWID;
		this.firstName = firstName;
		this.lastName = lastName;
		isLocked = false;
	}
	
	public void checkoutTool(Tool tool) {
		tool.checkoutTool();	
		toolsCheckedOut.add(tool);
		
		DBCollection usersCollection = Driver.getAccessTracker().getDatabase().getCollection("Users");
		DBCursor cursor = usersCollection.find(new BasicDBObject("CWID", CWID));
		if (cursor.hasNext()) {
			DBObject result = cursor.next();
			BasicDBList checkedoutTools = new BasicDBList();
			for (Tool t:toolsCheckedOut) {
				checkedoutTools.add(new BasicDBObject("upc", t.getUPC()));
			}
			result.put("checkedOutTools", checkedoutTools);

			usersCollection.update(new BasicDBObject("CWID", CWID), result);
		}
	}
	
	public void returnTools(ArrayList<Tool> tools) {
		
		for (Tool tool : tools){
			tool.returnTool();
			toolsCheckedOut.remove(tool);
			
			DBCollection usersCollection = Driver.getAccessTracker().getDatabase().getCollection("Users");
			DBCursor cursor = usersCollection.find(new BasicDBObject("CWID", CWID));
			if (cursor.hasNext()) {
				DBObject result = cursor.next();
				BasicDBList checkedoutTools = new BasicDBList();
				for (Tool t:toolsCheckedOut) {
					checkedoutTools.remove(new BasicDBObject("upc", t.getUPC()));
				}
				result.put("checkedOutTools", checkedoutTools);
	
				usersCollection.update(new BasicDBObject("CWID", CWID), result);
			}
		}
	}
	
	public void useMachine(Machine m) {
		m.use();
	}
	
	public void stopUsingMachine(Machine m) {
		m.stopUsing();
	}

	public String getCWID() {
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
