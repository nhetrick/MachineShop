package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class HomeScreen extends JFrame {
	
	private JPanel headerBar;
	private JPanel centerPanel;
	private Calendar calendar;
	private Clock time;
	private Font headerFont;
	
	public HomeScreen() {
		
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		headerFont = new Font("SansSerif", Font.BOLD, 32);
		
		calendar = Calendar.getInstance();
		
		headerBar = new JPanel(new GridLayout(1, 3));
		
		JLabel nameLabel = new JLabel("Taylor Sallee");
		JLabel centerLabel = new JLabel("");
		time = new Clock(headerFont);
		
		nameLabel.setFont(headerFont);
		centerLabel.setFont(headerFont);
		
		headerBar.add(nameLabel);
		headerBar.add(time);
		
		add(headerBar, BorderLayout.NORTH);
		
		centerPanel = new JPanel(new BorderLayout());
		
		centerPanel = new SystemAdminGUI();
		//AdminGUI g = new AdminGUI();
		//DataEntryGUI g = new DataEntryGUI();
		//SystemAdminGUI g = new SystemAdminGUI();
		//UserGUI g = new UserGUI();
		add(centerPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}
