package main;
import java.util.ArrayList;


public class AccessTracker implements Blastercard {
	private ArrayList<Machine> machines;
	private ArrayList<Tool>	tools;
	private ArrayList<Tool> availableTools;
	private ArrayList<User> currentUsers;
	private InputReader inputReader;
	private Log log;
	
	public AccessTracker() {
		currentUsers = new ArrayList<User>();
		inputReader = new InputReader();
		machines = new ArrayList<Machine>();
		tools = new ArrayList<Tool>();
		availableTools = new ArrayList<Tool>();
	}
	
	public boolean userExistsInDataBase(int CWID) {
		return false;
	}
	
	// Loads an existing user from the database into RAM
	// The user is found by querying by CWID
	public User loadUser(int CWID) {
		return new User("", "", 0);
	}
	
	
	// Creates a new user. Should be called by processLogIn()
	public void createUser(String firstName, String lastName, int CWID) {
		
	}
	
	// Takes a CWID and attempts to load that user from the
	// database and add them to the list of current users.
	// If the CWID doesn't exist, a new user is created, added to the list
	// of current users, and persisted to the database.
	// Also adds data to the log for this user
	public void processLogIn(int CWID) {
		
	}
	
	public void displayUserMachines(int CWID) {
		
	}
	
	// Removes the user with this CWID from the list
	// of current users.
	// Also finishes the log entry for this user.
	public void processLogOut(int CWID) {
		
	}
	
	// super bad. myLife.close()
	public void updateTools() {
		availableTools.clear();
		for (Tool t: tools) {
			if (!t.isCheckedOut())
				availableTools.add(t);
		}
	}

	@Override
	public boolean checkLegitimacy(int CWID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String loadName(int CWID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User getUser(int CWID) {
		// cheating right now
		// need to fix later
		User returnUser = new User("", "", CWID);
		return returnUser;
	}
	
	public ArrayList<User> getCurrentUsers() {
		return currentUsers;
	}

	public Log getLog() {
		return log;
	}

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public ArrayList<Tool> getTools() {
		return tools;
	}

	public ArrayList<Tool> getAvailableTools() {
		return availableTools;
	}
	
}

