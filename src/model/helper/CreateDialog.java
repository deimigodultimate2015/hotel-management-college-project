package model.helper;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class CreateDialog {
	public static void createStandarDialog(String title, String header, String content, AlertType alrT) {
		Alert alr = new Alert(alrT);
		alr.setTitle(title);
		alr.setContentText(content);
		alr.setHeaderText(header);
		alr.show();
	}
	
	public static boolean createConfirmDiaglog(String title, String header, String content) {
		Alert alr = new Alert(AlertType.CONFIRMATION);
		alr.setTitle(title);
		alr.setContentText(content);
		alr.setHeaderText(header);
		Optional<ButtonType> ops = alr.showAndWait();
		if(ops.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String createInputDialog(String title, String header, String content) {
		TextInputDialog alr = new TextInputDialog();
		alr.setTitle(title);
		alr.setContentText(content);
		alr.setHeaderText(header);
		Optional<String> ops = alr.showAndWait();
		if(ops.isPresent()) {
			return ops.get();
		} else {
			return null;
		}
	}
	
}
