package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AR_ToolsPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel buttonPanel;
	
	public AR_ToolsPanel() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		
		//contentPanel.setLayout(new GridLayout(6,4));
		buttonPanel.setLayout(new GridLayout(1,2));
		
		JTextField toolID = new JTextField();
		JLabel toolIDLabel = new JLabel("Tool ID:");
		
		toolID.setPreferredSize(new Dimension(200, 50));
		toolIDLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(toolIDLabel);
		contentPanel.add(toolID);
		
		JTextField toolName = new JTextField();
		JLabel toolNameLabel = new JLabel("Tool Name:");
		
		toolName.setPreferredSize(new Dimension(200, 50));
		toolNameLabel.setPreferredSize(new Dimension(200, 50));
		
		contentPanel.add(toolNameLabel);
		contentPanel.add(toolName);
		
		JButton addButton = new JButton("Add Tool");
		JButton removeButton = new JButton("Remove Tool");
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
}
