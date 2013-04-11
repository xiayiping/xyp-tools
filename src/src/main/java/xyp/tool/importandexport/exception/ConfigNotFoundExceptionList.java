package xyp.tool.importandexport.exception;

import java.util.List;

public class ConfigNotFoundExceptionList extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<ConfigNotFoundException> getConfigList() {
		return configList;
	}

	private List<ConfigNotFoundException> configList = null;

	public ConfigNotFoundExceptionList(List<ConfigNotFoundException> models) {
		super();
		this.configList = models;
	}
}
