package GUI;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.Log;
import main.LogEntry;
import main.User;

public class ViewActiveUsersPanel extends ContentPanel {
	
	JTable table;
	JScrollPane scroller;
	
	public ViewActiveUsersPanel() {
		super("View Active Users");
		
		setLayout(new GridLayout(0, 1));
		ArrayList<User> usersWithTools = Driver.getAccessTracker().getUsersWithTools();
		ArrayList<User> currentUsers = Driver.getAccessTracker().getCurrentUsers();

		ArrayList<User> activeUsers = removeDuplicates(usersWithTools, currentUsers);

		String[] columns = {"Name", "Machine Certifications", "Current Machines", "Current Tools", "System Priviledges"};
		int size = activeUsers.size();
		String data[][] = new String[size][7];
		if (size > 0) {
			for (int i=0; i<size; i++) {
				User u = activeUsers.get(i);
				data[i][0] = u.getFirstName() + " " + u.getLastName();
				data[i][1] = u.getCertifiedMachines().toString();
				
				if (currentUsers.contains(u)) {
					data[i][2] = u.getCurrentEntry().getMachinesUsed().toString();
				} else {
					data[i][2] = "[]";
				}
				
				data[i][3] = u.getToolsCheckedOut().toString();
				
				if (u.isSystemAdmin()) {
					data[i][4] = "System Administrator";
				} else if (u.isAdmin()) {
					data[i][4] = "Administrator";
				} else {
					data[i][4] = "Basic User";
				}
			}
		}
		table = new JTable(data, columns);
		scroller = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		add(scroller);
	}

	public ArrayList<User> removeDuplicates(ArrayList<User> listA, ArrayList<User> listB) {
		ArrayList<User> newList = new ArrayList<User>();
		newList = listA;

		for (User u : listB) {
			if (!newList.contains(u)) {
				newList.add(u);
			}
		}
		
		return newList;
	}
}
