package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.*;

public class MainGUI {

	private JFrame frame;
	private Font messageFont;
	private KeyListener reader; 
	private AccessTracker tracker;
	private User currentUser;
	private JPanel centerPanel;

	public MainGUI() {

		reader = new InputReader(this);
		messageFont = new Font("SansSerif", Font.BOLD, 42);
		JLabel message = new JLabel("Please swipe your Blastercard");
		message.setFont(messageFont);
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(message);
		setup();

	}

	public void setup() {

		frame = new JFrame();
		frame.setSize(800, 700);
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(centerPanel);
		frame.setVisible(true);

		frame.addKeyListener(reader);
		frame.setFocusable(true);

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
				frame.dispose();
				frame = new HomeScreen(currentUser);
				InputReader.resetErrorCount();
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
