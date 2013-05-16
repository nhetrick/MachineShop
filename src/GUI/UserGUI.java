package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UserGUI extends JPanel {
	
	private JPanel centerPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	
	private Font buttonFont;
	
	public UserGUI() {
		
		setLayout(new BorderLayout());
		
		buttonFont = new Font("SansSerif", Font.BOLD, 20);
		
		centerPanel = new JPanel(new BorderLayout());
		contentPanel = new JPanel();
		buttonPanel = new JPanel(new GridLayout(4, 1));
		
		JButton hello = new JButton();
		hello.setText("hello");
		
		contentPanel.add(hello, BorderLayout.CENTER);
		centerPanel.add(contentPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton logOut = new JButton();
		JButton checkOutTools = new JButton();
		JButton selectMachine = new JButton();
		JButton returnTools = new JButton();
		
		logOut.setText("Log Out");
		selectMachine.setText("Select machines");
		checkOutTools.setText("Check out tools");
		returnTools.setText("Return tools");
		
		buttonPanel.add(selectMachine);
		buttonPanel.add(checkOutTools);
		buttonPanel.add(returnTools);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		
	}
	
}
