package xyp.tool.excel.util;

import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelCellWriter {
	private static Log logger = LogFactory.getLog(ExcelCellWriter.class);

	public static void writeToCell(Object value, Row row, int cellIndex, HSSFCellStyle style) {
		if (value == null) {
			return;
		}
		writeToCell(value, row.createCell(cellIndex), style);
	}

	public static void writeToCell(Object value, Cell cell, HSSFCellStyle style) {
		if (value == null || cell == null)
			return;

		if (value instanceof String) {
			cell.setCellValue((String) value);
		} else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof BigDecimal) {
			cell.setCellValue(((BigDecimal) value).doubleValue());
		} else if (value instanceof java.util.Date) {
			cell.setCellValue((java.util.Date) value);
		} else if (value instanceof java.sql.Date) {
			cell.setCellValue((java.sql.Date) value);
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar) value);
		} else if (value instanceof Boolean) {
			if (null != value && (Boolean) value) {
				cell.setCellValue((Boolean) value);
			}
		} else {

			cell.setCellValue(value.toString());

			if (logger.isWarnEnabled()) {
				Exception e = new IllegalArgumentException(
						"get value"
								+ value
								+ " of type "
								+ value.getClass().getName()
								+ " , only accept String, Integer, Double, Long, BigDecimal, Boolean, java.util.Date, java.sql.Date, java.util.Calendar value, and their toString() function are used");
				logger.warn(e, e);
			}
		}
		if (style != null)
			cell.setCellStyle(style);
	}

}
