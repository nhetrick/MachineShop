package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import main.User;

public class GUIP extends JPanel {
	
	protected Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font titleFont = new Font("SansSerif", Font.BOLD, 38);
	protected Font textFont = new Font("SansSerif", Font.BOLD, 28);
	protected Font resultsFont = new Font("SansSerif", Font.BOLD, 24);
	protected Font borderFont = new Font("SansSerif", Font.BOLD, 20);
	protected Font smallFont = new Font("SansSerif", Font.BOLD, 16);
	protected Font titleInPanelFont = new Font("SansSerif", Font.BOLD, 30);
	
	protected ActionListener buttonListener;
	
	protected User currentUser = Driver.getAccessTracker().getCurrentUser();
	protected GridBagConstraints c = new GridBagConstraints();
	
	protected Color darkBlue = new Color(33, 49, 77);
	protected Color orange = new Color(210, 73, 18);
	
}
