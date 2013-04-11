package xyp.tool.util;

public class StringUtil {

	public static String erasePathEndSlash(String path) {
		if (null == path) {
			return null;
		}
		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

	public static String formatPathSlash(String path) {
		if (null == path) {
			return null;
		}
		return path.replaceAll("\\\\", "/");
	}
}
