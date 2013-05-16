package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.*;

public class MainGUI{

	private JFrame frame;
	private Font messageFont;

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

		boolean bool = false;
		if ( bool ) {		
			frame = new HomeScreen();
		}
	}

	public void waitForInput() {
		KeyListener reader = new InputReader();
		frame.addKeyListener(reader);
		frame.setFocusable(true);
		String input = ((InputReader) reader).getInput();
		while ( input.equals("") || input.charAt(input.length() - 1) != '?') {
			input = ((InputReader) reader).getInput();
		}
		try {
			((InputReader) reader).strip();
			if ( !((InputReader) reader).getCWID().equals("") ) {
				frame.setVisible(false);
				frame = new HomeScreen();
				frame.setVisible(true);
			}
		} catch (InputReaderException e) {
			String message = e.getMessage();
			JOptionPane popup = new JOptionPane(message);
			popup.setVisible(true);	
		}
	}

	public static void main(String[] args) {
		MainGUI m = new MainGUI();
		m.waitForInput();
	}

}
