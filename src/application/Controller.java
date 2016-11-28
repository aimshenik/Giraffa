package application;

import java.io.File;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

public class Controller {

	@FXML
	private TextField saSelectDirectoryTxtField;
	@FXML
	private TextArea filesTextArea;
	@FXML
	private AnchorPane pliScrollPane;
	
	

	public void clickBtn1() {
		System.out.println(saSelectDirectoryTxtField.getText());
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
                filesTextArea.setText(dir.getAbsolutePath());
            } else {
            	System.out.println("NULL");
            }
		}

	}

	private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {

		directoryChooser.setTitle("Select Some Directories");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
	}
}
