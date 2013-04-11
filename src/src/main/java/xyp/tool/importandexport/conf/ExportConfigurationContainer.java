package xyp.tool.importandexport.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.XMLReaderFactory;

import xyp.tool.importandexport.model.ExportColumnModel;
import xyp.tool.importandexport.model.ExportSheetModel;

public class ExportConfigurationContainer {
	private static Log logger = LogFactory.getLog(ExportConfigurationContainer.class);

	public static final String ATT_NAME = "name";
	public static final String ATT_KEY = "key";
	public static final String ATT_TITLE = "title";
	public static final String ATT_FORMAT = "format";
	public static final String ELE_SHEET = "sheet";
	public static final String ELE_COLUMN = "column";
	public static final String ATT_SHEET_NAME_COLOR = "sheetNameColor";
	public static final String ATT_TITLE_COLOR = "titleColor";
	public static final String ATT_TITLE_STYLE = "titleStyle";

	public static final String ATT_ALLOW_DUPLICATE = "allowDuplicate";

	public static final String PROPERTIES_ROOT_PATH = "root.path";

	private Properties properties = new Properties();

	private ExportConfigurationContainerHandler handler = null;
	private XMLReader configReader = null;

	private Map<String, ExportSheetModel> exportMap = new TreeMap<String, ExportSheetModel>(
			new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});

	private ExportConfigurationContainer(Properties props) throws SAXException,
			FileNotFoundException, IOException {
		this.properties = props;
		this.handler = new ExportConfigurationContainerHandler();
		this.configReader = XMLReaderFactory.createXMLReader();
		configReader.setContentHandler(handler);

		String rootPath = properties.getProperty(PROPERTIES_ROOT_PATH);
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		processFile(file);
	}

	public static ExportConfigurationContainer newInstance(File propertiesFile) {
		FileInputStream fi = null;
		try {
			Properties prop = new Properties();
			fi = new FileInputStream(propertiesFile);
			prop.load(fi);
			ExportConfigurationContainer ec = new ExportConfigurationContainer(prop);
			return ec;
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		} catch (SAXException e) {
			logger.error(e, e);
		} finally {
			try {
				fi.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public void processFile(File file) throws FileNotFoundException, IOException, SAXException {

		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				processFile(child);
			}
		} else {
			logger.info("process file " + file.getAbsolutePath());
			handler.currentFile = file;
			configReader.parse(new InputSource(new FileInputStream(file)));
		}
	}

	private class ExportConfigurationContainerHandler extends DefaultHandler2 {
		File currentFile = null;
		ExportSheetModel sheetModel = null;

		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (ELE_SHEET.equals(qName)) {
				String sheetName = attributes.getValue(ATT_NAME);
				String sheetNameColor = attributes.getValue(ATT_SHEET_NAME_COLOR);
				// String titleColor = attributes.getValue(ATT_TITLE_COLOR);
				List<ExportColumnModel> columnList = new LinkedList<ExportColumnModel>();

				sheetModel = new ExportSheetModel(sheetName, sheetNameColor, /*
																			 * titleColor
																			 * ,
																			 */columnList);

			} else if (ELE_COLUMN.equals(qName)) {
				String key = attributes.getValue(ATT_KEY);
				if (null == key) {
					throw new SAXException("key cannot be null " + currentFile.getAbsolutePath());
				}
				String title = attributes.getValue(ATT_TITLE);
				String titleStyle = attributes.getValue(ATT_TITLE_STYLE);
				String format = attributes.getValue(ATT_FORMAT);
				String allDuplicate = attributes.getValue(ATT_ALLOW_DUPLICATE);
				boolean alldupli = true;
				if (null != allDuplicate && "false".equalsIgnoreCase(allDuplicate)) {
					alldupli = false;
				}

				ExportColumnModel columnModel = new ExportColumnModel(key, title, titleStyle,
						format);
				columnModel.setAllowDuplicate(alldupli);
				sheetModel.getColumnList().add(columnModel);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (ELE_SHEET.equals(qName)) {
				exportMap.put(getConfigName(currentFile.getAbsolutePath()), sheetModel);
				currentFile = null;
				sheetModel = null;
			} else if (ELE_COLUMN.equals(qName)) {
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
		}
	}

	private String getConfigName(String fileAbsPath) {
		String rp = this.properties.getProperty(PROPERTIES_ROOT_PATH);
		rp = rp.replaceAll("\\\\", "/");
		fileAbsPath = fileAbsPath.replaceAll("\\\\", "/");
		int prefixCount = rp.length();
		if (!rp.endsWith("/"))
			prefixCount++;
		return fileAbsPath.substring(prefixCount);
	}

	public ExportSheetModel getExportSheetModel(String name) {
		return this.exportMap.get(name);
	}

	public Map<String, ExportSheetModel> getMap() {
		return this.exportMap;
	}
}
