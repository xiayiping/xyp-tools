package xyp.tool.excel.exception;

public class TypeNotSupportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6676208170151145495L;

	/**
	 * 
	 * @param classname
	 */
	public TypeNotSupportException(Class<?> clz) {
		super("type " + clz + " not supported");
	}

	public TypeNotSupportException(String clz) {
		super("type " + clz + " not supported");
	}
}
