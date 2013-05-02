package xyp.tool.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSourceUtil {

	private static Log logger = LogFactory.getLog(DataSourceUtil.class);

	public static DataSource getDataSource(File config) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(config));
			DataSource ds = BasicDataSourceFactory.createDataSource(prop);
			return ds;
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}
}
