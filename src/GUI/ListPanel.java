package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListPanel extends JPanel {
	
	JPanel contentPanel;
	JPanel checkBoxPanel;
	JPanel parametersPanel;
	
	JComboBox<String> parameters;
	
	JCheckBox box1;
	JCheckBox box2;
	
	public ListPanel() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		checkBoxPanel = new JPanel();
		parametersPanel = new JPanel();
		
		contentPanel.setLayout(new GridLayout(0, 1));
		
		parameters = new JComboBox<String>();
		JLabel parametersLabel = new JLabel("Select Parameter");
		
		parameters.addItem(" ");
		parameters.addItem("Machines");
		parameters.addItem("Tools");
		
		parameters.setPreferredSize(new Dimension(200, 50));
		parametersLabel.setPreferredSize(new Dimension(200, 50));
		
		parametersPanel.add(parametersLabel);
		parametersPanel.add(parameters);
		parameters.addActionListener(new ComboBoxListener());
		
		//add something to this to display data...maybe JLabels
		for (int i = 0; i < 50; i++) {
			contentPanel.add(new JLabel("HeLLo" + i));
		}
		
		box1 = new JCheckBox(" ", false);
		box2 = new JCheckBox(" ", false);
		box1.setVisible(false);
		box2.setVisible(false);
		
		checkBoxPanel.add(box1);
		checkBoxPanel.add(box2);
	
		add(parametersPanel, BorderLayout.NORTH);
		add(new JScrollPane(contentPanel),BorderLayout.CENTER);
		add(checkBoxPanel, BorderLayout.SOUTH);
		
	}
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == parameters) {
				String parameter = parameters.getSelectedItem().toString();
				if (parameter == "Machines") {
					box1.setText("In Use");
					box1.setVisible(true);
					box2.setText("Not In Use");
					box2.setVisible(true);
				} else if (parameter == "Tools") {
					box1.setText("Checked Out");
					box1.setVisible(true);
					box2.setText("Not Checked Out");
					box2.setVisible(true);
				} else {
					box1.setText(" ");
					box1.setText(" ");
					box1.setVisible(false);
					box2.setVisible(false);
				}
			}
		}
	}
	
}
