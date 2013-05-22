package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class UserGUI extends JPanel {
	
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JPanel machinePermissions;
	private JPanel checkedOutTools;
	private MainGUI mainGui;
	private Font buttonFont;
	
	public UserGUI(MainGUI mainGui) {
		this.mainGui = mainGui;
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel = new JPanel(new GridLayout(4, 1));
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
		
		logOut.setText("Log Out");
		selectMachine.setText("Select Machines");
		checkOutTools.setText("Check Out Tools");
		returnTools.setText("Return Tools");
		
		logOut.setFont(buttonFont);
		checkOutTools.setFont(buttonFont);
		selectMachine.setFont(buttonFont);
		returnTools.setFont(buttonFont);
		
		buttonPanel.add(selectMachine);
		buttonPanel.add(checkOutTools);
		buttonPanel.add(returnTools);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		
		logOut.addActionListener(new LogOutListner());
	}

	public class LogOutListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainGui = new MainGUI();
		}
	}
}
