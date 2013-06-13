package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExcelExporter {

	public void exportTable(JTable table, String filename) throws Exception{
		FileWriter out = makeFileWriter(filename);
		writeTable(table, out);
		out.close();
	}
	
	public void exportTables(ArrayList<JTable> tables, String filename) throws Exception{		
		FileWriter out = makeFileWriter(filename);
		for (int i=0; i< tables.size(); ++i){
			writeTable(tables.get(i), out);
		}
		out.close();
	}
	
	private void writeTable(JTable table, FileWriter outFile) throws IOException{
		TableModel model = table.getModel();
		
		// write column names
		for(int col=0; col < model.getColumnCount(); col++) {
			outFile.write(model.getColumnName(col)+"\t");
		}
		
		outFile.write("\n");
		if (table.getName() != null){
			outFile.write(table.getName()+"\n");
		}
		
		// write data
		for(int row=0; row < model.getRowCount(); row++){
			for(int col=0; col < model.getColumnCount(); col++){
				if (model.getValueAt(row, col) == null){
					outFile.write("\t");
				} else {
					String cell = model.getValueAt(row, col).toString();
					
					cell = cell.replaceAll(",", " ");
					
					outFile.write(cell+"\t");
				}
			}
			outFile.write("\n");
		}
	}
	
	private FileWriter makeFileWriter(String filename) throws IOException{
		File file = new File(filename);
		if (!dirExists(file)){
			throw new IOException("directory could not be created");
		}
		
		FileWriter out = new FileWriter(file);
		
		return out;
	}
	
	
	private boolean dirExists(File file) throws IOException{
		File dir = new File(file.getParent());
		if (dir.exists()){
			return true;
		} else {
			return dir.mkdirs();
		}
	}
}
