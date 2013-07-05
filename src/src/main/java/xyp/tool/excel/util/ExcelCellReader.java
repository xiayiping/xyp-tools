package xyp.tool.excel.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * except date , return null when the cell is null. boolean returns false if
 * null<br/>
 * 
 * @author xiayip
 * 
 */
public class ExcelCellReader {

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>number: return floor int value</li>
	 *         <li>string: parse to int or throws exception</li>
	 *         <li>formula: return result of formula</li>
	 *         <li>cell is null or else: return 0</li>
	 *         </ul>
	 * @throws NumberFormatException
	 */
	public static Integer getIntFromCell(Cell cell, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return new Double(cell.getNumericCellValue()).intValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Integer.parseInt(cell.getRichStringCellValue().getString());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			Double d = new Double(cellValue.getNumberValue());
			return d.intValue();
		}
		return null;
	}

	public static Integer getIntFromCell(Row row, int cellIndex, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getIntFromCell(cell, evaluator);
	}

	public static Integer getIntFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) throws NumberFormatException {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getIntFromCell(cell, evaluator);
	}

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>number: return floor long value</li>
	 *         <li>string: parse to long or throws exception</li>
	 *         <li>formula: return result of formula</li>
	 *         <li>cell is null or else: return 0</li>
	 *         </ul>
	 * @throws NumberFormatException
	 */
	public static Long getLongFromCell(Cell cell, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return new Double(cell.getNumericCellValue()).longValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Long.parseLong(cell.getRichStringCellValue().getString());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			Double d = new Double(cellValue.getNumberValue());
			return d.longValue();
		}
		return null;
	}

	public static Long getLongFromCell(Row row, int cellIndex, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getLongFromCell(cell, evaluator);
	}

	public static Long getLongFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) throws NumberFormatException {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getLongFromCell(cell, evaluator);
	}

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>number: return double value</li>
	 *         <li>string: parse to double or throws exception</li>
	 *         <li>formula: return result of formula</li>
	 *         <li>cell is null or else: return 0</li>
	 *         </ul>
	 * @throws NumberFormatException
	 */
	public static Float getFloatFromCell(Cell cell, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return new Float(cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Float.parseFloat(cell.getRichStringCellValue().getString());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			return new Float(cellValue.getNumberValue());
		}
		return null;
	}

	public static Float getFloatFromCell(Row row, int cellIndex, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getFloatFromCell(cell, evaluator);
	}

	public static Float getFloatFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) throws NumberFormatException {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getFloatFromCell(cell, evaluator);
	}

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>number: return double value</li>
	 *         <li>string: parse to double or throws exception</li>
	 *         <li>formula: return result of formula</li>
	 *         <li>cell is null or else: return 0</li>
	 *         </ul>
	 * @throws NumberFormatException
	 */
	public static Double getDoubleFromCell(Cell cell, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return new Double(cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Double.parseDouble(cell.getRichStringCellValue().getString());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			return new Double(cellValue.getNumberValue());
		}
		return null;
	}

	public static Double getDoubleFromCell(Row row, int cellIndex, FormulaEvaluator evaluator)
			throws NumberFormatException {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getDoubleFromCell(cell, evaluator);
	}

	public static Double getDoubleFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) throws NumberFormatException {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getDoubleFromCell(cell, evaluator);
	}

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>boolean: return boolean value</li>
	 *         <li>number: return &gt; 0</li>
	 *         <li>string: true if and only if upper case value is 'TRUE'</li>
	 *         <li>formula: return result of formula</li>
	 *         <li>cell is null or else: return false</li>
	 *         </ul>
	 */
	public static Boolean getBooleanFromCell(Cell cell, FormulaEvaluator evaluator) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return (cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return (cell.getNumericCellValue() > 0);
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			return cellValue.getBooleanValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String s = (cell.getRichStringCellValue().getString().trim()).toUpperCase();
			if ("TRUE".equals(s)) {
				return Boolean.TRUE;
			} else if ("FALSE".equals(s)) {
				return Boolean.FALSE;
			}
		}
		return null;
	}

	public static Boolean getBooleanFromCell(Row row, int cellIndex, FormulaEvaluator evaluator) {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getBooleanFromCell(cell, evaluator);
	}

	public static Boolean getBooleanFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getBooleanFromCell(cell, evaluator);
	}

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>string: return null if empty. If not empty, throw
	 *         parseException</li>
	 *         <li>number: return to Date</li>
	 *         <li>formula: return result of formula to long and to date</li>
	 *         <li>cell is null : return null</li>
	 *         <li>else: throw parseException</li>
	 *         </ul>
	 * @throws ParseException
	 */
	public static Date getDateFromCell(Cell cell, FormulaEvaluator evaluator) {

		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING && "".equals(getStringFromCell(cell, null))) {
			return null;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return (cell.getDateCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			long lo = new Double(cellValue.getNumberValue()).longValue();
			return new Date(lo);
		}

		return null;

	}

	public static Date getDateFromCell(Row row, int cellIndex, FormulaEvaluator evaluator) {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getDateFromCell(cell, evaluator);
	}

	public static Date getDateFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getDateFromCell(cell, evaluator);
	}

	/**
	 * 
	 * @param cell
	 * @param evaluator
	 * @return if cell type is
	 *         <ul>
	 *         <li>string: return "" if empty.</li>
	 *         <li>number: string of number</li>
	 *         <li>boolean: TRUE FALSE</li>
	 *         <li>formula: result string</li>
	 *         <li>else: ""</li>
	 *         </ul>
	 */
	public static String getStringFromCell(Cell cell, FormulaEvaluator evaluator) {

		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String val = cell.getRichStringCellValue().getString().trim();
			if (val.length() == 0) {
				return null;
			}
			return val;
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double d = cell.getNumericCellValue();
			return new Double(d).toString().replaceAll("\\.0+$", "");
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			Boolean val = getBooleanFromCell(cell, evaluator);
			return val == null ? null : val.toString();
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			return (cellValue.getStringValue().trim());
		}

		return null;
	}

	public static String getStringFromCell(Row row, int cellIndex, FormulaEvaluator evaluator) {
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getStringFromCell(cell, evaluator);
	}

	public static String getStringFromCell(Sheet sheet, int rowIndex, int cellIndex,
			FormulaEvaluator evaluator) {
		Row row = sheet.getRow(rowIndex);
		if (null == row)
			return null;
		Cell cell = row.getCell(cellIndex);
		if (null == cell)
			return null;
		return getStringFromCell(cell, evaluator);
	}

}
