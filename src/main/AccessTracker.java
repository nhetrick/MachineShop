package main;
import java.util.ArrayList;


public class AccessTracker {
	
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
		// Do the initialization stuff for the log
		Log.setup();
	}
	
	public boolean userExistsInDataBase(int CWID) {
		return false;
	}
	
	// Loads an existing user from the database into RAM
	// The user is found by querying by CWID
	public User loadUser(int CWID) {
		return new User("", "", 0);
	}
	
	// Loads all the tools from the database into RAM
	public void loadTools() {
		// tools = db.get(tools);
	}
	
	// Loads all the machines from the database into RAM
	public void loadMachines() {
		// tools = db.get(machines);
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
		// IF the user with this CWID is locked (boolean isLocked)
		// THEN display some error message, and make a note somewhere
		// (log this attempt for admin to view later)
	
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
	
	// Check that the CWID exists in the blastercard database
	public boolean checkLegitimacy(int CWID) {
		return false;
	}

	
	/********************************** GETTERS AND SETTERS *******************************************/
	
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

