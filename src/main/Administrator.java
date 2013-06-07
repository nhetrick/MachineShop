package main;

public class Administrator extends User {
	
	protected boolean isSystemAdmin = false;
	
	public Administrator(String firstName, String lastName, String cwid, String email, String department) {
		super(firstName, lastName, cwid, email, department);
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
