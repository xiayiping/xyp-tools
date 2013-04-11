package xyp.tool.excel.reader;

import java.text.ParseException;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import xyp.tool.excel.SupportedType;
import xyp.tool.excel.exception.TypeNotSupportException;
import xyp.tool.excel.util.ExcelCellReader;

public class CellReaderFactory {

	private static final TreeMap<String, ICellReader> map = new TreeMap<String, ICellReader>();
	static {
		map.put(SupportedType.TYPE_BOOLEAN, new BooleanCellReader());
		map.put(SupportedType.TYPE_DATE, new DateCellReader());
		map.put(SupportedType.TYPE_DOUBLE, new DoubleCellReader());
		map.put(SupportedType.TYPE_FLOAT, new FloatCellReader());
		map.put(SupportedType.TYPE_INTEGER, new IntegerCellReader());
		map.put(SupportedType.TYPE_LONG, new LongCellReader());
		map.put(SupportedType.TYPE_STRING, new StringCellReader());
	}

	public static ICellReader getCellReader(String clz)
			throws TypeNotSupportException {
		ICellReader rd = map.get(clz);
		if (null == rd)
			throw new TypeNotSupportException(clz);
		return rd;
	}

	private static class StringCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator) {
			return ExcelCellReader.getStringFromCell(cell, evaluator);
		}
	}

	private static class IntegerCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator) {
			return ExcelCellReader.getIntFromCell(cell, evaluator);
		}
	}

	private static class LongCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator) {
			return ExcelCellReader.getLongFromCell(cell, evaluator);
		}
	}

	private static class FloatCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator) {
			return ExcelCellReader.getFloatFromCell(cell, evaluator);
		}
	}

	private static class DoubleCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator) {
			return ExcelCellReader.getDoubleFromCell(cell, evaluator);
		}
	}

	private static class BooleanCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator) {
			return ExcelCellReader.getBooleanFromCell(cell, evaluator);
		}
	}

	private static class DateCellReader implements ICellReader {
		public Object readFromCell(Cell cell, FormulaEvaluator evaluator)
				throws ParseException {

			return ExcelCellReader.getDateFromCell(cell, evaluator);

		}
	}

}
