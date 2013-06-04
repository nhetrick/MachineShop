package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.Machine;
import main.SystemAdministrator;
import main.User;

public class AddUsersPanel extends ContentPanel {
	
	private JButton saveButton;
	private ButtonListener buttonListener;
	
	JTextField firstNameField;
	JTextField lastNameField;
	JTextField cwidField;
	
	public AddUsersPanel() {
		
		super("Add a New User");
		buttonListener = new ButtonListener();
		
		JLabel firstNameLabel = new JLabel("First Name:");
		JLabel lastNameLabel = new JLabel("Last Name:");
		JLabel cwidLabel = new JLabel("CWID:");
		
		firstNameLabel.setFont(buttonFont);
		lastNameLabel.setFont(buttonFont);
		cwidLabel.setFont(buttonFont);
		
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		cwidField = new JTextField();
		
		firstNameField.setFont(textFont);
		lastNameField.setFont(textFont);
		cwidField.setFont(textFont);
		
		JPanel firstNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel lastNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel cwidPanel = new JPanel(new GridLayout(1, 2));
		
		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(firstNameField);
				
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameField);
		
		cwidPanel.add(cwidLabel);
		cwidPanel.add(cwidField);
		
		JPanel permissionsPanel = new JPanel();
		permissionsPanel.setBorder(new TitledBorder("Add User Permissions"));
		
		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(new JPanel(), c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.8;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		add(title, c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 0;
		add(new JPanel(), c);
		
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
		add(cwidPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		add(permissionsPanel, c);
		
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
		String first = firstNameField.getText();
		String last = lastNameField.getText();
		String cwid = cwidField.getText();
		if (first != null && last != null && cwid != null) {
			User u = new User(first, last, cwid);
			((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addUser(u);
		}	
	}
	
	private void clearFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		cwidField.setText("");
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton ) {
				saveUser();
				clearFields();
			}
		}
	}

}
