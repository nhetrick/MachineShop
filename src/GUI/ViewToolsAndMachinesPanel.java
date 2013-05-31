package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import main.Machine;
import main.Tool;

public class ViewToolsAndMachinesPanel extends ContentPanel {
	
	private JCheckBox inUseBox;
	private JCheckBox notInUseBox;
	private JCheckBox checkedOutBox;
	private JCheckBox notCheckedOutBox;
	private CheckBoxListener checkBoxListener;
	
	JPanel mainMachinesPanel;
	JPanel mainToolsPanel;
	
	public ViewToolsAndMachinesPanel() {
		
		super("View Tools and Machines");
		checkBoxListener = new CheckBoxListener();
		
		inUseBox = new JCheckBox("In Use");
		notInUseBox = new JCheckBox("Not In Use");
		checkedOutBox = new JCheckBox("Checked Out");
		notCheckedOutBox = new JCheckBox("Not Checked Out");
		
		inUseBox.setFont(textFont);
		notInUseBox.setFont(textFont);
		checkedOutBox.setFont(textFont);
		notCheckedOutBox.setFont(textFont);
		
		inUseBox.addActionListener(checkBoxListener);
		notInUseBox.addActionListener(checkBoxListener);
		checkedOutBox.addActionListener(checkBoxListener);
		notCheckedOutBox.addActionListener(checkBoxListener);
		
		inUseBox.setHorizontalAlignment(JCheckBox.CENTER);
		notInUseBox.setHorizontalAlignment(JCheckBox.CENTER);
		checkedOutBox.setHorizontalAlignment(JCheckBox.CENTER);
		notCheckedOutBox.setHorizontalAlignment(JCheckBox.CENTER);
		
		mainMachinesPanel = new JPanel();
		mainToolsPanel = new JPanel();
		
		mainMachinesPanel.setLayout(new GridLayout(0, 4));
		mainToolsPanel.setLayout(new GridLayout(0, 4));
		
		JPanel machinesPanel = new JPanel(new GridBagLayout());
		machinesPanel.setBorder(new TitledBorder("Machines"));
		
		JPanel toolsPanel = new JPanel(new GridBagLayout());
		toolsPanel.setBorder(new TitledBorder("Tools"));
		
		JPanel machinesCheckBoxPanel = new JPanel(new GridLayout(1, 2));
		JPanel toolsCheckBoxPanel = new JPanel( new GridLayout(1, 2));
		
		machinesCheckBoxPanel.add(inUseBox);
		machinesCheckBoxPanel.add(notInUseBox);
		toolsCheckBoxPanel.add(checkedOutBox);
		toolsCheckBoxPanel.add(notCheckedOutBox);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		machinesPanel.add(machinesCheckBoxPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy = 1;
		machinesPanel.add(mainMachinesPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		toolsPanel.add(toolsCheckBoxPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy = 1;
		toolsPanel.add(mainToolsPanel, c);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.gridx = 1;
		c.gridy = 1;
		add(machinesPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.gridx = 1;
		c.gridy = 2;
		add(toolsPanel, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
		
		displayInUseMachines(false);
		displayInUseMachines(true);
		displayCheckedOutTools(false);
		displayCheckedOutTools(true);
	}
	
	public void showConfirmPopup() {
		JOptionPane.showConfirmDialog(this, "Are you sure you want to remove these users?");
	}
	
	public void displayInUseMachines(boolean use) {
		for (Machine m : Driver.getAccessTracker().getMachines()) {
			if (use == m.isInUse()) {
				JLabel label = new JLabel(m.getName());
				label.setFont(resultsFont);
				if (m.isInUse()) {
					label.setForeground(Color.GRAY);
				} 
				mainMachinesPanel.add(label);
			}
		}
	}
	
	public void displayCheckedOutTools(boolean status) {
		for (Tool t : Driver.getAccessTracker().getTools()) {
			if (status == t.isCheckedOut()) {
				JLabel label = new JLabel(t.getName());
				label.setFont(resultsFont);
				if (t.isCheckedOut()) {
					label.setForeground(Color.GRAY);
				} 
				mainToolsPanel.add(label);
			}
		}
	}
	
	private class CheckBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == inUseBox || e.getSource() == notInUseBox ) {
				if (inUseBox.isSelected() && !notInUseBox.isSelected()) {
					mainMachinesPanel.removeAll();
					displayInUseMachines(true);
				} else if (!inUseBox.isSelected() && notInUseBox.isSelected()) {
					mainMachinesPanel.removeAll();
					displayInUseMachines(false);
				} else {
					mainMachinesPanel.removeAll();
					displayInUseMachines(false);
					displayInUseMachines(true);
				} 
			} else if ( e.getSource() == checkedOutBox || e.getSource() == notCheckedOutBox ) {
				if (checkedOutBox.isSelected() && !notCheckedOutBox.isSelected()) {
					mainToolsPanel.removeAll();
					displayCheckedOutTools(true);
				} else if (!checkedOutBox.isSelected() && notCheckedOutBox.isSelected()) {
					mainToolsPanel.removeAll();
					displayCheckedOutTools(false);
				} else {
					mainToolsPanel.removeAll();
					displayCheckedOutTools(false);
					displayCheckedOutTools(true);
				}
			} 
		}
	}

}

