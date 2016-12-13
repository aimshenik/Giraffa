package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exceller {

	private String trPLI = "";
	private String compared = "";

	public String getCompared() {
		return compared;
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
			if (file.isFile()) {
				s = (file.getName().endsWith(".xls") || file.getName().endsWith(".XLS")) ? s + excelXLSHandler(file)
						: s + excelXLSTHandler(file);
			}
		}
		trPLI = s;
		return s;
	}

	public String readClientsAssetsToString(String pathToFolder) throws FileNotFoundException, IOException {
		File file = new File(pathToFolder);
		if (file.isFile()) {
			return (file.getName().endsWith(".xls") || file.getName().endsWith(".XLS")) ? excelXLSAssetsHandler(file)
					: excelXLSXAssetsHandler(file);
		} else {
			return "Client's assets is not the file!";
		}

	}

	private String excelXLSHandler(File file) throws FileNotFoundException, IOException {
		HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
		String s = "";
		// console
		System.out.println("в файле " + file.getName() + " " + myExcelBook.getNumberOfSheets() + " Закладок");
		for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
			HSSFSheet currentSheet = myExcelBook.getSheetAt(i);
			int rowNumber = currentSheet.getPhysicalNumberOfRows(), firstrowNumber = currentSheet.getFirstRowNum(),
					lastrowNumber = currentSheet.getLastRowNum();
			// console
			System.out.println("\t В за кладке " + i + " всего значимых строк " + rowNumber + ", первая - "
					+ firstrowNumber + ", последняя - " + lastrowNumber);

			for (int j = firstrowNumber; j <= lastrowNumber; j++) {
				HSSFRow currentRow = currentSheet.getRow(j);

				if (currentRow != null) {
					int firstcellNumber = currentRow.getFirstCellNum(), lastcellNumber = currentRow.getLastCellNum();

					for (int k = firstcellNumber; k <= lastcellNumber; k++) {
						// console
						System.out.println("пытаемся собрать данные из строки " + k);
						s = (currentRow.getCell(k) != null) ? s + currentRow.getCell(k).toString() + " " : s;
					}

				}
			}

		}
		// console
		System.out.println(s);
		myExcelBook.close();

		return s;

	}

	private String excelXLSTHandler(File file) throws FileNotFoundException, IOException {
		XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
		String s = "";
		// console
		System.out.println("в файле " + file.getName() + " " + myExcelBook.getNumberOfSheets() + " Закладок");
		for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
			XSSFSheet currentSheet = myExcelBook.getSheetAt(i);
			int rowNumber = currentSheet.getPhysicalNumberOfRows(), firstrowNumber = currentSheet.getFirstRowNum(),
					lastrowNumber = currentSheet.getLastRowNum();
			// console
			System.out.println("\t В за кладке " + i + " всего значимых строк " + rowNumber + ", первая - "
					+ firstrowNumber + ", последняя - " + lastrowNumber);

			for (int j = firstrowNumber; j <= lastrowNumber; j++) {
				XSSFRow currentRow = currentSheet.getRow(j);

				if (currentRow != null) {
					int firstcellNumber = currentRow.getFirstCellNum(), lastcellNumber = currentRow.getLastCellNum();

					for (int k = firstcellNumber; k <= lastcellNumber; k++) {
						// console
						System.out.println("пытаемся собрать данные из строки " + k);
						s = (currentRow.getCell(k) != null) ? s + currentRow.getCell(k).toString() + " " : s;
					}

				}
			}

		}
		// console
		System.out.println(s);
		myExcelBook.close();

		return s;
	}

	private String excelXLSAssetsHandler(File file) throws FileNotFoundException, IOException {
		
		String s = "";
		String s2 = "";
		
		HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
		
		HSSFSheet currentSheet = myExcelBook.getSheetAt(0);
		HSSFRow zeroRow = currentSheet.getRow(0);

		int pliCellNumber = 0, pliDescCellNumber = 0;

		if (zeroRow != null) {
			int lastcellNumber = zeroRow.getLastCellNum();

			for (int k = 0; k <= lastcellNumber; k++) {

				if (zeroRow.getCell(k) != null && zeroRow.getCell(k).toString().equals("PLI")) {
					pliCellNumber = k;
				} else if (zeroRow.getCell(k) != null && zeroRow.getCell(k).toString().equals("Product Name : PLI")) {
					pliDescCellNumber = k;
				}
			}
			System.out.println("pliCellNumber = " + pliCellNumber + "; pliDescCellNumber = " + pliDescCellNumber);
		}

		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {
			HSSFRow currentRow = currentSheet.getRow(i);
			s = (currentRow != null && currentRow.getCell(pliCellNumber) != null
					&& currentRow.getCell(pliCellNumber).toString().length() != 0)
							? s + "\t'" + currentRow.getCell(pliCellNumber).toString() + "' | '"
									+ currentRow.getCell(pliDescCellNumber).toString() + "'\n"
							: s;
			s2 = (currentRow != null && currentRow.getCell(pliCellNumber) != null
					&& currentRow.getCell(pliCellNumber).toString().length() != 0
					&& trPLI.contains(currentRow.getCell(pliCellNumber).toString()))
							? s2 + "\t'" + currentRow.getCell(pliCellNumber).toString() + "' | '"
									+ currentRow.getCell(pliDescCellNumber).toString() + "'\n"
							: s2;
		}

		// console
		System.out.println(s);
		myExcelBook.close();

		compared = s2;
		return s;
	}

	private String excelXLSXAssetsHandler(File file) throws FileNotFoundException, IOException {
		XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
		String s = "";
		String s2 = "";

		XSSFSheet currentSheet = myExcelBook.getSheetAt(0);
		XSSFRow zeroRow = currentSheet.getRow(0);

		int pliCellNumber = 0, pliDescCellNumber = 0;

		if (zeroRow != null) {
			int lastcellNumber = zeroRow.getLastCellNum();

			for (int k = 0; k <= lastcellNumber; k++) {

				if (zeroRow.getCell(k) != null && zeroRow.getCell(k).toString().equals("PLI")) {
					pliCellNumber = k;
				} else if (zeroRow.getCell(k) != null && zeroRow.getCell(k).toString().equals("Product Name : PLI")) {
					pliDescCellNumber = k;
				}
			}
			System.out.println("pliCellNumber = " + pliCellNumber + "; pliDescCellNumber = " + pliDescCellNumber);
		}

		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {
			XSSFRow currentRow = currentSheet.getRow(i);
			s = (currentRow != null && currentRow.getCell(pliCellNumber) != null
					&& currentRow.getCell(pliCellNumber).toString().length() != 0)
							? s + "\t'" + currentRow.getCell(pliCellNumber).toString() + "' | '"
									+ currentRow.getCell(pliDescCellNumber).toString() + "'\n"
							: s;
			s2 = (currentRow != null && currentRow.getCell(pliCellNumber) != null
					&& currentRow.getCell(pliCellNumber).toString().length() != 0
					&& trPLI.contains(currentRow.getCell(pliCellNumber).toString()))
							? s2 + "\t'" + currentRow.getCell(pliCellNumber).toString() + "' | '"
									+ currentRow.getCell(pliDescCellNumber).toString() + "'\n"
							: s2;
		}

		// console
		System.out.println(s);
		myExcelBook.close();

		compared = s2;
		return s;
	}

}
