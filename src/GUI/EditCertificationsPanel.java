package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.Machine;
import main.MachineComparator;
import main.SystemAdministrator;
import main.User;

public class EditCertificationsPanel extends ContentPanel {

	private JButton saveButton;
	private JButton goButton;
	private JTextField cwidField;
	private String start = ";984000017";
	private String error = "E?";

	private JScrollPane scroller;
	private JPanel permissionsPanel;
	private User user;

	public EditCertificationsPanel() {
		
		super("Edit User's Machine Certifications");
		buttonListener = new ButtonListener();

		JLabel cwidLabel = new JLabel("Enter CWID:");
		cwidField = new JTextField();

		cwidField.setFont(textFont);
		cwidField.addActionListener(buttonListener);

		cwidLabel.setFont(borderFont);

		goButton = new JButton("Go");
		goButton.setFont(buttonFont);
		goButton.addActionListener(buttonListener);

		permissionsPanel = new JPanel(new GridLayout(0, 2));

		// sorts the machines list
		ArrayList<Machine> sorted = new ArrayList<Machine>();
		sorted = Driver.getAccessTracker().getMachines();
		Collections.sort(sorted, new MachineComparator());
		
		for ( Machine m : sorted ) {
			JCheckBox cb = new JCheckBox(m.getName() + " [" + m.getID() + "]");
			cb.setHorizontalAlignment(JCheckBox.LEFT);
			cb.setFont(borderFont);
			permissionsPanel.add(cb);
		}

		scroller = new JScrollPane(permissionsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		TitledBorder border = new TitledBorder("User Certifications");
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
	
	// Clears all the text fields, and set the user to null.
	private void clearFields() {
		cwidField.setText("");
		user = null;
		for (int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
			( (JCheckBox) permissionsPanel.getComponent(i) ).setSelected(false);
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
					ArrayList<Machine> machines = new ArrayList<Machine>();
					
					for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
						if ( cb.isSelected() ) {
							for ( Machine m : Driver.getAccessTracker().getMachines() ) {
								String s = cb.getText();
								s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
								String ID = m.getID();
								if ( s.equals(ID) ) {
									machines.add(m);
								}
							}
						}
					}
					SystemAdministrator admin = (SystemAdministrator) Driver.getAccessTracker().getCurrentUser();
					admin.updateCertifications(user, machines);
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

				for ( int i = 0; i < permissionsPanel.getComponentCount(); ++i ) {
					JCheckBox cb = (JCheckBox) permissionsPanel.getComponent(i);
					String s = cb.getText();
					s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
					if (user.getCertifiedMachines().isEmpty())
						cb.setSelected(false);
					else {
						for ( Machine m : user.getCertifiedMachines() ) {
							if ( m.getID().equals(s) ) {
								cb.setSelected(true);
								break;
							} else {
								cb.setSelected(false);
							}
						}
					}
				}
			}
		}
	}

}
