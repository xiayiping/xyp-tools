package xyp.tool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class QueryUtil {

	public static List<Map<String, Object>> executeQueryToMapList(DataSource dataSource, String sql)
			throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			List<Map<String, Object>> mapList = null;

			ResultSet rs = stmt.executeQuery();
			mapList = ResultSetUtil.getMapFromResultSet(rs);
			return mapList;
		} finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (null != conn)
					conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static List<Map<String, Object>> executeQueryToMapList(DataSource dataSource,
			String sql, int start, int rowCount) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			List<Map<String, Object>> mapList = null;

			ResultSet rs = stmt.executeQuery();
			mapList = ResultSetUtil.getMapFromResultSet(rs, start, rowCount);
			return mapList;
		} finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (null != conn)
					conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static List<Map<String, Object>> executeQueryToMapList(DataSource dataSource,
			String sql, List<Object> params) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			List<Map<String, Object>> mapList = null;

			ResultSet rs = stmt.executeQuery();
			mapList = ResultSetUtil.getMapFromResultSet(rs);
			return mapList;
		} finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (null != conn)
					conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static List<Map<String, Object>> executeQueryToMapList(DataSource dataSource,
			String sql, List<Object> params, int start, int rowCount) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			List<Map<String, Object>> mapList = null;

			ResultSet rs = stmt.executeQuery();
			mapList = ResultSetUtil.getMapFromResultSet(rs, start, rowCount);
			return mapList;
		} finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (null != conn)
					conn.close();
			} catch (Exception e) {
			}
		}
	}
}
