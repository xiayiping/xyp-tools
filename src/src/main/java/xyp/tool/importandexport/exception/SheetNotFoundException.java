package xyp.tool.importandexport.exception;

public class SheetNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int sheetIndex;
	private String sheetName = null;

	public int getSheetIndex() {
		return sheetIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public SheetNotFoundException(String name, int index) {
		super("sheet not found for sheet-name: '" + name + "'. Or index: " + index);
		this.sheetName = name;
		this.sheetIndex = index;
	}
}
