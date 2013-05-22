package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.*;

public class MainGUI extends JFrame{
	private Toolkit tk;
	private JFrame frame;
	private Font messageFont;
	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;
	private JPanel centerPanel;

	public MainGUI() {
//<<<<<<< HEAD
//		tk = Toolkit.getDefaultToolkit();
//		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));
//		messageFont = new Font("SansSerif", Font.BOLD, 42);
//		
//		//frame = new JFrame();
//		setExtendedState(MAXIMIZED_BOTH);
//		setLayout(new GridBagLayout());
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		
//		setUndecorated(true);
//		setResizable(false);
//		
//		JLabel message = new JLabel("Please swipe your Blastercard");
//		message.setFont(messageFont);
//
//		JPanel centerPanel = new JPanel(new BorderLayout());
//		centerPanel.add(message);
//
//		add(centerPanel);
//		setVisible(true);
//		
//		addKeyListener(reader);
//		setFocusable(true);
//		
//=======
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
		
		tk = Toolkit.getDefaultToolkit();
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));

		frame = new JFrame();
//		frame.setSize(800, 700);
		
		frame.setExtendedState(MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(centerPanel);
		frame.setVisible(true);

		frame.addKeyListener(reader);
		frame.setFocusable(true);

//>>>>>>> 4c76383272b3b7c9bb0fe027ba29ce640034da92
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
//<<<<<<< HEAD
//				int CWID = Integer.parseInt(inReader.getCWID());
//				System.out.println("Parsed the int");
//				String userName = tracker.processLogIn(CWID);
//				//setVisible(false);
//				//frame = new HomeScreen(userName);
//				add(new HomeScreen(userName));
//				//setVisible(true);
//=======
				CWID = Integer.parseInt(inReader.getCWID());
				currentUser = tracker.processLogIn(CWID);
				frame.dispose();
				frame = new HomeScreen(currentUser);
				InputReader.resetErrorCount();
//>>>>>>> 4c76383272b3b7c9bb0fe027ba29ce640034da92
			}
		} catch (InputReaderException e) {
			String message = e.getMessage();
			if ( InputReader.getErrorCount() < 3 ) {
				JOptionPane.showMessageDialog(frame, message);
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
						frame.dispose();
						frame = new HomeScreen(currentUser);
						InputReader.resetErrorCount();
					}
				}
			}
		}
	}

	public void enterPressed() {
		handleInput();
	}

	public static void main(String[] args) {
		MainGUI m = new MainGUI();	
	}

}
