package GUI;

import java.awt.Dimension;
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

import main.BlasterCardListener;
import main.SystemAdministrator;
import main.User;
import main.UserComparator;

import com.mongodb.DBObject;

public class EditPrivilegesPanel extends ContentPanel {

	private JButton saveButton;
	private JButton nameSearchGoButton;
	private JButton idSearchGoButton;
	private JTextField nameSearchField;
	private JTextField idSearchField;
	private JPanel resultsPanel;
	private JScrollPane scroller;

	// These Hold the list of users to potentially be deleted (searched by the admin)
	ArrayList<DBObject> userList;
	private ArrayList<User> resultsList;

	public EditPrivilegesPanel() {

		super("Edit User Privileges");
		buttonListener = new ButtonListener();
		userList = new ArrayList<DBObject>();
		resultsList = new ArrayList<User>();

		JLabel nameSearchLabel = new JLabel("Search By Name:");
		JLabel idSearchLabel = new JLabel("Search By CWID:");

		nameSearchField = new JTextField();
		idSearchField = new JTextField();

		nameSearchField.setText("Search All");
		idSearchField.setText("Search All");

		nameSearchField.addActionListener(buttonListener);
		idSearchField.addActionListener(buttonListener);

		JPanel nameSearchPanel = new JPanel(new GridLayout(1, 3));
		JPanel idSearchPanel = new JPanel(new GridLayout(1, 3));

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

		resultsPanel = new JPanel(new GridLayout(0, 1));

		scroller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		
		scroller.setPreferredSize(new Dimension(scroller.getWidth(), scroller.getHeight()));
		scroller.setMaximumSize(scroller.getPreferredSize());
		scroller.getVerticalScrollBar().setUnitIncrement(13);

		TitledBorder border = new TitledBorder("Search Results");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);

		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);

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
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		add(scroller, c);

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		add(saveButton, c);

		c.weighty = 0.1;
		c.gridy = 4;
		add(new JPanel(), c);

	}

	// Clears all the text fields.
	public void clearFields() {
		resultsPanel.removeAll();
		nameSearchField.setText("");
		idSearchField.setText("");
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == saveButton) {
				if ( !resultsList.isEmpty() ) {
					// First check that they actually want to change the permissions.
					if ( confirm("Are you sure you want to change the permissions for these users?") ) {
						SystemAdministrator admin = (SystemAdministrator) Driver.getAccessTracker().getCurrentUser();
						for ( int i = 0; i < resultsPanel.getComponentCount(); ++i ) {
							JPanel panel = (JPanel) resultsPanel.getComponent(i);
							JLabel label = (JLabel) panel.getComponent(0);
							String s = label.getText();
							JCheckBox isAdminBox = (JCheckBox) panel.getComponent(1);
							JCheckBox isSystemAdminBox = (JCheckBox) panel.getComponent(2);

							boolean isAdmin = (isAdminBox.isSelected() || isSystemAdminBox.isSelected());
							boolean isSystemAdmin = isSystemAdminBox.isSelected();

							for ( User u : resultsList ) {
								if ( s.equals(u.getFirstName() + " " + u.getLastName() + " [" + u.getDepartment() + "]") ) {
									admin.updatePermissions(u, isAdmin, isSystemAdmin);
								}
							}
						}
						clearFields();
						resultsList.clear();
						userList.clear();
						repaint();
					}
				}

			} else if (e.getSource() == nameSearchGoButton || e.getSource() == idSearchGoButton |
					e.getSource() == nameSearchField || e.getSource() == idSearchField ) {
				resultsPanel.removeAll();
				resultsList.clear();
				userList.clear();
				repaint();

				if ( e.getSource() == nameSearchGoButton || e.getSource() == nameSearchField ) {

					if ( nameSearchField.getText().equals("Search All") || nameSearchField.getText().equals(""))
						userList = Driver.getAccessTracker().searchDatabase("Users", "CWID", "");
					else						
						userList = Driver.getAccessTracker().searchDatabaseForUser(nameSearchField.getText());

				} else {
					// e.getSource() == idSearchField 
					if ( idSearchField.getText().equals("Search All"))
						userList = Driver.getAccessTracker().searchDatabase("Users", "CWID", "");
					else {
						String input = idSearchField.getText();
						if ( input.length() > 7)
							input = BlasterCardListener.strip(input);
						userList = Driver.getAccessTracker().searchDatabase("Users", "CWID", idSearchField.getText());
					}
					idSearchField.setText("");
					nameSearchField.setText("");
				}

				for ( DBObject u : userList ) {
					
					if (!(((String) u.get("CWID")).equals(Driver.getAccessTracker().getCurrentUser().getCWID()))){
						User user = new User( (String) u.get("firstName"), (String) u.get("lastName"), (String) u.get("CWID"), (String) u.get("email"), (String) u.get("department"));

						boolean isAdmin = false;
						boolean isSystemAdmin = false;

						if ( u.get("isAdmin") != null ) {
							isAdmin = (boolean) u.get("isAdmin");
						}
						if ( u.get("isSystemAdmin") != null ) {
							isSystemAdmin = (boolean) u.get("isSystemAdmin");						
						}

						user.setAdmin(isAdmin);
						user.setSystemAdmin(isSystemAdmin);
						resultsList.add(user);
					}
				}
				
				// sorts the resultslist.
				Collections.sort(resultsList, new UserComparator());
				
				// We don't want the current SystemAdmin to be able to edit their own permissions
				resultsList.remove(currentUser);
				int row = 0;
				for ( User u : resultsList ) {
					JPanel userPanel = new JPanel(new GridLayout(1, 3));
					if ( (row % 2) == 0 ) {
						userPanel.setBackground(coolGray);
					}
					++row;
					
					JLabel userName = new JLabel(u.getFirstName() + " " + u.getLastName() + " [" + u.getDepartment() + "]");
					JCheckBox isAdmin = new JCheckBox("Administrator");
					JCheckBox isSystemAdmin = new JCheckBox("System Administrator");
					
					if ( u.isAdmin() ) {
						isAdmin.setSelected(true);
						if ( u.isSystemAdmin() ) {
							isSystemAdmin.setSelected(true);
						}
					}

					userName.setFont(smallFont);
					isAdmin.setFont(smallFont);
					isSystemAdmin.setFont(smallFont);

					userPanel.add(userName);
					userPanel.add(isAdmin);
					userPanel.add(isSystemAdmin);

					resultsPanel.add(userPanel);
				}
			}
		}
	}
}
