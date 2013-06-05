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
	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;
	private JPanel centerPanel;
	private JPanel headerBar;
	private JPanel homeCenterPanel;
	private Clock time;
	private Font headerFont;
	private GridBagConstraints c = new GridBagConstraints();
	private static int MAX_ERROR_COUNT = 3;

	public enum SearchBy {
		CWID("CWID"), NAME("Name");
		private final String name;
		private SearchBy(String name){
			this.name = name;
		}
		public String toString(){
			return name;
		}
	};
	
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
	}
	
	public void handleInput() {

		InputReader inReader = (InputReader) reader;

		try {
			inReader.strip();			
			if (inReader.getCWID() == null) {
				return;
			}
			if (InputReader.isValidCWID(inReader.getCWID())) {
				login(inReader.getCWID());
			}
			
		} catch (InputReaderException e) {
			
			if (InputReader.getErrorCount() < MAX_ERROR_COUNT) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				return;
			}
			
			String input;
			
			if (InputReader.getErrorCount() == 999) {
				input = JOptionPane.showInputDialog("Enter CWID.");
			} else {
				input = JOptionPane.showInputDialog("An Error has occurred. Please enter your CWID.");
			}
			if (input == null) {
				InputReader.resetErrorCount();
				return;
			}
			
			int tries = 0;
			while (!InputReader.isValidCWID(input) && tries < MAX_ERROR_COUNT) {
				input = JOptionPane.showInputDialog("Not a valid CWID. Please try again. ");
				if (input == null){
					InputReader.resetErrorCount();
					return;
				}
				++tries;
			}
			
			InputReader.resetErrorCount();
			if (InputReader.isValidCWID(input)){
				login(input);
			}
		}
	}
	
	public void login(String CWID){
		currentUser = tracker.processLogIn(CWID);
		if ( currentUser != null ) {
			remove(centerPanel);
			ProcessHomeScreen(currentUser);
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
		
		headerBar = new JPanel(new GridLayout(1, 2));
		
		String userName = currentUser.getFirstName() + " " + currentUser.getLastName();
		
		JLabel nameLabel = new JLabel(userName);
		time = new Clock(headerFont);
		
		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		time.setHorizontalAlignment(JLabel.RIGHT);
		
		nameLabel.setFont(headerFont);
		
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
		
		c.gridy = 1;
		c.gridx = 0;
		c.weighty = 0.9;
		c.fill = GridBagConstraints.BOTH;
		add(homeCenterPanel, c);
		
		setVisible(true);
	}
}
