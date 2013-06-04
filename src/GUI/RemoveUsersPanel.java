package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

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
import main.InputReader;
import main.User;

public class RemoveUsersPanel extends ContentPanel {
	
	private JComboBox<SearchBy> searchParameter;
	private JLabel enterLabel;
	private JButton removeButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	private ComboBoxListener comboBoxListener;
	private CheckBoxListener checkBoxListener;
	private JTextField searchField;
	private JPanel resultsPanel;
	private SearchBy searchBy;
	private ArrayList<String> cwidsToRemove;
	
	public RemoveUsersPanel() {
		
		super("Remove Users");
		cwidsToRemove = new ArrayList<String>();
		buttonListener = new ButtonListener();
		comboBoxListener = new ComboBoxListener();
		checkBoxListener = new CheckBoxListener();
		
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
		resultsPanel.setLayout(new BorderLayout());
		
		removeButton = new JButton("Remove Users");
		removeButton.setFont(buttonFont);
		removeButton.addActionListener(new ButtonListener());
		
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
		add(removeButton, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
		
	}
	
	public void showConfirmPopup() {
		if (cwidsToRemove.size() == 0){
			JOptionPane.showConfirmDialog(this, "No users are selected.");
			return;
		}
		JOptionPane.showConfirmDialog(this, "Are you sure you want to remove these users?\n" + cwidsToRemove);
	}
	
	public void clear(){
		resultsPanel.removeAll();
		cwidsToRemove.clear();
		searchField.setText("");
	}
	
	public void findUsers(){
		switch (searchBy){
		case CWID:
			String cwid = searchField.getText();
			clear();
			if (InputReader.isValidCWID(cwid)){
				
				User user = Driver.getAccessTracker().findUserByCWID(cwid);
				String show = user.getCWID() +
						" " + user.getFirstName() +
						" " + user.getLastName();
				
				JCheckBox cb = new JCheckBox(show); 
				cb.setName(show);
				cb.setFont(textFont);
				cb.addItemListener(checkBoxListener);
				
				//TODO layout 
				resultsPanel.add(cb, BorderLayout.WEST);
			} else {
				JOptionPane.showMessageDialog(resultsPanel, "Invalid CWID");
			}
			break;
		case NAME:
			//TODO find by name

			break;
		default:
			// do nothing
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
			if (e.getSource() == removeButton) {
				showConfirmPopup();
				//TODO remove users. Even if logged in (in local memory). 
			} else if ( e.getSource() == goButton ) {
				findUsers();
			}
		}
	}

	private class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox check = (JCheckBox) e.getSource();
			String name = check.getName();
			String cwid = name.substring(0,InputReader.CWID_LENGTH);

			switch (e.getStateChange()){
			case ItemEvent.SELECTED:
				cwidsToRemove.add(cwid);
				break;
			case ItemEvent.DESELECTED:
				if (cwidsToRemove.contains(cwid)){
					cwidsToRemove.remove(cwid);
				}
				break;
			}
		}
	}
}

