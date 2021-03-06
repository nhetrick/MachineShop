package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import main.SystemAdministrator;
import main.User;

public class SystemAdminGUI extends MainPanel {

	private JPanel massLogOutPanel;
	private JButton dataEntryButton = new JButton("Administrative Functions");
	private JButton basicUserButton = new JButton("Basic User Functions");
	private JButton finishButton = new JButton("Finish");
	private JButton logOutButton = new JButton("Sign Out");
	private JButton massLogOutButton = new JButton("Sign Out All Users");
	private JScrollPane scroller;

	public SystemAdminGUI(User user) {

		super();
		buttonListener = new ButtonListener();
		contentPanel = new JPanel(new GridBagLayout());	
		
		dataEntryButton.setFont(buttonFont);
		basicUserButton.setFont(buttonFont);
		finishButton.setFont(buttonFont);
		logOutButton.setFont(buttonFont);

		dataEntryButton.addActionListener(buttonListener);
		basicUserButton.addActionListener(buttonListener);
		finishButton.addActionListener(new GUI.DoneListener());
		logOutButton.addActionListener(new GUI.SysAdminLogOutListener());

		buttonPanel.add(dataEntryButton);
		buttonPanel.add(basicUserButton);
		buttonPanel.add(finishButton);
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

		massLogOutButton.setFont(buttonFont);
		massLogOutButton.addActionListener(buttonListener);

		scroller = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
				if ( Driver.getAccessTracker().getCurrentUsers().size() > 1 ) {
					if ( confirm("Are you sure you want to sign out all current users?")) {
						((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).logOutAllUsers();
						scroller.removeAll();
						repaint();
					}
				}
			}
		}

	}
}
