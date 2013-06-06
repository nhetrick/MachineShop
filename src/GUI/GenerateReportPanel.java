package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.Log;
import main.LogEntry;
import main.SystemAdministrator;

public class GenerateReportPanel extends ContentPanel {

	private JComboBox<String> parameters;
	private ButtonListener buttonListener;
	private ComboBoxListener comboBoxListener;
	private JButton generateButton;
	private JPanel dataEntryPanel;
	private JPanel resultsPanel;
	private JScrollPane scroller;
	
	JTextField startField;
	JTextField endField;
	JTextField cwidField;
	JTextField toolNameField;
	JTextField machineNameField;
	
	private String currentParameter;
	
	public GenerateReportPanel() {
		
		super("Generate a Report");
		buttonListener = new ButtonListener();
		comboBoxListener = new ComboBoxListener();
		
		parameters = new JComboBox<String>();
		JLabel parameterLabel = new JLabel("Report Parameter:");
		
		parameters.addItem("Date");
		parameters.addItem("User");
		parameters.addItem("Tool");
		parameters.addItem("Machine");
		parameters.addActionListener(comboBoxListener);
		
		currentParameter = "Date";
		
		parameterLabel.setFont(buttonFont);
		parameters.setFont(textFont);
		
		generateButton = new JButton("Generate");
		generateButton.setFont(buttonFont);
		generateButton.addActionListener(buttonListener);
		
		JPanel parameterPanel = new JPanel(new GridLayout(1, 2));
		
		parameterPanel.add(parameterLabel);
		parameterPanel.add(parameters);
		
		dataEntryPanel = new JPanel(new GridBagLayout());
		dataEntryPanel.setBorder(new TitledBorder("Parameters"));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		dataEntryPanel.add(new DatePanel(), c);
		
		resultsPanel = new JPanel();
		scroller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		resultsPanel.setBorder(new TitledBorder("Results"));
		resultsPanel.setLayout(new GridLayout(0, 1));
		
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
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(dataEntryPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 3;
		add(generateButton, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		add(scroller, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
		
	}
	
	private class ComboBoxListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == parameters) {
				String parameter = parameters.getSelectedItem().toString();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1;
				if (parameter == "Date") {
					currentParameter = "Date";
					dataEntryPanel.removeAll();
					dataEntryPanel.add(new DatePanel(), c);
				} else if (parameter == "User") {
					currentParameter = "User";
					dataEntryPanel.removeAll();
					dataEntryPanel.add(new UserPanel(), c);
				} else if (parameter == "Tool") {
					currentParameter = "Tool";
					dataEntryPanel.removeAll();
					dataEntryPanel.add(new ToolPanel(), c);
				} else if (parameter == "Machine") {
					currentParameter = "Machine";
					dataEntryPanel.removeAll();
					dataEntryPanel.add(new MachinePanel(), c);
				}
			}
		}
	}
	
	private void showResults() {
		for (LogEntry entry : Log.getResults()) {
			JLabel label = new JLabel(entry.toString());
			System.out.println(entry.toString());
			resultsPanel.add(label);
		}
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == generateButton) {
				if (currentParameter.equals("User")) {
					resultsPanel.removeAll();
					String cwid = cwidField.getText();
					Log.extractLog(Driver.getAccessTracker().findUserByCWID(cwid));
					showResults();
				} else if (currentParameter.equals("Date")) {
					resultsPanel.removeAll();
					Date start;
					Date end;
					try {
						start = new SimpleDateFormat("MM/DD/YYYY", Locale.ENGLISH).parse(startField.getText());
						end = new SimpleDateFormat("MM/DD/YYYY", Locale.ENGLISH).parse(endField.getText());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						System.out.println("Invalid Date");
						e1.printStackTrace();
						return;
					}
					Log.extractLog(start, end);
					showResults();
				} else if (currentParameter.equals("Tool")) {
					resultsPanel.removeAll();
					String tool = toolNameField.getText();
					System.out.println(tool);
					Log.extractLogCheckedOutTool(Driver.getAccessTracker().getToolByName(tool));
					System.out.println(Log.getResults());
					showResults();
				}
			}
		}
	}
	
	private class DatePanel extends JPanel {
		
		DatePanel() {
			
			Font smallFont = new Font("SansSerif", Font.BOLD, 18);
			
			setLayout(new GridLayout(2, 2));
			
			JLabel startLabel = new JLabel("Enter Start Date (MM/DD/YYYY):");
			JLabel endLabel = new JLabel("Enter End Date (MM/DD/YYYY):");
			startField = new JTextField();
			endField = new JTextField();
			
			startLabel.setFont(smallFont);
			endLabel.setFont(smallFont);
			startField.setFont(smallFont);
			endField.setFont(smallFont);
			
			add(startLabel);
			add(startField);
			add(endLabel);
			add(endField);
		}
		
	}
	
	private class UserPanel extends JPanel {
		
		UserPanel() {
			setLayout(new GridLayout(1, 2));
			
			JLabel cwidLabel = new JLabel("Enter User's CWID:");
			cwidField = new JTextField();
			
			cwidLabel.setFont(buttonFont);
			cwidField.setFont(textFont);
			
			add(cwidLabel);
			add(cwidField);
		}
		
	}

	private class ToolPanel extends JPanel {
		ToolPanel() {
			setLayout(new GridLayout(1, 2));
			
			JLabel toolNameLabel = new JLabel("Enter Name of Tool:");
			toolNameField = new JTextField();
			
			toolNameLabel.setFont(buttonFont);
			toolNameField.setFont(textFont);
			
			add(toolNameLabel);
			add(toolNameField);
		}
	}
	
	private class MachinePanel extends JPanel {
		MachinePanel() {
			setLayout(new GridLayout(1, 2));
			
			JLabel machineNameLabel = new JLabel("Enter Name of Machine:");
			machineNameField = new JTextField();
			
			machineNameLabel.setFont(buttonFont);
			machineNameField.setFont(textFont);
			
			add(machineNameLabel);
			add(machineNameField);
		}
	}
	
}
