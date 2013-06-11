package main;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExcelExporter {

	public void exportTable(JTable table, String filename) throws Exception{
		File file = new File(filename);
		TableModel model = table.getModel();
		FileWriter out = new FileWriter(file);
		
		// write column names
		for(int col=0; col < model.getColumnCount(); col++) {
			out.write(model.getColumnName(col)+"\t");
		}
		
		out.write("\n");
		
		// write data
		for(int row=0; row < model.getRowCount(); row++){
			for(int col=0; col < model.getColumnCount(); col++){
				out.write(model.getValueAt(row, col).toString()+"\t");
			}
			out.write("\n");
		}

		out.close();
	}
}
