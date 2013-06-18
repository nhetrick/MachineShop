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

public class AdminGUI extends UserGUI {

	private JButton generateReport = new JButton("Generate Report");
	private JButton viewActiveUsers = new JButton("View Active Users");
	private JButton viewToolsAndMachines = new JButton("View Tools/Machines");
	
	public AdminGUI(User user) {
		
		super(user);
		
		for ( JButton b : buttons ) {
			b.removeActionListener(buttonListener);
		}
		buttons.clear();
		
		buttonListener = new ButtonListener();
		
		buttons.add(selectMachinesButton);
		buttons.add(checkOutToolsButton);
		buttons.add(returnToolsButton);
		buttons.add(generateReport);
		buttons.add(viewActiveUsers);
		buttons.add(viewToolsAndMachines);
		buttons.add(doneButton);
		buttons.add(logOutButton);
		
		formatAndAddButtons();

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
					useMachines(current);
				} else {
					isCheckingOutTools = false;
					switchPanels(new AdminGUI(currentUser));
				}
			} else if ( e.getSource() == returnToolsButton ) {
				if ( !isCheckingOutTools ) {
					returnTools();
					current.setBackground(orange);
				} else {
					switchPanels(new AdminGUI(currentUser));
					isCheckingOutTools = false;
				}
			} else if ( e.getSource() == doneButton ) {
				useMachines(current);
				Driver.getMainGui().restart();
			} else if ( e.getSource() == viewToolsAndMachines ) {
				switchContentPanel(new ViewToolsAndMachinesPanel());
				current.setBackground(orange);
				isCheckingOutTools = false;
			} else if ( e.getSource() == generateReport ) {
				switchContentPanel(new GenerateReportPanel());
				current.setBackground(orange);
				isCheckingOutTools = false;
			} else if ( e.getSource() == viewActiveUsers ) {
				switchContentPanel(new ViewActiveUsersPanel());
				current.setBackground(orange);
				isCheckingOutTools = false;
			}
		}
	}
}
