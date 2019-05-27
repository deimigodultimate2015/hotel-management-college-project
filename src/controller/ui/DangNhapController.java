package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.RunApp;
import controller.ui.tempData.LoginedUser;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.dao.DangNhap;
import model.helper.CreateDialog;
import model.helper.DialogCreator;
import model.helper.RememberMe;

public class DangNhapController implements Initializable{

    @FXML
    private Button btnExit;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink lnkFPass;
    
    DangNhap loginC = new DangNhap();

    @FXML
    private CheckBox chkRemember;
    
    public static Stage stage ;
    
    private double xOffset = 0;
	private double yOffset = 0;
	
    Task<Void> task ;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLoginInit();
		btnExitInit();
		if(!RememberMe.isItEmpty()) {
			txtUsername.setText(RememberMe.whatInYourMind()[0]);
			txtPassword.setText(RememberMe.whatInYourMind()[1]);
		}
	}
	
	
	
	public void btnLoginInit() {
		txtUsername.setStyle(null);
		txtPassword.setStyle(null);
		btnLogin.setOnAction(ex -> {
			if(!loginC.logined(txtUsername.getText(), txtPassword.getText()).equals("non_access")) {
				LoginedUser.insert(txtUsername.getText());
				if(chkRemember.isSelected()) {
					RememberMe.rememberThis(txtUsername.getText(), txtPassword.getText());
				} else {
					RememberMe.dontRememberThis();
				}
				if(loginC.logined(txtUsername.getText(), txtPassword.getText()).equals("true")) {
					openFXML("QuanLyIndex.fxml");
				} else {
					openFXML("Index.fxml");
				}
			} else {
				CreateDialog.createStandarDialog("Đăng nhập", "Đăng nhập thất bại", "Thông tin tài khoản hoặc mật khẩu đã nhập sai, hãy nhập lại", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtUsername);
				new DialogCreator().makeThatTextfieldError(txtPassword);
			}
		});
	}
	
	public void btnExitInit() {
		lnkFPass.setOnAction(esx -> {
			try {
				stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/ForgotPassword.fxml"));
				root.setOnMousePressed(e -> {
					yOffset = e.getSceneY();
					xOffset = e.getSceneX();
				});
				
				root.setOnMouseDragged(e -> {
					stage.setX(e.getScreenX() - xOffset);
					stage.setY(e.getScreenY() - yOffset);
				});	
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.showAndWait();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btnExit.setOnAction(e -> {
			RunApp.getStage().close();
		});
	}
	
	public void openFXML(String fxml) {
		try {
			stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/"+fxml));
			root.setOnMousePressed(e -> {
				yOffset = e.getSceneY();
				xOffset = e.getSceneX();
			});
			
			root.setOnMouseDragged(e -> {
				stage.setX(e.getScreenX() - xOffset);
				stage.setY(e.getScreenY() - yOffset);
			});	
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			btnExit.fire();
		} catch (Exception exs) {
			exs.printStackTrace();
			
		}
	}

}
