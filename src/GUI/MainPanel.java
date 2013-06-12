package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.User;

public class MainPanel extends GUI {
	
	protected JPanel contentPanel;
	protected JPanel buttonPanel;
	protected ArrayList<JButton> buttons;
	
	public MainPanel() {

		//MainGUI.pushToStack(this);
		System.out.println("Pushed to stack");

		setLayout(new BorderLayout());
		// Set Content Panel in child panels
		contentPanel = new JPanel();
		buttonPanel = new JPanel(new GridLayout(0, 1));
		buttons = new ArrayList<JButton>();
		
		contentPanel.setBorder(new EtchedBorder());
		buttonPanel.setBorder(new EtchedBorder());
		
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
		cards.add(panel);
		CardLayout cl = (CardLayout) cards.getLayout();
        cl.last(cards);
		contentPanel.add(cards, BorderLayout.CENTER);
		System.out.println("Switched Content Panel. Pushed to stack");
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
	
}
