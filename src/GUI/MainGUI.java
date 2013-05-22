package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.*;

public class MainGUI extends JFrame{
	private Toolkit tk;
	private Font messageFont;
	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;
	private JPanel centerPanel;
	private JPanel headerBar;
	private JPanel homeCenterPanel;
	private Calendar calendar;
	private Clock time;
	private Font headerFont;

	public MainGUI() {
		messageFont = new Font("SansSerif", Font.BOLD, 42);
		reader = new InputReader(this);
		messageFont = new Font("SansSerif", Font.BOLD, 42);
		JLabel message = new JLabel("Please swipe your Blastercard");
		message.setFont(messageFont);
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(message);
		setup();

	}

	public void setup() {
		//sets the cursor to invisible
		tk = Toolkit.getDefaultToolkit();
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));

		//sets the screen to full screen
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);
		
		setLayout(new GridBagLayout());
		
		//disables Alt+F4
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		add(centerPanel);
		setVisible(true);

		addKeyListener(reader);
		setFocusable(true);

		tracker = new AccessTracker();
		tracker.messAroundWithDatabase();
	}

	public void handleInput() {

		InputReader inReader = (InputReader) reader;
		int CWID;
		boolean returnToStart = false;

		try {
			inReader.strip();
			if ( !inReader.getCWID().equals("") ) {
				CWID = Integer.parseInt(inReader.getCWID());
				currentUser = tracker.processLogIn(CWID);
				remove(centerPanel);
				ProcessHomeScreen(currentUser);
				InputReader.resetErrorCount();
			}
		} catch (InputReaderException e) {
			String message = e.getMessage();
			if ( InputReader.getErrorCount() < 3 ) {
				JOptionPane.showMessageDialog(this, message);
			} else {
				String input = JOptionPane.showInputDialog("An Error has occurred. Please enter your CWID.");
				if ( input == null ) {
					InputReader.resetErrorCount();
				} else {
					int tries = 0;
					while ( input.length() != 8 && tries < 3) {
						input = JOptionPane.showInputDialog("Not a valid CWID. Please try again. ");
						++tries;
						if ( tries == 3 ) {
							returnToStart = true;
							InputReader.resetErrorCount();
						}
					}
					if ( !returnToStart ) {
						CWID = Integer.parseInt(input);
						currentUser = tracker.processLogIn(CWID);
						remove(centerPanel);
						ProcessHomeScreen(currentUser);
						InputReader.resetErrorCount();
					}
				}
			}
		}
	}

	public void enterPressed() {
		handleInput();
	}
	
	public void ProcessHomeScreen(User currentUser) {
		setLayout(new BorderLayout());
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
		
		homeCenterPanel = new JPanel(new BorderLayout());
		
		if ( currentUser.isAdmin() ) {
			if ( ((Administrator) currentUser).isSystemAdmin() ) {
				homeCenterPanel = new SystemAdminGUI();
			} else {
				homeCenterPanel = new AdminGUI();
			}
		} else {
			homeCenterPanel = new UserGUI();
		}
		add(homeCenterPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		MainGUI m = new MainGUI();
	}

}