package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AR_MachinesPanel extends JPanel {

	JPanel contentPanel;
	JPanel buttonPanel;
	
	public AR_MachinesPanel() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		
		//contentPanel.setLayout(new GridLayout(6,4));
		buttonPanel.setLayout(new GridLayout(1,2));
		
		JTextField machineID = new JTextField();
		JLabel machineIDLabel = new JLabel("Machine ID:");
		
		machineID.setPreferredSize(new Dimension(200, 50));
		machineIDLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(machineIDLabel);
		contentPanel.add(machineID);
		
		JTextField machineName = new JTextField();
		JLabel machineNameLabel = new JLabel("Machine Name:");
		
		machineName.setPreferredSize(new Dimension(200, 50));
		machineNameLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(machineNameLabel);
		contentPanel.add(machineName);
		
		JButton addButton = new JButton("Add Machine");
		JButton removeButton = new JButton("Remove Machine");
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
}

