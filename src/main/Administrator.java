package main;

public class Administrator extends User {
	
	protected boolean isSystemAdmin = false;
	
	public Administrator(String firstName, String lastName, int CWID) {
		super(firstName, lastName, CWID);
		isAdmin = true;
	}
	
	public void generateReport() {
		
	}
	
	public void viewAllTools() {
		
	}
	
	public void viewAllMachines() {
		
	}
	
	public void viewAllUsers() {
		
	}
	
	public boolean isSystemAdmin() {
		return isSystemAdmin;
	}

}
