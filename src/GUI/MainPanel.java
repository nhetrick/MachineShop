package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
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
		setLayout(new BorderLayout()); 
	}
	
	protected boolean confirm(String message) {
		if ( JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION ) {
			return true;
		}
		return false;
	}
	
	protected void switchPanels(JPanel panel) {
		removeAll();
		add(panel, BorderLayout.CENTER);
		repaint();
	}
	
	protected void switchContentPanel(JPanel panel) {
		resetButtonBackgrounds();
		contentPanel.removeAll();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(panel, BorderLayout.CENTER);
		repaint();
	}
	
	public void resetButtonBackgrounds(){
		for ( int i = 0; i < buttonPanel.getComponentCount(); ++i)  {
			JButton b = (JButton) buttonPanel.getComponent(i);
			b.setBackground(null);
		}
	}
	
}
