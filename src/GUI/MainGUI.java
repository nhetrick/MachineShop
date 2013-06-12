package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import main.*;

public class MainGUI extends JFrame {

	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;

	private JPanel mainPanel; // Basically The Entire Panel
	private JPanel headerBar;
	private JPanel footerBar;
	private JPanel mainContentPanel; // The content Panel (between header and footer)

	private Clock time;
	private Font headerFont;

	private GridBagConstraints c = new GridBagConstraints();
	private static int MAX_ERROR_COUNT = 3;
	private JButton backButton;
	private JButton helpButton;
	private JButton aboutButton;
	private JButton close;
	private JFrame frame;
	private ButtonListener buttonListener;

	private final String mPath = "/Images/minesM.jpg";
	private final String bannerPath = "/Images/banner.jpg";

	private JLabel minesMlabel;
	private JLabel bannerLabel;
	
	private Color borderColor = Color.black;
	private Color darkBlue = new Color(33, 49, 77);
	private Color orange = new Color(210, 73, 42);
	private Color coolGray = new Color(178, 180, 179);
	
	protected Font smallFont = new Font("SansSerif", Font.BOLD, 12);

	// For keeping track of which panel to go to when the "back" button is pressed
	private static Stack<JPanel> panelStack;

	public MainGUI() {
		
		this.getContentPane().setBackground(Color.white);
		reader = new InputReader(this);
		mainPanel = new SwipeCardPanel();
		buttonListener = new ButtonListener();
		setup();

		panelStack = new Stack<JPanel>();

		ImageIcon mIcon = createImageIcon(mPath);
		minesMlabel = new JLabel(mIcon);

		ImageIcon bannerIcon = createImageIcon(bannerPath);
		bannerLabel = new JLabel(bannerIcon);

		backButton = new JButton("Back");
		backButton.addActionListener(buttonListener);
		
		helpButton = new JButton("Help");
		helpButton.addActionListener(buttonListener);
		helpButton.setFont(smallFont);
		
		aboutButton = new JButton("About");
		aboutButton.addActionListener(buttonListener);
		aboutButton.setFont(smallFont);
	
	}

