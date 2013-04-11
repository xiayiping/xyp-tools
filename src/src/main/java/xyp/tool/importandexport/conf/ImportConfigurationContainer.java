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

import xyp.tool.excel.SupportedType;
import xyp.tool.importandexport.model.ImportColumnModel;
import xyp.tool.importandexport.model.ImportSheetModel;

public class ImportConfigurationContainer {
	private static Log logger = LogFactory.getLog(ImportConfigurationContainer.class);

	public static final String ATT_SHEET_NAME = "name";
	public static final String ATT_SHEET_INDEX = "index";
	public static final String ATT_START_ROW = "startRow";

	public static final String ATT_KEY = "key";
	public static final String ATT_TYPE = "type";

	public static final String ELE_SHEET = "sheet";
	public static final String ELE_COLUMN = "column";

	public static final String PROPERTIES_ROOT_PATH = "root.path";

	private Properties properties = new Properties();

	private ExportConfigurationContainerHandler handler = null;
	private XMLReader configReader = null;

	private Map<String, ImportSheetModel> importMap = new TreeMap<String, ImportSheetModel>(
			new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});

	private ImportConfigurationContainer(Properties props) throws SAXException,
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

	public static ImportConfigurationContainer newInstance(File propertiesFile) {
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(propertiesFile);
			Properties prop = new Properties();
			prop.load(fi);
			ImportConfigurationContainer ec = new ImportConfigurationContainer(prop);
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
		ImportSheetModel sheetModel = null;

		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (ELE_SHEET.equals(qName)) {
				String sheetName = attributes.getValue(ATT_SHEET_NAME);
				List<ImportColumnModel> columnList = new LinkedList<ImportColumnModel>();
				String startStr = attributes.getValue(ATT_START_ROW);
				String sheetIndexStr = attributes.getValue(ATT_SHEET_INDEX);
				Integer start = 1;
				Integer sheetIndex = 1;
				if (null != startStr) {
					try {
						start = Integer.parseInt(startStr);
					} catch (NumberFormatException e) {
						logger.error(e, e);
						throw new SAXException("start parse error at "
								+ currentFile.getAbsolutePath(), e);
					}
				}
				if (null != sheetIndexStr) {
					try {
						sheetIndex = Integer.parseInt(sheetIndexStr);
					} catch (NumberFormatException e) {
						logger.error(e, e);
						throw new SAXException("sheet index parse error at "
								+ currentFile.getAbsolutePath(), e);
					}
				}
				if (start < 1) {
					throw new SAXException("start " + start + " cannot less than 1 at"
							+ currentFile.getAbsolutePath());
				}
				if (sheetIndex < 1) {
					throw new SAXException("sheetIndex " + sheetIndex + " cannot less than 1 at"
							+ currentFile.getAbsolutePath());
				}

				sheetModel = new ImportSheetModel(sheetName, sheetIndex, start, columnList);

			} else if (ELE_COLUMN.equals(qName)) {
				String key = attributes.getValue(ATT_KEY);
				String type = attributes.getValue(ATT_TYPE);
				if (null == key) {
					throw new SAXException("key cannot be null at " + currentFile.getAbsolutePath());
				}
				if (null != type) {
					if (!SupportedType.isSupported(type)) {
						throw new SAXException("type" + type + "not supported at " + currentFile);
					}
				}

				ImportColumnModel columnModel = new ImportColumnModel(key, type);
				sheetModel.getColumnList().add(columnModel);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (ELE_SHEET.equals(qName)) {
				importMap.put(getConfigName(currentFile.getAbsolutePath()), sheetModel);
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

	public ImportSheetModel getImportSheetModel(String name) {
		return this.importMap.get(name);
	}

	public Map<String, ImportSheetModel> getMap() {
		return this.importMap;
	}
}
