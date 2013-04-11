package xyp.tool.importandexport.exception;

import java.util.List;

public class SheetNameDuplicateExceptionList extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<SheetNameDuplicateException> getDuplicateList() {
		return duplicateList;
	}

	private List<SheetNameDuplicateException> duplicateList;

	public SheetNameDuplicateExceptionList(List<SheetNameDuplicateException> duplicateList) {
		this.duplicateList = duplicateList;
	}
}
