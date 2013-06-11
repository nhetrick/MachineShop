package GUI;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.Log;
import main.LogEntry;
import main.User;

public class ViewActiveUsersPanel extends ContentPanel {
	
	public ViewActiveUsersPanel() {
		super("View Active Users");
		ArrayList<User> usersWithTools = Driver.getAccessTracker().getUsersWithTools();
		ArrayList<User> currentUsers = Driver.getAccessTracker().getCurrentUsers();

		ArrayList<User> activeUsers = removeDuplicates(usersWithTools, currentUsers);
//
//		String[] columns = {""};
//		int size = Log.getResults().size();
//		String data[][] = new String[size][7];
//		if (size > 0) {
//			for (int i=0; i<size; i++) {
//				LogEntry entry = Log.getResults().get(i);
//				data[i][0] = Integer.toString(entry.getID());
//				data[i][1] = entry.getCwid();
//				data[i][2] = entry.getTimeIn().toString();
//
//				String timeOut = "";
//				if (entry.getTimeOut() == null) {
//					data[i][3] = timeOut;
//				} else {
//					data[i][3] = entry.getTimeOut().toString();
//				}				
//				data[i][4] = entry.getMachinesUsed().toString();
//				data[i][5] = entry.getToolsCheckedOut().toString();
//				data[i][6] = entry.getToolsReturned().toString();
//			}
//		}
//		tabs.removeTabAt(1);
//		table = new JTable(data, columns);
//		scroller1 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		tabs.addTab("Log", scroller1);
//

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
