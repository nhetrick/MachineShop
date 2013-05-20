package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class AdminGUI extends JPanel {
	
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JPanel machinePermissions;
	private JPanel checkedOutTools;
	
	private Font buttonFont;
	
	public AdminGUI() {
		
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(5, 1));
		machinePermissions = new JPanel();
		checkedOutTools = new JPanel();
				
		machinePermissions.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "My Machines"));
		checkedOutTools.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Checked-out Tools"));
		
		contentPanel.add(machinePermissions);		
		contentPanel.add(checkedOutTools);
		
		centerPanel.add(contentPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton logOut = new JButton();
		JButton checkOutTools = new JButton();
		JButton selectMachine = new JButton();
		JButton returnTools = new JButton();
		JButton generateReport = new JButton();
		
		logOut.setText("Log Out");
		selectMachine.setText("Select Machines");
		checkOutTools.setText("Check Out Tools");
		returnTools.setText("Return Tools");
		generateReport.setText("Generate Report");
		
		logOut.setFont(buttonFont);
		checkOutTools.setFont(buttonFont);
		selectMachine.setFont(buttonFont);
		returnTools.setFont(buttonFont);
		generateReport.setFont(buttonFont);
		
		buttonPanel.add(selectMachine);
		buttonPanel.add(checkOutTools);
		buttonPanel.add(returnTools);
		buttonPanel.add(generateReport);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		
	}
	

}
