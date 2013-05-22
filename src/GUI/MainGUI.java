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
	private KeyListener reader = new InputReader(this);
	private AccessTracker tracker;
	
	public MainGUI() {
		tk = Toolkit.getDefaultToolkit();
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		setCursor(tk.createCustomCursor(image, new Point(0,0), "blank"));
		messageFont = new Font("SansSerif", Font.BOLD, 42);
		
		//frame = new JFrame();
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setUndecorated(true);
		setResizable(false);
		
		JLabel message = new JLabel("Please swipe your Blastercard");
		message.setFont(messageFont);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(message);

		add(centerPanel);
		setVisible(true);
		
		addKeyListener(reader);
		setFocusable(true);
		
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
				//setVisible(false);
				//frame = new HomeScreen(userName);
				add(new HomeScreen(userName));
				//setVisible(true);
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
