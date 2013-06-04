package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.AccessTracker;
import main.Tool;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RemoveToolsPanel extends ContentPanel {
	
	private JButton removeButton;
	private ButtonListener buttonListener;
	private JButton nameSearchGoButton;
	private JButton idSearchGoButton;
	private JTextField nameSearchField;
	private JTextField idSearchField;
	private JPanel resultsPanel;
	
	public RemoveToolsPanel() {
		
		super("Remove Tools");
		buttonListener = new ButtonListener();
		
		JLabel nameSearchLabel = new JLabel("Search By Name:");
		JLabel idSearchLabel = new JLabel("Search By ID (UPC):");
		
		nameSearchField = new JTextField();
		idSearchField = new JTextField();
		
		JPanel nameSearchPanel = new JPanel(new GridLayout(1, 3));
		JPanel idSearchPanel = new JPanel(new GridLayout(1, 3));
		
		nameSearchGoButton = new JButton("Go");
		idSearchGoButton = new JButton("Go");
		
		nameSearchGoButton.addActionListener(buttonListener);
		idSearchGoButton.addActionListener(buttonListener);
		
		nameSearchLabel.setFont(buttonFont);
		idSearchLabel.setFont(buttonFont);
		nameSearchField.setFont(textFont);
		idSearchField.setFont(textFont);
		nameSearchGoButton.setFont(buttonFont);
		idSearchGoButton.setFont(buttonFont);
		
		nameSearchPanel.add(nameSearchLabel);
		nameSearchPanel.add(nameSearchField);
		nameSearchPanel.add(nameSearchGoButton);
		
		idSearchPanel.add(idSearchLabel);
		idSearchPanel.add(idSearchField);
		idSearchPanel.add(idSearchGoButton);
		
		resultsPanel = new JPanel(new BorderLayout());
		TitledBorder border = new TitledBorder("Search Results");
		//border.setTitleFont(resultsFont);
		resultsPanel.setBorder(border);
		
		removeButton = new JButton("Remove Tools");
		removeButton.setFont(buttonFont);
		removeButton.addActionListener(buttonListener);
		
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
		add(nameSearchPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(idSearchPanel, c);
		
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
		JOptionPane.showConfirmDialog(this, "Are you sure you want to remove these tools?");
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == removeButton) {
				showConfirmPopup();
			} else if (e.getSource() == nameSearchGoButton | e.getSource() == idSearchGoButton ) {
				
				JPanel results = new JPanel(new GridBagLayout());
				
				ArrayList<Tool> tools = new ArrayList<Tool>();
				ArrayList<DBObject> toolList = new ArrayList<DBObject>();
				
				if ( e.getSource() == nameSearchGoButton ) {
					
					toolList = AccessTracker.searchDatabase("Tools", "name", nameSearchField.getText());
					
				} else {
					
					toolList = AccessTracker.searchDatabase("Tools", "upc", idSearchField.getText());
					
				}
				
				for ( DBObject t : toolList ) {
					Tool tool = new Tool( (String) t.get("name"), (String) t.get("upc"));
					tools.add(tool);
				}
				
				int y = 0;
				
				c.fill = GridBagConstraints.NONE;
				c.gridx = 0;
				
				c.weighty = (double) 1.0/tools.size();
								
				for ( Tool t : tools ) {
					JCheckBox cb = new JCheckBox(t.getName());
					cb.setHorizontalAlignment(JCheckBox.LEFT);
					cb.setFont(buttonFont);
					c.gridy = y;
					results.add(cb, c);
					++y;
				}
				
				resultsPanel.add(results, BorderLayout.WEST );
				
			}
		}
	}

}

