package xyp.tool.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResultSetUtil {

	public static List<Map<String, Object>> getMapFromResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		LinkedList<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		while (rs.next()) {
			Map<String, Object> map = new TreeMap<String, Object>();
			for (int i = 0; i < columnCount; i++) {
				map.put(md.getColumnLabel(i + 1), rs.getObject(i + 1));
			}
			list.addLast(map);
		}
		return list;
	}

	public static List<Map<String, Object>> getMapFromResultSet(ResultSet rs, int startWith,
			int rowCount) throws SQLException {
		ResultSetMetaData md = null;
		int columnCount = -1;
		LinkedList<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		int rowIndex = 0;
		// int rowCount = 0;
		
		while (rs.next() && rowIndex++ >= startWith && rowIndex - startWith <= rowCount) {
			if (null == md) {
				md = rs.getMetaData();
				columnCount = md.getColumnCount();
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			for (int i = 0; i < columnCount; i++) {
				map.put(md.getColumnLabel(i + 1), rs.getObject(i + 1));
			}
			list.addLast(map);
			// rowCount++;
		}
		return list;
	}

	public static void main(String[] args) {
		int i = 0;
		for (int j = 0; j < 100; j++) {
			if (i++ > 1 && i < 3)
				System.out.println(i);
		}
	}
}
