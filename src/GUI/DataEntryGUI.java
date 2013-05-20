package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class DataEntryGUI extends JPanel {
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JPanel machinePermissions;
	private JPanel checkedOutTools;
	
	private Font buttonFont;
	
	public DataEntryGUI() {
		
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(8, 1));

		
		
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
		
		logOut.setText("Log Out");
		permissions.setText("Add/Remove Permissions");
		users.setText("Add/Remove Users");
		tools.setText("Add/Remove Tools");
		machines.setText("Add/Remove Machines");
		administrators.setText("Add/Remove Administrators");
		lockUser.setText("Lock/Unlock User");
		generateReport.setText("Generate Report");
		
		logOut.setFont(buttonFont);
		permissions.setFont(buttonFont);
		users.setFont(buttonFont);
		tools.setFont(buttonFont);
		machines.setFont(buttonFont);
		administrators.setFont(buttonFont);
		lockUser.setFont(buttonFont);
		generateReport.setFont(buttonFont);
		
		buttonPanel.add(permissions);
		buttonPanel.add(tools);
		buttonPanel.add(machines);
		buttonPanel.add(users);
		buttonPanel.add(administrators);
		buttonPanel.add(lockUser);
		buttonPanel.add(generateReport);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		permissions.addActionListener(new PermissionsButtonListener());
	}

	private class PermissionsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			contentPanel.removeAll();
			//centerPanel.removeAll();
			contentPanel.add(new PermissionsPanel());
			//centerPanel.add(new DataEntryGUI());
		}
	}
}
