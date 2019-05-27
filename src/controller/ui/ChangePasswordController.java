package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.ui.tempData.LoginedUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import model.dao.NhanVienCRUD;
import model.entities.NhanVien;
import model.helper.CreateDialog;
import model.helper.DialogCreator;

public class ChangePasswordController implements Initializable{

    @FXML
    private Button btnExit;

    @FXML
    private PasswordField txtOldpassword;

    @FXML
    private Button btnChangeCommit;

    @FXML
    private PasswordField txtRePassword;

    @FXML
    private PasswordField txtReNewPassword;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnAction(e -> {
			if(LoginedUser.log.getPhanquyen()) {
				QuanLiIndexController.stage.close();
			} else {
				IndexController.stage.close();
			}
		});
		
		btnChangeCommit.setOnAction(e -> {
			txtOldpassword.setStyle(null);
			txtReNewPassword.setStyle(null);
			txtRePassword.setStyle(null);
			
			if(!txtOldpassword.getText().equals(LoginedUser.log.getMatKhau())) {
				CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật khẩu thất bại", "Mật khẩu cũ không khớp, vui lòng nhập lại", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtOldpassword);
				return;
			} else if (txtRePassword.getText().trim().isEmpty()) {
				CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật khẩu thất bại", "Không được để trống mật khẩu cũ", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtRePassword);
				return;
			} else if (txtRePassword.getText().trim().length() < 6) {
				CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật khẩu thất bại", "Mật khẩu mới phải nhiều hơn 6 kí tự", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtRePassword);
				return;
			} else if (!txtRePassword.getText().equals(txtReNewPassword.getText())) {
				CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật khẩu thất bại", "Mật khẩu nhập lại không khớp với mật khẩu mới", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtReNewPassword);
				return;
			}
			
			NhanVien nv = LoginedUser.log;
			nv.setMatKhau(txtRePassword.getText());
			
			new NhanVienCRUD().updatePassword(nv);
			CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật thành công", "Mật khẩu của bạn đã được cập nhật lại", AlertType.INFORMATION);
			btnExit.fire();
			
		});
		
		
	}
	
	

}
