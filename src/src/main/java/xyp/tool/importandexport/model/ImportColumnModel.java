package xyp.tool.importandexport.model;

import xyp.tool.excel.SupportedType;

public class ImportColumnModel {
	private String key = null;
	private String type = SupportedType.TYPE_STRING;

	@Override
	public ImportColumnModel clone() {
		return new ImportColumnModel(key, type);
	}

	public ImportColumnModel(String key, String type) {
		super();
		this.key = key.trim();
		if (type != null) {
			this.type = type;
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
