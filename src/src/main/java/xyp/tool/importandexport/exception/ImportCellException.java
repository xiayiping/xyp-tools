package xyp.tool.importandexport.exception;

import org.apache.poi.ss.usermodel.Cell;

@SuppressWarnings("serial")
public class ImportCellException extends Exception {
	private Cell cell = null;

	public ImportCellException(Throwable arg0, Cell cell) {
		super(new StringBuilder("error when read cell ").append(cell.getRowIndex()).append(",")
				.append(cell.getColumnIndex()).append(" of exception ").append(arg0).toString(),
				arg0);
		this.cell = cell;
	}

	public Cell getCell() {
		return cell;
	}
}