	protected static ImageIcon createImageIcon(String path) {

		java.net.URL imageURL = MainGUI.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return new ImageIcon(imageURL);
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
		
		if (Validator.isUnixOS()){
			setResizable(true);
		} else {
			setResizable(false);
		}

		setLayout(new GridBagLayout());

		//disable Alt+F4
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(mainPanel);
		setVisible(true);

		addKeyListener(reader);
		setFocusable(true);

		tracker = Driver.getAccessTracker();

		// Set up the look and feel of the frame/panels
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// use default look and feel
		}
	}

	public enum SearchBy {
		CWID("CWID"), NAME("Name"), UPC("upc");
		private final String name;
		private SearchBy(String name){
			this.name = name;
		}
		public String toString(){
			return name;
		}
	};

	// Returns the user to the "Swipe Blastercard" screen
	public void restart() {

		Driver.isLogInScreen = true;
		remove(headerBar);
		remove(footerBar);
		remove(mainContentPanel);
		add(mainPanel);
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
			if (Validator.isValidCWID(inReader.getCWID())) {
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
			while (!Validator.isValidCWID(input) && tries < MAX_ERROR_COUNT) {
				input = JOptionPane.showInputDialog("Not a valid CWID. Please try again. ");
				if (input == null){
					InputReader.resetErrorCount();
					return;
				}
				++tries;
			}

			InputReader.resetErrorCount();
			if (Validator.isValidCWID(input)){
				login(input);
			}
		}
	}

	public void login(String CWID){
		if (Driver.getAccessTracker().getCurrentUsers().contains(new User("", "", CWID, "", ""))) {
			currentUser = Driver.getAccessTracker().getUser(CWID);
		} else {
			currentUser = tracker.processLogIn(CWID);
		}
		if ( currentUser != null ) {
			remove(mainPanel);
			processHomeScreen(currentUser);
		}
	}

	public void enterPressed() {
		handleInput();
	}

	public User getCurrentUser() {
		return currentUser;
	}

	// This function does most of the work to set up the homescreen for whichever
	// user is currently logged in (whether they are an Admin, SystemAdmin, or User)

	public void processHomeScreen(User currentUser) {
		
		// Create header and footer bars
		headerFont = new Font("SansSerif", Font.BOLD, 42);
		headerBar = new JPanel(new GridLayout(1, 2));
		headerBar.setBorder(new LineBorder(borderColor, 4));
		headerBar.setBackground(Color.white);
		
		footerBar = new JPanel(new GridBagLayout());
		footerBar.setBackground(Color.white);
		footerBar.setBorder(new LineBorder(borderColor, 4));
		//footerBar.add(bannerLabel);
		JPanel helpButtons = new JPanel();
		helpButtons.setBackground(Color.WHITE);
		helpButtons.add(aboutButton);
		helpButtons.add(helpButton);
		footerBar.setLayout(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		footerBar.add(bannerLabel, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		footerBar.add(helpButtons, c);
		
		String userName = currentUser.getFirstName() + " " + currentUser.getLastName();
		
		// Add the user's name and the clock		
		JLabel nameLabel = new JLabel(userName);
		nameLabel.setBackground(Color.white);
		time = new Clock(headerFont);

		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		time.setHorizontalAlignment(JLabel.RIGHT);

		nameLabel.setFont(headerFont);

		JPanel leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBackground(Color.white);
		
		// Set up the constraints to format everything correctly
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.15;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		leftPanel.add(minesMlabel, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.85;
		c.fill = GridBagConstraints.HORIZONTAL;
		leftPanel.add(nameLabel, c);

		headerBar.add(leftPanel);
		headerBar.add(time);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		add(headerBar, c);
		
		// This is where we create and add the main panel with all the relevant information.
		// This panel will be switched in and out for other panels all throughout the runtime
		// of the program. These panels will be stored in a stack so that the back button will
		// function correctly.
		
		mainContentPanel = new JPanel();
		mainContentPanel.setBackground(Color.white);

		if ( currentUser.isAdmin() ) {
			if ( ((Administrator) currentUser).isSystemAdmin() ) {
				mainContentPanel = new SystemAdminGUI(currentUser);
			} else {
				mainContentPanel = new AdminGUI(currentUser);
			}
		} 	else {
			mainContentPanel = new UserGUI(currentUser);
		}
		
		mainContentPanel.setBorder(new LineBorder(borderColor, 4));
		
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.9;
		c.fill = GridBagConstraints.BOTH;
		add(mainContentPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		add(footerBar, c);

		setVisible(true);

	}

	public void goBack() {
		System.out.println(panelStack.toString());
		if ( !panelStack.isEmpty() )
			panelStack.pop();
		if ( !panelStack.isEmpty() )
			mainContentPanel = panelStack.pop();
	}

	public static void pushToStack(JPanel panel) {
		panelStack.push(panel);
	}
	
	private void displayTextFile(String title, String file) {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setTitle(title);
		frame.setSize(getMinimumSize());
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		JScrollPane scroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Scanner in;
		try {
			in = new Scanner(new FileReader(file));
			while (in.hasNextLine()) {
				JLabel label = new JLabel();
				label.setText(in.nextLine());
				label.setFont(smallFont);
				panel.add(label);
			}			
		} catch (FileNotFoundException e) {
			System.out.println("Documentation Could Not Be Found");
		}
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.9;
		c.fill = GridBagConstraints.BOTH;
		frame.add(scroller, c);
		
		close = new JButton("Close");
		close.addActionListener(buttonListener);
		close.setFont(smallFont);

		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.NONE;
		frame.add(close, c);
		
		frame.setVisible(true);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == backButton ) {
				goBack();
				repaint();
			} else if (e.getSource() == helpButton) {
				displayTextFile("Help", "help.txt");
			} else if (e.getSource() == aboutButton) {
				displayTextFile("About", "about.txt");
			} else if (e.getSource() == close) {
				frame.setVisible(false);
				frame.dispose();
				repaint();
			}
		}
	}
}
