package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.Machine;

public class EditPermissionsPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel buttonPanel;
	JComboBox<String> machineNames;
	JTextField cwidText;
	int CWID = 0;
	Machine permission;
	GridBagConstraints c;
	
	public EditPermissionsPanel() {
				
		Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
		Font titleFont = new Font("SansSerif", Font.BOLD, 38);
		Font textFont = new Font("SansSerif", Font.BOLD, 28);
		
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		
		JLabel title = new JLabel("Edit User Permissions");
		title.setFont(titleFont);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.gridx = 0;
		c.gridy = 0;
		add(new JPanel(), c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		add(title, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.gridx = 2;
		c.gridy = 0;
		add(new JPanel(), c);
		
		JPanel cwidPanel = new JPanel(new GridLayout(1, 3));
		JLabel cwidLabel = new JLabel("Enter user CWID:");
		JTextField cwidField = new JTextField();
		cwidField.setFont(textFont);
		JButton go = new JButton("Go");
		go.setFont(buttonFont);
		cwidLabel.setFont(buttonFont);
		
		cwidPanel.add(cwidLabel);
		cwidPanel.add(cwidField);
		cwidPanel.add(go);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 1;
		add(cwidPanel, c);

		JPanel machinePanel = new JPanel(new GridLayout(1, 1));
		machinePanel.setBorder(new TitledBorder("User machines"));
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(machinePanel, c);
		
		JButton saveButton = new JButton("Save");
		saveButton.setFont(buttonFont);
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
