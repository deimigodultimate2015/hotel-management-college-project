package model.helper;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class DialogCreator {
	
	String textFieldErrorStyle = ".text-field {\r\n" + 
			"	-fx-background-color: transparent;\r\n" + 
			"	-fx-border-width: 0 0 2 0;\r\n" + 
			"	-fx-border-color: #cc0000;\r\n" + 
			"}";
	
	String datePcikerErrorStyle = ".date-picker {\r\n" + 
			"	-fx-border-color: #cc0000;\r\n" + 
			"	-fx-border-width: 0 0 2 0;\r\n" + 
			"	-fx-background-color: transparent;\r\n" + 
			"	-fx-padding: 0px;\r\n" + 
			"}";
	
	public void simpleDialog(String title, String head, String body, AlertType type) {
		Alert alert = new Alert(type);
		alert.setHeaderText(head);
		alert.setContentText(body);
		alert.setTitle(title);
		alert.showAndWait();
	}
	
	public void makeThatTextfieldError(TextField textField) {
		textField.setStyle(textFieldErrorStyle);
		textField.requestFocus();
	}
	
	public void makeThatDatePickerError(DatePicker datePicker) {
		datePicker.setStyle(datePcikerErrorStyle);
	}
	
	public String inputDialog(String title, String head, String body) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(head);
		dialog.setTitle(title);
		dialog.setContentText(body);
		
		Optional<String> data =dialog.showAndWait();
		if(data.isPresent()) {
			return data.get();
		} else {
			return null;
		}
	}
}
