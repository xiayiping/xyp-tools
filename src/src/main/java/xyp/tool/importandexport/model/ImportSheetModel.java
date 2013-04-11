package xyp.tool.importandexport.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import xyp.tool.importandexport.exception.ConfigException;

/**
 * if no sheet name provided, sheet index will be used. Otherwise only uses
 * sheet name.
 * 
 * @author xiayip
 * 
 */
public class ImportSheetModel {

	private int start = 0;
	private String sheetName = null;
	private int sheetIndex = 0;
	private List<ImportColumnModel> columnList = null;

	@Override
	public ImportSheetModel clone() {
		Iterator<ImportColumnModel> it = this.columnList.iterator();
		LinkedList<ImportColumnModel> list = new LinkedList<ImportColumnModel>();
		while (it.hasNext()) {
			list.addLast(it.next().clone());
		}
		return new ImportSheetModel(sheetName, sheetIndex, start, list);
	}

	/**
	 * sheet name has a higher priority to sheet index. The import tool will use
	 * sheet index only when the sheet name is null.
	 * 
	 * @param name
	 * @param start
	 *            based on 1 for excel convention, the constructor will change
	 *            it to 0 based.
	 * @param columnList
	 * @throws ConfigException
	 */
	public ImportSheetModel(String sheetName, Integer sheetIndex, Integer start,
			List<ImportColumnModel> columnList) {

		if (null != start)
			this.start = (start) - 1;

		this.sheetName = sheetName;

		if (null != sheetIndex) {
			this.sheetIndex = sheetIndex - 1;
		}
		this.sheetName = sheetName;
		this.columnList = columnList;

	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start - 1;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String name) {
		this.sheetName = name;
	}

	public List<ImportColumnModel> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ImportColumnModel> columnList) {
		this.columnList = columnList;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheet) {
		this.sheetIndex = sheet - 1;
	}

}
