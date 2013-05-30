package GUI;

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
import main.InputReader;
import main.User;

public class RemoveUsersPanel extends ContentPanel {
	
	private JComboBox<String> searchParameter;
	private JLabel enterLabel;
	private JButton removeButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	private ComboBoxListener comboBoxListener;
	private JTextField searchField;
	private JPanel resultsPanel;
	private enum SearchBy {CWID, NAME};
	private SearchBy searchBy;
	
	public RemoveUsersPanel() {
		
		super("Remove Users");
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
		JOptionPane.showConfirmDialog(this, "Are you sure you want to remove these users?");
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
			if (e.getSource() == removeButton) {
				showConfirmPopup();
				//TODO remove users. Even if logged in (in local memory). 
				//TODO checkBoxListener
			} else if ( e.getSource() == goButton ) {
				resultsPanel.removeAll();
				switch (searchBy){
				case CWID:
					String input = searchField.getText();
					if (InputReader.isValidCWID(input)){
						//TODO refactor
						int CWID = Integer.parseInt(input);
						
						User user = AccessTracker.findUserByCWID(CWID);
						String show = user.getCWID() +
								" " + user.getFirstName() +
								" " + user.getLastName();
						
						JCheckBox cb = new JCheckBox(show); 
						cb.setFont(textFont);
						
						resultsPanel.add(cb);
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

}

