package xyp.tool.importandexport.interfaces;

import java.util.List;
import java.util.Map;

import xyp.tool.importandexport.exception.ExportException;
import xyp.tool.importandexport.model.ExportSheetModel;

public interface IExport<T> {
	public T exportMapList(String configPath, List<? extends Map<String, Object>> values)
			throws ExportException;

	public <E> T exportObjectList(String configPath, List<? extends E> values)
			throws ExportException;

	public T exportMapList(ExportSheetModel sheetModel, List<? extends Map<String, Object>> values)
			throws ExportException;

	public <E> T exportObjectList(ExportSheetModel sheetModel, List<? extends E> values)
			throws ExportException;

	public T exportMapLists(String[] configPathes,
			List<? extends List<? extends Map<String, Object>>> values) throws ExportException;

	public <E> T exportObjectLists(String[] configPathes, List<? extends List<? extends E>> values/*
																								 * ,
																								 * boolean
																								 * allInOneSheet
																								 */)
			throws ExportException;

}
