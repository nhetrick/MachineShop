package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.sound.sampled.ReverbType;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.Machine;
import main.Tool;
import main.User;

public class UserGUI extends JPanel {
	
	private static JPanel centerPanel;
	private static JPanel contentPanel;
	private static JPanel buttonPanel;
	private JPanel machinePermissions;
	private static JPanel checkedOutTools;
	private static UserCheckoutToolPanel userCheckoutToolPanel;
	private static User currentUser;
	
	private JButton logOut = new JButton();
	private JButton checkOutTools = new JButton();
	private JButton selectMachine = new JButton();
	private JButton returnTools = new JButton();
	private JButton done = new JButton();
	
	private ButtonListener buttonListener;
	
	private Font buttonFont;
	private ArrayList<Machine> selectedMachines;
	private ArrayList<Tool> toolsToReturn;
	private static ToolCheckBoxListener toolCheckBoxListener;
	
	public UserGUI(User user) {
		currentUser = user;
		
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		buttonListener = new ButtonListener();
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(5, 1));
		userCheckoutToolPanel = new UserCheckoutToolPanel();
		machinePermissions = new JPanel();
		checkedOutTools = new JPanel();
		
		selectedMachines = new ArrayList<Machine>();
		toolsToReturn = new ArrayList<Tool>();
				
		toolCheckBoxListener = new ToolCheckBoxListener();
		
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
		
		logOut.setText("Log Out");
		selectMachine.setText("Select Machines");
		checkOutTools.setText("Check Out Tools");
		returnTools.setText("Return Tools");
		done.setText("Done");
		
		logOut.setFont(buttonFont);
		checkOutTools.setFont(buttonFont);
		selectMachine.setFont(buttonFont);
		returnTools.setFont(buttonFont);
		done.setFont(buttonFont);
		
		buttonPanel.add(selectMachine);
		buttonPanel.add(checkOutTools);
		buttonPanel.add(returnTools);
		buttonPanel.add(done);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		
		checkOutTools.addActionListener(buttonListener);
		selectMachine.addActionListener(buttonListener);
		returnTools.addActionListener(buttonListener);
		
		logOut.addActionListener(new ListenerHelpers.LogOutListner());

		done.addActionListener(new ListenerHelpers.DoneListener());

	}
	
	public static void returnHome(){
		centerPanel.remove(userCheckoutToolPanel);
		centerPanel.add(contentPanel, BorderLayout.CENTER);
		
		displayUserCheckedOutTools();
		centerPanel.repaint();
	}
	
	private void displayUserMachinePermissions() {
		machinePermissions.removeAll();
		ArrayList<Machine> machines = currentUser.getCertifiedMachines();
		for (Machine m:machines) {
			String show = m.getName() + " (" + m.getID() + ")";
			JCheckBox machine = new JCheckBox(show);
			machine.setName(m.getID());
			machine.setFont(new Font("SansSerif", Font.BOLD, 20));
			if (selectedMachines.contains(m)){
				machine.setEnabled(false);
			}
			machine.addItemListener(new MachineCheckBoxListener());
			machinePermissions.add(machine);
		}
	}
	
	private static void displayUserCheckedOutTools() {
		checkedOutTools.removeAll();
		ArrayList<Tool> tools = currentUser.getToolsCheckedOut();
		for (Tool t:tools) {
			String show = t.getName() + " (" + t.getUPC() + ")";
			JCheckBox tool = new JCheckBox(show);
			tool.setName(t.getUPC());
			tool.setFont(new Font("SansSerif", Font.BOLD, 20));
			tool.addItemListener(toolCheckBoxListener);
			checkedOutTools.add(tool);
		}
	}	
	
	public void selectMachines(){
		
		Driver.getAccessTracker().getCurrentUser().getCurrentEntry().addMachinesUsed(selectedMachines);
		
		String message = "You are using:\n\n";
		for ( Machine m : selectedMachines ) {
			message += m + "\n";
		}
		
		JOptionPane.showMessageDialog(this, message);
		
		displayUserMachinePermissions();
		//selectedMachines.clear();
	}
	
	public void returnTools(){
		
		if (toolsToReturn.size() == 0){
			JOptionPane.showMessageDialog(this, "No tools selected");
			return;
		}
		
		Driver.getAccessTracker().getCurrentUser().getCurrentEntry().addToolsReturned(toolsToReturn);
		
		Driver.getAccessTracker().getCurrentUser().returnTools(toolsToReturn);
		displayUserCheckedOutTools();
		centerPanel.repaint();
		
		toolsToReturn.clear();
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == checkOutTools ) {
				centerPanel.remove(contentPanel);

				centerPanel.add(userCheckoutToolPanel);
				centerPanel.repaint();
			} else if ( e.getSource() == selectMachine ){
				selectMachines();
			} else if ( e.getSource() == returnTools ){
				returnTools();
			}
		}
	}
	
	private class MachineCheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox check = (JCheckBox) e.getSource();
			String id = check.getName();
			
			Machine m = Driver.getAccessTracker().getMachineByID(id);
			
			switch (e.getStateChange()){
			case ItemEvent.SELECTED:
				selectedMachines.add(m);
				break;
			case ItemEvent.DESELECTED:
				if (selectedMachines.contains(m)){
					selectedMachines.remove(m);
				}
				break;
			}
		}
	}
	
	private class ToolCheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox check = (JCheckBox) e.getSource();
			String upc = check.getName();
			
			Tool t = Driver.getAccessTracker().getToolByUPC(upc);
			
			switch (e.getStateChange()){
			case ItemEvent.SELECTED:
				toolsToReturn.add(t);
				break;
			case ItemEvent.DESELECTED:
				if (toolsToReturn.contains(t)){
					toolsToReturn.remove(t);
				}
				break;
			}
		}
	}
}
