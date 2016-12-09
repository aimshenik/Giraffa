package application;

import java.io.File;

public class Exceller {

	public void createEmpty() {

	}

	public String readAllFilesToString(String pathTofolder) {
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

		return "Error while reading the PLI files";
	}

}
