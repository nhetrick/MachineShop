package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import main.AccessTracker;
import main.InputReader;

public class RemoveUsersPanel extends ContentPanel {
	
	private JComboBox<String> searchParameter;
	private JLabel enterLabel;
	private JButton removeButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	private ComboBoxListener comboBoxListener;
	JTextField searchField;
	public enum SearchBy {CWID, NAME};
	public SearchBy searchBy;
	JPanel resultsPanel;
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
			} else if ( e.getSource() == goButton ) {
				switch (searchBy){
				case CWID:
					String input = searchField.getText();
					if (InputReader.isValidCWID(input)){
						int CWID = Integer.parseInt(input);
						Driver.getAccessTracker();
						DBCollection users = AccessTracker.getDatabase().getCollection("Users");
						DBCursor cursor = users.find(new BasicDBObject("CWID", CWID));
						resultsPanel.removeAll();
						while (cursor.hasNext()){
							BasicDBObject result = (BasicDBObject) cursor.next();
							String show = result.getString("CWID") +
									" " + result.getString("firstName") +
									" " + result.getString("lastName");
							JCheckBox cb = new JCheckBox(show); 
							cb.setFont(textFont);
							resultsPanel.add(cb);
						}
					} else {
						JOptionPane.showMessageDialog(resultsPanel, "Invalid CWID");
					}
					break;
				case NAME:
					final DBObject textSearchCommand = new BasicDBObject();
					textSearchCommand.put("text", "Users");
					textSearchCommand.put("search", searchField.getText());
					final CommandResult commandResult = AccessTracker.getDatabase().command(textSearchCommand);
					System.out.println(commandResult.toString());
					break;
				}

			}
		}
	}

}

