package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class PermissionsPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel buttonPanel;
	
	public PermissionsPanel() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		
		//contentPanel.setLayout(new GridLayout(6,4));
		buttonPanel.setLayout(new GridLayout(1,2));
		
		JTextField cwidText = new JTextField();
		JLabel cwidLabel = new JLabel("CWID:");
		
		cwidText.setPreferredSize(new Dimension(200, 50));
		cwidLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(cwidLabel);
		contentPanel.add(cwidText);
		
		JComboBox<String> machineNames = new JComboBox<String>();
		JLabel machineLabel = new JLabel("Machine:");
		
		machineNames.setPreferredSize(new Dimension(200, 50));
		machineLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(machineLabel);
		contentPanel.add(machineNames);
		
		JButton addButton = new JButton("Add Permissions");
		JButton removeButton = new JButton("Remove Permissions");
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
}
