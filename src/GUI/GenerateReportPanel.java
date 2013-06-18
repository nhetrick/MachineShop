package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.BlasterCardListener;
import main.ExcelExporter;
import main.Log;
import main.LogEntry;
import main.Statistics;

public class GenerateReportPanel extends ContentPanel {

	private JComboBox<String> parameters;
	private ComboBoxListener comboBoxListener;
	private JButton generateButton;
	private JButton saveExcelButton;
	private JPanel dataEntryPanel;
	private JPanel resultsPanel;
	private JScrollPane statsScroller;
	private JScrollPane logScroller;
	private JScrollPane machineFreqScoller;
	private JScrollPane toolFreqScoller;
	private JScrollPane deptFreqScoller;
	private JTabbedPane tabs;
	private Statistics stats;
	private JTable deptFreqsTable;
	public JTable machineFreqsTable;
	public JTable toolFreqsTable;
	private JTable generalStatsTable;
	private JTable logTable;
	
	private JTextField cwidField;
	private JTextField toolNameField;
	private JTextField machineNameField;
	
	Calendar start;
	Calendar end;
	
	JComboBox<String> startMonths;
	JComboBox<String> endMonths;
	JComboBox<String> startDays;
	JComboBox<String> endDays;
	JComboBox<String> startYears;
	JComboBox<String> endYears;
	
	int sMonth, sDay, sYear, eMonth, eDay, eYear;
	
	private String currentParameter;
	SimpleDateFormat dateFileFormat;
	public GenerateReportPanel() {

		super("Generate Reports & Statistics");
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
		dateFileFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
		
		setDefaultDate();
		
		parameterLabel.setFont(buttonFont);
		parameters.setFont(textFont);
		
		generateButton = new JButton("Generate");
		generateButton.setFont(buttonFont);
		generateButton.addActionListener(buttonListener);
		
		saveExcelButton = new JButton("Save to Excel file");
		saveExcelButton.setFont(buttonFont);
		saveExcelButton.addActionListener(buttonListener);
		
		JPanel parameterPanel = new JPanel(new GridLayout(1, 2));
		
		parameterPanel.add(parameterLabel);
		parameterPanel.add(parameters);
		
		dataEntryPanel = new JPanel(new GridBagLayout());
		dataEntryPanel.setBorder(new TitledBorder("Parameters"));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		dataEntryPanel.add(new DatePanel(), c);
		
		resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(0, 1));
		
		statsScroller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		machineFreqScoller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		toolFreqScoller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		deptFreqScoller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logScroller = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tabs = new JTabbedPane(); 

		
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
		c.weighty = 0.4;
		c.gridx = 1;
		c.gridy = 4;
		add(tabs, c);
		
		c.weighty = 0.1;
		c.gridy = 5;
		saveExcelButton.setVisible(false);
		add(saveExcelButton, c);
	
