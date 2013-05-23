package main;
import java.util.ArrayList;

import GUI.Driver;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class SystemAdministrator extends Administrator {
	private AccessTracker tracker;
	private DB database;
	private DBCollection users;
	
	public SystemAdministrator(String firstName, String lastName, int CWID) {
		super(firstName, lastName, CWID);
		isSystemAdmin = true;
		tracker = Driver.getAccessTracker();
		database = tracker.getDatabase();
		users = database.getCollection("Users");
		
		//ArrayList<Machine> machines = new ArrayList<Machine>();
		
		//machines.add(new Machine("Table Saw", "1PLZ4"));
		//machines.add(new Machine("3D Printer", "W33V4"));
		//machines.add(new Machine("Chain Saw", "44MAK"));
	
		//addPermission(new User("Kevin", "Moore", 11111111), machines);
	}
	
	public void addAdministrator(User user) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (cursor.hasNext()) {
			BasicDBObject result = (BasicDBObject) cursor.next();
			result.put("isAdmin", true);
			result.put("isSystemAdmin", false);
		}
	}
	
	public void removeAdministrator(User user) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (cursor.hasNext()) {
			BasicDBObject result = (BasicDBObject) cursor.next();
			result.put("isAdmin", false);
			result.put("isSystemAdmin", false);
		}
	}
	
	public void addSystemAdministrator(User user) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (cursor.hasNext()) {
			BasicDBObject result = (BasicDBObject) cursor.next();
			result.put("isAdmin", true);
			result.put("isSystemAdmin", true);
		}
	}
	
	public void removeSystemAdministrator(User user) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (cursor.hasNext()) {
			BasicDBObject result = (BasicDBObject) cursor.next();
			result.put("isAdmin", false);
			result.put("isSystemAdmin", false);
		}
	}
	
	public void updatePermission(User user, ArrayList<Machine> machines) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (cursor.hasNext()) {
			DBObject result = cursor.next();
			BasicDBList machinePermissions = new BasicDBList();
			for (Machine m:machines) {
				machinePermissions.add(new BasicDBObject("id", m.getID()));
			}
			result.put("certifiedMachines", machinePermissions);
			
			users.update(new BasicDBObject("CWID", user.getCWID()), result);
			user.loadCertifiedMachines(machines);
		}
	}
	
	public void removeUsers(ArrayList<User> users) {
		
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
