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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import main.SystemAdministrator;
import main.User;

public class SystemAdminGUI extends MainPanel {

	private JPanel massLogOutPanel;
	private JButton logOutButton;
	private JButton dataEntryButton;
	private JButton basicUserButton;
	private JButton massLogOutButton;
	private JScrollPane scroller;

	private ButtonListener buttonListener;

	public SystemAdminGUI(User user) {

		super(user);
		buttonListener = new ButtonListener();

		logOutButton = new JButton();
		dataEntryButton = new JButton();
		basicUserButton = new JButton();

		logOutButton.setText("Log Out");
		dataEntryButton.setText("Data Entry");
		basicUserButton.setText("Basic User");

		logOutButton.setFont(buttonFont);
		dataEntryButton.setFont(buttonFont);
		basicUserButton.setFont(buttonFont);

		dataEntryButton.addActionListener(buttonListener);
		basicUserButton.addActionListener(buttonListener);
		logOutButton.addActionListener(new ListenerHelpers.LogOutListener());

		buttonPanel.add(dataEntryButton);
		buttonPanel.add(basicUserButton);
		buttonPanel.add(logOutButton);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.7;
		c.weighty = 1;
		
		contentPanel.add(buttonPanel, c);
		createMassLogOutPanel();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.3;
		c.weighty = 1;

		contentPanel.add(massLogOutPanel, c);
		
		add(contentPanel, BorderLayout.CENTER);
		
		MainGUI.pushToStack(this);
		
	}
	
	public void setup() {
		super.setup();
		contentPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridLayout(3, 1));
	}

	private void createMassLogOutPanel() {

		massLogOutPanel = new JPanel();
		massLogOutPanel.setLayout(new GridBagLayout());
		JPanel users = new JPanel();
		users.setLayout(new GridLayout(0, 1));

		for (User u : Driver.getAccessTracker().getCurrentUsers()) {
			if (!u.getCWID().equals(Driver.getAccessTracker().getCurrentUser().getCWID())) {
				JLabel label = new JLabel(u.getFirstName() + " " + u.getLastName());
				label.setFont(buttonFont);
				users.add(label);
			}
		}

		massLogOutButton = new JButton("Log Out All Users");
		massLogOutButton.setFont(buttonFont);
		massLogOutButton.addActionListener(buttonListener);

		scroller = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		TitledBorder border = new TitledBorder("Current Users");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.8;
		
		massLogOutPanel.add(scroller, c);
		
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.2;
		
		massLogOutPanel.add(massLogOutButton, c);

	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == dataEntryButton ) {
				switchPanels(new DataEntryGUI());
			} else if ( e.getSource() == basicUserButton ) {
				switchPanels(new UserGUI(currentUser));
			} else if ( e.getSource() == massLogOutButton ) {
				if ( confirm("Are you sure you want to log out all current users?")) {
					((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).logOutAllUsers();
					scroller.removeAll();
					repaint();
				}
			}
		}

	}
}
