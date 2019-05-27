package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.NhanVienCRUD;
import model.dao.NhanVienDAO;
import model.entities.NhanVien;
import model.helper.CreateDialog;
import model.helper.DialogCreator;

public class QuanLiNhanVienController implements Initializable{

    @FXML
    private Button btnExit;

    @FXML
    private Button btnReload;

    @FXML
    private TableView<NhanVien> tblView;

    @FXML
    private TableColumn<NhanVien, String> clmId;

    @FXML
    private TableColumn<NhanVien, String> clmName;

    @FXML
    private TableColumn<NhanVien, String> clmPhone;

    @FXML
    private TableColumn<NhanVien, String> clmState;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnAutoGen;

    @FXML
    private TextField txtEmail;

    @FXML
    private RadioButton chkFemale;

    @FXML
    private ToggleGroup tglState;

    @FXML
    private RadioButton chkEmp;

    @FXML
    private RadioButton chkManager;

    @FXML
    private ToggleGroup tglRole;

    @FXML
    private RadioButton chkMale;

    @FXML
    private Button btnUpdate;
    
    @FXML
    private PasswordField txtPassword;
    
    final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    ObservableList<NhanVien> list= FXCollections.observableArrayList();
    NhanVienDAO nvd = new NhanVienDAO();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableInit();
		tableClickInit();
		btnSaveInit();
		btnAutoGenInit();
		btnUpdateInit();
		btnExitInit();
		textFieldInit();
	}
	
	public void textFieldInit() {
		//50 255 11 20
		txtName.textProperty().addListener((obs, ovl, nvl) -> {
			if(nvl.length() > 50) {
				txtName.setText(ovl);
			}
		});
		
		txtEmail.textProperty().addListener((obs, ovl, nvl) -> {
			if(nvl.length() > 255) {
				txtEmail.setText(ovl);
			}
		});
		
		txtPhone.textProperty().addListener((obs, ovl, nvl) -> {
			if(nvl.length() > 11) {
				txtPhone.setText(ovl);
			}
		});
		
		txtPassword.textProperty().addListener((obs, ovl, nvl) -> {
			if(nvl.length() > 20) {
				txtPassword.lengthProperty();
			}
		});
	}
	
	public void btnExitInit() {
		btnExit.setOnAction(e -> {
			QuanLiIndexController.stage.close();
			DangNhapController.stage.show();
		});
		
		btnRefresh.setOnAction(e -> {
			txtName.setStyle(null);
			txtId.setStyle(null);
			txtEmail.setStyle(null);
			txtPhone.setStyle(null);
			txtPassword.setStyle(null);
			
			txtName.setText("");
			txtId.setText("");
			txtEmail.setText("");
			txtPhone.setText("");
			txtPassword.setText("");
		});
		
		btnReload.setOnAction(e -> {
			resetTable();
			btnRefresh.fire();
		});
	}
	
	public void btnSaveInit() {
		btnSave.setOnAction(e -> {
			txtName.setStyle(null);
			txtId.setStyle(null);
			txtEmail.setStyle(null);
			txtPhone.setStyle(null);
			txtPassword.setStyle(null);
			
			if(txtId.getText().trim().isEmpty()) {
				CreateDialog.createStandarDialog("Lưu nhân viên", "Lưu nhân viên thất bại", "Không được để trống mã nhân viên", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtId);
				return;
			} else if(nvd.findById(txtId.getText()) != null) {
				CreateDialog.createStandarDialog("Lưu nhân viên", "Lưu nhân viên thất bại", "Mã nhân viên này đã được sử dụng, hãy tạo mã nhân viên khác", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtId);
				return;
			} else if(!standardCheck("Lưu nhân viên")) {
				return;
			}
			
			NhanVien nv = new NhanVien();
			nv.setManv(txtId.getText());
			nv.setHoten(txtName.getText());
			nv.setSodienthoai(txtPhone.getText());
			nv.setEmail(txtEmail.getText());
			nv.setMatKhau(txtPassword.getText());
			nv.setPhanquyen(chkManager.isSelected());
			nv.setGioitinh(chkMale.isSelected());
			
			nvd.insert(nv);
			
			resetTable();
			CreateDialog.createStandarDialog("Lưu nhân viên", "Lưu nhân viên thành công", "Nhân viên đã được thêm vào", AlertType.INFORMATION);
		});
	}
	
	public void btnUpdateInit() {
		btnUpdate.setOnAction(e -> {
			txtName.setStyle(null);
			txtId.setStyle(null);
			txtEmail.setStyle(null);
			txtPhone.setStyle(null);
			txtPassword.setStyle(null);
			
			if(txtId.getText().trim().isEmpty()) {
				CreateDialog.createStandarDialog("Cập nhật thông tin nhân viên", "Cập nhật thông tin nhân viên thất bại", "Không được để trống mã nhân viên", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtId);
				return;
			} else if(nvd.findById(txtId.getText()) == null) {
				CreateDialog.createStandarDialog("Cập nhật thông tin nhân viên", "Cập nhật thông tin nhân viên thất bại", "Mã nhân viên này không tồn tại, hãy nhập lại", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtId);
				return;
			} else if(!standardCheck("Cập nhật thông tin nhân viên")) {
				return;
			}
			
			NhanVien nv = new NhanVien();
			nv.setManv(txtId.getText());
			nv.setHoten(txtName.getText());
			nv.setSodienthoai(txtPhone.getText());
			nv.setEmail(txtEmail.getText());
			nv.setMatKhau(txtPassword.getText());
			nv.setPhanquyen(chkManager.isSelected());
			nv.setGioitinh(chkMale.isSelected());
			
			nvd.update(nv);
			
			resetTable();
			CreateDialog.createStandarDialog("Cập nhật thông tin nhân viên", "Cập nhật thông tin nhân viên thành công", "Nhân viên đã được thêm vào", AlertType.INFORMATION);
		});
	}
	
	public void btnAutoGenInit() {
		btnAutoGen.setOnAction(e -> {
			txtId.setText(new NhanVienCRUD().randomGenerateMaNV());
		});
	}
	
	public void tableInit() {
		list = FXCollections.observableArrayList(nvd.select());
		tblView.setItems(list);
		clmId.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("manv"));
		clmName.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("hoten"));
		clmPhone.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("sodienthoai"));
		clmState.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("phanquyenSTR"));
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(nvd.select());
		tblView.setItems(list);
		tblView.refresh();
	}
	
	public void tableClickInit() {
		tblView.setOnMouseClicked(e -> {
			NhanVien nv = tblView.getSelectionModel().getSelectedItem();
			if(nv != null) {
				txtId.setText(nv.getManv());
				txtName.setText(nv.getHoten());
				txtPhone.setText(nv.getSodienthoai());
				txtEmail.setText(nv.getEmail());
				txtPassword.setText(nv.getMatKhau());
				
				if(nv.getGioitinh()) {
					chkMale.setSelected(true);
				} else {
					chkFemale.setSelected(true);
				}
				
				if(nv.getPhanquyen()) {
					chkManager.setSelected(true);
				} else {
					chkEmp.setSelected(true);
				}
			}
		});
	}
	
	public boolean standardCheck(String title) {
		if(txtName.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, title+" thất bại", "Không được để trống họ tên", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtName);
			return false;
		} else if(txtPhone.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, title+" thất bại", "Không được để trống số điện thoại", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtPhone);
			return false;
		} else if(txtPhone.getText().trim().length() < 8) {
			CreateDialog.createStandarDialog(title, title+" thất bại", " SỐ điện thoại không được ít hơn 8 số", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtPhone);
			return false;
		} else if(txtEmail.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, title+" thất bại", "Không được để trống email", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtEmail);
			return false;
		} else if(!txtEmail.getText().matches(EMAIL_REGEX)) {
			CreateDialog.createStandarDialog(title, title+" thất bại", "Email không đúng định dạng", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtEmail);
			return false;
		} else if(txtPassword.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, title+" thất bại", "Password không được để trống", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtPassword);
			return false;
		} else if(txtPassword.getText().length() < 6) {
			CreateDialog.createStandarDialog(title, title+" thất bại", "Password phải hơn 6 kí tự", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtPassword);
			return false;
		}
		
		return true;

	}

}
