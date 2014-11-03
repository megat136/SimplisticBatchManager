package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.BatFileModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accesobjects.XMLDataMapper;

public class BatTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	Logger logger=LoggerFactory.getLogger(BatTableModel.class);
	String[] columnNames = { "Job Name", "briefDesc", "Filename",
			"CommandArgs", "Creation Time", "nextExecution",
			"executionPattern", "isActive" };
	Object[][] data;

	/*
	 * Every Change at BatFile entity effects this method. You need the change
	 * BatFile.getAsRow() method
	 */
	public BatTableModel(List<BatFileModel> modelList) {
		data = new Object[modelList.size()][];
		for (int i = 0; i < modelList.size(); i++) {
			data[i] = modelList.get(i).getAsRow();
		}
	}

	public BatTableModel() {

	}

	public void initializeTable(List<BatFileModel> batfiles) {
		data = new Object[batfiles.size()][];
		for (int i = 0; i < batfiles.size(); i++) {
			data[i] = batfiles.get(i).getAsRow();
		}
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int column) {
		return data[row][column];
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];

	}
	
	@Override
	public void fireTableDataChanged(){
		try {
			initializeTable(XMLDataMapper.getInstance().findAll());
		} catch (Exception e) {
			logger.error("Can't fire tableDataChanged event. Check your XML file for errors.",e);
		}
	}
}
