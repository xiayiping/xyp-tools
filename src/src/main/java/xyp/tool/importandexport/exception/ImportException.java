package xyp.tool.importandexport.exception;

@SuppressWarnings({ "serial" })
public class ImportException extends Exception {

	public ImportException(ImportCellExceptionList list) {
		super("caused by cell import exceptions", list);
	}

	public ImportException(SheetNotFoundException snf) {
		super("caused by sheet not found", snf);
	}

	public ImportException(ConfigNotFoundException cnf) {
		super("caused by config not found: " + cnf.getConfigName(), cnf);
	}
}
