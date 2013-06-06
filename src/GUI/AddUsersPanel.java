package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.Machine;
import main.OracleConnection;
import main.SystemAdministrator;
import main.User;

public class AddUsersPanel extends ContentPanel {

	private JButton saveButton;
	private ButtonListener buttonListener;	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField userIDField;
	private JPanel permissionsPanel;
	private JScrollPane scroller;
	private User addedUser;
	private OracleConnection connection;

	public AddUsersPanel() {
		
		super("Add a New User");
		try {
			connection = new OracleConnection();
			connection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buttonListener = new ButtonListener();

		JLabel firstNameLabel = new JLabel("First Name:");
		JLabel lastNameLabel = new JLabel("Last Name:");
		JLabel userIDLabel = new JLabel("User CWID:");

		firstNameLabel.setFont(buttonFont);
		lastNameLabel.setFont(buttonFont);
		userIDLabel.setFont(buttonFont);

		firstNameField = new JTextField();
		lastNameField = new JTextField();
		userIDField = new JTextField();

		firstNameField.setFont(textFont);
		lastNameField.setFont(textFont);
		userIDField.setFont(textFont);

		firstNameField.setPreferredSize(new Dimension(firstNameField.getWidth(), firstNameField.getHeight()));
		firstNameField.setMaximumSize(firstNameField.getPreferredSize());

		lastNameField.setPreferredSize(new Dimension(lastNameField.getWidth(), lastNameField.getHeight()));
		lastNameField.setMaximumSize(lastNameField.getPreferredSize());

		userIDField.setPreferredSize(new Dimension(userIDField.getWidth(), userIDField.getHeight()));
		userIDField.setMaximumSize(userIDField.getPreferredSize());

		userIDField.addActionListener(buttonListener);

		permissionsPanel = new JPanel(new GridLayout(0, 2));

		for ( Machine m : Driver.getAccessTracker().getMachines() ) {
			JCheckBox cb = new JCheckBox(m.getName() + " [" + m.getID() + "]");
			cb.setHorizontalAlignment(JCheckBox.LEFT);
			cb.setFont(borderFont);
			permissionsPanel.add(cb);
		}

		scroller = new JScrollPane(permissionsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);		
		scroller.setPreferredSize(new Dimension(scroller.getWidth(), scroller.getHeight()));
		scroller.setMaximumSize(scroller.getPreferredSize());
		scroller.getVerticalScrollBar().setUnitIncrement(13);

		TitledBorder border = new TitledBorder("Add Permissions");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);

		JPanel firstNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel lastNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel userIDPanel = new JPanel(new GridLayout(1, 2));

		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(firstNameField);

		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameField);

		userIDPanel.add(userIDLabel);
		userIDPanel.add(userIDField);

		saveButton = new JButton("Save");
		
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);

		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.05;
		c.gridx = 1;
		c.gridy = 1;
		add(firstNamePanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.05;
		c.gridx = 1;
		c.gridy = 2;
		add(lastNamePanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.05;
		c.gridx = 1;
		c.gridy = 3;
		add(userIDPanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.55;
		c.gridx = 1;
		c.gridy = 4;
		add(scroller, c);

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.15;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		add(saveButton, c);
		saveButton.setPreferredSize(new Dimension(saveButton.getWidth(), saveButton.getHeight()) );
		saveButton.setMaximumSize(saveButton.getPreferredSize());
		
		c.weighty = 0.05;
		c.gridy = 6;
		add(new JPanel(), c);
		
	}

	private boolean saveUser(ArrayList<Machine> machines) {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String cwid = userIDField.getText();

		SystemAdministrator admin = ((SystemAdministrator) Driver.getAccessTracker().getCurrentUser());

		try {
			addedUser = admin.loadNewUser(cwid, connection);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		if ( addedUser == null ) {
			String message = "Our records show that the CWID " + cwid + " either does not exist or is not an active " +
					"student at Mines.\nDo you still want to add " + firstName +  " " + lastName +
					" to the database?\n\nThis action is highly discouraged.";

			if ( JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION ) {
				addedUser = new User(firstName, lastName, cwid);
				if (admin.addUser(addedUser) ) {
					admin.updatePermission(addedUser, machines);
					return true;
				}
			}
		} else {
			if ( firstName.equals(addedUser.getFirstName()) && lastName.equals(addedUser.getLastName()) ) {
				if (admin.addUser(addedUser) ) {
					admin.updatePermission(addedUser, machines);
					return true;
				}
			} else {
				String message = "Our records show that the name associated with this CWID is " +
						addedUser.getFirstName() + " " + addedUser.getLastName() + ".\n" +
						"Is this the user you meant to add?\n\nClick Yes to add this user " +
						"to the database, or No/Cancel to go back.";
				if ( JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION ) {
					if (admin.addUser(addedUser) ) {
						admin.updatePermission(addedUser, machines);
						return true;
					}
				}
			}
		}
		return false;
	}

	private void clearFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		userIDField.setText("");
		for (int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
			( (JCheckBox) permissionsPanel.getComponent(i) ).setSelected(false);
		}
	}

	public boolean confirmSubmission(ArrayList<String> permissionsList ) {
		String list = "";
		for ( String s : permissionsList ) {
			list += s + "\n";
		}
		String message = "Are you sure you want to give " + firstNameField.getText() + " " + lastNameField.getText() + " " + "these permissions?\n\n" + list;
		if (JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton || e.getSource() == userIDField ) {
				boolean noBoxesChecked = true;
				for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
					JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
					if ( cb.isSelected() ) {
						noBoxesChecked = false;
					}
				}

				ArrayList<String> added = new ArrayList<String>();
				ArrayList<Machine> machines = new ArrayList<Machine>();

				if (firstNameField.getText().equals("") || userIDField.getText().equals("") || lastNameField.getText().equals("")) {
					showMessage("Please fill in all three fields.");
				} else if ( userIDField.getText().length() != 8 ) {
					showMessage("Please enter an 8-digit CWID.");
				} else if ( noBoxesChecked ) {
					// When adding a user from this screen, the admin must add some machine permissions.				
					showMessage("Please select at least one machine for which this user is certified.");
				} else {
					for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
						if ( cb.isSelected() ) {
							for ( Machine m : Driver.getAccessTracker().getMachines() ) {
								String s = cb.getText();
								s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
								String ID = m.getID();
								if ( s.equals(ID) ) {
									added.add(m.getName() + " [" + ID + "]");
									machines.add(m);
								}
							}
						}
					}
					if ( confirmSubmission(added) ) {
						if ( saveUser(machines) ) {
							clearFields();
						}
					}
				}
			}
		}
	}
	
}
