package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.Machine;
import main.Tool;
import main.User;

public class UserGUI extends JPanel {
	
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JPanel machinePermissions;
	private JPanel checkedOutTools;
	private User currentUser;
	
	private Font buttonFont;
	
	public UserGUI(User user) {
		currentUser = user;
		
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(4, 1));
		machinePermissions = new JPanel();
		checkedOutTools = new JPanel();
				
		machinePermissions.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "My Machines"));
		machinePermissions.setLayout(new GridLayout(10, 5));
		
		checkedOutTools.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Checked-out Tools"));
		checkedOutTools.setLayout(new GridLayout(10, 5));
	
		displayUserMachinePermissions();
		displayUserCheckedOutTools();
		
		contentPanel.add(machinePermissions);		
		contentPanel.add(checkedOutTools);
		
		centerPanel.add(contentPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton logOut = new JButton();
		JButton checkOutTools = new JButton();
		JButton selectMachine = new JButton();
		JButton returnTools = new JButton();
		
		logOut.setText("Log Out");
		selectMachine.setText("Select Machines");
		checkOutTools.setText("Check Out Tools");
		returnTools.setText("Return Tools");
		
		logOut.setFont(buttonFont);
		checkOutTools.setFont(buttonFont);
		selectMachine.setFont(buttonFont);
		returnTools.setFont(buttonFont);
		
		buttonPanel.add(selectMachine);
		buttonPanel.add(checkOutTools);
		buttonPanel.add(returnTools);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		
	}
	
	private void displayUserMachinePermissions() {
		ArrayList<Machine> machines = currentUser.getCertifiedMachines();
		for (Machine m:machines) {
			JCheckBox machine = new JCheckBox(m.getName());
			machine.setFont(new Font("SansSerif", Font.BOLD, 20));
			machinePermissions.add(machine);
		}
	}
	
	private void displayUserCheckedOutTools() {
		ArrayList<Tool> tools = currentUser.getToolsCheckedOut();
		for (Tool t:tools) {
			JCheckBox tool = new JCheckBox(t.getName());
			tool.setFont(new Font("SansSerif", Font.BOLD, 20));
			checkedOutTools.add(tool);
		}
	}
	
}
