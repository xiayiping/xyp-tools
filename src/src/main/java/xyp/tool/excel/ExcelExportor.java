package xyp.tool.excel;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;

import xyp.tool.excel.util.ExcelCellWriter;
import xyp.tool.importandexport.conf.ExportConfigurationContainer;
import xyp.tool.importandexport.exception.ConfigNotFoundException;
import xyp.tool.importandexport.exception.ConfigNotFoundExceptionList;
import xyp.tool.importandexport.exception.ExportException;
import xyp.tool.importandexport.exception.SheetNameDuplicateException;
import xyp.tool.importandexport.exception.SheetNameDuplicateExceptionList;
import xyp.tool.importandexport.interfaces.IExport;
import xyp.tool.importandexport.model.ExportColumnModel;
import xyp.tool.importandexport.model.ExportSheetModel;

public class ExcelExportor implements IExport<HSSFWorkbook> {

	private static Log logger = LogFactory.getLog(ExcelExportor.class);
	private ExportConfigurationContainer configContainer;

	private ExcelExportor(ExportConfigurationContainer container) {
		this.configContainer = container;
	}

	public static ExcelExportor newInstance(ExportConfigurationContainer container) {
		return new ExcelExportor(container);
	}

	private List<HSSFCellStyle> getStyles(HSSFWorkbook workbook, ExportSheetModel sheetModel,
			DataFormat format) {
		LinkedList<HSSFCellStyle> styles = new LinkedList<HSSFCellStyle>();

		for (ExportColumnModel cm : sheetModel.getColumnList()) {
			String value = cm.getExcelFormat();
			if (value != null) {
				HSSFCellStyle style = workbook.createCellStyle();
				style.setDataFormat(format.getFormat(value));
				styles.addLast(style);

			} else {
				styles.addLast(null);
			}
		}

		return styles;
	}

	private int writeMapToSheet(ExportSheetModel sheetModel, HSSFWorkbook workbook,
			HSSFSheet sheet, DataFormat format, List<? extends Map<String, Object>> values,
			int startRow, Map<String, HSSFCellStyle> titleStyleMap) {
		startRow = writeTitle(sheetModel, sheet, startRow, titleStyleMap);
		List<HSSFCellStyle> styles = getStyles(workbook, sheetModel, format);
		startRow = writeMapData(sheetModel, sheet, styles, values, startRow);
		return startRow;
	}

	/**
	 * 
	 * @param sheetModel
	 * @param workbook
	 * @param sheet
	 * @param format
	 * @param values
	 * @param startRow
	 * @param allInOne
	 * @return
	 */
	private <E> int writeObjectToSheet(ExportSheetModel sheetModel, HSSFWorkbook workbook,
			HSSFSheet sheet, DataFormat format, List<? extends E> values, int startRow,
			Map<String, HSSFCellStyle> titleStyleMap/*
													 * , boolean allInOne
													 */) {
		startRow = writeTitle(sheetModel, sheet, startRow, titleStyleMap/*
																		 * ,
																		 * allInOne
																		 */);
		List<HSSFCellStyle> styles = getStyles(workbook, sheetModel, format);
		startRow = writeObjectData(sheetModel, sheet, styles, values, startRow);
		return startRow;
	}

	private int writeMapData(ExportSheetModel sheetModel, HSSFSheet sheet,
			List<HSSFCellStyle> styles, List<? extends Map<String, Object>> values, int startRow) {
		Iterator<? extends Map<String, Object>> valueIter = values.iterator();

		Map<Integer, Object> currentValueMap = new HashMap<Integer, Object>();

		while (valueIter.hasNext()) {

			Map<String, Object> valueMap = valueIter.next();

			HSSFRow row = sheet.createRow(startRow++);
			Iterator<ExportColumnModel> columnModelIter = sheetModel.getColumnList().iterator();
			Iterator<HSSFCellStyle> cellStyleIter = styles.iterator();
			int colIndex = 0;
			while (columnModelIter.hasNext()) {
				ExportColumnModel columnModel = columnModelIter.next();
				HSSFCellStyle cellStyle = cellStyleIter.next();

				Object value = valueMap.get(columnModel.getKey());
				try {
					if (null != value) {

						// check for duplicate value
						Object lastValue = currentValueMap.get(colIndex);

						if (columnModel.isAllowDuplicate() || !value.equals(lastValue)) {
							HSSFCell c = row.createCell(colIndex);
							ExcelCellWriter.writeToCell(value, c, cellStyle);
							currentValueMap.put(colIndex, value);
						}
					}
				} finally {
					colIndex++;
				}

			}
		}
		return startRow;
	}

