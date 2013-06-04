package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import GUI.MainGUI.SearchBy;

import main.AccessTracker;
import main.Administrator;
import main.InputReader;
import main.SystemAdministrator;
import main.User;


public class EditPrivilegesPanel extends ContentPanel {
	
	private JComboBox<SearchBy> searchParameter;
	private SearchBy searchBy;
	private JLabel enterLabel;
	private JButton saveButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	private ComboBoxListener comboBoxListener;
	private AdminCheckBoxListener adminCheckBoxListner;
	private SystemCheckBoxListener systemCheckBoxListner;
	private JTextField searchField;
	private JPanel resultsPanel;
	private User userInQuestion;
	private boolean newAdmin;
	private boolean newSystemAdmin;
	
	public EditPrivilegesPanel() {
				
		super("Edit User's Privileges");
		buttonListener = new ButtonListener();
		comboBoxListener = new ComboBoxListener();
		adminCheckBoxListner = new AdminCheckBoxListener();
		systemCheckBoxListner = new SystemCheckBoxListener();
		searchField = new JTextField();
		JLabel searchLabel = new JLabel("Search By:");
		
		searchLabel.setFont(buttonFont);
		
		searchParameter = new JComboBox<SearchBy>();
		searchParameter.setFont(textFont);
		
		searchParameter.addItem(SearchBy.NAME);
		searchParameter.addItem(SearchBy.CWID);
		
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
		resultsPanel.setLayout(new GridLayout(1, 3));
		
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
	
	public void clear(){
		resultsPanel.removeAll();
		searchField.setText("");
	}
	
	public void findUsers(){
		Font labelFont = new Font("SansSerif", Font.BOLD, 22);
		switch (searchBy){
		case CWID:
			String cwid = searchField.getText();
			clear();
			if (InputReader.isValidCWID(cwid)){
				//TODO refactor				
				User user = AccessTracker.findUserByCWID(cwid);
				userInQuestion = user;
				
				JCheckBox cb1 = new JCheckBox();
				cb1.setSelected(user.isAdmin());
				
				JCheckBox cb2 = new JCheckBox();
				
				if (user.isAdmin()) {
					Administrator adminUser = (Administrator) user;
					cb2.setSelected(adminUser.isSystemAdmin());
				}
				
				else
					cb2.setSelected(false);
					
				//TODO show CWID and name?
				String show = user.getCWID() +
						" " + user.getFirstName() +
						" " + user.getLastName();
				
				JPanel adminPanel = new JPanel(new GridLayout(3, 1));
				JPanel systemPanel = new JPanel(new GridLayout(3, 1));
				JPanel namePanel = new JPanel(new GridLayout(3, 1));
				
				JLabel name = new JLabel(show);
				name.setFont(labelFont);
				
				JLabel adminLabel = new JLabel("Admin");
				JLabel systemLabel = new JLabel("System Admin");
				JLabel nameLabel = new JLabel("Name and CWID");
				
				adminLabel.setFont(labelFont);
				systemLabel.setFont(labelFont);
				nameLabel.setFont(labelFont);
				
				adminPanel.add(adminLabel);
				systemPanel.add(systemLabel);
				namePanel.add(nameLabel);
				
				adminPanel.add(cb1);
				systemPanel.add(cb2);
				namePanel.add(name);
				
				adminPanel.add(new JPanel());
				systemPanel.add(new JPanel());
				namePanel.add(new JPanel ());
				
				cb1.addItemListener(adminCheckBoxListner);
				cb2.addItemListener(systemCheckBoxListner);
				
				resultsPanel.add(namePanel);
				resultsPanel.add(adminPanel);
				resultsPanel.add(systemPanel);
				
				searchField.setText("");
			} else {
				JOptionPane.showMessageDialog(resultsPanel, "Invalid CWID");
			}
			break;
		case NAME:

			break;
		}
	}
		
	private class ComboBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == searchParameter) {
				SearchBy parameter = (SearchBy) searchParameter.getSelectedItem();
				switch (parameter){
				case CWID:
					searchBy = SearchBy.CWID;
					enterLabel.setText("Enter CWID:");
					break;
				case NAME:
					searchBy = SearchBy.NAME;
					enterLabel.setText("Enter Name:");
					break;
				default:
					break;
				}
			}
		}
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton ) {
				// TODO
				if (newSystemAdmin) {
					((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addAdministrator(userInQuestion);
					((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addSystemAdministrator(userInQuestion);
				}
				else {
					if (newAdmin) {
						((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).addAdministrator(userInQuestion);
						((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).removeSystemAdministrator(userInQuestion);
					}
					else {
						((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).removeAdministrator(userInQuestion);
						((SystemAdministrator) Driver.getAccessTracker().getCurrentUser()).removeSystemAdministrator(userInQuestion);
					}
				}
			} else if ( e.getSource() == goButton ) {
				findUsers();
			}
		}
	}
	
	private class AdminCheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
//			JCheckBox check = (JCheckBox) e.getSource();
//			String name = check.getName();
//			System.out.println(name);
//			String cwid = name.substring(0,InputReader.CWID_LENGTH);
//			
//			userInQuestion = AccessTracker.findUserByCWID(Integer.parseInt(cwid));

			switch (e.getStateChange()){
			case ItemEvent.SELECTED:
				newAdmin = true;
				break;
			case ItemEvent.DESELECTED:
				newAdmin = false;
				break;
			}
		}
		
	}
	

	private class SystemCheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
//			JCheckBox check = (JCheckBox) e.getSource();
//			String name = check.getName();
//			String cwid = name.substring(0,InputReader.CWID_LENGTH);
//			
//			userInQuestion = AccessTracker.findUserByCWID(Integer.parseInt(cwid));

			switch (e.getStateChange()){
			case ItemEvent.SELECTED:
				newSystemAdmin = true;
				break;
			case ItemEvent.DESELECTED:
				newSystemAdmin = false;
				break;
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
