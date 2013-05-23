package GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwipeCardPanel extends JPanel{
	private Font messageFont;
	
	public SwipeCardPanel(){
		setLayout(new BorderLayout());
		messageFont = new Font("SansSerif", Font.BOLD, 42);
		
		JLabel message = new JLabel("Please swipe your Blastercard");
		message.setFont(messageFont);
		
		add(message);
	}
}
