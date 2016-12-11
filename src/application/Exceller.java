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
		String s = "��������� ����� ���� ������: ";
		for (File file : f2) {
			XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
			// console
			System.out.println("� ����� " + file.getName() + " " + myExcelBook.getNumberOfSheets() + " ��������");
			for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
				XSSFSheet currentSheet = myExcelBook.getSheetAt(i);
				int rowNumber = currentSheet.getPhysicalNumberOfRows(), firstrowNumber = currentSheet.getFirstRowNum(),
						lastrowNumber = currentSheet.getLastRowNum();
				// console
				System.out.println("\t � �� ������ " + i + " ����� ����� " + rowNumber + ", ������ - " + firstrowNumber
						+ ", ��������� - " + lastrowNumber);

				for (int j = firstrowNumber; j <= lastrowNumber; j++) {
					XSSFRow currentRow = currentSheet.getRow(j);

					if (currentRow != null) {
						System.out.println("����������� NOT NULL");

						int firstcellNumber = currentRow.getFirstCellNum(),
								lastcellNumber = currentRow.getLastCellNum();

						for (int k = firstcellNumber; k <= lastcellNumber; k++) {
							System.out.println("�������� ������� ������ �� ������ " + k);
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
