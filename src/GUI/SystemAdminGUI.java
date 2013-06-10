package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.SystemAdministrator;
import main.User;

public class SystemAdminGUI extends JPanel {
	private JPanel centerPanel;
	private JPanel buttonPanel;
	private JPanel massLogOutPanel;
	private User currentUser;
	
	private Font buttonFont;

	
	public SystemAdminGUI(User user) {
		currentUser = user;
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(3, 1));
		
		centerPanel.add(buttonPanel, BorderLayout.CENTER);
		
		JButton logOut = new JButton();
		JButton dataEntry = new JButton();
		JButton basicUser = new JButton();
		
		logOut.setText("Log Out");
		dataEntry.setText("Data Entry");
		basicUser.setText("Basic User");
		
		logOut.setFont(buttonFont);
		dataEntry.setFont(buttonFont);
		basicUser.setFont(buttonFont);
	
		addLogOutPanel();
		
		buttonPanel.add(dataEntry);
		buttonPanel.add(basicUser);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		dataEntry.addActionListener(new DataEntryButtonListener());
		basicUser.addActionListener(new BasicUserButtonListener());
		
		logOut.addActionListener(new ListenerHelpers.LogOutListner());
		
		MainGUI.pushToStack(this);
	}
	
	private void addLogOutPanel() {
		massLogOutPanel = new JPanel();
		massLogOutPanel.setLayout(new BorderLayout());
		JPanel users = new JPanel();
		users.setLayout(new GridLayout(0, 1));
		for (User u : Driver.getAccessTracker().getCurrentUsers()) {
			if (!u.getCWID().equals(Driver.getAccessTracker().getCurrentUser().getCWID())) {
				JLabel label = new JLabel(u.getFirstName() + " " + u.getLastName());
				label.setFont(buttonFont);
				users.add(label);
			}
		}
		JScrollPane scroller = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		massLogOutPanel.add(scroller, BorderLayout.CENTER);
		JButton massLogOut = new JButton("Log Out All Users");
		massLogOut.addActionListener(new LogOutAllUsersButtonListener());
		massLogOut.setFont(buttonFont);
		massLogOutPanel.add(massLogOut, BorderLayout.SOUTH);
		
		centerPanel.add(massLogOutPanel, BorderLayout.EAST);
	}
	
	private class DataEntryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			centerPanel.removeAll();
			centerPanel.add(new DataEntryGUI());
		}
	}
	
	private class BasicUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			centerPanel.removeAll();
			centerPanel.add(new UserGUI(currentUser));
		}
	}
	
	private class LogOutAllUsersButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).logOutAllUsers();
			massLogOutPanel.removeAll();
			addLogOutPanel();
		}
	}
}
