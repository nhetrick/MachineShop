package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddMachinesPanel extends ContentPanel {
	
	private JButton saveButton;
	private ButtonListener buttonListener;
	
	public AddMachinesPanel() {
		
		super("Add a New Machine");
		buttonListener = new ButtonListener();
		
		JLabel machineNameLabel = new JLabel("Machine Name:");
		JLabel machineIDLabel = new JLabel("Machine ID:");
		
		machineNameLabel.setFont(buttonFont);
		machineIDLabel.setFont(buttonFont);
		
		JTextField machineNameField = new JTextField();
		JTextField machineIDField = new JTextField();
		
		machineNameField.setFont(textFont);
		machineIDField.setFont(textFont);
		
		JPanel machineNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel machineIDPanel = new JPanel(new GridLayout(1, 2));
		
		machineNamePanel.add(machineNameLabel);
		machineNamePanel.add(machineNameField);
				
		machineIDPanel.add(machineIDLabel);
		machineIDPanel.add(machineIDField);
		
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
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton ) {
				// TO DO
			}
		}
	}

}
