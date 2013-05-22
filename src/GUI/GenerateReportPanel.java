package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GenerateReportPanel extends JPanel {

	JPanel contentPanel;
	JPanel buttonPanel;
	JPanel parametersPanel;
	
	JComboBox<String> parameters;
	
	JTextField text1;
	JLabel label1;
	
	JTextField text2;
	JLabel label2;
	
	public GenerateReportPanel() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		parametersPanel = new JPanel();
		
		parameters = new JComboBox<String>();
		JLabel parametersLabel = new JLabel("Select Report Parameter:");
		
		parameters.addItem(" ");
		parameters.addItem("User");
		parameters.addItem("Tool");
		parameters.addItem("Machine");
		parameters.addItem("Date");
		parameters.addItem("All");
		
		parameters.setPreferredSize(new Dimension(200, 50));
		parametersLabel.setPreferredSize(new Dimension(200, 50));
		
		parametersPanel.add(parametersLabel);
		parametersPanel.add(parameters);
		parameters.addActionListener(new ComboBoxListener());
		
		//contentPanel.setLayout(new GridLayout(6,4));
		//buttonPanel.setLayout(new GridLayout(1,2));
		
		JButton generateButton = new JButton("Generate Report");
		
		buttonPanel.add(generateButton);
		
		text1 = new JTextField();
		label1 = new JLabel(" ");
		text1.setPreferredSize(new Dimension(200, 50));
		label1.setPreferredSize(new Dimension(200, 50));
		text2 = new JTextField();
		label2 = new JLabel(" ");
		text2.setPreferredSize(new Dimension(200, 50));
		label2.setPreferredSize(new Dimension(200, 50));
		text1.setVisible(false);
		text2.setVisible(false);
		
		contentPanel.add(label1);
		contentPanel.add(text1);
		contentPanel.add(label2);
		contentPanel.add(text2);
	
		add(parametersPanel, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == parameters) {
				String parameter = parameters.getSelectedItem().toString();
				if (parameter == "User") {
					label1.setText("CWID:");
					text1.setVisible(true);
					label2.setText(" ");
					text2.setVisible(false);
				} else if (parameter == "Tool") {
					label1.setText("Tool ID:");
					text1.setVisible(true);
					label2.setText(" ");
					text2.setVisible(false);
				} else if (parameter == "Machine") {
					label1.setText("Machine ID:");
					text1.setVisible(true);
					label2.setText(" ");
					text2.setVisible(false);
				} else if (parameter == "Date") {
					label1.setText("Start Date:");
					text1.setVisible(true);
					label2.setText("End Date:");
					text2.setVisible(true);
				} else {
					label1.setText(" ");
					text1.setVisible(false);
					label2.setText(" ");
					text2.setVisible(false);
				}
			}
		}
	}
	
}
