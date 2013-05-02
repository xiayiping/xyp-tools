package xyp.tool.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.XMLReaderFactory;

import xyp.tool.velocity.VelocityEngineWrapper;

public class SqlContainer {
	private static Log logger = LogFactory.getLog(SqlContainer.class);

	public static final String PROPERTIES_ROOT_PATH = "root.path";
	public static final String PROPERTIES_VELOCITY_CONFIG = "velocity.config.file.name";
	public static final String CONFIG_ATT_PREFIX = "prefix";
	public static final String CONFIG_ATT_NAME = "name";
	public static final String CONFIG_ELE_SQL = "sql";
	public static final String CONFIG_ELE_ROOT = "root";

	private Properties properties = new Properties();

	private SqlConfigContentHandler handler = null;
	private XMLReader configReader = null;

	private VelocityEngineWrapper velocityEngineWrapper = null;

	private HashMap<String, String> sqlMap = new HashMap<String, String>();

	public static SqlContainer newInstance(File configFile) {
		SqlContainer singleton = null;
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(configFile);

			singleton = new SqlContainer(fi);
			singleton.init();
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		} catch (SAXException e) {
			logger.error(e, e);
		} catch (URISyntaxException e) {
			logger.error(e, e);
		} finally {
			try {
				fi.close();
			} catch (Exception e) {
			}
		}

		return singleton;
	}

	private SqlContainer(InputStream is) throws FileNotFoundException, IOException, SAXException,
			URISyntaxException {

		try {
			logger.info("loading configurations.");
			properties.load(is);
			String velocityFileName = properties.getProperty(PROPERTIES_VELOCITY_CONFIG);

			if (null != velocityFileName && !velocityFileName.isEmpty()) {
				URI uri = SqlContainer.class.getClassLoader()
						.getResource(velocityFileName).toURI();

				velocityEngineWrapper = VelocityEngineWrapper.newInstance(new File(uri));
			}
		} catch (IOException e) {
			logger.error("can't find properties file.", e);
		} catch (NullPointerException np) {
			logger.warn(np, np);
		}

	}

	private void init() throws FileNotFoundException, IOException, SAXException {

		handler = new SqlConfigContentHandler();
		configReader = XMLReaderFactory.createXMLReader();
		configReader.setContentHandler(handler);

		String rootPath = properties.getProperty(PROPERTIES_ROOT_PATH);
		File file = new File(rootPath);

		processFile(file);
	}

	public void processFile(File file) throws FileNotFoundException, IOException, SAXException {

		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				processFile(child);
			}
		} else {

			logger.info("process file " + file.getAbsolutePath());
			configReader.parse(new InputSource(new FileInputStream(file)));
		}
	}

	private class SqlConfigContentHandler extends DefaultHandler2 {

		private boolean processRoot = false;
		private boolean processSql = false;

		StringBuilder sqlsb = null;
		String prefix = "";
		String sqlName = "";

		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (CONFIG_ELE_ROOT.equals(localName)) {
				processRoot = true;
				prefix = attributes.getValue(CONFIG_ATT_PREFIX);
			} else if (CONFIG_ELE_SQL.equals(localName)) {

				sqlName = attributes.getValue(CONFIG_ATT_NAME);
				sqlsb = new StringBuilder();
				processSql = true;
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (CONFIG_ELE_ROOT.equals(localName)) {
				processRoot = false;
			} else if (CONFIG_ELE_SQL.equals(localName)) {
				processSql = false;
				sqlMap.put(prefix + "." + sqlName, sqlsb.toString().trim() + " ");

			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			if (processSql) {
				sqlsb.append(ch, start, length);
			}
			if (processRoot) {
			}
		}
	}

	public String getSql(String name) {
		String sql = sqlMap.get(name);
		// logger.debug(sql);
		return sql;
	}

	public String getSql(String name, Map<String, Object> param) {
		String sql = sqlMap.get(name);
		sql = velocityEngineWrapper.evaluate(param, sql);
		// logger.debug(sql);
		return sql;
	}

	public Map<String, String> getSqlMap() {
		return this.sqlMap;
	}

}
