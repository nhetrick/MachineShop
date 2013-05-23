package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LockUserPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel buttonPanel;
	GridBagConstraints c = new GridBagConstraints();
	
	public LockUserPanel() {
		setLayout(new GridBagLayout());
		
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
		
		JButton addButton = new JButton("Lock User");
		JButton removeButton = new JButton("Unlock User");
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		add(contentPanel, c);
		
		c.gridy = 3;
		c.weightx = 0.5;
		c.weighty = 0.5;
		add(buttonPanel, c);
	}
}
