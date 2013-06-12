package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContentPanel extends GUI {
	
	protected JLabel title;
	
	public ContentPanel(String title) {
		
		this.title = new JLabel(title);
		this.title.setFont(titleFont);
		setLayout(new GridBagLayout());
		
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
