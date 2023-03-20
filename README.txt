ConvertExcelToJSON
------------------

The Excel file that should be used is entered as a property in the ExcelFilePath.properties file.
It can be absolute (full path) or relative (the Excel file is placed in the same directory as the 
runnable jar file).

Examples:
---------
-Absolute path (Note the "\\")
ExcelFilePath=C:\\EXAMPLE\\PATH\\CA_ParamOverviewER6.xlsx

-Relative path
ExcelFilePath=CA_ParamOverviewER6.xlsx

How to use the ConvertExcelToJSON app:
--------------------------------------
-Set the file path as described above in the ExcelFilePath.properties file.
-Double click on the ConvertExcelToJSON.bat file.
-The Scenario ID drop down will, if a correct Excel file has been chosen, have a list of all scenario ID's 
from the file. If the chosen Excel file is not correct, close the app and change the property in the 
ExcelFilePath.properties file accordingly and run the app again.
