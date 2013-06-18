package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.mongodb.DBObject;

import main.Tool;
import main.ToolComparator;

public class CheckoutToolsPanel extends ContentPanel {

	private JButton checkoutButton;
	private ButtonListener buttonListener;
	private JButton nameSearchGoButton;
	private JButton idSearchGoButton;
	private JTextField nameSearchField;
	private JTextField idSearchField;
	private JPanel resultsPanel;
	private JScrollPane scroller;

	// Holds the list of tools to potentially be deleted (searched by the admin)
	private ArrayList<Tool> resultsList; 

	public CheckoutToolsPanel() {

		super("Check Out Tools");
		buttonListener = new ButtonListener();
		resultsList = new ArrayList<Tool>();

		JLabel nameSearchLabel = new JLabel("Search By Name:");
		JLabel idSearchLabel = new JLabel("Search By ID (UPC):");

		nameSearchField = new JTextField();
		idSearchField = new JTextField();

		nameSearchField.setText("Search All");
		idSearchField.setText("Search All");

		nameSearchField.addActionListener(buttonListener);
		idSearchField.addActionListener(buttonListener);

		JPanel nameSearchPanel = new JPanel(new GridLayout(1, 3));
		JPanel idSearchPanel = new JPanel(new GridLayout(1, 3));

		JPanel dataPanel = new JPanel(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		dataPanel.add(nameSearchPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		dataPanel.add(idSearchPanel, c);

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

		resultsPanel = new JPanel(new GridLayout(0, 1));

		scroller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		
		scroller.setPreferredSize(new Dimension(scroller.getWidth(), scroller.getHeight()));
		scroller.setMaximumSize(scroller.getPreferredSize());
		scroller.getVerticalScrollBar().setUnitIncrement(13);

		TitledBorder border = new TitledBorder("Search Results");
		border.setTitleFont(borderFont);
		scroller.setBorder(border);

		checkoutButton = new JButton("Check Out");
		checkoutButton.setFont(buttonFont);
		checkoutButton.addActionListener(buttonListener);

		/////////////////////////////////////////////////////////////////////////////////////////////////
		/******************** All weighty values should add up to 0.9 ***********************************
		 ******************** All weightx values should add up to 0.8 **********************************/
		/////////////////////////////////////////////////////////////////////////////////////////////////

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		add(dataPanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		add(scroller, c);

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		add(checkoutButton, c);

		c.weighty = 0.1;
		c.gridy = 4;
		add(new JPanel(), c);

	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == checkoutButton) {
				boolean noBoxesChecked = true;
				for ( int i = 0; i < resultsPanel.getComponentCount(); ++i ) {
					JCheckBox cb = (JCheckBox) resultsPanel.getComponent(i);
					if ( cb.isSelected() ) {
						noBoxesChecked = false;
					}
				}
				ArrayList<String> checkedOut = new ArrayList<String>();
				ArrayList<Tool> tcheckedOut = new ArrayList<Tool>();
				if ( !noBoxesChecked) {
					ArrayList<JCheckBox> checkedBoxes = new ArrayList<JCheckBox>();
					for ( int i = 0; i < resultsPanel.getComponentCount(); ++i ) {
						JCheckBox cb = (JCheckBox) resultsPanel.getComponent(i);
						if ( cb.isSelected() ) {
							for ( Tool t : resultsList ) {
								String s = cb.getText();
								s = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
								String UPC = t.getUPC();
								if ( s.equals(UPC) ) {
									checkedOut.add(t.getName() + " [" + UPC + "]");
									tcheckedOut.add(t);
									checkedBoxes.add(cb);
									currentUser.checkoutTool(Driver.getAccessTracker().getToolByUPC(UPC));
								}
							}
						}
					}
					
					currentUser.getCurrentEntry().addToolsCheckedOut(tcheckedOut);
					
					for ( JCheckBox cb : checkedBoxes ) {
						cb.setEnabled(false);
						cb.setSelected(false);
					}
					repaint();

					// Lists all the tools that are being checked out.
					String message = "You Checked Out:\n\n";
					for ( String s : checkedOut ) {
						message += s + "\n";
					}
					showMessage(message);
				}
			} else if (e.getSource() == nameSearchGoButton || e.getSource() == idSearchGoButton |
					e.getSource() == nameSearchField || e.getSource() == idSearchField ) {

				resultsPanel.removeAll();
				resultsList.clear();
				repaint();
				ArrayList<DBObject> toolList = new ArrayList<DBObject>();

				if ( e.getSource() == nameSearchGoButton || e.getSource() == nameSearchField ) {

					if ( nameSearchField.getText().equals("Search All"))
						toolList = Driver.getAccessTracker().searchDatabase("Tools", "name", "");
					else
						toolList = Driver.getAccessTracker().searchDatabase("Tools", "name", nameSearchField.getText());

				} else {
					if ( idSearchField.getText().equals("Search All"))
						toolList = Driver.getAccessTracker().searchDatabase("Tools", "name", "");
					else
						toolList = Driver.getAccessTracker().searchDatabase("Tools", "upc", idSearchField.getText());

				}

				for ( DBObject t : toolList ) {
					Tool tool = Driver.getAccessTracker().getToolByUPC((String) t.get("upc")); 
					resultsList.add(tool);
				}

				// sorts the resultslist
				Collections.sort(resultsList, new ToolComparator());
				for ( Tool t : resultsList ) {
					JCheckBox cb = new JCheckBox(t.getName() + " [" + t.getUPC() + "]");
					cb.setHorizontalAlignment(JCheckBox.LEFT);
					cb.setFont(buttonFont);
					if ( t.isCheckedOut() ) {
						cb.setEnabled(false);
					} else {
						cb.setEnabled(true);
					}
					resultsPanel.add(cb);
				}				
			}
		}
	}
}