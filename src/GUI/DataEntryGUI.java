package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DataEntryGUI extends JPanel {
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	
	private Font buttonFont;
	
	public DataEntryGUI() {
		
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(9, 1));
		
		centerPanel.add(contentPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton logOut = new JButton();
		JButton permissions = new JButton();
		JButton users = new JButton();
		JButton tools = new JButton();
		JButton machines = new JButton();
		JButton administrators = new JButton();
		JButton lockUser = new JButton();
		JButton generateReport = new JButton();
		JButton list = new JButton();
		
		logOut.setText("Log Out");
		permissions.setText("Edit Permissions");
		users.setText("Edit Users");
		tools.setText("Edit Tools");
		machines.setText("Edit Machines");
		administrators.setText("Edit Administrators");
		lockUser.setText("Lock/Unlock User");
		generateReport.setText("Generate Report");
		list.setText("List Tools/Machines");
		
		logOut.setFont(buttonFont);
		permissions.setFont(buttonFont);
		users.setFont(buttonFont);
		tools.setFont(buttonFont);
		machines.setFont(buttonFont);
		administrators.setFont(buttonFont);
		lockUser.setFont(buttonFont);
		generateReport.setFont(buttonFont);
		list.setFont(buttonFont);		
		
		buttonPanel.add(permissions);
		buttonPanel.add(tools);
		buttonPanel.add(machines);
		buttonPanel.add(users);
		buttonPanel.add(administrators);
		buttonPanel.add(lockUser);
		buttonPanel.add(generateReport);
		buttonPanel.add(list);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		permissions.addActionListener(new PermissionsButtonListener());
		users.addActionListener(new UsersButtonListener());
		tools.addActionListener(new ToolsButtonListener());
		machines.addActionListener(new MachinesButtonListener());
		administrators.addActionListener(new AdministratorsButtonListener());
		lockUser.addActionListener(new LockUserButtonListener());
		generateReport.addActionListener(new GenerateReportButtonListener());
		list.addActionListener(new ListButtonListener());
	}
	
	public void switchPanels(JPanel panel) {
		contentPanel.removeAll();
		contentPanel.add(panel);
	}

	private class PermissionsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new EditPermissionsPanel());
		}
	}
	
	private class UsersButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new EditUsersPanel());
		}
	}
	
	private class ToolsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new EditToolsPanel());
		}
	}
	
	private class MachinesButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new EditMachinesPanel());
		}
	}
	
	private class AdministratorsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new EditAdministratorsPanel());
		}
	}
	
	private class LockUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new LockUserPanel());
		}
	}
	
	private class GenerateReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new GenerateReportPanel());
		}
	}
	
	private class ListButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new ListPanel());
		}
	}
	
	private class LogOutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
}
