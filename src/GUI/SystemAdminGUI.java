package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SystemAdminGUI extends JPanel {
	private JPanel centerPanel;
	private JPanel buttonPanel;
	
	private Font buttonFont;
	
	public SystemAdminGUI() {
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		
		buttonFont = new Font("SansSerif", Font.BOLD, 24);
		
		centerPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(3, 1));
		
		centerPanel.add(buttonPanel, BorderLayout.CENTER);
		
		JButton logOut = new JButton();
		JButton dataEntry = new JButton();
		JButton basicUser = new JButton();
		
		logOut.setText("Log Out");
		dataEntry.setText("Data Entry");
		basicUser.setText("Basic User");
		
		logOut.setFont(buttonFont);
		dataEntry.setFont(buttonFont);
		basicUser.setFont(buttonFont);
		
		
		buttonPanel.add(dataEntry);
		buttonPanel.add(basicUser);
		buttonPanel.add(logOut);
		
		add(centerPanel, BorderLayout.CENTER);
		dataEntry.addActionListener(new DataEntryButtonListener());
		basicUser.addActionListener(new BasicUserButtonListener());
		basicUser.addActionListener(new ListenerHelpers.LogOutListner());
		
	}
	
	private class DataEntryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			centerPanel.removeAll();
			centerPanel.add(new DataEntryGUI());
		}
	}
	
	private class BasicUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			centerPanel.removeAll();
			centerPanel.add(new UserGUI());
		}
	}
}
