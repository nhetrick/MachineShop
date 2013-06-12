package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
	ArrayList<LogEntry> results;
	private int numEntries;
	private int numEntriesModified;
	private int numUsers;
	private int numTools;
	private int numMachines;
	private int numDept;
	private long totalTimeLoggedIn;
	private long avgTimeLoggedIn;
	private ArrayList<String> users;
	private int numLockedUsers;
	private int numNotLoggedOut;
	private Map<String, Integer> machineFrequencies;
	private Map<String, Integer> toolsFrequencies;
	private Map<String, Integer> deptFrequencies;
	
	public Statistics() {
		results = Log.getResults();
		numEntries = 0;
		numEntriesModified = 0;
		numUsers = 0;
		numTools = 0;
		numMachines = 0;
		numDept = 0;
		totalTimeLoggedIn = 0;
		avgTimeLoggedIn = 0;
		users = new ArrayList<String>();
		numLockedUsers = 0;
		numNotLoggedOut = 0;
		machineFrequencies = new HashMap<String, Integer>();
		toolsFrequencies = new HashMap<String, Integer>();
		deptFrequencies = new HashMap<String, Integer>();
	}
	
	public void run() {
		for (LogEntry entry : results) {
			numEntries++;
			userCheck(entry.getCwid());
			String dept = entry.getDept();
			if (dept != null) {
				deptCheck(dept);
			}
			if (entry.getTimeOut() != null) {
				determineLogInTime(entry.getTimeIn().getTime(), entry.getTimeOut().getTime());
				numEntriesModified++;
			}
			machineCheck(entry.getMachinesUsed());
			toolsCheck(entry.getToolsCheckedOut());
			toolsCheck(entry.getToolsReturned());
		}
		determineAvgTime();
	}
	
	private void userCheck(String cwid) {
		if (cwid.contains("LOCKED")) {
			numLockedUsers++;
			cwid = (String) cwid.subSequence(0, 8);
			System.out.println("CWID: " + cwid);
		}
		if (!users.contains(cwid)) {
			numUsers++;
			users.add(cwid);
		}
	}
	
	private void deptCheck(String dept) {
		if (!deptFrequencies.containsKey(dept)) {
			numDept++;
			deptFrequencies.put(dept, 1);
		} else {
			deptFrequencies.put(dept,  deptFrequencies.get(dept) + 1);
		}
	}

	private void determineLogInTime(Long start, Long end) {
		totalTimeLoggedIn += (end - start);
	}
	
	private void determineAvgTime() {
		if (numEntriesModified != 0) {
			avgTimeLoggedIn = totalTimeLoggedIn / numEntriesModified;
		}
	}
	
	private void machineCheck(ArrayList<Machine> machines) {
		for (Machine m : machines) {
			String name = m.getName() + " (" + m.getID() + ")";
			if (!machineFrequencies.containsKey(name)) {
				numMachines++;
				machineFrequencies.put(name, 1);
			} else {
				machineFrequencies.put(name, machineFrequencies.get(name) + 1);
			}
		}
	}
	
	private void toolsCheck(ArrayList<Tool> tools) {
		for (Tool t : tools) {
			String name = t.getName() + " (" + t.getUPC() + ")";
			
			if (!toolsFrequencies.containsKey(name)) {
				numTools++;
				toolsFrequencies.put(name, 1);
			} else {
				toolsFrequencies.put(name,  toolsFrequencies.get(name) + 1);
			}
		}
	}
	
	public Map<String, String> getGeneralStatistics() {	
		Map<String, String> dateStats = new HashMap<String, String>();
		
		dateStats.put("Number of Entries", Integer.toString(getNumEntries()));
		dateStats.put("Number of Unique Users", Integer.toString(getNumUsers()));
		dateStats.put("Number of Locked Out Tries", Integer.toString(getNumLockedUsers()));
		dateStats.put("Number of Different Tools Used", Integer.toString(getNumTools()));
		dateStats.put("Number of Different Machines Used", Integer.toString(getNumMachines()));
		
		return dateStats;
 	}
	
	public Map<String, String> getUserStatistics() {	
		Map<String, String> userStats = new HashMap<String, String>();
		userStats.put("Number of Entries", Integer.toString(getNumEntries()));
		userStats.put("Number of Different Tools Used", Integer.toString(getNumTools()));
		userStats.put("Number of Different Machines Used", Integer.toString(getNumMachines()));
		userStats.put("Number of Locked Out Tries", Integer.toString(getNumLockedUsers()));
		
		return userStats;
	}
	
	public Map<String, String> getGeneralToolStats(){
		Map<String, String> toolStats = new HashMap<String, String>();
		toolStats.put("Number of Entries", Integer.toString(getNumEntries()));
		toolStats.put("Number of Unique Users", Integer.toString(getNumUsers()));
		
		return toolStats;
	}
	
	public Map<String, String> getGeneralMachineStats(){
		Map<String, String> machineStats = new HashMap<String, String>();
		machineStats.put("Number of Entries", Integer.toString(getNumEntries()));
		machineStats.put("Number of Unique Users", Integer.toString(getNumUsers()));
		
		return machineStats;
	}
	
	//------------------------------GETTERS AND SETTERS------------------------------------

	public ArrayList<LogEntry> getResults() {
		return results;
	}

	public int getNumEntries() {
		return numEntries;
	}

	public int getNumUsers() {
		return numUsers;
	}

	public int getNumTools() {
		return numTools;
	}

	public int getNumMachines() {
		return numMachines;
	}
	
	public int getNumEntriesModified() {
		return numEntriesModified;
	}

	public int getNumDept() {
		return numDept;
	}

	public Map<String, Integer> getDeptFrequencies() {
		return deptFrequencies;
	}

	public double getTotalTimeLoggedIn() {
		return totalTimeLoggedIn;
	}

	public double getAvgTimeLoggedIn() {
		return avgTimeLoggedIn;
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public int getNumLockedUsers() {
		return numLockedUsers;
	}

	public int getNumNotLoggedOut() {
		return numNotLoggedOut;
	}

	public Map<String, Integer> getMachineFrequencies() {
		return machineFrequencies;
	}

	public Map<String, Integer> getToolsFrequencies() {
		return toolsFrequencies;
	}
}
