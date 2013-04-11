package xyp.tool.excel;

public class SupportedType {
	/**
	 * java.lang.Integer
	 */
	public static final String TYPE_INTEGER = java.lang.Integer.class.getName();
	/**
	 * java.lang.Long
	 */
	public static final String TYPE_LONG = java.lang.Long.class.getName();
	/**
	 * java.lang.Float
	 */
	public static final String TYPE_FLOAT = java.lang.Float.class.getName();
	/**
	 * java.lang.Double
	 */
	public static final String TYPE_DOUBLE = java.lang.Double.class.getName();
	/**
	 * java.lang.Boolean
	 */
	public static final String TYPE_BOOLEAN = java.lang.Boolean.class.getName();

	/**
	 * java.lang.String
	 */
	public static final String TYPE_STRING = java.lang.String.class.getName();
	/**
	 * java.util.Date
	 */
	public static final String TYPE_DATE = java.util.Date.class.getName();

	public static boolean isSupported(String arg) {
		return TYPE_INTEGER.equals(arg) || TYPE_LONG.equals(arg)
				|| TYPE_FLOAT.equals(arg) || TYPE_DOUBLE.equals(arg)
				|| TYPE_BOOLEAN.equals(arg) || TYPE_STRING.equals(arg)
				|| TYPE_DATE.equals(arg);
	}
}
