package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import main.*;

public class MainGUI extends JFrame{
	private Toolkit tk;
	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;
	private JPanel centerPanel;
	private JPanel headerBar;
	private JPanel homeCenterPanel;
	private Calendar calendar;
	private Clock time;
	private Font headerFont;
	private JPanel currentPanel;
	private GridBagConstraints c = new GridBagConstraints();

	public MainGUI() {
		reader = new InputReader(this);
		centerPanel = new SwipeCardPanel();
		setup();
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			// use default
		}
	}

	public void setup() {
		//sets the cursor to invisible
//		tk = Toolkit.getDefaultToolkit();
//		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));

		//sets the screen to full screen
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);
		
		setLayout(new GridBagLayout());
		
		//disables Alt+F4
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(centerPanel);
		setVisible(true);

		addKeyListener(reader);
		setFocusable(true);

		tracker = Driver.getAccessTracker();
	}

	
	public void restart() {
		remove(headerBar);
		remove(homeCenterPanel);
		add(centerPanel);
		setVisible(true);
		repaint();
		System.out.println(tracker.getCurrentUsers());
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
				repaint();
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
						repaint();
						InputReader.resetErrorCount();
					}
				}
			}
		}
	}

	public void enterPressed() {
		handleInput();
	}

	public User getCurrentUser() {
		return currentUser;
	}

	
	public void ProcessHomeScreen(User currentUser) {
		headerFont = new Font("SansSerif", Font.BOLD, 42);
		
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

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(headerBar, c);
		
		homeCenterPanel = new JPanel();
		
		if ( currentUser.isAdmin() ) {
			if ( ((Administrator) currentUser).isSystemAdmin() ) {
				homeCenterPanel = new SystemAdminGUI(currentUser);
			} else {
				homeCenterPanel = new AdminGUI(currentUser);
		}
		} 	else {
			homeCenterPanel = new UserGUI(currentUser);
		}
		currentPanel = homeCenterPanel;
		
		c.gridy = 1;
		c.gridx = 0;
		c.weighty = 0.9;
		c.fill = GridBagConstraints.BOTH;
		add(homeCenterPanel, c);
		
		setVisible(true);
	}
}
