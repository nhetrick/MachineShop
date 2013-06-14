package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.GUI.LogOutListener;

public class DataEntryGUI extends MainPanel {
	
	private JButton certifications = new JButton("Machine Certifications");
	private JButton users = new JButton("Edit Users");
	private JButton tools = new JButton("Edit Tools");
	private JButton machines = new JButton("Edit Machines");
	private JButton privileges = new JButton("User Privileges");
	private JButton generateReport = new JButton("Generate Report");
	private JButton viewActiveUsers = new JButton("View Active Users");
	private JButton viewToolsAndMachines = new JButton("View Tools/Machines");
	private JButton logInUser = new JButton("Sign In Another User");
	private JButton done = new JButton(" Finish");
	private JButton logOut = new JButton("Sign Out");
	
	public DataEntryGUI() {
		
		super();
		contentPanel = new JPanel(new BorderLayout());
		buttonListener = new ButtonListener();
		setLayout(new GridBagLayout());
		
		buttons.add(certifications);
		buttons.add(users);
		buttons.add(tools);
		buttons.add(machines);
		buttons.add(privileges);
		buttons.add(generateReport);
		buttons.add(viewActiveUsers);
		buttons.add(viewToolsAndMachines);
		buttons.add(logInUser);
		buttons.add(done);
		buttons.add(logOut);
		
		formatAndAddButtons();
		
		logOut.removeActionListener(buttonListener);
		logOut.addActionListener(new GUI.SysAdminLogOutListener());
		
		done.removeActionListener(buttonListener);
		done.addActionListener(new GUI.DoneListener());
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_START;
		
		contentPanel = new ViewToolsAndMachinesPanel();
		
		add(contentPanel, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0;
		c.gridx = 1;
		
		add(buttonPanel, c);
		
	}
	
	private class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if ( e.getSource() == certifications ) {
				switchContentPanel(new EditCertificationsPanel());
			} else if ( e.getSource() == users ) {
				switchContentPanel(new EditUsersPanel());
			} else if ( e.getSource() == tools ) {
				switchContentPanel(new EditToolsPanel());
			} else if ( e.getSource() == machines ) {
				switchContentPanel(new EditMachinesPanel());
			} else if ( e.getSource() == privileges ) {
				switchContentPanel(new EditPrivilegesPanel());
			} else if ( e.getSource() == generateReport ) {
				switchContentPanel(new GenerateReportPanel());
			} else if ( e.getSource() == viewActiveUsers ) {
				switchContentPanel(new ViewActiveUsersPanel() );
			} else if ( e.getSource() == viewToolsAndMachines ) {
				switchContentPanel(new ViewToolsAndMachinesPanel());
			} else if ( e.getSource() == logInUser ) {
				switchContentPanel(new LogInAnotherUserPanel());
			}
			JButton current = (JButton) e.getSource();
			current.setBackground(orange);
		}
	}
}
