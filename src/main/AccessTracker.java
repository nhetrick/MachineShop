package main;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import GUI.Driver;

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
	private User currentUser = new User("", "", "");

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
		loadTools();

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
	public User loadUser(String CWID) {
		if (currentUsers.contains(new User("", "", CWID))) {
			currentUser = getUser(CWID);
		} else {
			currentUser = findUserByCWID(CWID);
			if ( currentUser == null ) {
				try {
					currentUser = createUser(CWID);
					if ( currentUser == null ) {
						JOptionPane.showMessageDialog(Driver.getMainGui(),
								"Sorry, our records show you are not a registered Mines student." +
								"\nIf you believe this is an error, please visit the registrar." +
								"\n\nTo use the machine shop, you must talk to the attendant on duty."
								);
						return null;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		currentUsers.add(currentUser);
		return currentUser;
	}

	// Loads all the tools from the database into RAM
	public void loadTools() {
		tools.clear();
		DBCollection allTools = database.getCollection("Tools");
		DBCursor cursor = allTools.find();
		while(cursor.hasNext()) {
			DBObject tool = cursor.next();
			Tool t = new Tool((String) tool.get("name"), (String) tool.get("upc"));
			tools.add(t);
		}
	}

	public void addTool(Tool t) {
		tools.add(t);
	}

	public void removeTool(Tool t) {
		tools.remove(t);
	}

	// Loads all the machines from the database into RAM
	public void loadMachines() {
		machines.clear();
		DBCollection allMachines = database.getCollection("Machines");
		DBCursor cursor = allMachines.find();
		while(cursor.hasNext()) {
			DBObject machine = cursor.next();
			Machine m = new Machine((String) machine.get("name"), (String) machine.get("ID"));
			machines.add(m);
		}
	}

	public void addMachine(Machine m) {
		machines.add(m);
	}

	public void removeMachine(Machine m) {
		machines.remove(m);
	}

	// Creates a new user. Should be called by loadUser()
	// Persists new user to database
	public User createUser(String firstName, String lastName, String CWID) {
		User newUser = new User(firstName, lastName, CWID);

		BasicDBObject document = new BasicDBObject();
		document.put("firstName", firstName);
		document.put("lastName", lastName);
		document.put("CWID", CWID);

		DBCollection users = database.getCollection("Users");

		users.insert(document);

		currentUsers.add(newUser);

		return newUser;
	}

	public User createUser(String CWID) throws SQLException {
		OracleConnection oracleConnection = new OracleConnection();
		oracleConnection.getConnection();
		ArrayList<String> results = oracleConnection.select(CWID);
		oracleConnection.close();

		if ( results.size() != 0 ) {
			return createUser(results.get(1), results.get(2), CWID);
		} else {
			return null;
		}
	}

	public void removeUser(User u) {
		currentUsers.remove(u);
	}

	public void clearUsers(ArrayList<User> users) {
		for (User u:users) {
			currentUsers.remove(u);
		}
	}

	public void lockUser(User u) {
		currentUsers.remove(u);
		u.setLockedStatus(true);
		currentUsers.add(u);

	}

	public void unlockUser(User u) {
		currentUsers.remove(u);
		u.setLockedStatus(false);
		currentUsers.add(u);

	}

	// Loads the user with this CWID to list of current users
	// Adds entry to log
	public User processLogIn(String CWID) {
		// IF the user with this CWID is locked (boolean isLocked)
		// THEN display some error message, and make a note somewhere
		// (log this attempt for admin to view later)
		currentUser = loadUser(CWID);
		if ( currentUser != null ) {
			Log.startEntry(currentUser);
			return currentUser;
		}
		return null;

	}

	// Removes the user with this CWID from the list
	// of current users.
	// Also finishes the log entry for this user.
	public void processLogOut(String CWID) {
		Log.finishEntry(getUser(CWID).getCurrentEntry());
		currentUsers.remove(getUser(CWID));
	}

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

	public User findUserByCWID(String cwid){
		DBCollection users = database.getCollection("Users");
		DBObject result = users.findOne(new BasicDBObject("CWID", cwid));
		boolean isAdministrator;
		boolean isSystemAdministrator;
		boolean isLocked;
		String firstName = "";
		String lastName = "";
		User user;

		if ( result == null ) {
			return null;
		}

		if (result.get("locked") == null) {
			isLocked = false;
		} else {
			isLocked = (boolean) result.get("locked");
		}

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
				user = new SystemAdministrator(firstName, lastName, cwid);
			} else {
				user = new Administrator(firstName, lastName, cwid);
			}
		}  else {
			user = new User(firstName, lastName, cwid);
		}

		user.setLockedStatus(isLocked);

		//Retrieve user's certified machines
		ArrayList<Machine> machinesList = new ArrayList<Machine>();
		DBCollection machinesColl = database.getCollection("Machines");

		ArrayList<BasicDBObject> certMachines = (ArrayList<BasicDBObject>)result.get("certifiedMachines");
		if (certMachines == null) {
			user.loadCertifiedMachines(new ArrayList<Machine>());
		} else {
			for(BasicDBObject embedded : certMachines){ 
				String id = (String)embedded.get("id"); 
				DBCursor machine = machinesColl.find(new BasicDBObject("ID", id));
				if (machine.hasNext()) {
					machinesList.add(new Machine((String) machine.next().get("name"), id));
				}
			} 
			user.loadCertifiedMachines(machinesList);
		}

		//Retrieve user's checkedOutTools
		ArrayList<Tool> checkedOutToolsList = new ArrayList<Tool>();
		DBCollection toolsColl = database.getCollection("Tools");

		ArrayList<BasicDBObject> COTools = (ArrayList<BasicDBObject>)result.get("checkedOutTools");
		if(COTools == null) {
			user.loadCheckedOutTools(new ArrayList<Tool>());
		} else {
			for(BasicDBObject embedded : COTools){ 
				String upc = (String) embedded.get("upc"); 
				DBCursor tool = toolsColl.find(new BasicDBObject("upc", upc));
				if (tool.hasNext()) {
					checkedOutToolsList.add(new Tool((String) tool.next().get("name"), upc));
				}
			} 
		}
		user.loadCheckedOutTools(checkedOutToolsList);

		return user;
	}

	public ArrayList<DBObject> searchDatabase(String collectionName, String searchFieldName, String searchFieldValue) {

		DBCollection collection = database.getCollection(collectionName);
		Pattern p = Pattern.compile(searchFieldValue, Pattern.CASE_INSENSITIVE);
		DBCursor cursor = collection.find(new BasicDBObject(searchFieldName, p));

		ArrayList<DBObject> returnList = new ArrayList<DBObject>();
		while (cursor.hasNext()) {
			returnList.add(cursor.next());
		}

		return returnList;

	}

	/********************************** GETTERS AND SETTERS *******************************************/

	public User getUser(String cwid) {
		for (User u : currentUsers) {
			if (u.getCWID() == cwid) {
				return u;
			}
		}
		return null;
	}

	public ArrayList<User> getCurrentUsers() {
		return currentUsers;
	}

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public Machine getMachineByName(String name) {
		for (Machine m:machines) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	public Machine getMachineByID(String id) {
		for (Machine m:machines) {
			if (m.getID().equals(id)) {
				return m;
			}
		}
		return null;
	}

	public Tool getToolByUPC(String upc){
		for (Tool t : tools){
			if (t.getUPC().equals(upc)){
				return t;
			}
		}
		return null;
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

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}


}

