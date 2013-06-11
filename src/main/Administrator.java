package main;

public class Administrator extends User {
	
	public Administrator(String firstName, String lastName, String cwid, String email, String department) {
		super(firstName, lastName, cwid, email, department);
		isAdmin = true;
		isSystemAdmin = false;
	}
	
	public void generateReport() {
		
	}
	
	public void viewAllTools() {
		
	}
	
	public void viewAllMachines() {
		
	}
	
	public void viewAllUsers() {
		
	}

}
