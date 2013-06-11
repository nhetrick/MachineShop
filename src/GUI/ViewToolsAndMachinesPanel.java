package GUI;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import main.Machine;
import main.Tool;

public class ViewToolsAndMachinesPanel extends ContentPanel {
	
	private JLabel inUseLabel;
	private JLabel notInUseLabel;
	private JLabel checkedOutLabel;
	private JLabel notCheckedOutLabel;
	
	private JPanel mainMachinesPanel;
	private JPanel mainToolsPanel;
	
	private JPanel blackMachinesPanel;
	private JPanel grayMachinesPanel;
	
	private JPanel blackToolsPanel;
	private JPanel grayToolsPanel;
	
	private JScrollPane machinesScroller;
	private JScrollPane toolsScroller;
	
	public ViewToolsAndMachinesPanel() {
		
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
		
		mainMachinesPanel = new JPanel();
		blackMachinesPanel = new JPanel(new GridLayout(0,  1));
		grayMachinesPanel = new JPanel(new GridLayout(0,  1));
		
		mainToolsPanel = new JPanel();
		blackToolsPanel = new JPanel(new GridLayout(0,  1));
		grayToolsPanel = new JPanel(new GridLayout(0,  1));
		
		mainMachinesPanel.setLayout(new GridLayout(0, 2));
		mainToolsPanel.setLayout(new GridLayout(0, 2));
		
		JPanel machinesPanel = new JPanel(new GridBagLayout());
		
		for (Machine m : Driver.getAccessTracker().getMachines()) {
			JLabel l = new JLabel(m.getName() + " [" + m.getID() + "] " + "(" + m.getNumUsers() + " Users)");
			l.setFont(resultsFont);
			l.setHorizontalAlignment(JLabel.CENTER);
			if (m.getNumUsers() > 0) {
				l.setEnabled(false);
				grayMachinesPanel.add(l);
			} else {
				blackMachinesPanel.add(l);
			}
		}

		mainMachinesPanel.add(grayMachinesPanel);
		mainMachinesPanel.add(blackMachinesPanel);
		
		JPanel toolsPanel = new JPanel(new GridBagLayout());
		
		for (Tool t : Driver.getAccessTracker().getTools()) {
			JLabel l = new JLabel(t.getName() + " [" + t.getUPC() + "]" );
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setFont(resultsFont);
			if (t.isCheckedOut()) {
				l.setEnabled(false);
				grayToolsPanel.add(l);
			} else {
				blackToolsPanel.add(l);
			}
		}	
		
		mainToolsPanel.add(grayToolsPanel);
		mainToolsPanel.add(blackToolsPanel);
		
		JPanel machinesLabelPanel = new JPanel(new GridLayout(1, 2));
		JPanel toolsLabelPanel = new JPanel( new GridLayout(1, 2));
		
		machinesLabelPanel.add(inUseLabel);
		machinesLabelPanel.add(notInUseLabel);
		toolsLabelPanel.add(checkedOutLabel);
		toolsLabelPanel.add(notCheckedOutLabel);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		machinesPanel.add(machinesLabelPanel, c);
		
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
		toolsPanel.add(toolsLabelPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy = 1;
		toolsPanel.add(mainToolsPanel, c);
		
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

