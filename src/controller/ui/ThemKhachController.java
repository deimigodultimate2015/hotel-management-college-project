package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.ui.tempData.ThemKhach_DatPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.dao.KhachHangCRUD;
import model.entities.KhachHang;
import model.helper.DialogCreator;

public class ThemKhachController implements Initializable {

    @FXML
    private Button btnExit;
    
    @FXML
    private Button btnReload;

    @FXML
    private TableView<KhachHang> tblView;

    @FXML
    private TableColumn<KhachHang, String> clmId;

    @FXML
    private TableColumn<KhachHang, String> clmCustomer;

    @FXML
    private TableColumn<KhachHang, String> clmPhone;

    @FXML
    private TableColumn<KhachHang, String> clmState;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

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
    private Button btnAutoGen;

    @FXML
    private Button btnReserv;
    
    public static Stage stage ;
    double yOffset;
    double xOffset;
    
    KhachHangCRUD khCRUD = new KhachHangCRUD();
    DialogCreator dialog = new DialogCreator();
    final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
    ObservableList<KhachHang> list ;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		TableInit();
		btnSaveInit();
		btnExitInit();
		btnAutoGenerateInit();
		clickUp();
		fieldInit();
		btnNewInit();
		btnReservInit();
	}
    
    public void btnReservInit() {
    	btnReserv.setOnAction(exs -> {
    		try {
    			if(tblView.getSelectionModel().getSelectedItem() == null) {
    				dialog.simpleDialog("Đặt phòng thất bại", "Phải chọn khách hàng ở bảng để đặt phòng", null, AlertType.WARNING);
    				return;
    			} else if (khCRUD.layKhachHangId(tblView.getSelectionModel().getSelectedItem().getMaKH()).isTrangThai() == true) {
    				dialog.simpleDialog("Đặt phòng thất bại", "Khách hàng này hiện đang check in", null, AlertType.WARNING);
    				return;
    			}
    			ThemKhach_DatPhong.khachHang = tblView.getSelectionModel().getSelectedItem();
    			stage = new Stage();
    			Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/DatPhong.fxml"));
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
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    	});
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
		clmCustomer.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("hoTen"));
		clmPhone.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("dienThoai"));
		clmState.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("trangThaiReal"));
	}
	
	public void btnAutoGenerateInit() {
		btnAutoGen.setOnAction(e -> {
			txtId.setText(khCRUD.randomGenerateMaKH());
		});
	}
	
	public void btnSaveInit() {
		
		btnSave.setOnAction(e -> {
			
			txtId.setStyle(null);
			txtName.setStyle(null);
			txtPhone.setStyle(null);
			txtEmail.setStyle(null);
			txtCMND.setStyle(null);
			
			if (khCRUD.layKhachHangId(txtId.getText()).getMaKH() != null) {
				dialog.simpleDialog("Lưu thất bại", "Mã khách hàng đã tồn tại", "Hãy thử lại lần nữa", AlertType.WARNING);
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
			
			khCRUD.themKhachHang(kh);
			
			resetTable();
			
			dialog.simpleDialog("Lưu thành công", "Lưu khách hàng thành công", "Khách hàng "+txtId.getText()+" - "+txtName.getText()+" đã được lưu", AlertType.INFORMATION);
			
		});
		
	}
	
	public void btnExitInit() {
		btnExit.setOnAction(e -> {
			IndexController.stage.close();
			DangNhapController.stage.show();
		});
		
		btnReload.setOnAction(e -> {
			resetTable();
			btnRefresh.fire();
		});
	}
	
	public void clickUp() {
		tblView.setOnMouseClicked(e -> {
			KhachHang kh = tblView.getSelectionModel().getSelectedItem();
			if(kh != null) {
					txtId.setText(kh.getMaKH());
				txtName.setText(kh.getHoTen());
				txtEmail.setText(kh.getEmail());
				txtPhone.setText(kh.getDienThoai());
				txtCMND.setText(kh.getCMND());
				txtNote.setText(kh.getGhiChu());
			}
		});
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(khCRUD.layKhachHangs());
		tblView.setItems(list);
		tblView.refresh();
	}
	
	public boolean simpleCheck() {
		String header = "Lưu thất bại";
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
