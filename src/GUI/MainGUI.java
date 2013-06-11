package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import main.*;

public class MainGUI extends JFrame {
	
	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;
	private JPanel centerPanel;
	private JPanel headerBar;
	private JPanel footerBar;
	private JPanel homeCenterPanel;
	private Clock time;
	private Font headerFont;
	private GridBagConstraints c = new GridBagConstraints();
	private static int MAX_ERROR_COUNT = 3;
	private JButton backButton;
	private ButtonListener buttonListener;
	
	private final String trianglePath = "/Images/triangle.jpg";
	private final String bannerPath = "/Images/banner.jpg";
	
	private BufferedImage triangleLogo;
	private BufferedImage banner;
	
	private JLabel triangleLabel;
	private JLabel bannerLabel;
	
	private static Stack<JPanel> panelStack;
	
	public MainGUI() {
		
		reader = new InputReader(this);
		centerPanel = new SwipeCardPanel();
		buttonListener = new ButtonListener();
		setup();
		
		panelStack = new Stack<JPanel>();
		
		ImageIcon triangleIcon = createImageIcon(trianglePath);
		triangleLabel = new JLabel(triangleIcon);
		
		ImageIcon bannerIcon = createImageIcon(bannerPath);
		bannerLabel = new JLabel(bannerIcon);
		
		System.out.println(triangleLabel.toString());
		
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
		
		backButton = new JButton("Back");
		backButton.addActionListener(buttonListener);
		
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
		setResizable(false);
		
		setLayout(new GridBagLayout());
		
		//disable Alt+F4
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(centerPanel);
		setVisible(true);

		addKeyListener(reader);
		setFocusable(true);

		tracker = Driver.getAccessTracker();
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
	
	public void restart() {
		Driver.isLogInScreen = true;
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
			processHomeScreen(currentUser);
		}
	}
	
	public void enterPressed() {
		handleInput();
	}

	public User getCurrentUser() {
		return currentUser;
	}
	
	public void processHomeScreen(User currentUser) {
		
		headerFont = new Font("SansSerif", Font.BOLD, 42);
		headerBar = new JPanel(new GridLayout(1, 2));
		headerBar.setBackground(Color.white);
		
		footerBar = new JPanel(new GridBagLayout());
		footerBar.setBackground(Color.white);
		footerBar.add(bannerLabel);
		
		String userName = currentUser.getFirstName() + " " + currentUser.getLastName();
		
		JLabel nameLabel = new JLabel(userName);
		nameLabel.setBackground(Color.white);
		time = new Clock(headerFont);
		
		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		time.setHorizontalAlignment(JLabel.RIGHT);
		
		nameLabel.setFont(headerFont);
		
		JPanel leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBackground(Color.white);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		leftPanel.add(triangleLabel, c);
		
		c.gridx = 2;
		c.weightx = 0.8;
		c.fill = GridBagConstraints.HORIZONTAL;
		leftPanel.add(nameLabel, c);
		
		headerBar.add(leftPanel);
		headerBar.add(time);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
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
		c.weighty = 0.8;
		c.fill = GridBagConstraints.BOTH;
		add(homeCenterPanel, c);
		
		c.gridy = 2;
		c.gridx = 0;
		c.weighty = 0.8;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(footerBar, c);
		
		setVisible(true);
		
	}
	
	public void goBack() {
		System.out.println(panelStack.toString());
		if ( !panelStack.isEmpty() )
			panelStack.pop();
		if ( !panelStack.isEmpty() )
			homeCenterPanel = panelStack.pop();
	}
	
	public static void pushToStack(JPanel panel) {
		panelStack.push(panel);
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == backButton ) {
				goBack();
				repaint();
			}
		}
	}
}
