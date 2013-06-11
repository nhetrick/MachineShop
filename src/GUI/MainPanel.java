package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.User;

public class MainPanel extends JPanel {
	
	protected JPanel contentPanel;
	protected JPanel buttonPanel;
	protected User currentUser;
	protected GridBagConstraints c;
	
	protected Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font borderFont = new Font("SansSerif", Font.BOLD, 20);
	
	public MainPanel(User user) {
		
		c = new GridBagConstraints();
		currentUser = user;
		setup();
		
	}
	
	public void setup() {
		
	}
	
	protected boolean confirm(String message) {
		if ( JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION ) {
			return true;
		}
		return false;
	}
	
}
