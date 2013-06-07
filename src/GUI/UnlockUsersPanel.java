package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.SystemAdministrator;
import main.User;

import com.mongodb.DBObject;

public class UnlockUsersPanel extends ContentPanel {
	
	private JButton unlockButton;
	private ButtonListener buttonListener;
	private JButton nameSearchGoButton;
	private JButton idSearchGoButton;
	private JTextField nameSearchField;
	private JTextField idSearchField;
	private JPanel resultsPanel;
	private JScrollPane scroller;
	
	// Holds the list of users to potentially be unlocked (searched by the admin)
	private ArrayList<User> resultsList;
	
	public UnlockUsersPanel() {
		
		super("Unlock Users");
		buttonListener = new ButtonListener();
		resultsList = new ArrayList<User>();
		
		JLabel nameSearchLabel = new JLabel("Search By Name:");
		JLabel idSearchLabel = new JLabel("Search By CWID:");
		
		nameSearchField = new JTextField();
		idSearchField = new JTextField();
		
		nameSearchField.setText("Search All");
		idSearchField.setText("Search All");
		
		nameSearchField.addActionListener(buttonListener);
		idSearchField.addActionListener(buttonListener);
		
		JPanel nameSearchPanel = new JPanel(new GridLayout(1, 3));
		JPanel idSearchPanel = new JPanel(new GridLayout(1, 3));
		
		JPanel dataPanel = new JPanel(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		dataPanel.add(nameSearchPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		dataPanel.add(idSearchPanel, c);
		
		nameSearchGoButton = new JButton("Go");
		idSearchGoButton = new JButton("Go");
		
		nameSearchGoButton.addActionListener(buttonListener);
		idSearchGoButton.addActionListener(buttonListener);
		
		nameSearchLabel.setFont(buttonFont);
		idSearchLabel.setFont(buttonFont);
		nameSearchField.setFont(textFont);
		idSearchField.setFont(textFont);
		nameSearchGoButton.setFont(buttonFont);
		idSearchGoButton.setFont(buttonFont);
		
		nameSearchPanel.add(nameSearchLabel);
		nameSearchPanel.add(nameSearchField);
		nameSearchPanel.add(nameSearchGoButton);
		
		idSearchPanel.add(idSearchLabel);
		idSearchPanel.add(idSearchField);
		idSearchPanel.add(idSearchGoButton);
		
		resultsPanel = new JPanel(new GridLayout(0, 1));
		
		scroller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		scroller.setPreferredSize(new Dimension(scroller.getWidth(), scroller.getHeight()));
		scroller.setMaximumSize(scroller.getPreferredSize());
		scroller.getVerticalScrollBar().setUnitIncrement(13);
		
		TitledBorder border = new TitledBorder("Search Results");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);
		
		unlockButton = new JButton("Unlock Users");
		unlockButton.setFont(buttonFont);
		unlockButton.addActionListener(buttonListener);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		add(dataPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		add(scroller, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		add(unlockButton, c);
		
		c.weighty = 0.1;
		c.gridy = 4;
		add(new JPanel(), c);
		
	}
	
	public boolean confirmSubmission() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to unlock these users?") == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == unlockButton) {
				boolean noBoxesChecked = true;
				for ( int i = 0; i < resultsPanel.getComponentCount(); ++i ) {
					JCheckBox cb = (JCheckBox) resultsPanel.getComponent(i);
					if ( cb.isSelected() ) {
						noBoxesChecked = false;
					}
				}
				ArrayList<String> unlocked = new ArrayList<String>();
				// First check that they actually want to unlock the users.
				if ( !noBoxesChecked && confirmSubmission()) {
					ArrayList<JCheckBox> unlockedBoxes = new ArrayList<JCheckBox>();
					SystemAdministrator admin = (SystemAdministrator) Driver.getAccessTracker().getCurrentUser();
					for ( int i = 0; i < resultsPanel.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) resultsPanel.getComponent(i);
						if ( cb.isSelected() ) {
							for ( User u : resultsList ) {
								String s = cb.getText();
								s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
								String CWID = u.getCWID();
								if ( s.equals(CWID) ) {
									unlocked.add(u.getFirstName() + " " + u.getLastName() + " [" + CWID + "]");
									unlockedBoxes.add(cb);
									admin.unlockUser(u);
								}
							}
						}
					}
					
					resultsList.clear();
					for ( JCheckBox cb : unlockedBoxes ) {
						resultsPanel.remove(cb);
					}
					repaint();
					
					String message = "You Unlocked:\n\n";
					for ( String s : unlocked ) {
						message += s + "\n";
					}
					showMessage(message);
				}
			} else if (e.getSource() == nameSearchGoButton || e.getSource() == idSearchGoButton |
					   e.getSource() == nameSearchField || e.getSource() == idSearchField ) {
				
				resultsPanel.removeAll();
				resultsList.clear();
				repaint();
				ArrayList<DBObject> userList = new ArrayList<DBObject>();
				
				if ( e.getSource() == nameSearchGoButton || e.getSource() == nameSearchField ) {
					
					if ( nameSearchField.getText().equals("Search All") || nameSearchField.getText().equals(""))
						userList = Driver.getAccessTracker().searchDatabase("Users", "CWID", "");
					else						
						userList = Driver.getAccessTracker().searchDatabaseForUser(nameSearchField.getText());
					
				} else {
					if ( idSearchField.getText().equals("Search All"))
						userList = Driver.getAccessTracker().searchDatabase("Users", "CWID", "");
					else
						userList = Driver.getAccessTracker().searchDatabase("Users", "CWID", idSearchField.getText());
					
				}
				
				for ( DBObject u : userList ) {
					User user = new User( (String) u.get("firstName"), (String) u.get("lastName"), (String) u.get("CWID"), (String) u.get("email"), (String) u.get("department"));
					boolean locked = false;
					if (u.get("locked") != null)
							locked = (boolean) u.get("locked");
					if (locked)
						resultsList.add(user);
				}
								
				for ( User u : resultsList ) {
					JCheckBox cb = new JCheckBox(u.getFirstName() + " " + u.getLastName() + " [" + u.getCWID() + "]");
					cb.setHorizontalAlignment(JCheckBox.LEFT);
					cb.setFont(buttonFont);
					resultsPanel.add(cb);
				}				
			}
		}
	}

}

