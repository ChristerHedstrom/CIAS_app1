package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Test {
	public static void main(String[] args) {
		Properties appProperties = new Properties();

		try {
			String path = System.getProperty("user.dir");
			System.out.println(path);
			appProperties.load(new FileInputStream(path + "\\ExcelFilePath.properties"));
			String filePath = (String) appProperties.get("ExcelFilePath");

			if (!filePath.contains("C:\\")) {
				filePath = path + "\\" + filePath;
			}

			System.out.println("filePath: " + filePath);
		} catch (IOException e) {

		}

	}

}
