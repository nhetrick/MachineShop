package main;
import java.util.ArrayList;


public class SystemAdministrator extends Administrator {
	
	public SystemAdministrator(String firstName, String lastName, int CWID) {
		super(firstName, lastName, CWID);
		isSystemAdmin = true;
	}
	
	public void addAdministrator(User user) {
		
	}
	
	public void removeAdministrator(User user) {
		
	}
	
	public void addPermission(User user, Machine machine) {
		
	}
	
	public void removeUsers(ArrayList<User> users) {
		
	}
	
	public void removePermission(User user, Machine machine) {
		
	}
	
	public void generateReport() {
		
	}
	
	public void addTool(Tool t) {
		
	}
	
	public void addMachine(Machine m) {
		
	}
	
	public void removeTool(Tool t) {
		
	}
	
	public void removeMachine(Machine m) {
		
	}
	
	public void lockUser(User user) {
		
	}
	
	public void unlockUser(User user) {
		
	}
}
