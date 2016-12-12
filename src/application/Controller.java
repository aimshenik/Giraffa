package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

public class Controller {

	@FXML
	private TextField saSelectDirectoryTxtField, saSelectFileTxtField;
	@FXML
	private TextArea logsTextArea, logsTextArea2, trPLITextArea, clientPLITextArea, clientSolutionsPLITextArea;
	@FXML
	private AnchorPane pliScrollPane;
	@FXML
	private Tab tabZero, tabOne, tabTwo;
	@FXML
	private TabPane tabPane;

	public void clickBtn1() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		configuringDirectoryChooser(directoryChooser);

		File dir = directoryChooser.showDialog(saSelectDirectoryTxtField.getScene().getWindow());
		if (dir != null) {
			saSelectDirectoryTxtField.setText(dir.getAbsolutePath());
			System.out.println(dir.getAbsolutePath());
		} else {
			System.out.println("NULL");
		}

	}

	public void clickBtn2() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		configuringDirectoryChooser(directoryChooser);

		File dir = directoryChooser.showDialog(saSelectFileTxtField.getScene().getWindow());
		if (dir != null) {
			saSelectFileTxtField.setText(dir.getAbsolutePath());
			System.out.println(dir.getAbsolutePath());
		} else {
			System.out.println("NULL");
		}

		Logger.write("Selected Assets file = " + dir.getAbsolutePath(), logsTextArea2);

	}

	public void clickExtract() throws FileNotFoundException, IOException {
		
		String filesNames = new Exceller().readAllFilesNamesToString(saSelectDirectoryTxtField.getText());
		String filesContent = new Exceller().readAllFilesContentToString(saSelectDirectoryTxtField.getText());

		Logger.write(filesNames, logsTextArea2);
		Logger.write(filesContent, logsTextArea2);
		Logger.write(filesContent, trPLITextArea);
	}

	public void selectDirectory() {

		if (saSelectDirectoryTxtField.getText().equals("Click me")) {
			System.out.println(new Date());
			saSelectDirectoryTxtField.clear();

			final DirectoryChooser directoryChooser = new DirectoryChooser();
			configuringDirectoryChooser(directoryChooser);

			File dir = directoryChooser.showDialog(saSelectDirectoryTxtField.getScene().getWindow());
			if (dir != null) {
				saSelectDirectoryTxtField.setText(dir.getAbsolutePath());
				System.out.println(dir.getAbsolutePath());
			} else {
				System.out.println("NULL");
			}
		}

	}

	private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {

		directoryChooser.setTitle("Select Some Directories");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

	}

	public void goToTabZero() {
		tabPane.getSelectionModel().select(0);
	}

	public void goToTabOne() {
		tabPane.getSelectionModel().select(1);
	}

}
