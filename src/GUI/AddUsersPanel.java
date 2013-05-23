package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class AddUsersPanel extends JPanel {
	
	GridBagConstraints c;
	
	public AddUsersPanel() {
		
		Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
		Font titleFont = new Font("SansSerif", Font.BOLD, 38);
		
		JLabel title = new JLabel("Add New User");
		title.setFont(titleFont);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		JLabel lastNameLabel = new JLabel("Last Name:");
		JLabel cwidLabel = new JLabel("CWID:");
		
		firstNameLabel.setFont(buttonFont);
		lastNameLabel.setFont(buttonFont);
		cwidLabel.setFont(buttonFont);
		
		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();
		JTextField cwidField = new JTextField();
		
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
		
		JButton saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		
		setLayout(new GridBagLayout());

		c = new GridBagConstraints();
		
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

}
