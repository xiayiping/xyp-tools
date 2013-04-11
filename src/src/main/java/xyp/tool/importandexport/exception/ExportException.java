package xyp.tool.importandexport.exception;

public class ExportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExportException(ConfigNotFoundException cnf) {
		super("config not found for " + cnf.getConfigName(), cnf);
	}

	public ExportException(ConfigNotFoundExceptionList cnf) {
		super("config list not found for ", cnf);
	}

	public ExportException(SheetNameDuplicateException cnf) {
		super("sheet name duplicated", cnf);
	}

	public ExportException(SheetNameDuplicateExceptionList cnf) {
		super("sheet name duplicated", cnf);
	}
}
