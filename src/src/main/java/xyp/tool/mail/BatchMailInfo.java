package xyp.tool.mail;

public class BatchMailInfo {

	/**
	 * mail template name
	 */
	private String textTemplate = null;
	/**
	 * xls file that contains varible for filling in mail template
	 */
	private String xlsFile = "";
	/**
	 * last column
	 */
	private String endColumn = "B";
	/**
	 * the column that mail to
	 */
	private String mailToColumn = null;
	private String mailCcColumn = null;
	private String mailBccColumn = null;
	private String mailFromColumn = null;
	private String mailSubjectColumn = null;
	private String mailInlineFileColumn = null;
	private String mailAttachFileColumn = null;

	private int startRow = 0;
	private int endRow = 0;
	private int sheetIndex = 0;

	public BatchMailInfo() {
	}

	public void setXlsFile(String xlsFile) {
		this.xlsFile = xlsFile;
	}

	/**
	 * 
	 * @param endRow
	 *            1 based. inner code will convert it to 0 based.
	 * 
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow - 1;
	}

	/**
	 * 
	 * 
	 * @param endRow
	 *            1 based. inner code will convert it to 0 based.
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow - 1;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex - 1;
	}

	// public void setTemplateName(String templateName) {
	// this.templateName = templateName;
	// }

	public void setEndColumn(String endColumn) {
		this.endColumn = endColumn.toUpperCase();
	}

	public void setMailToColumn(String mailToColumn) {
		this.mailToColumn = mailToColumn.toUpperCase();
	}

	// public String getTemplateName() {
	// return templateName;
	// }

	public String getXlsFile() {
		return xlsFile;
	}

	public String getEndColumn() {
		return endColumn;
	}

	public String getMailToColumn() {
		return mailToColumn;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public String getMailCcColumn() {
		return mailCcColumn;
	}

	public void setMailCcColumn(String mailCcColumn) {
		this.mailCcColumn = mailCcColumn;
	}

	public String getMailBccColumn() {
		return mailBccColumn;
	}

	public void setMailBccColumn(String mailBccColumn) {
		this.mailBccColumn = mailBccColumn;
	}

	public String getMailFromColumn() {
		return mailFromColumn;
	}

	public void setMailFromColumn(String mailFromColumn) {
		this.mailFromColumn = mailFromColumn;
	}

	public String getMailSubjectColumn() {
		return mailSubjectColumn;
	}

	public void setMailSubjectColumn(String mailSubjectColumn) {
		this.mailSubjectColumn = mailSubjectColumn;
	}

	public String getMailInlineFileColumn() {
		return mailInlineFileColumn;
	}

	public void setMailInlineFileColumn(String mailInlineFileColumn) {
		this.mailInlineFileColumn = mailInlineFileColumn;
	}

	public String getMailAttachFileColumn() {
		return mailAttachFileColumn;
	}

	public void setMailAttachFileColumn(String mailAttachFileColumn) {
		this.mailAttachFileColumn = mailAttachFileColumn;
	}

	public String getTextTemplate() {
		return textTemplate;
	}

	public void setTextTemplate(String textTemplate) {
		this.textTemplate = textTemplate;
	}

}
