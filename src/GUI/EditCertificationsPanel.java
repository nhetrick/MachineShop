package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.mongodb.DBObject;

import main.BlasterCardListener;
import main.Machine;
import main.MachineComparator;
import main.SystemAdministrator;
import main.User;

public class EditCertificationsPanel extends ContentPanel {

	private JButton saveButton;
	private JTextField nameSearchField;
	private JTextField idSearchField;
	private JButton nameSearchGoButton;
	private JButton idSearchGoButton;
	private JScrollPane scroller;
	private JPanel permissionsPanel;
	private User user;

	public EditCertificationsPanel() {

		super("Edit User's Machine Certifications");
		buttonListener = new ButtonListener();

		JLabel nameSearchLabel = new JLabel("Search By Name:");
		JLabel idSearchLabel = new JLabel("Search By CWID:");

		nameSearchField = new JTextField();
		idSearchField = new JTextField();

		nameSearchField.addActionListener(buttonListener);
		idSearchField.addActionListener(buttonListener);

		JPanel nameSearchPanel = new JPanel(new GridLayout(1, 3));
		JPanel idSearchPanel = new JPanel(new GridLayout(1, 3));

		permissionsPanel = new JPanel(new GridLayout(0, 2));

		// sorts the machines list
		ArrayList<Machine> sorted = new ArrayList<Machine>();
		sorted = Driver.getAccessTracker().getMachines();
		Collections.sort(sorted, new MachineComparator());

		for ( Machine m : sorted ) {
			JCheckBox cb = new JCheckBox(m.getName() + " [" + m.getID() + "]");
			cb.setHorizontalAlignment(JCheckBox.LEFT);
			cb.setFont(borderFont);
			permissionsPanel.add(cb);
		}

		scroller = new JScrollPane(permissionsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		TitledBorder border = new TitledBorder("User Certifications");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);

		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);

		JPanel dataPanel = new JPanel(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		dataPanel.add(nameSearchPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		dataPanel.add(idSearchPanel, c);

		nameSearchGoButton = new JButton("Go");
		idSearchGoButton = new JButton("Go");

		nameSearchGoButton.addActionListener(buttonListener);
		idSearchGoButton.addActionListener(buttonListener);

		nameSearchLabel.setFont(buttonFont);
		idSearchLabel.setFont(buttonFont);
		nameSearchField.setFont(textFont);
		idSearchField.setFont(textFont);
		nameSearchGoButton.setFont(buttonFont);
		idSearchGoButton.setFont(buttonFont);

		nameSearchPanel.add(nameSearchLabel);
		nameSearchPanel.add(nameSearchField);
		nameSearchPanel.add(nameSearchGoButton);

		idSearchPanel.add(idSearchLabel);
		idSearchPanel.add(idSearchField);
		idSearchPanel.add(idSearchGoButton);

		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		add(dataPanel, c);		

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(scroller, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		add(saveButton, c);

		c.weighty = 0.1;
		c.gridy = 4;
		add(new JPanel(), c);
	}

	// Clears all the text fields.
	private void clearFields() {
		idSearchField.setText("");
		nameSearchField.setText("");
		for (int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
			( (JCheckBox) permissionsPanel.getComponent(i) ).setSelected(false);
		}
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton) {
				if ( user == null ) {
					showMessage("Please enter the user's CWID.");
				}
				else {
					ArrayList<Machine> machines = new ArrayList<Machine>();

					for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
						if ( cb.isSelected() ) {
							for ( Machine m : Driver.getAccessTracker().getMachines() ) {
								String s = cb.getText();
								s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
								String ID = m.getID();
								if ( s.equals(ID) ) {
									machines.add(m);
								}
							}
						}
					}
					SystemAdministrator admin = (SystemAdministrator) Driver.getAccessTracker().getCurrentUser();
					admin.updateCertifications(user, machines);
				}
				clearFields();

			} else if ( e.getSource() == nameSearchGoButton || e.getSource() == idSearchGoButton ||
					e.getSource() == nameSearchField    || e.getSource() == idSearchField) {

				if ( e.getSource() == idSearchGoButton || e.getSource() == idSearchField ) {

					String input = BlasterCardListener.strip(idSearchField.getText());
					user = Driver.getAccessTracker().findUserByCWID(input);
					if ( user != null ) {

						idSearchField.setText(user.getFirstName() + " " + user.getLastName() + " [" + user.getDepartment() + "]");
						nameSearchField.setText(user.getFirstName() + " " + user.getLastName() + " [" + user.getDepartment() + "]");

						for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
							JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
							String s = cb.getText();
							s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
							if (user.getCertifiedMachines().isEmpty())
								cb.setSelected(false);
							else {
								for ( Machine m : user.getCertifiedMachines() ) {
									if ( m.getID().equals(s) ) {
										cb.setSelected(true);
										break;
									} else {
										cb.setSelected(false);
									}
								}
							}
						}
					}
				} else {
					String input = nameSearchField.getText();
					ArrayList<DBObject> users = Driver.getAccessTracker().searchDatabaseForUser(input);
					user = null;
					ArrayList<User> usersFound = new ArrayList<User>();
					for ( DBObject dbo : users ) {
						if ( ((String) dbo.get("firstName") + " " + (String) dbo.get("lastName")).equalsIgnoreCase(input) ) {
							user = Driver.getAccessTracker().findUserByCWID((String) dbo.get("CWID"));
							usersFound.add(user);
						}
					}
					if ( user != null ) {

						if ( usersFound.size() > 1) {
							String message = "There were " + usersFound.size() + " users found with this name.\n" +
									         "Please search the user by CWID to find the one you want to edit.";
							showMessage(message);
						} else {

							idSearchField.setText(user.getFirstName() + " " + user.getLastName() + " [" + user.getDepartment() + "]");
							nameSearchField.setText(user.getFirstName() + " " + user.getLastName() + " [" + user.getDepartment() + "]");

							for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
								JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
								String s = cb.getText();
								s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
								if (user.getCertifiedMachines().isEmpty())
									cb.setSelected(false);
								else {
									for ( Machine m : user.getCertifiedMachines() ) {
										if ( m.getID().equals(s) ) {
											cb.setSelected(true);
											break;
										} else {
											cb.setSelected(false);
										}
									}
								}
							}
						}
					}
				}
			}
			repaint();
		}
	}

}
