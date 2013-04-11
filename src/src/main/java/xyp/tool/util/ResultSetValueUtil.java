package xyp.tool.util;

import java.math.BigDecimal;

public class ResultSetValueUtil {

	public static String getStringValue(Object val) {
		try {
			return val.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static Boolean getBooleanValue(Object val) {
		if (val == null) {
			return null;
		}
		Integer intval = getIntegerValue(val);
		if (intval == null) {
			return null;
		} else {

			if (val instanceof Boolean) {
				return (Boolean) val;
			} else {
				return intval > 0;
			}
		}
	}

	public static Integer getIntegerValue(Object val) {
		if (val == null) {
			return null;
		}
		if (val instanceof Long) {
			return ((Long) val).intValue();
		} else if (val instanceof Integer) {
			return (Integer) val;
		} else if (val instanceof BigDecimal) {
			return ((BigDecimal) val).intValue();
		} else if (val instanceof Double) {
			return ((Double) val).intValue();
		} else if (val instanceof Float) {
			return ((Float) val).intValue();
		}
		return null;
	}

	public static Long getLongValue(Object val) {
		if (val == null) {
			return null;
		}
		if (val instanceof Long || val instanceof Integer) {
			return (Long) val;
		} else if (val instanceof BigDecimal) {
			return ((BigDecimal) val).longValue();
		} else if (val instanceof Double) {
			return ((Double) val).longValue();
		} else if (val instanceof Float) {
			return ((Float) val).longValue();
		}
		return null;
	}
}
