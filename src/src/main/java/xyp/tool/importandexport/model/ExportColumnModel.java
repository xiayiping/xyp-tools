package xyp.tool.importandexport.model;

public class ExportColumnModel {

	@Override
	public ExportColumnModel clone() {
		return new ExportColumnModel(key, title, titleStyle, excelFormat);
	}

	public ExportColumnModel(String key, String title, String titleStyle, String excelFormat) {
		this.key = key;
		if (null == title || title.trim().isEmpty()) {
			this.title = this.key;
		} else {
			this.title = title;
		}

		if (null == titleStyle || titleStyle.trim().isEmpty()) {

		} else {
			this.titleStyle = titleStyle;
		}
		if (null != excelFormat && !excelFormat.trim().isEmpty())
			this.excelFormat = excelFormat;
	}

	/**
	 * used when export to excel, title
	 */
	private String title = null;
	private String titleStyle = null;
	/**
	 * used for map key, SQL result field and Object properties.
	 */
	private String key = null;

	/**
	 * excel format of the cell, in excel, right click excel cell , and choose
	 * format, and choose customer, see the format details
	 */
	private String excelFormat = null;
	
	private boolean allowDuplicate = true;

	/**
	 * java type the column presents
	 */
	// private String javaType = null;

	// public String getJavaType() {
	// return javaType;
	// }
	//
	// public void setJavaType(String javaType) {
	// this.javaType = javaType;
	// }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcelFormat() {
		return excelFormat;
	}

	public void setExcelFormat(String excelFormat) {
		if (null == excelFormat || excelFormat.trim().isEmpty())
			return;
		this.excelFormat = excelFormat;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static void main(String[] args) {
		System.out.println("   ".trim().isEmpty());
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
	}

	public boolean isAllowDuplicate() {
		return allowDuplicate;
	}

	public void setAllowDuplicate(boolean allowDuplicate) {
		this.allowDuplicate = allowDuplicate;
	}

}
