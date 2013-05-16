package main;
import java.util.ArrayList;


public class AccessTracker implements Blastercard {
	private ArrayList<Machine> machines;
	private ArrayList<Tool>	tools;
	private ArrayList<User> currentUsers;
	private InputReader inputReader;
	private Log log;
	
	public AccessTracker() {
		currentUsers = new ArrayList<User>();
		inputReader = new InputReader();
	}
	
	public boolean userExistsInDataBase(int CWID) {
		return false;
	}
	
	public void loadUser(int CWID) {
		
	}

	public void createUser(String firstName, String lastName, int CWID) {
		
	}
	
	public void processLogIn(int CWID) {
		
	}
	
	public void displayUserMachines(int CWID) {
		
	}
	
	public void addLogEntry(int CWID) {
		
	}
	
	public void addLogEntry(int CWID, String ID) {
		
	}
	
	public void processLogOut(int CWID) {
		
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
	
}

