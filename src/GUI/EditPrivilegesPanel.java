package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.AccessTracker;
import main.Administrator;
import main.InputReader;
import main.User;


public class EditPrivilegesPanel extends ContentPanel {
	
	private JComboBox<String> searchParameter;
	private SearchBy searchBy;
	private enum SearchBy {CWID, NAME};
	private JLabel enterLabel;
	private JButton saveButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	private ComboBoxListener comboBoxListener;
	private JTextField searchField;
	private JPanel resultsPanel;
	
	public EditPrivilegesPanel() {
				
		super("Edit User's Privileges");
		buttonListener = new ButtonListener();
		comboBoxListener = new ComboBoxListener();
		searchField = new JTextField();
		JLabel searchLabel = new JLabel("Search By:");
		
		searchLabel.setFont(buttonFont);
		
		searchParameter = new JComboBox<String>();
		searchParameter.setFont(textFont);
		
		searchParameter.addItem("Name");
		searchParameter.addItem("CWID");
		
		searchParameter.addActionListener(comboBoxListener);
		
		enterLabel = new JLabel("Enter name:");
		goButton = new JButton("Go");
		
		goButton.addActionListener(buttonListener);
		
		enterLabel.setFont(buttonFont);
		searchField.setFont(textFont);
		goButton.setFont(buttonFont);
		
		JPanel parameterPanel = new JPanel(new GridBagLayout());
		JPanel searchPanel = new JPanel(new GridLayout(1, 3));
		
		searchPanel.add(enterLabel);
		searchPanel.add(searchField);
		searchPanel.add(goButton);
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new TitledBorder("Search Results"));
		resultsPanel.setLayout(new GridLayout(2, 3));
		
		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(new ButtonListener());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.27;
		c.gridx = 0;
		c.gridy = 0;
		parameterPanel.add(searchLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.73;
		c.gridx = 1;
		c.gridy = 0;
		parameterPanel.add(searchParameter, c);
		
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
		add(parameterPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(searchPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 3;
		add(resultsPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		add(saveButton, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
	
	}
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == searchParameter) {
				String parameter = searchParameter.getSelectedItem().toString();
				if (parameter == "CWID") {
					searchBy = SearchBy.CWID;
					enterLabel.setText("Enter CWID:");
				} else if (parameter == "Name") {
					searchBy = SearchBy.NAME;
					enterLabel.setText("Enter Name:");
				}
			}
		}
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton ) {
				// TODO
			} else if ( e.getSource() == goButton ) {
				resultsPanel.removeAll();
				switch (searchBy){
				case CWID:
					String input = searchField.getText();
					if (InputReader.isValidCWID(input)){
						//TODO refactor
						int CWID = Integer.parseInt(input);
						
						User user = AccessTracker.findUserByCWID(CWID);
						
						System.out.println(user);
						System.out.println(user.isAdmin());
						
						JCheckBox cb1 = new JCheckBox();
						cb1.setSelected(user.isAdmin());
						cb1.setFont(textFont);
						
						JCheckBox cb2 = new JCheckBox();
						
						if (user.isAdmin()) {
							Administrator adminUser = (Administrator) user;
							cb2.setSelected(adminUser.isSystemAdmin());
							cb2.setFont(textFont);
						}
						
						else
							cb2.setSelected(false);
							
						
						//TODO show CWID and name?
						String show = user.getCWID() +
								" " + user.getFirstName() +
								" " + user.getLastName();
						
						JPanel adminPanel = new JPanel(new GridLayout());
						JPanel systemPanel = new JPanel(new GridLayout());
						JPanel namePanel = new JPanel(new GridLayout());
						
						JLabel name = new JLabel(show);
						name.setFont(textFont);
						
						JLabel adminLabel = new JLabel("Admin");
						JLabel systemLabel = new JLabel("System Admin");
						JLabel nameLabel = new JLabel("Name and CWID");
						
						adminLabel.setFont(textFont);
						systemLabel.setFont(textFont);
						nameLabel.setFont(textFont);
						
						adminPanel.add(adminLabel);
						systemPanel.add(systemLabel);
						namePanel.add(nameLabel);
						
						adminPanel.add(cb1);
						systemPanel.add(cb2);
						namePanel.add(name);
						
						resultsPanel.add(adminPanel);
						resultsPanel.add(systemPanel);
						resultsPanel.add(namePanel);
						
						searchField.setText("");
					} else {
						JOptionPane.showMessageDialog(resultsPanel, "Invalid CWID");
					}
					break;
				case NAME:

					break;
				}

			}
		}
	}
	
//	private void addMachineOptions() {
//		machineNames.addItem("");
//		ArrayList<Machine> machines = Driver.getAccessTracker().getMachines();
//		for (Machine m:machines) {
//			machineNames.addItem(m.getName());
//		}
//	}
//	
//	private void getDataEntered() {
//		String id = cwidText.getText();
//		CWID = Integer.parseInt(id);
//	}
//	
//	private class ComboListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			String machine = "";
//			if (e.getSource() == machineNames) {
//				machine = machineNames.getSelectedItem().toString();
//			} 
//			permission = Driver.getAccessTracker().getMachineByName(machine);
//		}
//		
//	}
//	
//	private class AddButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			getDataEntered();
//			if (!(CWID == 0 || permission == null)) {
//				System.out.println("Add: " + CWID + permission.getName());
//			}
//		}
//	}
//	
//	private class RemoveButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			getDataEntered();
//			if (!(CWID == 0 || permission == null)) {
//				System.out.println("Remove: " + CWID + permission.getName());
//			}
//		}
//	}
}
