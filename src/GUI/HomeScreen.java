package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import main.Administrator;
import main.User;

public class HomeScreen extends JFrame {
	private Toolkit tk;
	private JPanel headerBar;
	private JPanel centerPanel;
	private Calendar calendar;
	private Clock time;
	private Font headerFont;
	
//<<<<<<< HEAD
//	public HomeScreen(String userName) {
//		tk = Toolkit.getDefaultToolkit();
//		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));
//=======
	public HomeScreen(User currentUser) {
		tk = Toolkit.getDefaultToolkit();
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));

//>>>>>>> 4c76383272b3b7c9bb0fe027ba29ce640034da92
		
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);
//		setSize(800, 700);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		headerFont = new Font("SansSerif", Font.BOLD, 32);
		
		calendar = Calendar.getInstance();
		
		headerBar = new JPanel(new GridLayout(1, 3));
		
		String userName = currentUser.getFirstName() + " " + currentUser.getLastName();
		
		JLabel nameLabel = new JLabel(userName);
		JLabel centerLabel = new JLabel("");
		time = new Clock(headerFont);
		
		nameLabel.setFont(headerFont);
		centerLabel.setFont(headerFont);
		
		headerBar.add(nameLabel);
		headerBar.add(time);
		
		add(headerBar, BorderLayout.NORTH);
		
		centerPanel = new JPanel(new BorderLayout());
		
		if ( currentUser.isAdmin() ) {
			if ( ((Administrator) currentUser).isSystemAdmin() ) {
				centerPanel = new SystemAdminGUI();
			} else {
				centerPanel = new AdminGUI();
			}
		} else {
			centerPanel = new UserGUI();
		}
		add(centerPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}
