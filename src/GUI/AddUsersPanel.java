package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public AddUsersPanel() {

		super("Add a New User");
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
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		add(saveButton, c);

		c.weighty = 0.1;
		c.gridy = 6;
		add(new JPanel(), c);

	}

	private void saveUser() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String cwid = userIDField.getText();

		if (firstName.equals("") || cwid.equals("") || lastName.equals("")) {
			JOptionPane.showMessageDialog(this, "Please fill in all three fields.");
		} else {
			addedUser = new User(firstName, lastName, cwid);
			((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addUser(addedUser);
		}
	}

	private void clearFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		userIDField.setText("");
	}

	public boolean confirmSubmission(ArrayList<String> permissionsList ) {
		String list = "";
		for ( String s : permissionsList ) {
			list += s + "\n";
		}
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to give this user these permissions?" + "\n\n" + list	) == JOptionPane.YES_OPTION) {
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
				ArrayList<String> permissionsList = new ArrayList<String>();
				SystemAdministrator admin = (SystemAdministrator) Driver.getAccessTracker().getCurrentUser();
				boolean noBoxesChecked = true;
				for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
					JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
					if ( cb.isSelected() ) {
						noBoxesChecked = false;
					}
				}

				ArrayList<String> added = new ArrayList<String>();
				
				// When adding a user from this screen, the admin must add some machine permissions.
				if ( noBoxesChecked ) {
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
									// ADD PERMISSIONS TO THE USER FOR THE SELECTED MACHINES
								}
							}
						}
					}
					saveUser();
					clearFields();
				}
			}
		}
	}

}