		c.weighty = 0.1;
		c.gridy = 5;
		add(new JPanel(), c);
	}
	
	private void addTabs() {
		if (tabs.getTabCount() > 0) {
			tabs.removeAll();
		}
		tabs.addTab("General Statistics", statsScroller);
		tabs.addTab("Machine Frequencies", machineFreqScoller);
		tabs.addTab("Tool Frequencies", toolFreqScoller);
		tabs.addTab("Department Frequencies", deptFreqScoller);
		tabs.addTab("Log", logScroller);
	}
	
	// Sets the date as today's date.
	private void setDefaultDate() {
		Calendar today = Calendar.getInstance();
		sYear = today.get(Calendar.YEAR);
		eYear = today.get(Calendar.YEAR);
		sMonth = today.get(Calendar.MONTH);
		eMonth = today.get(Calendar.MONTH);
		sDay = today.get(Calendar.DATE);
		eDay = today.get(Calendar.DATE) ;
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
			} else if (e.getSource() == startMonths) {
				sMonth = Integer.parseInt(startMonths.getSelectedItem().toString()) - 1;
			} else if (e.getSource() == startDays) {
				sDay = Integer.parseInt(startDays.getSelectedItem().toString());
			} else if (e.getSource() == startYears) {
				sYear = Integer.parseInt(startYears.getSelectedItem().toString());
			} else if (e.getSource() == endMonths) {
				eMonth = Integer.parseInt(endMonths.getSelectedItem().toString()) - 1;
			} else if (e.getSource() == endDays) {
				eDay = Integer.parseInt(endDays.getSelectedItem().toString());
			} else if (e.getSource() == endYears) {
				eYear =	Integer.parseInt(endYears.getSelectedItem().toString());
			}
		}
	}
	
	private void showLog() {
		String[] columns = {"Entry ID", "User", "Dept", "Time In", "Time Out", "Machines Used", 
		                    "Tools Checked Out", "Tools Returned"};
		
		int size = Log.getResults().size();
		String data[][] = new String[size][8];
		if (size > 0) {
			for (int i=0; i<size; i++) {
				LogEntry entry = Log.getResults().get(i);
				data[i][0] = Integer.toString(entry.getID());
				data[i][1] = entry.getCwid();
				data[i][2] = entry.getDept();
				data[i][3] = entry.getTimeIn().toString();
				
				String timeOut = "";
				if (entry.getTimeOut() == null) {
					data[i][4] = timeOut;
				} else {
					data[i][4] = entry.getTimeOut().toString();
				}				
				data[i][5] = entry.getMachinesUsed().toString();
				data[i][6] = entry.getToolsCheckedOut().toString();
				data[i][7] = entry.getToolsReturned().toString();
			}
		}
		
		logTable = new JTable(data, columns);
		logScroller.setViewportView(logTable);
	}
	
	public JTable createGeneralStatsTable(Map<String, String> generalStats){
		String columns[] = {"", ""};
		String data[][] =  new String[generalStats.size()+1][2];
		
		int index = 0;
		
		for (Map.Entry<String, String> entry: generalStats.entrySet()){
			String title = entry.getKey();
			String stat = entry.getValue();
			data[index][0] = title;
			data[index][1] = stat;
			index++;			
		}
		data[index][0] = "Avg Time Logged In";
		data[index][1] = getAvgLogInTime();
		
		JTable statsTable = new JTable(data, columns);
		statsTable.setName("General Statistics:");
		
		return statsTable;
	}
	
	public JTable createMachineFrequenciesTable(Map<String, Integer> machineFrequencies){
		String machFreqData[][] = new String[machineFrequencies.size()][2];
		int index = 0;
		String columns[] = {"", ""};
		for (Map.Entry<String, Integer> entry: machineFrequencies.entrySet()){
			String title = entry.getKey().toString();
			String stat = entry.getValue().toString();
			machFreqData[index][0] = title;
			machFreqData[index][1] = stat;
			index++;			
		}
		JTable machineFreqs = new JTable(machFreqData, columns);
		machineFreqs.setName("Machine Frequencies:");
		return machineFreqs;
	}
	
	public JTable createToolFrequenciesTable(Map<String, Integer> toolFrequencies) {
		
		String toolFreqData[][] = new String[toolFrequencies.size()][2];
		int index = 0;
		String columns[] = {"", ""};
		for (Map.Entry<String, Integer> entry: toolFrequencies.entrySet()){
			String title = entry.getKey().toString();
			String stat = entry.getValue().toString();
			toolFreqData[index][0] = title;
			toolFreqData[index][1] = stat;
			index++;			
		}
		
		JTable toolFreqs = new JTable(toolFreqData, columns);
		toolFreqs.setName("Tool Frequencies:");
		
		return toolFreqs;
		
	}
	
	public JTable createDeptFrequenciesTable(Map<String, Integer> deptFrequencies){
		String deptFreqData[][] = new String[deptFrequencies.size()][2];
		int index = 0;
		String columns[] = {"",""};
		for (Map.Entry<String, Integer> entry: deptFrequencies.entrySet()){
			String title = entry.getKey().toString();
			String stat = entry.getValue().toString();
			deptFreqData[index][0] = title;
			deptFreqData[index][1] = stat;
			index++;			
		}
		
		JTable deptFreqTable = new JTable(deptFreqData, columns);
		deptFreqTable.setName("Department Frequencies:");
		
		return deptFreqTable;
	}
	
	public void showDateStats(){
		generalStatsTable = createGeneralStatsTable(stats.getGeneralStatistics());
		machineFreqsTable = createMachineFrequenciesTable(stats.getMachineFrequencies());
		toolFreqsTable = createToolFrequenciesTable(stats.getToolsFrequencies());
		deptFreqsTable = createDeptFrequenciesTable(stats.getDeptFrequencies());
		
		statsScroller.setViewportView(generalStatsTable);
		machineFreqScoller.setViewportView(machineFreqsTable);
		toolFreqScoller.setViewportView(toolFreqsTable);
		deptFreqScoller.setViewportView(deptFreqsTable);
	}
	
	public void showgeneralUserStats(){
		generalStatsTable = createGeneralStatsTable(stats.getUserStatistics());
		machineFreqsTable = createMachineFrequenciesTable(stats.getMachineFrequencies());
		toolFreqsTable = createToolFrequenciesTable(stats.getToolsFrequencies());
		
		statsScroller.setViewportView(generalStatsTable);
		machineFreqScoller.setViewportView(machineFreqsTable);
		toolFreqScoller.setViewportView(toolFreqsTable);
		
		tabs.remove(3);
	}
	
	public void showToolStats(){
		generalStatsTable = createGeneralStatsTable(stats.getGeneralToolStats());
		deptFreqsTable = createDeptFrequenciesTable(stats.getDeptFrequencies());
		
		statsScroller.setViewportView(generalStatsTable);
		deptFreqScoller.setViewportView(deptFreqsTable);
		
		tabs.remove(1);
		tabs.remove(1);
	}
	
	public void showMachineStats(){
		generalStatsTable = createGeneralStatsTable(stats.getGeneralMachineStats());
		deptFreqsTable = createDeptFrequenciesTable(stats.getDeptFrequencies());
		statsScroller.setViewportView(generalStatsTable);
		deptFreqScoller.setViewportView(deptFreqsTable);
		
		tabs.remove(1);
		tabs.remove(1);
	}
	
	private String getAvgLogInTime(){
		int seconds = (int) (stats.getAvgTimeLoggedIn() / 1000) % 60;
		int minutes = (int) ((stats.getAvgTimeLoggedIn() / (1000*60)) % 60);
		int hours = (int) ((stats.getAvgTimeLoggedIn() / (1000*60*60)) % 24);
		
		String avg = String.format("%d Hours, %d Minutes, %d Seconds", hours, minutes, seconds);
		
		return avg; 
	}
	
	private void showReport() {
		showLog();
		
		stats = new Statistics();
		stats.run();
	
		if (currentParameter.equals("User")) {
			showgeneralUserStats();
		} else if (currentParameter.equals("Date")) {
			showDateStats();
		} else if (currentParameter.equals("Tool")) {
			showToolStats();
		} else if (currentParameter.equals("Machine")) {
			showMachineStats();
		}
	}
	
	private void setDates() {
		start = Calendar.getInstance();
		end = Calendar.getInstance();
		start.set(sYear, sMonth, sDay, 0, 0, 0);
		end.set(eYear, eMonth, eDay, 23, 59, 59);
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addTabs();
			if (e.getSource() == generateButton | e.getSource() == cwidField | e.getSource() == toolNameField | e.getSource() == machineNameField ) {
				if (currentParameter.equals("User")) {
					resultsPanel.removeAll();
					setDates();
					String cwid = BlasterCardListener.strip(cwidField.getText());
					if ( !cwid.equals("") ) {
						Log.extractLog(start.getTime(), end.getTime(), true);
						Log.extractResultsByUser(cwid);
						showReport();
					}
				} else if (currentParameter.equals("Date")) {
					resultsPanel.removeAll();		
					setDates();
					Log.extractLog(start.getTime(), end.getTime(), true);
					showReport();
				} else if (currentParameter.equals("Tool")) {
					resultsPanel.removeAll();
					setDates();
					String tool = toolNameField.getText();
					Log.extractLog(start.getTime(), end.getTime(), true);
					Log.extractResultsByTool(tool);
					showReport();
				} else if (currentParameter.equals("Machine")) {
					resultsPanel.removeAll();
					setDates();
					String machine = machineNameField.getText();
					Log.extractLog(start.getTime(), end.getTime(), true);
					Log.extractResultsByMachine(machine);
					showReport();
				}
				saveExcelButton.setVisible(true);
			} else if (e.getSource() == saveExcelButton){
				ExcelExporter exp = new ExcelExporter();

				ArrayList<JTable> tables = new ArrayList<JTable>();
				
				switch (currentParameter){
				case "Date":
					tables.add(generalStatsTable);
					tables.add(machineFreqsTable);
					tables.add(toolFreqsTable);
					tables.add(deptFreqsTable);
					break;
				case "User":
					tables.add(generalStatsTable);
					tables.add(machineFreqsTable);
					tables.add(toolFreqsTable);
					break;
				case "Tool":
					tables.add(generalStatsTable);
					tables.add(deptFreqsTable);
					break;
				case "Machine":
					tables.add(generalStatsTable);
					tables.add(deptFreqsTable);
					break;
				}
				
				try {
					String date = dateFileFormat.format(Calendar.getInstance().getTime());
					String logFile = "ReportExports/"+currentParameter+"_log_export - "+date+".xls";
					String statsFile = "ReportExports/"+currentParameter+"_statistics_export - "+date+".xls";
					
					exp.exportTable(logTable, logFile);
					exp.exportTables(tables, statsFile);
					
					showMessage("Report Saved to ReportExports");
				} catch (Exception e1) {
					showMessage("Export failed!");
					e1.printStackTrace();
					return;
				}
			}
		}
	}
	
	private class DatePanel extends JPanel {
		DatePanel() {
			
			Font smallFont = new Font("SansSerif", Font.BOLD, 18);
			
			setLayout(new GridLayout(2, 2));
			
			JLabel startMonth = new JLabel("START: Month ");
			JLabel endMonth = new JLabel("END: Month ");
			JLabel startDay = new JLabel("Day ");
			JLabel endDay = new JLabel("Day ");
			JLabel startYear = new JLabel("Year ");
			JLabel endYear = new JLabel("Year ");
			
			startMonth.setFont(smallFont);
			endMonth.setFont(smallFont);
			startDay.setFont(smallFont);
			endDay.setFont(smallFont);
			startYear.setFont(smallFont);
			endYear.setFont(smallFont);
			
			startMonth.setHorizontalAlignment(JLabel.RIGHT);
			endMonth.setHorizontalAlignment(JLabel.RIGHT);
			startDay.setHorizontalAlignment(JLabel.RIGHT);
			endDay.setHorizontalAlignment(JLabel.RIGHT);
			startYear.setHorizontalAlignment(JLabel.RIGHT);
			endYear.setHorizontalAlignment(JLabel.RIGHT);
			
			
			final String[] MONTHS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
			
			final String[] DAYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", 
                "12", "13", "14", "15", "16", "17", "18", "19", "20", 
                "21", "22", "23", "24", "25", "26", "27", "28", "29", 
                "30", "31"};
			
			final String[] YEARS = {"2013", "2014", "2015", "2016", "2017", "2018", "2019", 
					"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
					"2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037",
					"2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", 
					"2047", "2048", "2049", "2050"};
			
			startMonths = new JComboBox<String>();
			for (String i : MONTHS) {
				startMonths.addItem(i);
			}
			startMonths.addActionListener(new ComboBoxListener());
			
			startMonths.setSelectedItem(Integer.toString(sMonth + 1));
			
			endMonths = new JComboBox<String>();
			for (String i : MONTHS) {
				endMonths.addItem(i);
			}
			endMonths.addActionListener(new ComboBoxListener());
			
			endMonths.setSelectedItem(Integer.toString(eMonth + 1));
			
			startDays = new JComboBox<String>();
			for (String i : DAYS) {
				startDays.addItem(i);
			}
			startDays.addActionListener(new ComboBoxListener());
			
			startDays.setSelectedItem(Integer.toString(sDay));
			
			endDays = new JComboBox<String>();
			for (String i : DAYS) {
				endDays.addItem(i);
			}
			endDays.addActionListener(new ComboBoxListener());
			
			endDays.setSelectedItem(Integer.toString(eDay));
			
			startYears = new JComboBox<String>();
			for (String i : YEARS) {
				startYears.addItem(i);
			}
			startYears.addActionListener(new ComboBoxListener());
			
			startYears.setSelectedItem(Integer.toString(sYear));
			
			endYears = new JComboBox<String>();
			for (String i : YEARS) {
				endYears.addItem(i);
			}
			endYears.addActionListener(new ComboBoxListener());
			
			endYears.setSelectedItem(Integer.toString(eYear));
			
			add(startMonth);
			add(startMonths);
			add(startDay);
			add(startDays);
			add(startYear);
			add(startYears);
			add(endMonth);
			add(endMonths);
			add(endDay);
			add(endDays);
			add(endYear);
			add(endYears);			
		}
	}
	
	private class UserPanel extends JPanel {
		UserPanel() {
			setLayout(new GridBagLayout());
			
			JPanel cwid = new JPanel();
			cwid.setLayout(new GridLayout(1, 2));
			
			JLabel cwidLabel = new JLabel("Enter User's CWID:");
			cwidLabel.setHorizontalAlignment(JLabel.CENTER);
			
			cwidField = new JTextField();
			cwidLabel.setFont(buttonFont);
			cwidField.setFont(textFont);
			cwidField.addActionListener(buttonListener);
			
			cwid.add(cwidLabel);
			cwid.add(cwidField);
	
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.5;
			c.gridx = 0;
			c.gridy = 0;
			c.ipady = 10;
			add(cwid, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.5;
			c.gridx = 0;
			c.gridy = 2;
			add(new DatePanel(), c);
		}	
	}
	
	private class ToolPanel extends JPanel {
		ToolPanel() {
			setLayout(new GridBagLayout());
			
			JPanel tool = new JPanel();
			tool.setLayout(new GridLayout(1, 2));
			
			JLabel toolNameLabel = new JLabel("Enter Name of Tool:");
			toolNameLabel.setHorizontalAlignment(JLabel.CENTER);
			
			toolNameField = new JTextField();
			toolNameLabel.setFont(buttonFont);
			toolNameField.setFont(textFont);
			toolNameField.addActionListener(buttonListener);
			
			tool.add(toolNameLabel);
			tool.add(toolNameField);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.5;
			c.gridx = 0;
			c.gridy = 0;
			c.ipady = 10;
			add(tool, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.5;
			c.gridx = 0;
			c.gridy = 2;
			add(new DatePanel(), c);
		}
	}
	
	private class MachinePanel extends JPanel {
		MachinePanel() {
			setLayout(new GridBagLayout());
			
			JPanel machine = new JPanel();
			machine.setLayout(new GridLayout(1, 2));
			
			JLabel machineNameLabel = new JLabel("Enter Name of Machine:");
			machineNameLabel.setHorizontalAlignment(JLabel.CENTER);
			
			machineNameField = new JTextField();
			machineNameLabel.setFont(buttonFont);
			machineNameField.setFont(textFont);
			machineNameField.addActionListener(buttonListener);
			
			machine.add(machineNameLabel);
			machine.add(machineNameField);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.5;
			c.gridx = 0;
			c.gridy = 0;
			c.ipady = 10;
			add(machine, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.5;
			c.gridx = 0;
			c.gridy = 2;
			add(new DatePanel(), c);
		}
	}	
}
