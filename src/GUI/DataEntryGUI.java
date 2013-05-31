package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.ListenerHelpers.LogOutListner;

public class DataEntryGUI extends JPanel {
	
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private Font buttonFont;
	
	private JButton logOut;
	private JButton certifications;
	private JButton users;
	private JButton tools;
	private JButton machines;
	private JButton privileges;
	private JButton generateReport;
	private JButton view;
	
	private ButtonListener buttonListener;
	private Color buttonBackground;
	
	public DataEntryGUI() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		buttonListener = new ButtonListener();
		
		buttonBackground = new Color(63, 146, 176);
		buttonFont = new Font("SansSerif", Font.BOLD, 30);
		
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createEtchedBorder());
		buttonPanel = new JPanel(new GridLayout(8, 1));
		buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		
		logOut = new JButton();
		certifications = new JButton();
		users = new JButton();
		tools = new JButton();
		machines = new JButton();
		privileges = new JButton();
		generateReport = new JButton();
		view = new JButton();
		
		logOut.setFont(buttonFont);
		certifications.setFont(buttonFont);
		users.setFont(buttonFont);
		tools.setFont(buttonFont);
		machines.setFont(buttonFont);
		privileges.setFont(buttonFont);
		generateReport.setFont(buttonFont);
		view.setFont(buttonFont);
		
		logOut.setText("Log Out");
		certifications.setText("Machine Certifications");
		users.setText("Edit Users");
		tools.setText("Edit Tools");
		machines.setText("Edit Machines");
		privileges.setText("User Privileges");
		generateReport.setText("Generate Report");
		view.setText("View Tools/Machines");
		
		certifications.addActionListener(buttonListener);
		users.addActionListener(buttonListener);
		tools.addActionListener(buttonListener);
		machines.addActionListener(buttonListener);
		privileges.addActionListener(buttonListener);
		generateReport.addActionListener(buttonListener);
		view.addActionListener(buttonListener);
		logOut.addActionListener(new LogOutListner());
		
		buttonPanel.add(certifications);
		buttonPanel.add(tools);
		buttonPanel.add(machines);
		buttonPanel.add(users);
		buttonPanel.add(privileges);
		buttonPanel.add(generateReport);
		buttonPanel.add(view);
		buttonPanel.add(logOut);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_START;
		
		add(contentPanel, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0;
		c.gridx = 1;
		
		add(buttonPanel, c);
		
	}
	
	public void resetButtonBackgrounds(){
		certifications.setBackground(null);
		users.setBackground(null);
		tools.setBackground(null);
		machines.setBackground(null);
		privileges.setBackground(null);
		generateReport.setBackground(null);
		view.setBackground(null);	
	}
	
	public void switchPanels(JPanel panel) {
		contentPanel.removeAll();
		resetButtonBackgrounds();
		contentPanel.add(panel);
		repaint();
	}
	
	private class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if ( e.getSource() == certifications ) {
				switchPanels(new EditCertificationsPanel());
			} else if ( e.getSource() == users ) {
				switchPanels(new EditUsersPanel());
			} else if ( e.getSource() == tools ) {
				switchPanels(new EditToolsPanel());
			} else if ( e.getSource() == machines ) {
				switchPanels(new EditMachinesPanel());
			} else if ( e.getSource() == privileges ) {
				switchPanels(new EditPrivilegesPanel());
			} else if ( e.getSource() == generateReport ) {
				switchPanels(new GenerateReportPanel());
			} else if ( e.getSource() == view ) {
				switchPanels(new ViewToolsAndMachinesPanel());
			}
			JButton current = (JButton) e.getSource();
			current.setBackground(buttonBackground);
		}
	}

}
