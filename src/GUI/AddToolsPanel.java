package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class AddToolsPanel extends ContentPanel {
	
	private JButton saveButton;
	private ButtonListener buttonListener;
	
	public AddToolsPanel() {
		
		super("Add a New Tool");
		buttonListener = new ButtonListener();
		
		JLabel toolNameLabel = new JLabel("Tool Name:");
		JLabel toolIDLabel = new JLabel("Tool ID (UPC):");
		
		toolNameLabel.setFont(buttonFont);
		toolIDLabel.setFont(buttonFont);
		
		JTextField toolNameField = new JTextField();
		JTextField toolIDField = new JTextField();
		
		toolNameField.setFont(textFont);
		toolIDField.setFont(textFont);
		
		JPanel toolNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel toolIDPanel = new JPanel(new GridLayout(1, 2));
		
		toolNamePanel.add(toolNameLabel);
		toolNamePanel.add(toolNameField);
				
		toolIDPanel.add(toolIDLabel);
		toolIDPanel.add(toolIDField);
		
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
		add(toolNamePanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(toolIDPanel, c);
		
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
