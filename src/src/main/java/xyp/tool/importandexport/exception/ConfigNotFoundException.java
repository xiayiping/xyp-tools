package xyp.tool.importandexport.exception;

@SuppressWarnings("serial")
public class ConfigNotFoundException extends Exception {

	private String configName = null;

	public ConfigNotFoundException(String configname) {
		super("config name : " + configname + " cannot be found.");
		this.configName = configname;
	}

	public String getConfigName() {
		return configName;
	}

}
