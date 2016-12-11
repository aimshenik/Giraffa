package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exceller {

	public void createEmpty() {

	}

	public String readAllFilesNamesToString(String pathTofolder) {
		File f = new File(pathTofolder);
		String s = "\n\t";
		if (f.isDirectory()) {
			File[] arr = f.listFiles();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].isFile()) {
					s += "File#" + i + " = (" + arr[i].getName() + ")\n\t";
				}
			}
			return "Solution PLI files list is : " + s;
		}

		return "Error while reading the PLI files names";
	}

	public String readAllFilesContentToString(String pathToFolder) throws FileNotFoundException, IOException {
		File f = new File(pathToFolder);
		File[] f2 = f.listFiles();
		String s = "Суммарный текст всех файлов: ";
		for (File file : f2) {
			XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
			// console
			System.out.println("в файле " + file.getName() + " " + myExcelBook.getNumberOfSheets() + " Закладок");
			for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
				XSSFSheet currentSheet = myExcelBook.getSheetAt(i);
				int rowNumber = currentSheet.getPhysicalNumberOfRows(), firstrowNumber = currentSheet.getFirstRowNum(),
						lastrowNumber = currentSheet.getLastRowNum();
				// console
				System.out.println("\t В за кладке " + i + " всего строк " + rowNumber + ", первая - " + firstrowNumber
						+ ", последняя - " + lastrowNumber);

				for (int j = firstrowNumber; j <= lastrowNumber; j++) {
					XSSFRow currentRow = currentSheet.getRow(j);

					if (currentRow != null) {
						System.out.println("перехватили NOT NULL");

						int firstcellNumber = currentRow.getFirstCellNum(),
								lastcellNumber = currentRow.getLastCellNum();

						for (int k = firstcellNumber; k <= lastcellNumber; k++) {
							System.out.println("пытаемся собрать данные из строки " + k);
							s = (currentRow.getCell(k) != null)
									? s + currentRow.getCell(k).toString() + " " : s;
						}

					}
				}

			}
			// console
			System.out.println(s);
			myExcelBook.close();
			

		}
		return s;
	}

}
