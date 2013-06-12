package GUI;

import java.awt.GridBagConstraints;
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

import main.Machine;
import main.SystemAdministrator;
import main.Tool;
import main.User;

public class LogInAnotherUserPanel extends ContentPanel {

	private JButton saveButton;
	private JButton goButton;
	private ButtonListener buttonListener;
	private JTextField cwidField;
	private String start = ";984000017";
	private String error = "E?";

	private JScrollPane scroller;
	private JPanel selectionPanel;
	private JPanel machines;
	private JPanel tool1;
	private JPanel tool2;
	private User user;

	public LogInAnotherUserPanel() {
		// All the fonts are in ContentPanel.
		super("Log In Another User");
		buttonListener = new ButtonListener();

		JLabel cwidLabel = new JLabel("Enter CWID:");
		cwidField = new JTextField();

		cwidField.setFont(textFont);
		cwidField.addActionListener(buttonListener);

		cwidLabel.setFont(borderFont);

		goButton = new JButton("Go");
		goButton.setFont(buttonFont);
		goButton.addActionListener(buttonListener);

		selectionPanel = new JPanel(new GridLayout(1, 3));
		
		machines = new JPanel();
		tool1 = new JPanel();
		tool2 = new JPanel();
		
		machines.setLayout(new GridLayout(0, 1));
		tool1.setLayout(new GridLayout(0, 1));
		tool2.setLayout(new GridLayout(0, 1));
		
		machines.setBorder(new TitledBorder("Select Machines"));
		tool1.setBorder(new TitledBorder("Check Out Tools"));
		tool2.setBorder(new TitledBorder("Return Tools"));
		
		selectionPanel.add(machines);
		selectionPanel.add(tool1);
		selectionPanel.add(tool2);
		
		scroller = new JScrollPane(selectionPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		TitledBorder border = new TitledBorder("Selection Options");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);

		JPanel cwidPanel = new JPanel(new GridLayout(1, 3));

		cwidPanel.add(cwidLabel);
		cwidPanel.add(cwidField);
		cwidPanel.add(goButton);

		saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
		saveButton.addActionListener(buttonListener);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 1;
		add(cwidPanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(scroller, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		add(saveButton, c);

		c.weighty = 0.1;
		c.gridy = 4;
		add(new JPanel(), c);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	private void showMachines() {
		for ( Machine m : user.getCertifiedMachines() ) {
			JCheckBox cb = new JCheckBox(m.getName() + " [" + m.getID() + "]");
			cb.setHorizontalAlignment(JCheckBox.LEFT);
			cb.setFont(borderFont);
			machines.add(cb);
		}
	}
	
	private void showTool1() {
		for ( Tool t : Driver.getAccessTracker().getTools()) {
			if (!t.isCheckedOut()) {
				JCheckBox cb = new JCheckBox(t.getName() + " [" + t.getUPC() + "]");
				cb.setHorizontalAlignment(JCheckBox.LEFT);
				cb.setFont(borderFont);
				tool1.add(cb);
			}
		}
	}
	
	private void showTool2() {
		for ( Tool t : user.getToolsCheckedOut()) {
			JCheckBox cb = new JCheckBox(t.getName() + " [" + t.getUPC() + "]");
			cb.setHorizontalAlignment(JCheckBox.LEFT);
			cb.setFont(borderFont);
			tool1.add(cb);
		}
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == saveButton) {
				if ( user == null ) {
					showMessage("Please enter the user's CWID.");
				}
				else {
					ArrayList<Machine> machinesSelected = new ArrayList<Machine>();

					for ( int i = 0; i < machines.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) machines.getComponent(i);
						if ( cb.isSelected() ) {
							String s = cb.getText();
							s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
							for ( Machine m : Driver.getAccessTracker().getMachines() ) {
								String ID = m.getID();
								if ( s.equals(ID) ) {
									machinesSelected.add(m);
								}
							}
						}
					}
					
					for (Machine m : Driver.getAccessTracker().getMachines()) {
						if (machinesSelected.contains(m))
							m.use();
					}
					
					ArrayList<Tool> tool1Selected = new ArrayList<Tool>();

					for ( int i = 0; i < tool1.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) tool1.getComponent(i);
						if ( cb.isSelected() ) {
							String s = cb.getText();
							s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
							for ( Tool t : Driver.getAccessTracker().getTools() ) {
								String UPC = t.getUPC();
								if ( s.equals(UPC) ) {
									tool1Selected.add(t);
								}
							}
						}
					}
					
					ArrayList<Tool> tool2Selected = new ArrayList<Tool>();

					for ( int i = 0; i < tool2.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) tool2.getComponent(i);
						if ( cb.isSelected() ) {
							String s = cb.getText();
							s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
							for ( Tool t : Driver.getAccessTracker().getTools() ) {
								String UPC = t.getUPC();
								if ( s.equals(UPC) ) {
									tool2Selected.add(t);
								}
							}
						}
					}
					
//					User current = Driver.getAccessTracker().getCurrentUser();
//					
//					Driver.getAccessTracker().processLogIn(user.getCWID());
//					user.getCurrentEntry().addMachinesUsed(machinesSelected);
//					user.getCurrentEntry().addToolsCheckedOut(tool1Selected);
//					user.getCurrentEntry().addToolsReturned(tool2Selected);
//					Driver.getAccessTracker().processLogOut(user.getCWID());
//					
//					Driver.getAccessTracker().setCurrentUser(current);
					
				}
				clearFields();

			} else if ( e.getSource() == goButton || e.getSource() == cwidField ) {
				String input = cwidField.getText();
				if (input.contains(start)) {
					// if the input starts with ;984000017, strips the next 8 digits, and set it as the input.
					input = input.split(start)[1].substring(0, 8);
					cwidField.setText(input);
				} else if (input.contains(error)) {
					showMessage("Card read error. Please try again.");
					cwidField.setText("");
					return;
				} else if (input.length() != 8){
					showMessage("Please enter an 8-digit CWID.");
					cwidField.setText("");
					return;
				}

				user = Driver.getAccessTracker().findUserByCWID(input);
				cwidField.setText(user.getFirstName() + " " + user.getLastName());

				showMachines();
				showTool1();
				showTool2();
			}
		}
	}

	// Clears all the text fields to empty, and set the user null.
	private void clearFields() {
		cwidField.setText("");
		user = null;
		for (int i = 0; i < selectionPanel.getComponentCount(); ++i ) {
			( (JCheckBox) selectionPanel.getComponent(i) ).setSelected(false);
		}
	}
}
