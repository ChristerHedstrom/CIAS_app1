package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetExcelFilePath {
	static String filePath;

	public static String getFilePath(String filePath) {
		Properties appProperties = new Properties();

		try {
			String path = System.getProperty("user.dir");
			appProperties.load(new FileInputStream(path + "\\ExcelFilePath.properties"));
			filePath = (String) appProperties.get(filePath);
						
			if ((filePath == null) || (!filePath.contains("xlsx")))
				return null;

			if (!filePath.contains("C:\\")) {
				filePath = path + "\\" + filePath;
			}
		} catch (IOException e) {

		}

		return filePath;
	}
}
