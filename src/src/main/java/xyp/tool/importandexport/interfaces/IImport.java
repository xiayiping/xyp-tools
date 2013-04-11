package xyp.tool.importandexport.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import xyp.tool.importandexport.exception.ConfigNotFoundException;
import xyp.tool.importandexport.exception.ImportException;

public interface IImport {
	
	/**
	 * 
	 * @param source
	 * @param configName
	 * @return
	 * @throws ImportException
	 * @throws ConfigNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Map<String, Object>> importFromSourceToMap(File source, String configName)
			throws ImportException,  FileNotFoundException, IOException;

}
