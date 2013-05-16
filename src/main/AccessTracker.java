package main;
import java.util.ArrayList;


public class AccessTracker implements Blastercard {
	private ArrayList<User> currentUsers;
	private InputReader inputReader;
	
	public AccessTracker() {
		currentUsers = new ArrayList<User>();
		inputReader = new InputReader();
	}
	
	public boolean userExistsInDataBase(int CWID) {
		return false;
	}
	
	public void loadUser(int CWID) {
		
	}
	
	public void createUser(int CWID, String firstName, String lastName) {
		
	}
	
	public void processLogIn(int CWID) {
		
	}
	
	public void displayUserMachines(int CWID) {
		
	}
	
	public void addLogEntry(int CWID) {
		
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
}

