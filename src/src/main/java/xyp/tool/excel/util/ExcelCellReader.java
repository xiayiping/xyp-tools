package xyp.tool.excel.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

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
	public static Integer getIntFromCell(Cell cell, FormulaEvaluator evaluator) throws NumberFormatException {
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
	public static Long getLongFromCell(Cell cell, FormulaEvaluator evaluator) throws NumberFormatException {
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
	public static Float getFloatFromCell(Cell cell, FormulaEvaluator evaluator) throws NumberFormatException {
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
	public static Double getDoubleFromCell(Cell cell, FormulaEvaluator evaluator) throws NumberFormatException {
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
	public static boolean getBooleanFromCell(Cell cell, FormulaEvaluator evaluator) {
		if (cell == null) {
			return false;
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
			return "TRUE".equals(s);
		}
		return false;
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
	public static Date getDateFromCell(Cell cell, FormulaEvaluator evaluator) throws ParseException {

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

		throw new ParseException("parse date failed. " + cell.getStringCellValue(), 0);

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
			return (cell.getRichStringCellValue().getString().trim());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double d = cell.getNumericCellValue();
			return new Double(d).toString().replaceAll("\\.0+$", "");
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return getBooleanFromCell(cell, evaluator) ? "TRUE" : "FALSE";
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			CellValue cellValue = evaluator.evaluate(cell);
			return (cellValue.getStringValue().trim());
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(new Double(100.00).toString().replaceAll("\\.0+$", ""));

		Double d1 = new Double(100.92);
		System.out.println(d1.toString());
		System.out.println(d1.longValue());
		// System.out.println(d1.);
		System.out.println(new Double(100.0066880009999990000000000000).toString().replaceAll("(\\.0+)$", ""));

		System.out.println("----------------------");
		BigDecimal db = new BigDecimal(123.45678922222);

		db.setScale(5, RoundingMode.CEILING);

		System.out.println(db);
		System.out.println(1.2345000D);
		System.out.println(db.toString());
		System.out.println(db.toPlainString());

		BigDecimal bd2 = new BigDecimal(1.11);
		BigDecimal db3 = bd2.divide(new BigDecimal(3));
		System.out.println(db3);

		// Integer ii = null;
	}

}