	private <E> int writeObjectData(ExportSheetModel sheetModel, HSSFSheet sheet,
			List<HSSFCellStyle> styles, List<? extends E> values, int startRow) {
		Iterator<? extends E> valueIter = values.iterator();
		Map<Integer, Object> currentValueMap = new HashMap<Integer, Object>();

		while (valueIter.hasNext()) {

			E valueMap = valueIter.next();

			HSSFRow row = sheet.createRow(startRow++);
			Iterator<ExportColumnModel> columnModelIter = sheetModel.getColumnList().iterator();
			Iterator<HSSFCellStyle> cellStyleIter = styles.iterator();
			int colIndex = 0;
			while (columnModelIter.hasNext()) {
				ExportColumnModel columnModel = columnModelIter.next();
				HSSFCellStyle cellStyle = cellStyleIter.next();

				Object value;
				try {
					String key = columnModel.getKey();
					if (key.contains(".")) {
						value = PropertyUtils.getNestedProperty(valueMap, key);
					} else {
						value = PropertyUtils.getSimpleProperty(valueMap, key);
					}

					// Object value = valueMap.get(columnModel.getKey());

					if (null != value) {
						Object lastValue = currentValueMap.get(colIndex);
						if (columnModel.isAllowDuplicate() || !value.equals(lastValue)) {
							HSSFCell c = row.createCell(colIndex);
							ExcelCellWriter.writeToCell(value, c, cellStyle);
							currentValueMap.put(colIndex, value);
						}
					}
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
				} catch (Exception e) {
					logger.error(e, e);
				} finally {
					colIndex++;
				}
				// valueMap.get(columnModel.getKey());
			}
		}
		return startRow;
	}

	/**
	 * 
	 * @param sheetModel
	 * @param sheet
	 * @param format
	 * @param startRow
	 * @param allInOne
	 * @return the next row index to write data or title
	 */
	private int writeTitle(ExportSheetModel sheetModel, HSSFSheet sheet, int startRow,
			Map<String, HSSFCellStyle> titleStyleMap/*
													 * , boolean allInOne
													 */) {

		HSSFRow titleRow = null;
		// HSSFCell cell = null;
		// HSSFWorkbook book = sheet.getWorkbook();
		/*
		 * if (allInOne) { titleRow = sheet.createRow(startRow++); cell =
		 * titleRow.createCell(0); cell.setCellValue(sheetModel.getName());
		 * 
		 * if (null != sheetModel.getSheetNameColor()) {
		 * 
		 * HSSFCellStyle sheetNameStyle = book.createCellStyle(); try {
		 * sheetNameStyle.setFillForegroundColor(IndexedColors.valueOf(
		 * sheetModel.getSheetNameColor()).getIndex());
		 * sheetNameStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		 * cell.setCellStyle(sheetNameStyle); } catch (Exception e) { if
		 * (logger.isInfoEnabled()) logger.info(e, e); } } }
		 */
		titleRow = sheet.createRow(startRow++);
		// try {
		// if (null != sheetModel.getTitleColor()) {
		// titleStyle = book.createCellStyle();
		// titleStyle.setFillForegroundColor(IndexedColors.valueOf(sheetModel.getTitleColor())
		// .getIndex());
		// titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// }
		// } catch (Exception e) {
		// if (logger.isInfoEnabled())
		// logger.info(e, e);
		// }
		Iterator<ExportColumnModel> colModelIter = sheetModel.getColumnList().iterator();
		int colIndex = 0;
		while (colModelIter.hasNext()) {
			ExportColumnModel cm = colModelIter.next();
			HSSFCell c = titleRow.createCell(colIndex++);
			ExcelCellWriter.writeToCell(cm.getTitle(), c, null);

			if (null != cm.getTitleStyle()) {
				String tstyle = cm.getTitleStyle();

				HSSFCellStyle titleStyle = titleStyleMap.get(tstyle);
				if (null == titleStyle) {
					titleStyle = sheet.getWorkbook().createCellStyle();
					titleStyleMap.put(tstyle, titleStyle);
				}
				try {
					titleStyle.setFillForegroundColor(IndexedColors.valueOf(tstyle).getIndex());
					titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					c.setCellStyle(titleStyle);
				} catch (Exception e) {
					if (logger.isInfoEnabled())
						logger.info(e, e);
				}
			}
		}
		return startRow;
	}

