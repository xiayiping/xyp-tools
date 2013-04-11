package xyp.tool.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtil {
	public static void writhToFile(String content, String file) throws IOException {

		FileWriter fw = new FileWriter(file);

		fw.write(content);
		fw.close();

	}
}
