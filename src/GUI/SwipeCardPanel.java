package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwipeCardPanel extends JPanel {
	
	private Font messageFont;
	private final static String trianglePath = "/Images/triangle.jpg";
	private GridBagConstraints c = new GridBagConstraints();
	
	public SwipeCardPanel() {
		
		setBackground(Color.white);
		
		Driver.isLogInScreen = true;
		setLayout(new GridBagLayout());
		messageFont = new Font("SansSerif", Font.BOLD, 50);
		
		JLabel picture = new JLabel(GUI.createImageIcon(trianglePath));
		picture.setBackground(Color.white);
		picture.setVerticalAlignment(JLabel.TOP);
		
		JLabel message = new JLabel("Please Scan Your Blastercard");
		message.setFont(messageFont);
		message.setVerticalAlignment(JLabel.CENTER);
		
		c.ipady = 40;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.6;
		add(picture, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.4;
		add(message, c);
		
	}
	
}
