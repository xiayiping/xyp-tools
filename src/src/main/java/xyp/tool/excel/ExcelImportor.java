package xyp.tool.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import xyp.tool.excel.exception.TypeNotSupportException;
import xyp.tool.excel.reader.CellReaderFactory;
import xyp.tool.excel.reader.ICellReader;
import xyp.tool.importandexport.conf.ImportConfigurationContainer;
import xyp.tool.importandexport.exception.ConfigNotFoundException;
import xyp.tool.importandexport.exception.ImportCellException;
import xyp.tool.importandexport.exception.ImportCellExceptionList;
import xyp.tool.importandexport.exception.ImportException;
import xyp.tool.importandexport.exception.SheetNotFoundException;
import xyp.tool.importandexport.interfaces.IImport;
import xyp.tool.importandexport.model.ImportColumnModel;
import xyp.tool.importandexport.model.ImportSheetModel;

public class ExcelImportor implements IImport {

	private static Log logger = LogFactory.getLog(ExcelImportor.class);
	private ImportConfigurationContainer configContainer;

	private ExcelImportor(ImportConfigurationContainer container) {
		this.configContainer = container;
	}

	public static ExcelImportor newInstance(ImportConfigurationContainer container) {
		return new ExcelImportor(container);
	}

	@Override
	public List<Map<String, Object>> importFromSourceToMap(File source, String configName)
			throws ImportException, FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(source));
		ImportSheetModel sheetModel = getImportSheetModel(configName);
		return getMapFromWorkBook(workbook, sheetModel);
	}

	private List<Map<String, Object>> getMapFromWorkBook(HSSFWorkbook workbook,
			ImportSheetModel sheetModel) throws ImportException {

		String sheetName = null;
		HSSFSheet sheet = null;
		sheetName = sheetModel.getSheetName();
		int sheetIndex = sheetModel.getSheetIndex();
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		if (sheetName != null) {
			sheet = workbook.getSheet(sheetName);
		} else {
			try {
				sheet = workbook.getSheetAt(sheetIndex);
			} catch (Exception e) {
				throw new ImportException(new SheetNotFoundException(sheetName, sheetIndex));
			}
		}
		if (null == sheet) {
			throw new ImportException(new SheetNotFoundException(sheetName, sheetIndex));
		}
		LinkedList<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		LinkedList<ImportCellException> cellExList = new LinkedList<ImportCellException>();
		int startRow = sheetModel.getStart();
		int lastRow = sheet.getLastRowNum();
		for (int i = startRow; i <= lastRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (null == row)
				break;
			Map<String, Object> map = new HashMap<String, Object>();
			list.addLast(map);
			List<ImportColumnModel> columnList = sheetModel.getColumnList();
			Iterator<ImportColumnModel> it = columnList.iterator();
			int j = -1;
			while (it.hasNext()) {
				j++;
				ImportColumnModel columnModel = it.next();
				HSSFCell cell = null;
				cell = row.getCell(j);
				try {
					cell = row.getCell(j);
					ICellReader cellReader = CellReaderFactory.getCellReader(columnModel.getType());
					Object value = cellReader.readFromCell(cell, evaluator);
					map.put(columnModel.getKey(), value);
				} catch (TypeNotSupportException e) {
					logger.error(e, e);
					cellExList.addLast(new ImportCellException(e, cell));
				} catch (Throwable t) {
					logger.error(t, t);
					cellExList.addLast(new ImportCellException(t, cell));
				}
			}
		}
		if (cellExList.size() > 0) {
			throw new ImportException(new ImportCellExceptionList(cellExList));
		}
		return list;

	}

	private ImportSheetModel getImportSheetModel(String configName) throws ImportException {
		ImportSheetModel sm = this.configContainer.getImportSheetModel(configName);
		if (null == sm)
			throw new ImportException(new ConfigNotFoundException(configName));
		return sm;
	}

}
