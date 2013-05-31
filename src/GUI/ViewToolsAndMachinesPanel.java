package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

public class ViewToolsAndMachinesPanel extends ContentPanel {
	
	private JCheckBox inUseBox;
	private JCheckBox notInUseBox;
	private JCheckBox checkedOutBox;
	private JCheckBox notCheckedOutBox;
	private CheckBoxListener checkBoxListener;
	
	public ViewToolsAndMachinesPanel() {
		
		super("View Tools and Machines");
		checkBoxListener = new CheckBoxListener();
		
		inUseBox = new JCheckBox("In Use");
		notInUseBox = new JCheckBox("Not In Use");
		checkedOutBox = new JCheckBox("Checked Out");
		notCheckedOutBox = new JCheckBox("Not Checked Out");
		
		inUseBox.addActionListener(checkBoxListener);
		notInUseBox.addActionListener(checkBoxListener);
		checkedOutBox.addActionListener(checkBoxListener);
		notCheckedOutBox.addActionListener(checkBoxListener);
		
		inUseBox.setHorizontalAlignment(JCheckBox.CENTER);
		notInUseBox.setHorizontalAlignment(JCheckBox.CENTER);
		checkedOutBox.setHorizontalAlignment(JCheckBox.CENTER);
		notCheckedOutBox.setHorizontalAlignment(JCheckBox.CENTER);
		
		JPanel mainMachinesPanel = new JPanel();
		JPanel mainToolsPanel = new JPanel();
		
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
		
	}
	
	public void showConfirmPopup() {
		JOptionPane.showConfirmDialog(this, "Are you sure you want to remove these users?");
	}
	
	private class CheckBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == inUseBox) {
				// TODO
			} else if ( e.getSource() == notInUseBox ) {
				// TODO
			} else if ( e.getSource() == checkedOutBox ) {
				// TODO
			} else if ( e.getSource() == notCheckedOutBox ) {
				// TODO
			}
		}
	}

}

