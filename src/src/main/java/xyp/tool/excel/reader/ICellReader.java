package xyp.tool.excel.reader;

import java.text.ParseException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public interface ICellReader {
	public Object readFromCell(Cell cell, FormulaEvaluator evaluator)
			throws ParseException;
}
