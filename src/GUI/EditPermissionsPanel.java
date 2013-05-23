package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Machine;

public class EditPermissionsPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel buttonPanel;
	JComboBox<String> machineNames;
	JTextField cwidText;
	int CWID = 0;
	Machine permission;
	
	public EditPermissionsPanel() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		
		//contentPanel.setLayout(new GridLayout(6,4));
		buttonPanel.setLayout(new GridLayout(1,2));
		
		cwidText = new JTextField();
		JLabel cwidLabel = new JLabel("CWID:");
		
		cwidText.setPreferredSize(new Dimension(200, 50));
		cwidLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(cwidLabel);
		contentPanel.add(cwidText);
		
		machineNames = new JComboBox<String>();
		JLabel machineLabel = new JLabel("Machine:");
		
		//addMachineOptions();
		
		machineNames.setPreferredSize(new Dimension(200, 50));
		machineLabel.setPreferredSize(new Dimension(200, 50));
		
		//machineNames.addActionListener(new ComboBoxListener());
		
		contentPanel.add(machineLabel);
		contentPanel.add(machineNames);
		
		JButton addButton = new JButton("Add Permissions");
		JButton removeButton = new JButton("Remove Permissions");
		
		//addButton.addActionListener(new AddButtonListener());
		//removeButton.addActionListener(new RemoveButtonListener());
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
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
