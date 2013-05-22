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
	private KeyListener reader = new InputReader(this);
	private AccessTracker tracker;
	
	public MainGUI() {

		messageFont = new Font("SansSerif", Font.BOLD, 42);
		
		frame = new JFrame();
		frame.setSize(800, 700);
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel message = new JLabel("Please swipe your Blastercard");
		message.setFont(messageFont);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(message);

		frame.add(centerPanel);
		frame.setVisible(true);
		
		frame.addKeyListener(reader);
		frame.setFocusable(true);
		
		tracker = new AccessTracker();
		tracker.messAroundWithDatabase();
		
	}

	public void handleInput() {
		
		InputReader inReader = (InputReader) reader;
		
		try {
			inReader.strip();
			if ( !inReader.getCWID().equals("") ) {
				int CWID = Integer.parseInt(inReader.getCWID());
				System.out.println("Parsed the int");
				String userName = tracker.processLogIn(CWID);
				//frame.setVisible(false);
				frame = new HomeScreen(userName);
				//frame.setVisible(true);
			}
		} catch (InputReaderException e) {
			String message = e.getMessage();
			JOptionPane.showMessageDialog(frame, message);
		}
	}
	
	public void enterPressed() {
		handleInput();
	}

	public static void main(String[] args) {
		MainGUI m = new MainGUI();	
	}

}
