package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.User;

public class GUI extends JPanel {
	
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected double constant = screenSize.getWidth()/1600.0;
	
	protected Font buttonFont = new Font("SansSerif", Font.BOLD, (int) (constant*28 )); //28
	protected Font titleFont = new Font("SansSerif", Font.BOLD, (int) (constant*38)); //38
	protected Font textFont = new Font("SansSerif", Font.BOLD, (int) (constant*26)); //24
	protected Font resultsFont = new Font("SansSerif", Font.BOLD, (int) (constant*24)); //24
	protected Font borderFont = new Font("SansSerif", Font.BOLD, (int) (constant*22)); //22
	protected Font smallFont = new Font("SansSerif", Font.BOLD, (int) (constant*20)); //20
	protected Font checkBoxFont = new Font("SansSerif", Font.BOLD, (int) (constant*18)); //18
	protected Font titleInPanelFont = new Font("SansSerif", Font.BOLD, (int) (constant*26)); //26

	protected ActionListener buttonListener;

	protected User currentUser = Driver.getAccessTracker().getCurrentUser();
	protected GridBagConstraints c = new GridBagConstraints();

	protected Color darkBlue = new Color(33, 49, 77);
	protected Color orange = new Color(210, 73, 18);
	protected Color coolGray = new Color(178, 180, 179);
	protected Color lightBlue = new Color(146, 162, 189);

	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	protected boolean confirm(String message) {
		if ( JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION ) {
			return true;
		}
		return false;
	}

	public static ImageIcon createImageIcon(String path) {

		java.net.URL imageURL = MainGUI.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return new ImageIcon(imageURL);
		}
	}
   
	public static class LogOutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = "Are you sure you want to sign out?" + 
					"\nTo stay in the machine shop, click 'Start Working'." +
					"\nSelect Yes to leave the machine shop. Select No/Cancel to go back.";
			if (JOptionPane.showConfirmDialog(Driver.getMainGui(), message) == JOptionPane.YES_OPTION) {
				Driver.getAccessTracker().processLogOut(Driver.getAccessTracker().getCurrentUser().getCWID());
				Driver.getMainGui().restart();
			}
		}
	}

	public static class SysAdminLogOutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = "Are you sure you want to sign out?";
			if (JOptionPane.showConfirmDialog(Driver.getMainGui(), message) == JOptionPane.YES_OPTION) {
				message = "Do you want to completely close the program for the day?";
				if (JOptionPane.showConfirmDialog(Driver.getMainGui(), message) == JOptionPane.YES_OPTION) {
					Driver.getAccessTracker().processLogOut(Driver.getAccessTracker().getCurrentUser().getCWID());
					Driver.exit();
				} else {
					Driver.getAccessTracker().processLogOut(Driver.getAccessTracker().getCurrentUser().getCWID());
					Driver.getMainGui().restart();
				}
			}
		}
	}

	public static class DoneListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Driver.getMainGui().restart();
		}
	}

}