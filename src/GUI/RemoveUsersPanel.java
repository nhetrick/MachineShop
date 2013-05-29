package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class RemoveUsersPanel extends JPanel {
	
	GridBagConstraints c;
	JComboBox<String> searchParameter;
	JLabel enterLabel;
	JButton removeButton;
	
	public RemoveUsersPanel() {
		
		c = new GridBagConstraints();
		
		Font buttonFont = new Font("SansSerif", Font.BOLD, 28);
		Font titleFont = new Font("SansSerif", Font.BOLD, 38);
		Font textFont = new Font("SansSerif", Font.BOLD, 28);
		
		JLabel title = new JLabel("Remove Users");
		title.setFont(titleFont);
		
		JLabel searchLabel = new JLabel("Search By:");
		
		searchLabel.setFont(buttonFont);
		
		searchParameter = new JComboBox<String>();
		searchParameter.setFont(textFont);
		
		searchParameter.addItem("User Name");
		searchParameter.addItem("CWID");
		
		searchParameter.addActionListener(new ComboBoxListener());
		
		enterLabel = new JLabel("Enter name:");
		JTextField searchField = new JTextField();
		JButton goButton = new JButton("Go");
		
		enterLabel.setFont(buttonFont);
		searchField.setFont(textFont);
		goButton.setFont(buttonFont);
		
		JPanel parameterPanel = new JPanel(new GridBagLayout());
		JPanel searchPanel = new JPanel(new GridLayout(1, 3));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.27;
		c.gridx = 0;
		c.gridy = 0;
		parameterPanel.add(searchLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.73;
		c.gridx = 1;
		c.gridy = 0;
		parameterPanel.add(searchParameter, c);
		
		searchPanel.add(enterLabel);
		searchPanel.add(searchField);
		searchPanel.add(goButton);
		
		JPanel resultsPanel = new JPanel();
		resultsPanel.setBorder(new TitledBorder("Search Results"));
		
		removeButton = new JButton("Remove Users");
		removeButton.setFont(buttonFont);
		removeButton.addActionListener(new ButtonListener());
		
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(new JPanel(), c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.8;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		add(title, c);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 0;
		add(new JPanel(), c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 1;
		add(parameterPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(searchPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 3;
		add(resultsPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		add(removeButton, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
		
	}
	
	public void showConfirmPopup() {
		JOptionPane.showConfirmDialog(this, "Are you sure you want to remove these users?");
	}
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == searchParameter) {
				String parameter = searchParameter.getSelectedItem().toString();
				if (parameter == "CWID") {
					enterLabel.setVisible(false);
					enterLabel.setText("Enter CWID:");
					enterLabel.setVisible(true);
				} else if (parameter == "User Name") {
					enterLabel.setVisible(false);
					enterLabel.setText("Enter Name:");
					enterLabel.setVisible(true);
				}
			}
			repaint();
		}
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == removeButton) {
				showConfirmPopup();
			}
		}
	}

}

