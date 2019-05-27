package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RunApp extends Application{
	private double xOffset = 0;
	private double yOffset = 0;
	static Stage stage;
	public static Scene scene;
	public static void setScene(Scene scene) {
		RunApp.scene = scene;
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static void setStage(Stage stage) {
		RunApp.stage = stage;
	}
	
	public static Stage getStage() {
		return RunApp.stage;
	}
	
	public static void main(String[]args) {
		launch(args);
	}
	
	public void start(Stage stage) throws Exception{
		try {
			RunApp.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/DangNhap.fxml"));
			root.setOnMousePressed(e -> {
				yOffset = e.getSceneY();
				xOffset = e.getSceneX();
			});
			
			root.setOnMouseDragged(e -> {
				stage.setX(e.getScreenX() - xOffset);
				stage.setY(e.getScreenY() - yOffset);
			});	
			
			scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
