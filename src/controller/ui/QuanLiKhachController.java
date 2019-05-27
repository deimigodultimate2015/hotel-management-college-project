package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.KhachHangCRUD;
import model.entities.KhachHang;
import model.helper.DialogCreator;

public class QuanLiKhachController implements Initializable{

    @FXML
    private Button btnExit;

    @FXML
    private TableView<KhachHang> tblView;

    @FXML
    private TableColumn<KhachHang, String> clmId;

    @FXML
    private TableColumn<KhachHang, String> clmName;

    @FXML
    private TableColumn<KhachHang, String> clmPhone;

    @FXML
    private TableColumn<KhachHang, String> clmState;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCMND;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextArea txtNote;

    @FXML
    private TextField txtEmail;

    @FXML
    private RadioButton rdbtnCIn;

    @FXML
    private ToggleGroup tglCheck;

    @FXML
    private RadioButton rdbtnCOut;
    
    KhachHangCRUD khCRUD = new KhachHangCRUD();
    DialogCreator dialog = new DialogCreator();
    final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
    ObservableList<KhachHang> list ;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtId.setDisable(true);
		TableInit();
		btnUpdateInit();
		btnExitInit();
		clickUp();
		fieldInit();
		btnNewInit();
		btnReloadInit();
	}
	
	public void btnReloadInit() {
		resetTable();
	}
	
	 public void btnNewInit() {
	    	btnRefresh.setOnAction(e -> {
	    		
	    		txtId.setStyle(null);
	    		txtName.setStyle(null);
	    		txtPhone.setStyle(null);
	    		txtEmail.setStyle(null);
	    		txtCMND.setStyle(null);
	    		
	    		txtId.setText("");
	    		txtName.setText("");
	    		txtPhone.setText("");
	    		txtEmail.setText("");
	    		txtCMND.setText("");
	    		txtNote.setText("");
	    	});
	    }
		
		public void TableInit() {
			list = FXCollections.observableArrayList(khCRUD.layKhachHangs());
			tblView.setItems(list);
			clmId.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("maKH"));
			clmName.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("hoTen"));
			clmPhone.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("dienThoai"));
			clmState.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("trangThaiReal"));
		}
		
		public void btnUpdateInit() {
			
			btnUpdate.setOnAction(e -> {
				
				txtId.setStyle(null);
				txtName.setStyle(null);
				txtPhone.setStyle(null);
				txtEmail.setStyle(null);
				txtCMND.setStyle(null);
				
				if (khCRUD.layKhachHangId(txtId.getText()).getMaKH() == null) {
					dialog.simpleDialog("Lưu thất bại", "Mã khách hàng không tồn tại","Có thể khách hàng đã bị xóa hoặc đã bị thay đổi nhưng chưa cập nhật, hãy load lại dữ liệu", AlertType.WARNING);
					dialog.makeThatTextfieldError(txtId);
					return;
				} else if(simpleCheck() == false) {
					return;
				}
				
				KhachHang kh = new KhachHang() ;
				kh.setMaKH(txtId.getText());
				kh.setCMND(txtCMND.getText());
				kh.setDienThoai(txtPhone.getText());
				kh.setHoTen(txtName.getText());
				kh.setEmail(txtEmail.getText());
				kh.setTrangThai(false);
				kh.setGhiChu(txtNote.getText());
				kh.setTrangThai(rdbtnCIn.isSelected()?true:false);
				
				khCRUD.capNhatKhachHang(kh);
				
				resetTable();
				
				dialog.simpleDialog("Cập nhật thành công", "Cập nhật khách hàng thành công", "Khách hàng "+txtId.getText()+" - "+txtName.getText()+" đã cập nhật", AlertType.INFORMATION);
				
			});
			
		}
		
		public void btnExitInit() {
			btnExit.setOnAction(e -> {
				QuanLiIndexController.stage.close();
				DangNhapController.stage.show();
			});
			
			btnReload.setOnAction(e -> {
				resetTable();
			});
		}
		
		public void clickUp() {
			tblView.setOnMouseClicked(e -> {
				KhachHang kh = tblView.getSelectionModel().getSelectedItem();
				txtId.setText(kh.getMaKH());
				txtName.setText(kh.getHoTen());
				txtEmail.setText(kh.getEmail());
				txtPhone.setText(kh.getDienThoai());
				txtCMND.setText(kh.getCMND());
				txtNote.setText(kh.getGhiChu());
				if(kh.isTrangThai()) { 
					rdbtnCIn.setSelected(true);
				} else {
					rdbtnCIn.setSelected(false);
				}
			});
		}
		
		public void resetTable() {
			list = FXCollections.observableArrayList(khCRUD.layKhachHangs());
			tblView.setItems(list);
			tblView.refresh();
		}
		
		public boolean simpleCheck() {
			String header = "Cập nhật thất bại";
			if(txtName.getText().trim().isEmpty() || txtName.getText().length() < 2) {
				dialog.simpleDialog(header, "Tên không được để trống", "Tên phải đặt từ 2 kí tự trở lên", AlertType.WARNING);
				dialog.makeThatTextfieldError(txtName);
				return false;
			} else if (txtCMND.getText().trim().isEmpty() || txtCMND.getText().length() < 9) {
				dialog.simpleDialog(header, "CMND không được để trống", "CMND phải đặt từ 9 kí tự trở lên", AlertType.WARNING);
				dialog.makeThatTextfieldError(txtCMND);
				return false;
			} else if (!txtEmail.getText().matches(EMAIL_REGEX)) {
				dialog.simpleDialog(header, "Email sai định dạng",null, AlertType.WARNING);
				dialog.makeThatTextfieldError(txtEmail);
				return false;
			} else if (txtPhone.getText().isEmpty() || txtPhone.getText().length() < 8) {
				dialog.simpleDialog(header, "Số điện thoại không được để trống", "Số điện thoại phải nhiều hơn 9 kí tự", AlertType.WARNING);
				dialog.makeThatTextfieldError(txtPhone);
				return false;
			} else {
				return true;
			}
		}
		
		public void fieldInit() {
			cutTextFieldToMax(txtName, 50);
			cutTextFieldToMax(txtEmail, 255);
			cutTextFieldToMax(txtCMND, 12);
			cutTextFieldToMax(txtPhone, 11);
			txtPhone.textProperty().addListener((obs, oldValue, newValue)-> {
				if (!newValue.matches("\\d*(\\.\\d*)?")) {
	                txtPhone.setText(oldValue);
	            }
			});
			
			txtNote.textProperty().addListener((obs, oldValue, newValue) -> {
				if(txtNote.getText().length() > 500) {
					String cut = txtNote.getText().substring(0, 500);
					txtNote.setText(cut);
				}
			});
		}
		
		public void cutTextFieldToMax(TextField txtField, int length) {
			txtField.textProperty().addListener((obs, oldValue, newValue) -> {
				if(txtField.getText().length() > length) {
					String cut = txtField.getText().substring(0, length);
					txtField.setText(cut);
				}
			});
		}

}
