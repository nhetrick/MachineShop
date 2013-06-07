package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContentPanel extends JPanel {
	
	protected JLabel title;
	
	protected Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font titleFont = new Font("SansSerif", Font.BOLD, 38);
	protected Font textFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font resultsFont = new Font("SansSerif", Font.BOLD, 24);
	protected Font borderFont = new Font("SansSerif", Font.BOLD, 20);
	protected Font smallFont = new Font("SansSerif", Font.BOLD, 16);
	
	protected GridBagConstraints c;
	
	public ContentPanel(String title) {
		this.title = new JLabel(title);
		this.title.setFont(titleFont);
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(new JPanel(), c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.8;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		add(this.title, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 0;
		add(new JPanel(), c);
	}
	
}
