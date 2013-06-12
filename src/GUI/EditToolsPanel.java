package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditToolsPanel extends ContentPanel {
	
	private JButton addButton;
	private JButton removeButton;
	
	public EditToolsPanel() {

		super("Edit Tools");
		buttonListener = new ButtonListener();
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		addButton = new JButton("Add Tools");
		removeButton = new JButton("Remove Tools");
		
		addButton.setFont(buttonFont);
		removeButton.setFont(buttonFont);
		
		addButton.addActionListener(buttonListener);
		removeButton.addActionListener(buttonListener);
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2; 
		
		c.gridx = 1;
		c.gridy = 1;
		add(buttonPanel, c);
		
		c.gridy = 2;
		c.weighty = 0.7;
		add(new JPanel(), c);
		
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == addButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new AddToolsPanel(), BorderLayout.CENTER);
			} else if ( e.getSource() == removeButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new RemoveToolsPanel(), BorderLayout.CENTER);
				repaint();
			}
			repaint();
		}
	}	
}