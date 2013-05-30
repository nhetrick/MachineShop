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

public class EditCertificationsPanel extends ContentPanel {
	
	private JButton saveButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	
	public EditCertificationsPanel() {
				
		super("Edit User's Machine Certifications");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.gridx = 0;
		c.gridy = 0;
		add(new JPanel(), c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		add(title, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.gridx = 2;
		c.gridy = 0;
		add(new JPanel(), c);
		
		JLabel cwidLabel = new JLabel("Enter user CWID:");
		JTextField cwidField = new JTextField();
		
		cwidField.setFont(textFont);
		cwidLabel.setFont(buttonFont);
		
		goButton = new JButton("Go");
		goButton.setFont(buttonFont);
		goButton.addActionListener(buttonListener);
		
		JPanel machinePanel = new JPanel(new GridLayout(1, 1));
		machinePanel.setBorder(new TitledBorder("User machines"));
		
		JPanel cwidPanel = new JPanel(new GridLayout(1, 3));
		
		cwidPanel.add(cwidLabel);
		cwidPanel.add(cwidField);
		cwidPanel.add(goButton);
		
		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 1;
		add(cwidPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(machinePanel, c);
		
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
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton ) {
				// TO DO
			} else if ( e.getSource() == goButton ) {
				
			}
		}
	}
}
