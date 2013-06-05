package GUI;

import java.awt.Dimension;
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
import main.Tool;

public class AddToolsPanel extends ContentPanel {
	
	private JButton saveButton;
	private ButtonListener buttonListener;	
	private JTextField toolNameField;
	private JTextField toolIDField;
	
	public AddToolsPanel() {
		
		super("Add a New Tool");
		buttonListener = new ButtonListener();
		
		JLabel toolNameLabel = new JLabel("Tool Name:");
		JLabel toolIDLabel = new JLabel("Tool ID (UPC):");
		
		toolNameLabel.setFont(buttonFont);
		toolIDLabel.setFont(buttonFont);
		
		toolNameField = new JTextField();
		toolIDField = new JTextField();
		
		toolNameField.setFont(textFont);
		toolIDField.setFont(textFont);
		
		toolNameField.setPreferredSize(new Dimension(toolNameField.getWidth(), toolNameField.getHeight()));
		toolNameField.setMaximumSize(toolNameField.getPreferredSize());
		
		toolIDField.setPreferredSize(new Dimension(toolIDField.getWidth(), toolIDField.getHeight()));
		toolIDField.setMaximumSize(toolIDField.getPreferredSize());
		toolIDField.addActionListener(buttonListener);
		
		JPanel toolNamePanel = new JPanel(new GridLayout(1, 2));
		JPanel toolIDPanel = new JPanel(new GridLayout(1, 2));
		
		toolNamePanel.add(toolNameLabel);
		toolNamePanel.add(toolNameField);
				
		toolIDPanel.add(toolIDLabel);
		toolIDPanel.add(toolIDField);
		
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
	
	private void saveTool() {
		String name = toolNameField.getText();
		String upc = toolIDField.getText();
		
		if (name.equals("") || upc.equals("")) {
			JOptionPane.showMessageDialog(this, "Please fill in both fields.");
		} else {
			Tool t = new Tool(name, upc);
			((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addTool(t);
		}
	}
	
	private void clearFields() {
		toolNameField.setText("");
		toolIDField.setText("");
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton || e.getSource() == toolIDField ) {
				saveTool();
				clearFields();
			}
		}
	}
	
}
