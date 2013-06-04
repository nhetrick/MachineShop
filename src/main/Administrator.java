package main;

public class Administrator extends User {
	
	protected boolean isSystemAdmin = false;
	
	public Administrator(String firstName, String lastName, String cwid) {
		super(firstName, lastName, cwid);
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
