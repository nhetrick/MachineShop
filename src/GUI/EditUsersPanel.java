package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EditUsersPanel extends ContentPanel {
	
	private JButton addButton;
	private JButton removeButton;
	private JButton lockButton;
	private JButton unlockButton;
	private ButtonListener buttonListener;
	
	public EditUsersPanel() {
		
		super("Edit Users");
		buttonListener = new ButtonListener();
		
		addButton = new JButton("Add Users");
		removeButton = new JButton("Remove Users");
		lockButton = new JButton("Lock Users");
		unlockButton = new JButton("Unlock Users");
		
		addButton.setFont(buttonFont);
		removeButton.setFont(buttonFont);
		lockButton.setFont(buttonFont);
		unlockButton.setFont(buttonFont);
		
		addButton.addActionListener(buttonListener);
		removeButton.addActionListener(buttonListener);
		lockButton.addActionListener(buttonListener);
		unlockButton.addActionListener(buttonListener);
		
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(lockButton);
		buttonPanel.add(unlockButton);
		
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
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.gridx = 1;
		c.gridy = 1;
		add(buttonPanel, c);
		
		c.gridy = 2;
		c.weighty = 0.6;
		add(new JPanel(), c);
		
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == addButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new AddUsersPanel(), BorderLayout.CENTER);
			} else if ( e.getSource() == removeButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new RemoveUsersPanel(), BorderLayout.CENTER);
			} else if ( e.getSource() == lockButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new LockUsersPanel(), BorderLayout.CENTER);
			} else if ( e.getSource() == unlockButton ) {
				removeAll();
				setLayout(new BorderLayout());
				add(new UnlockUsersPanel(), BorderLayout.CENTER);
			}
			repaint();
		}
	}
}
