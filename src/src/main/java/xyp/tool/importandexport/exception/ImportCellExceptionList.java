package xyp.tool.importandexport.exception;

import java.util.List;

public class ImportCellExceptionList extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<ImportCellException> cellExceptions = null;

	public ImportCellExceptionList(List<ImportCellException> cellE) {
		this.cellExceptions = cellE;
	}
}
