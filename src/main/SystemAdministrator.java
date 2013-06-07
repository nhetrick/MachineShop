package main;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	
	public SystemAdministrator(String firstName, String lastName, String cwid, String email, String department) {
		super(firstName, lastName, cwid, email, department);
		isSystemAdmin = true;
		tracker = Driver.getAccessTracker();
		database = tracker.getDatabase();
		users = database.getCollection("Users");
	}
	
	public void updatePermissions(User user, boolean isAdmin, boolean isSystemAdmin) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (!(cursor == null)) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			obj.append("isAdmin", isAdmin);
			obj.append("isSystemAdmin", isSystemAdmin);
			users.update(new BasicDBObject("CWID", user.getCWID()), obj);
		}
	}
	
	public void updateCertifications(User user, ArrayList<Machine> machines) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (cursor.hasNext()) {
			DBObject result = cursor.next();
			BasicDBList machinePermissions = new BasicDBList();
			for (Machine m:machines) {
				machinePermissions.add(new BasicDBObject("id", m.getID()));
			}
			result.put("certifiedMachines", machinePermissions);
			
			users.update(new BasicDBObject("CWID", user.getCWID()), result);
			user.setCertifiedMachines(machines);
		}
	}
	
	public void removeUsers(ArrayList<User> userList) {
		for (User u : userList) {
			DBCursor cursor = users.find(new BasicDBObject("CWID", u.getCWID()));
			if (!(cursor == null)) {
				users.remove(cursor.next());
				tracker.removeUser(u);
			}
		}
	}
	
	public void generateReport() {
		
	}
	
	public void addTool(Tool t) {
		DBCollection tools = database.getCollection("Tools");
		DBCursor cursor = tools.find(new BasicDBObject("upc", t.getUPC()));
		if (!cursor.hasNext()) {
			BasicDBObject tool = new BasicDBObject();
			tool.put("name", t.getName());
			tool.put("upc", t.getUPC());
			tools.insert(tool);
			tracker.addTool(t);
		} else {
			JOptionPane.showMessageDialog(Driver.getMainGui(), "Tool already in system...Unable to add");
		}
	}
	
	public void addMachine(Machine m) {
		DBCollection machines = database.getCollection("Machines");
		DBCursor cursor = machines.find(new BasicDBObject("ID", m.getID()));
		if (!cursor.hasNext()) {
			BasicDBObject machine = new BasicDBObject();
			machine.put("name", m.getName());
			machine.put("ID", m.getID());
			machines.insert(machine);
			tracker.addMachine(m);
		} else {
			JOptionPane.showMessageDialog(Driver.getMainGui(), "Machine already in system...Unable to add");
		}
	}
	
	public boolean addUser(User u) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", u.getCWID()));
		if ( !cursor.hasNext() ) {
			BasicDBObject document = new BasicDBObject();
			document.put("firstName", u.getFirstName());
			document.put("lastName", u.getLastName());
			document.put("CWID", u.getCWID());
			users.insert(document);
			return true;
		} else {
			JOptionPane.showMessageDialog(Driver.getMainGui(), "User already in system...Unable to add");
			return false;
		}		
	}
	
	public void removeTool(String upc) {
		DBCollection tools = database.getCollection("Tools");
		DBCursor cursor = tools.find(new BasicDBObject("upc", upc));
		if (cursor != null) {
			DBObject obj = cursor.next();
			tools.remove(obj);
			tracker.removeTool(new Tool((String) obj.get("name"), (String) obj.get("upc")));
		}
	}
	
	public void removeMachine(String id) {
		DBCollection machines = database.getCollection("Machines");
		DBCursor cursor = machines.find(new BasicDBObject("ID", id));
		if (!(cursor == null)) {
			DBObject obj = cursor.next();
			machines.remove(obj);
			tracker.removeMachine(new Machine((String) obj.get("name"), (String) obj.get("ID")));
		}
	}
	
	public void removeUser(String cwid) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", cwid));
		if (!(cursor == null)) {
			DBObject obj = cursor.next();
			users.remove(obj);
			tracker.removeUser(new User((String) obj.get("firstName"), (String) obj.get("lastName"), (String) obj.get("CWID"), (String) obj.get("email"), (String) obj.get("department")));
		}
	}
	
	public User loadNewUser(String cwid, OracleConnection connection) throws SQLException {
		
		ArrayList<String> results = connection.select(cwid);
		
		if ( results.size() == 0 ) {
			return null;
		}
		return new User(results.get(1), results.get(2), cwid, results.get(3), results.get(4));
	}
	
	public void lockUser(User user) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (!(cursor == null)) {
			DBObject obj = cursor.next();
			obj.put("locked", true);
			users.update(new BasicDBObject("CWID", user.getCWID()), obj);
			user.setLockedStatus(true);
		}
	}
	
	public void unlockUser(User user) {
		DBCursor cursor = users.find(new BasicDBObject("CWID", user.getCWID()));
		if (!(cursor == null)) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			obj.append("locked", false);
			users.update(new BasicDBObject("CWID", user.getCWID()), obj);
			user.setLockedStatus(false);
		}
	}
}
