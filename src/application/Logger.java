package application;

import javafx.scene.control.TextArea;

public class Logger {
	
	public static void write(String logtext, TextArea tf) {
		
		String s = tf.getText();
		s +=  "\n" + logtext;
		tf.setText(s);
	
	}
}
