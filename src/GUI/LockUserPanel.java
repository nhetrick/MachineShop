package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LockUserPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel buttonPanel;
	
	public LockUserPanel() {
		setLayout(new BorderLayout());
		
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
		
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
}
