package main;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class AccessTracker {
	
	private ArrayList<Machine> machines;
	private ArrayList<Tool>	tools;
	private ArrayList<Tool> availableTools;
	private ArrayList<User> currentUsers;
	private InputReader inputReader;
	private static DB database;
	private final String hostName = "dharma.mongohq.com";
	private final int port = 10096;
	private final String dbName = "CSM_Machine_Shop";
	private final String username = "csm";
	private final String password = "machineshop";
	
	public AccessTracker() {
		currentUsers = new ArrayList<User>();
		inputReader = new InputReader();
		machines = new ArrayList<Machine>();
		tools = new ArrayList<Tool>();
		availableTools = new ArrayList<Tool>();
		
		// Do the initialization stuff for the log and database
		databaseSetup();
		Log.setup();
		
		loadMachines();
		System.out.println(machines.get(0).getName());
	}
	
	private void databaseSetup() {
		try {
			MongoClient client = new MongoClient(hostName, port);
			database = client.getDB(dbName);
			database.authenticate(username, password.toCharArray());			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
		
	// Takes a CWID and attempts to load that user from the
	// database and add them to the list of current users.
	// If the CWID doesn't exist, create new user.
	// Returns the name of the user with this CWID
	public User loadUser(int CWID) {
		User currentUser = new User("", "", 0);
		boolean isAdministrator;
		boolean isSystemAdministrator;
		String firstName = "";
		String lastName = "";
		
		DBCollection users = database.getCollection("Users");
		DBCursor cursor = users.find(new BasicDBObject("CWID", CWID));
		
		if ( !cursor.hasNext() ) {
			if ( !checkLegitimacy(CWID) ) {
				// DO_SOMETHING
			} else {
				// FOR NOW (UNTIL WE GET BLASTERCARD DATABASE ACCESS)
				firstName = JOptionPane.showInputDialog("Enter your first name.");
				lastName = JOptionPane.showInputDialog("Enter your last name.");
				currentUser = createUser(firstName, lastName, CWID);
			}			
		} else {
			DBObject result = cursor.next();

			if (result.get("isAdmin") == null) {
				isAdministrator = false;
			} else {
				isAdministrator = (boolean) result.get("isAdmin");
			}

			if ( result.get("isSystemAdmin") == null ) {
				isSystemAdministrator = false;
			} else {
				isSystemAdministrator = (boolean) result.get("isSystemAdmin");
			}

			firstName = (String) result.get("firstName");
			lastName = (String) result.get("lastName");

			if ( isAdministrator ) {
				if ( isSystemAdministrator ) {
					currentUser = new SystemAdministrator(firstName, lastName, CWID);
				} else {
					currentUser = new Administrator(firstName, lastName, CWID);
				}
			}  else {
				currentUser = new User(firstName, lastName, CWID);
			}
			
//			ArrayList<String> machineIDs = new ArrayList<String>();
//			
//			DBCollection ids = (DBCollection) result.get("certifiedMachines");
//			cursor = ids.find();
//			while ( cursor.hasNext() ) {
//				String id = (String) cursor.next().get(SOMETHING);
//			}
//			
//			loadUserMachines(currentUser);
//			loadUserTools(currentUser);
			
		}
		
		return currentUser;
	}
	
	private void loadUserTools(User user) {
		
	}

	private void loadUserMachines(User user) {
		
	}

	// Loads all the tools from the database into RAM
	public void loadTools() {
		DBCollection allTools = database.getCollection("Tools");
		DBCursor cursor = allTools.find();
		while(cursor.hasNext()) {
			DBObject tool = cursor.next();
			Tool t = new Tool((String) tool.get("name"), (int) tool.get("upc"));
			tools.add(t);
		}
	}
	
	// Loads all the machines from the database into RAM
	public void loadMachines() {
		DBCollection allMachines = database.getCollection("Machines");
		DBCursor cursor = allMachines.find();
		while(cursor.hasNext()) {
			DBObject machine = cursor.next();
			Machine m = new Machine((String) machine.get("name"), (String) machine.get("ID"));
			machines.add(m);
		}
	}
	
	// Creates a new user. Should be called by loadUser()
	// Persists new user to database
	public User createUser(String firstName, String lastName, int CWID) {
		User newUser = new User(firstName, lastName, CWID);
		
		BasicDBObject document = new BasicDBObject();
		document.put("firstName", firstName);
		document.put("lastName", lastName);
		document.put("CWID", CWID);
		
		DBCollection users = database.getCollection("Users");
		
		users.insert(document);
		
		return newUser;
	}
	
	// Loads the user with this CWID to list of current users
	// Adds entry to log
	public User processLogIn(int CWID) {
		// IF the user with this CWID is locked (boolean isLocked)
		// THEN display some error message, and make a note somewhere
		// (log this attempt for admin to view later)
		User currentUser = loadUser(CWID);
		currentUsers.add(currentUser);
		
		//Log.startEntry(currentUser);
		
		displayUserMachines(currentUser);
		
		return currentUser;
		
	}
	
	public void displayUserMachines(User user) {
		
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
		return true;
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

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public ArrayList<Tool> getTools() {
		return tools;
	}

	public ArrayList<Tool> getAvailableTools() {
		return availableTools;
	}
	
	public static DB getDatabase() {
		return database;
	}
	
}

