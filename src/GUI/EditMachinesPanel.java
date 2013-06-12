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

public class EditMachinesPanel extends ContentPanel {
	
	private JButton addButton;
	private JButton removeButton;
	
	public EditMachinesPanel() {

		super("Edit Machines");
		buttonListener = new ButtonListener();
		
		addButton = new JButton("Add Machines");
		removeButton = new JButton("Remove Machines");
		
		addButton.setFont(buttonFont);
		removeButton.setFont(buttonFont);
		
		addButton.addActionListener(buttonListener);
		removeButton.addActionListener(buttonListener);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
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
			if (e.getSource() == addButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new AddMachinesPanel(), BorderLayout.CENTER);
			} else if (e.getSource() == removeButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new RemoveMachinesPanel(), BorderLayout.CENTER);
			}
			repaint();
		}
	}
}