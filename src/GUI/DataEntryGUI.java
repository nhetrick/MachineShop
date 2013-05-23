package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class DataEntryGUI extends JPanel {
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JPanel machinePermissions;
	private JPanel checkedOutTools;
	private GridBagConstraints c = new GridBagConstraints();
	private Font buttonFont;
	
	public DataEntryGUI() {
		
		setLayout(new GridBagLayout());
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel();
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(9, 1));
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 2;
		c.anchor = GridBagConstraints.LINE_START;
		
		add(contentPanel, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 1;
		
		add(buttonPanel, c);
		
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
		permissions.setText("Add/Remove Permissions");
		users.setText("Add/Remove Users");
		tools.setText("Add/Remove Tools");
		machines.setText("Add/Remove Machines");
		administrators.setText("Add/Remove Administrators");
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
		
		permissions.addActionListener(new PermissionsButtonListener());
		users.addActionListener(new UsersButtonListener());
		tools.addActionListener(new ToolsButtonListener());
		machines.addActionListener(new MachinesButtonListener());
		administrators.addActionListener(new AdministratorsButtonListener());
		lockUser.addActionListener(new LockUserButtonListener());
		generateReport.addActionListener(new GenerateReportButtonListener());
		list.addActionListener(new ListButtonListener());
		logOut.addActionListener(new ListenerHelpers.LogOutListner());
	}
	
	public void switchPanels(JPanel panel) {
		contentPanel.removeAll();
		contentPanel.add(panel);
	}

	private class PermissionsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new AR_PermissionsPanel());
		}
	}
	
	private class UsersButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new AR_UsersPanel());
		}
	}
	
	private class ToolsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new AR_ToolsPanel());
		}
	}
	
	private class MachinesButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new AR_MachinesPanel());
		}
	}
	
	private class AdministratorsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switchPanels(new AR_AdministratorsPanel());
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

}
