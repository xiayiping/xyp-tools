package xyp.tool.util;

public class ExceptionUtil {

	public static String getStackTraceFromException(Throwable t, String newLine) {
		if (null == t)
			return null;
		StringBuilder sb = new StringBuilder(t.getClass().getName()).append(" - ").append(
				t.getMessage());

		StackTraceElement[] eles = t.getStackTrace();

		for (StackTraceElement ele : eles) {
			sb.append(newLine).append("       at ").append(ele.toString());
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			sb.append(newLine).append("caused by: ")
					.append(getStackTraceFromException(cause, newLine));
		}
		return sb.toString();
	}
}
