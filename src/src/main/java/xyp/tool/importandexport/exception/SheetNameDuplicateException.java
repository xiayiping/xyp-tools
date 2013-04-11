package xyp.tool.importandexport.exception;


public class SheetNameDuplicateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param arg0
	 *            should be a sheet name
	 * @param arg1
	 */
	public SheetNameDuplicateException(String[] I, String[] II) {
		super("name duplicated with" + I[0] + ":" + I[1] + "   <->   " + II[0] + ":" + II[1]);
		this.configAndNameI = I;
		this.configAndNameII = II;
	}

	private String[] configAndNameI;
	private String[] configAndNameII;

	public String[] getConfigAndNameI() {
		return configAndNameI;
	}

	public String[] getConfigAndNameII() {
		return configAndNameII;
	}

}