	@Override
	public HSSFWorkbook exportMapList(String configPath, List<? extends Map<String, Object>> values)
			throws ExportException {
		ExportSheetModel sheetModel = null;
		try {
			sheetModel = getSheetModel(configPath);
		} catch (ConfigNotFoundException e) {
			throw new ExportException(e);
		}
		return exportMapList(sheetModel, values);
	}

	@Override
	public <E> HSSFWorkbook exportObjectList(String configPath, List<? extends E> values)
			throws ExportException {
		ExportSheetModel sheetModel = null;
		try {
			sheetModel = getSheetModel(configPath);
		} catch (ConfigNotFoundException e) {
			throw new ExportException(e);
		}

		return exportObjectList(sheetModel, values);
	}

	@Override
	public HSSFWorkbook exportMapList(ExportSheetModel sheetModel,
			List<? extends Map<String, Object>> values) throws ExportException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		DataFormat format = workbook.createDataFormat();
		HSSFSheet sheet = workbook.createSheet(sheetModel.getName());

		Map<String, HSSFCellStyle> titleStyleMap = new HashMap<String, HSSFCellStyle>();

		writeMapToSheet(sheetModel, workbook, sheet, format, values, 0, titleStyleMap);
		return workbook;
	}

	@Override
	public <E> HSSFWorkbook exportObjectList(ExportSheetModel sheetModel, List<? extends E> values)
			throws ExportException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		Map<String, HSSFCellStyle> titleStyleMap = new HashMap<String, HSSFCellStyle>();
		DataFormat format = workbook.createDataFormat();
		HSSFSheet sheet = workbook.createSheet(sheetModel.getName());
		writeObjectToSheet(sheetModel, workbook, sheet, format, values, 0, titleStyleMap/*
																						 * ,
																						 * false
																						 */);
		return workbook;
	}

	@Override
	public HSSFWorkbook exportMapLists(String[] configPathes,
			List<? extends List<? extends Map<String, Object>>> values/*
																	 * , boolean
																	 * allInOneSheet
																	 */) throws ExportException {
		List<ExportSheetModel> exportSheetModels = null;
		try {
			exportSheetModels = getSheetModels(configPathes);
		} catch (SheetNameDuplicateExceptionList e) {
			logger.error(e, e);
			throw new ExportException(e);
		} catch (ConfigNotFoundExceptionList e) {
			logger.error(e, e);
			throw new ExportException(e);
		}
		Iterator<ExportSheetModel> sheetModelIter = exportSheetModels.iterator();
		Iterator<? extends List<? extends Map<String, Object>>> valuesIter = values.iterator();
		HSSFWorkbook workbook = new HSSFWorkbook();
		DataFormat format = workbook.createDataFormat();
		HSSFSheet sheet = null;
		int startRow = 0;

		Map<String, HSSFCellStyle> titleStyleMap = new HashMap<String, HSSFCellStyle>();
		Map<String, Integer> sheetNameCount = new HashMap<String, Integer>();
		while (sheetModelIter.hasNext()) {
			ExportSheetModel sheetModel = sheetModelIter.next();
			List<? extends Map<String, Object>> value = valuesIter.next();
			/*
			 * if (allInOneSheet) { if (null == sheet) { sheet =
			 * workbook.createSheet("ALL"); } } else {
			 */
			String sn = sheetModel.getName();
			Integer ct = sheetNameCount.get(sn);
			if (null != ct) {
				ct = ct + 1;
				sheetNameCount.put(sn, ct);
				sn = sn + "_" + ct;
			}
			else{
				ct=1;
				sheetNameCount.put(sn, ct);
			}
			sheet = workbook.createSheet(sn);
			/* } */

			startRow = writeMapToSheet(sheetModel, workbook, sheet, format, value, startRow,
					titleStyleMap/*
								 * , allInOneSheet
								 */);
			/*
			 * if (allInOneSheet) { startRow = startRow + 2; } else {
			 */
			startRow = 0;
			/* } */
		}
		return workbook;
	}

	@Override
	public <E> HSSFWorkbook exportObjectLists(String[] configPathes,
			List<? extends List<? extends E>> values/* , boolean allInOneSheet */)
			throws ExportException {
		List<ExportSheetModel> exportSheetModels = null;
		try {
			exportSheetModels = getSheetModels(configPathes);
		} catch (SheetNameDuplicateExceptionList e) {
			logger.error(e, e);
			throw new ExportException(e);
		} catch (ConfigNotFoundExceptionList e) {
			logger.error(e, e);
			throw new ExportException(e);
		}
		Iterator<ExportSheetModel> sheetModelIter = exportSheetModels.iterator();
		Iterator<? extends List<? extends E>> valuesIter = values.iterator();
		HSSFWorkbook workbook = new HSSFWorkbook();
		DataFormat format = workbook.createDataFormat();
		HSSFSheet sheet = null;
		int startRow = 0;
		Map<String, HSSFCellStyle> titleStyleMap = new HashMap<String, HSSFCellStyle>();
		Map<String, Integer> sheetNameCount = new HashMap<String, Integer>();
		while (sheetModelIter.hasNext()) {
			ExportSheetModel sheetModel = sheetModelIter.next();
			List<? extends E> value = valuesIter.next();
			// if (allInOneSheet) {
			// if (null == sheet) {
			// sheet = workbook.createSheet("ALL");
			// }
			// } else {
			String sn = sheetModel.getName();
			Integer ct = sheetNameCount.get(sn);
			if (null != ct) {
				ct = ct + 1;
				sheetNameCount.put(sn, ct);
				sn = sn + "_" + ct;
			}
			else{
				ct=1;
				sheetNameCount.put(sn, ct);
			}
			sheet = workbook.createSheet(sn);
			// }

			startRow = writeObjectToSheet(sheetModel, workbook, sheet, format, value, startRow,
					titleStyleMap/*
								 * , allInOneSheet
								 */);
			// if (allInOneSheet) {
			// startRow = startRow + 2;
			// } else {
			startRow = 0;
			// }
		}
		return workbook;
	}

	private ExportSheetModel getSheetModel(String configPath) throws ConfigNotFoundException {

		ExportSheetModel config = this.configContainer.getExportSheetModel(configPath);
		if (null == config) {
			throw new ConfigNotFoundException(configPath);
		}
		return config;
	}

	private List<ExportSheetModel> getSheetModels(String[] configPathes)
			throws SheetNameDuplicateExceptionList, ConfigNotFoundExceptionList {
		List<SheetNameDuplicateException> duplicateList = new LinkedList<SheetNameDuplicateException>();
		List<ConfigNotFoundException> configNotFoundExeptionList = new LinkedList<ConfigNotFoundException>();
		LinkedList<ExportSheetModel> models = new LinkedList<ExportSheetModel>();
		for (int i = 0; i < configPathes.length; i++) {
			String configPath1 = configPathes[i];
			ExportSheetModel config1 = null;
			try {
				config1 = getSheetModel(configPath1);
			} catch (ConfigNotFoundException e) {
				logger.error(e, e);
				configNotFoundExeptionList.add(e);
			}
			// for (int j = 1 + i; j < configPathes.length; j++) {
			// String configPath2 = configPathes[j];
			// ExportSheetModel config2 = null;
			// try {
			// config2 = getSheetModel(configPath2);
			// } catch (ConfigNotFoundException e) {
			// logger.error(e, e);
			// configNotFoundExeptionList.add(e);
			// }
			// if (null != config1 && null != config2) {
			// if (config2.getName().equals(config1.getName())) {
			// duplicateList.add(new SheetNameDuplicateException(new String[] {
			// configPath1, config1.getName() }, new String[] { configPath2,
			// config2.getName() }));
			// }
			// }
			// }
			models.addLast(config1);
		}
		if (configNotFoundExeptionList.size() > 0) {
			throw new ConfigNotFoundExceptionList(configNotFoundExeptionList);
		}
		if (duplicateList.size() > 0) {
			throw new SheetNameDuplicateExceptionList(duplicateList);
		}
		return models;
	}

}
