package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.User;

public class MainPanel extends JPanel {
	
	protected Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font titleFont = new Font("SansSerif", Font.BOLD, 38);
	protected Font textFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font resultsFont = new Font("SansSerif", Font.BOLD, 24);
	protected Font borderFont = new Font("SansSerif", Font.BOLD, 20);
	protected Font smallFont = new Font("SansSerif", Font.BOLD, 16);
	protected Font titleInPanelFont = new Font("SansSerif", Font.BOLD, 30);
	
	protected JPanel contentPanel;
	protected JPanel buttonPanel;
	protected User currentUser;
	protected GridBagConstraints c;
	
	protected ArrayList<JButton> buttons;
	protected ActionListener buttonListener;
	
	protected Color darkBlue = new Color(33, 49, 77);
	
	public MainPanel(User user) {
		
		c = new GridBagConstraints();
		currentUser = user;
		setLayout(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(0, 1));
		buttons = new ArrayList<JButton>();
		
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
	
	public void resetButtonBackgrounds() {
		for ( int i = 0; i < buttonPanel.getComponentCount(); ++i)  {
			JButton b = (JButton) buttonPanel.getComponent(i);
			b.setBackground(null);
		}
	}
	
	protected void formatAndAddButtons() {
		for ( JButton b : buttons ) {
			b.setFont(buttonFont);
			b.addActionListener(buttonListener);
			buttonPanel.add(b);
		}
	}
	
	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
}
