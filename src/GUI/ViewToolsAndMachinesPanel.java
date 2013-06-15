package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import main.Machine;
import main.MachineComparator;
import main.Tool;
import main.ToolComparator;

public class ViewToolsAndMachinesPanel extends ContentPanel {
	
	private JLabel inUseLabel;
	private JLabel notInUseLabel;
	private JLabel checkedOutLabel;
	private JLabel notCheckedOutLabel;
	
	private JScrollPane machinesScroller;
	private JScrollPane toolsScroller;
	
	public ViewToolsAndMachinesPanel() {
		// All the fonts are in ContetnPanel.
		super("View Tools and Machines");
		
		inUseLabel = new JLabel("In Use");
		notInUseLabel = new JLabel("Not In Use");
		checkedOutLabel = new JLabel("Checked Out");
		notCheckedOutLabel = new JLabel("Not Checked Out");
		
		inUseLabel.setFont(titleInPanelFont);
		notInUseLabel.setFont(titleInPanelFont);
		checkedOutLabel.setFont(titleInPanelFont);
		notCheckedOutLabel.setFont(titleInPanelFont);
		
		inUseLabel.setEnabled(false);
		checkedOutLabel.setEnabled(false);
		
		inUseLabel.setHorizontalAlignment(JCheckBox.CENTER);
		notInUseLabel.setHorizontalAlignment(JCheckBox.CENTER);
		checkedOutLabel.setHorizontalAlignment(JCheckBox.CENTER);
		notCheckedOutLabel.setHorizontalAlignment(JCheckBox.CENTER);
		
		JPanel machinesPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints machineBlack = new GridBagConstraints();
		GridBagConstraints machineGray = new GridBagConstraints();
		
		machineBlack.gridy = 1;
		machineBlack.gridx = 1;
		machineBlack.weighty = 0.9;
		machineBlack.weightx = 0.5;
		
		machineGray.gridy = 1;
		machineGray.gridx = 0;
		machineGray.weighty = 0.9;
		machineGray.weightx = 0.5;
		
		// sorts the machines list
		ArrayList<Machine> sortedMachine = new ArrayList<Machine>();
		sortedMachine = Driver.getAccessTracker().getMachines();
		Collections.sort(sortedMachine, new MachineComparator());
		
		for (Machine m : sortedMachine) {
			JLabel l = new JLabel(m.getName() + " [" + m.getID() + "] " + "(" + m.getNumUsers() + " Users)");
			l.setFont(smallFont);
			l.setHorizontalAlignment(JLabel.CENTER);
			if (m.getNumUsers() > 0) {
				l.setEnabled(false);
				machinesPanel.add(l, machineGray);
				machineGray.gridy++;
			} else {
				machinesPanel.add(l, machineBlack);
				machineBlack.gridy++;
			}
		}
		
		JPanel toolsPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints toolBlack = new GridBagConstraints();
		GridBagConstraints toolGray = new GridBagConstraints();

		toolBlack.gridy = 1;
		toolBlack.gridx = 1;
		toolBlack.weighty = 0.9;
		toolBlack.weightx = 0.5;

		toolGray.gridy = 1;
		toolGray.gridx = 0;
		toolGray.weighty = 0.9;
		toolGray.weightx = 0.5;
		
		// sorts the tools list
		ArrayList<Tool> sortedTools = new ArrayList<Tool>();
		sortedTools = Driver.getAccessTracker().getTools();
		Collections.sort(sortedTools, new ToolComparator());
		
		for (Tool t : sortedTools) {
			JLabel l = new JLabel(t.getName() + " [" + t.getUPC() + "]");
			l.setFont(smallFont);
			l.setHorizontalAlignment(JLabel.CENTER);
			if (t.isCheckedOut()) {
				l.setEnabled(false);
				toolsPanel.add(l, toolGray);
				toolGray.gridy++;
			} else {
				toolsPanel.add(l, toolBlack);
				toolBlack.gridy++;
			}
		}
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		machinesPanel.add(inUseLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		machinesPanel.add(notInUseLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		toolsPanel.add(checkedOutLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		toolsPanel.add(notCheckedOutLabel, c);

		machinesScroller = new JScrollPane(machinesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		machinesScroller.setPreferredSize(new Dimension(machinesScroller.getWidth(), machinesScroller.getHeight()));
		machinesScroller.setMaximumSize(machinesScroller.getPreferredSize());
		machinesScroller.getVerticalScrollBar().setUnitIncrement(13);
		
		TitledBorder machinesBorder = new TitledBorder("Machines");
		machinesBorder.setTitleFont(borderFont);
		machinesScroller.setBorder(machinesBorder);
		
		toolsScroller = new JScrollPane(toolsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		toolsScroller.setPreferredSize(new Dimension(toolsScroller.getWidth(), toolsScroller.getHeight()));
		toolsScroller.setMaximumSize(toolsScroller.getPreferredSize());
		toolsScroller.getVerticalScrollBar().setUnitIncrement(13);
		
		TitledBorder toolsBorder = new TitledBorder("Tools");
		toolsBorder.setTitleFont(borderFont);
		toolsScroller.setBorder(toolsBorder);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.gridx = 1;
		c.gridy = 1;
		add(machinesScroller, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.gridx = 1;
		c.gridy = 2;
		add(toolsScroller, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
		
	}
}
