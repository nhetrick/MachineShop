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

public class Main extends JFrame {
	
	private JPanel headerBar;
	private Calendar calendar;
	private Clock time;
	private Font headerFont;
	
	public Main() {
		
		setSize(800, 700);
		headerFont = new Font("SansSerif", Font.BOLD, 32);
		
		calendar = Calendar.getInstance();
		
		headerBar = new JPanel(new GridLayout(1, 2));
		headerBar.setBorder(new EtchedBorder());
		
		JLabel nameLabel = new JLabel("Taylor Sallee");
		nameLabel.setFont(headerFont);
		
		time = new Clock(headerFont);
		
		headerBar.add(nameLabel);
		headerBar.add(time);
		
		add(headerBar, BorderLayout.NORTH);
		
		UserGUI g = new UserGUI();
		add(g, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Main m = new Main();
	}
	
}
