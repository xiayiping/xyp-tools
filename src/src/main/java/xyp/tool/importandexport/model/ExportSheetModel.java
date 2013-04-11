package xyp.tool.importandexport.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExportSheetModel {

	@Override
	public ExportSheetModel clone() {
		LinkedList<ExportColumnModel> columns = new LinkedList<ExportColumnModel>();
		Iterator<ExportColumnModel> it = this.columnList.iterator();
		while (it.hasNext()) {
			columns.addLast(it.next().clone());
		}
		return new ExportSheetModel(this.name, this.sheetNameColor, /*this.titleColor,*/ columns);
	}

	public ExportSheetModel(String name, String sheetColor, /*String titleColor,*/
			List<ExportColumnModel> models) {
		this.name = name;
		this.sheetNameColor = sheetColor;
//		this.titleColor = titleColor;
		this.columnList = models;
	}

	private String name = null;
	private List<ExportColumnModel> columnList = null;

//	private String titleColor = null;
	private String sheetNameColor = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExportColumnModel> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ExportColumnModel> columnList) {
		this.columnList = columnList;
	}

//	public String getTitleColor() {
//		return titleColor;
//	}
//
//	public void setTitleColor(String titleColor) {
//		this.titleColor = titleColor;
//	}

	public String getSheetNameColor() {
		return sheetNameColor;
	}

	public void setSheetNameColor(String sheetNameColor) {
		this.sheetNameColor = sheetNameColor;
	}

}
