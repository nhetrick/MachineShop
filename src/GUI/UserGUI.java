package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import main.Machine;
import main.MachineComparator;
import main.Tool;
import main.ToolComparator;
import main.User;

public class UserGUI extends MainPanel {

	private JPanel machinesPanel;
	private JPanel checkedOutToolsPanel;
	private CheckoutToolsPanel checkoutToolsPanel;

	private JButton checkOutToolsButton = new JButton("Check Out Tools");
	private JButton selectMachinesButton = new JButton("Select Machines");
	private JButton returnToolsButton = new JButton("Return Tools");
	private JButton doneButton = new JButton("Start Working");
	private JButton logOutButton = new JButton("Log Out");

	private JScrollPane machinesScroller;
	private JScrollPane toolsScroller;

	private ArrayList<Machine> oldMachines;
	private ArrayList<Machine> selectedMachines;
	private ArrayList<Tool> toolsToReturn;

	private MachineCheckBoxListener machineCheckBoxListener;
	private ToolCheckBoxListener toolCheckBoxListener;

	private boolean isCheckingOutTools = false;

	public UserGUI(User user) {
		
		super();
		buttonListener = new ButtonListener();
		machineCheckBoxListener = new MachineCheckBoxListener();
		toolCheckBoxListener = new ToolCheckBoxListener();

		contentPanel = new JPanel(new GridLayout(2, 1));
		checkoutToolsPanel = new CheckoutToolsPanel();
		machinesPanel = new JPanel();
		checkedOutToolsPanel = new JPanel();

		selectedMachines = new ArrayList<Machine>();
		oldMachines = user.getCurrentEntry().getMachinesUsed();
		toolsToReturn = new ArrayList<Tool>();

		machinesPanel.setLayout(new GridLayout(0, 2));
		checkedOutToolsPanel.setLayout(new GridLayout(0, 1));

		displayUserMachinePermissions();
		displayUserCheckedOutTools();

		machinesScroller = new JScrollPane(machinesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);		
		TitledBorder machineBorder = new TitledBorder("My Machines");
		machineBorder.setTitleFont(borderFont);
		machinesScroller.setBorder(machineBorder);

		toolsScroller = new JScrollPane(checkedOutToolsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);		
		TitledBorder toolBorder = new TitledBorder("Checked out Tools");
		toolBorder.setTitleFont(borderFont);
		toolsScroller.setBorder(toolBorder);

		contentPanel.add(machinesScroller);		
		contentPanel.add(toolsScroller);

		buttons.add(selectMachinesButton);
		buttons.add(checkOutToolsButton);
		buttons.add(returnToolsButton);
		buttons.add(doneButton);
		buttons.add(logOutButton);

		formatAndAddButtons();

		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);

		logOutButton.removeActionListener(buttonListener);
		doneButton.removeActionListener(buttonListener);
		logOutButton.addActionListener(new GUI.LogOutListener());
		doneButton.addActionListener(new GUI.DoneListener());

	}

	private void displayUserMachinePermissions() {
		machinesPanel.removeAll();
		ArrayList<Machine> machines = currentUser.getCertifiedMachines();
		// sorts the machine list
		Collections.sort(machines, new MachineComparator());
		for (Machine m : machines) {
			String name = m.getName() + " [" + m.getID() + "]";
			JCheckBox machineBox = new JCheckBox(name);
			machineBox.setName(m.getID());
			machineBox.setFont(resultsFont);
			if (selectedMachines.contains(m) || oldMachines.contains(m)){
				machineBox.setEnabled(false);
			}
			machineBox.addItemListener(machineCheckBoxListener);
			machinesPanel.add(machineBox);
		}
	}

	private void displayUserCheckedOutTools() {
		checkedOutToolsPanel.removeAll();
		ArrayList<Tool> tools = currentUser.getToolsCheckedOut();
		// sorts the tools list
		Collections.sort(tools, new ToolComparator());
		for (Tool t : tools) {
			String name = t.getName() + " [" + t.getUPC() + "]";
			JCheckBox toolBox = new JCheckBox(name);
			toolBox.setName(t.getUPC());
			toolBox.setFont(resultsFont);
			toolBox.addItemListener(toolCheckBoxListener);
			checkedOutToolsPanel.add(toolBox);
		}
	}	

	public void selectMachines() {

		for (Machine m : currentUser.getCurrentEntry().getMachinesUsed()) {
			m.stopUsing();
		}

		currentUser.getCurrentEntry().addMachinesUsed(selectedMachines);
		System.out.println(currentUser.getCurrentEntry().getMachinesUsed());
		System.out.println(oldMachines);

		String message = "You are using:\n\n";

		for ( Machine m : selectedMachines ) {
			message += m + "\n";
		}

		for (Machine m : currentUser.getCurrentEntry().getMachinesUsed()) {
			m.use();
		}

		showMessage(message);

		displayUserMachinePermissions();
		resetButtonBackgrounds();
		repaint();

	}

	public void returnTools() {
		if (!isCheckingOutTools) {
			if (toolsToReturn.size() == 0) {
				showMessage("No tools selected");
			} else {

				currentUser.getCurrentEntry().addToolsReturned(toolsToReturn);

				currentUser.returnTools(toolsToReturn);

				displayUserCheckedOutTools();
				repaint();

				toolsToReturn.clear();
			}
		}
		resetButtonBackgrounds();
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton current = (JButton) e.getSource();
			if ( e.getSource() == checkOutToolsButton ) {
				switchContentPanel(new CheckoutToolsPanel());
				isCheckingOutTools = true;
				current.setBackground(orange);
			} else if ( e.getSource() == selectMachinesButton ) {
				if ( !isCheckingOutTools ) {
					boolean noBoxesSelected = true;
					for ( int i = 0; i < machinesPanel.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) machinesPanel.getComponent(i);
						if ( cb.isSelected() ) {
							noBoxesSelected = false;
						}
					}
					if ( !noBoxesSelected ) {
						selectMachines();
						current.setBackground(orange);
					}
				} else {
					isCheckingOutTools = false;
					switchPanels(new UserGUI(currentUser));
				}
			} else if ( e.getSource() == returnToolsButton ) {
				if ( !isCheckingOutTools ) {
					returnTools();
					current.setBackground(orange);
				} else {
					isCheckingOutTools = false;
					switchPanels(new UserGUI(currentUser));
				}
			}
		}
	}

	public class MachineCheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox check = (JCheckBox) e.getSource();
			String id = check.getName();

			Machine m = Driver.getAccessTracker().getMachineByID(id);

			switch (e.getStateChange()) {
			case ItemEvent.SELECTED:
				selectedMachines.add(m);
				break;
			case ItemEvent.DESELECTED:
				if (selectedMachines.contains(m)) {
					selectedMachines.remove(m);
				}
				break;
			}
		}
	}

	public class ToolCheckBoxListener implements ItemListener {
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
