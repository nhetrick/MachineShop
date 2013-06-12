package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExcelExporter {

	public void exportTable(JTable table, String filename) throws Exception{
		File file = new File(filename);
		if (!dirExists(file)){
			throw new IOException("directory could not be created");
		}
		
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
				if (model.getValueAt(row, col) == null){
					out.write("\t");
				} else {
					String cell = model.getValueAt(row, col).toString();
					
//					cell = cell.replaceAll(",", " ");
					
					out.write(cell+"\t");
				}
			}
			out.write("\n");
		}

		out.close();
	}
	
	public void exportTables(ArrayList<JTable> tables, String filename) throws Exception{
		File file = new File(filename);
		if (!dirExists(file)){
			throw new IOException("directory could not be created");
		}
		
		FileWriter out = new FileWriter(file);
		
		for (int i=0; i< tables.size(); ++i){

			TableModel model = tables.get(i).getModel();
			
			
			// write column names
			for(int col=0; col < model.getColumnCount(); col++) {
				out.write(model.getColumnName(col)+"\t");
			}
			
			out.write("\n");
			out.write(tables.get(i).getName()+"\n");
			
			// write data
			for(int row=0; row < model.getRowCount(); row++){
				for(int col=0; col < model.getColumnCount(); col++){
					if (model.getValueAt(row, col) == null){
						out.write("\t");
					} else {
						String cell = model.getValueAt(row, col).toString();
						
//						cell = cell.replaceAll(",", " ");
						
						out.write(cell+"\t");
					}
				}
				out.write("\n");
			}
		}
		
		
		out.close();
	}
	
	public boolean dirExists(File file) throws IOException{
		File dir = new File(file.getParent());
		if (dir.exists()){
			return true;
		} else {
			return dir.mkdirs();
		}
	}
}
