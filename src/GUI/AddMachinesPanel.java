package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.SystemAdministrator;
import main.Machine;

public class AddMachinesPanel extends ContentPanel {
	
	private JButton saveButton;	
	private JTextField machineNameField;
	private JTextField machineIDField;
	
	public AddMachinesPanel() {

		super("Add a New Machine");
		buttonListener = new ButtonListener();
		
		JLabel machineNameLabel = new JLabel("Machine Name:");
		JLabel machineIDLabel = new JLabel("Machine ID:");
		
		machineNameLabel.setFont(buttonFont);
		machineIDLabel.setFont(buttonFont);
		
		machineNameField = new JTextField();
		machineIDField = new JTextField();
		
		machineNameField.setFont(textFont);
		machineIDField.setFont(textFont);
		
		JPanel machineNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel machineIDPanel = new JPanel(new GridLayout(1, 2));
		
		machineNamePanel.add(machineNameLabel);
		machineNamePanel.add(machineNameField);
				
		machineIDPanel.add(machineIDLabel);
		machineIDPanel.add(machineIDField);
		
		machineIDField.addActionListener(buttonListener);
		
		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 1;
		add(machineNamePanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(machineIDPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		add(saveButton, c);
		
		c.weighty = 0.6;
		c.gridy = 6;
		add(new JPanel(), c);
		
	}
	
	// Saves the machine to database.
	private void saveMachine() {
		String name = machineNameField.getText();
		String id = machineIDField.getText();
		
		// Adding machines needs both name and ID of the machine being added.
		if (name.equals("") || id.equals("")) {
			JOptionPane.showMessageDialog(this, "Please fill in both fields.");
		} else {
			Machine t = new Machine(name, id);
			((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addMachine(t);
		}
	}
	
	// Clears all the text fields
	private void clearFields() {
		machineNameField.setText("");
		machineIDField.setText("");
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton || e.getSource() == machineIDField ) {
				saveMachine();
				clearFields();
			}
		}
	}
	
}
