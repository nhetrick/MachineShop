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

public class EditUsersPanel extends JPanel {
	
	JPanel buttonPanel;
	GridBagConstraints c;
	
	public EditUsersPanel() {
		
		c = new GridBagConstraints();
		
		setLayout(new GridBagLayout());
		
		Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
		Font titleFont = new Font("SansSerif", Font.BOLD, 38);
		
		buttonPanel = new JPanel(new GridLayout(1, 2));
		
		JLabel title = new JLabel("Edit Users", JLabel.CENTER);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setFont(titleFont);
		
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
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 0;
		add(new JPanel(), c);
		
		JButton addButton = new JButton("Add Users");
		JButton removeButton = new JButton("Remove Users");
		
		addButton.setFont(buttonFont);
		removeButton.setFont(buttonFont);
		
		addButton.addActionListener(new AddButtonListener());
		removeButton.addActionListener(new RemoveButtonListener());
		
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
	
	private class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			removeAll();
			setLayout(new BorderLayout());
			add(new AddUsersPanel(), BorderLayout.CENTER);
			repaint();
		}
	}
	
	private class RemoveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			removeAll();
			setLayout(new BorderLayout());
			add(new RemoveUsersPanel(), BorderLayout.CENTER);
			repaint();
		}
	}
}
