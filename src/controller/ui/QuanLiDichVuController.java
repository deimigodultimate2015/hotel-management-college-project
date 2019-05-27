package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DichVuDAO;
import model.entities.DichVu;
import model.helper.CreateDialog;
import model.helper.DialogCreator;

public class QuanLiDichVuController implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnReload;

    @FXML
    private TableView<DichVu> tblView;

    @FXML
    private TableColumn<DichVu, String> clmId;

    @FXML
    private TableColumn<DichVu, String> clmName;

    @FXML
    private TableColumn<DichVu, String> clmType;

    @FXML
    private TableColumn<DichVu, String> clmPrice;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtPrice;

    @FXML
    private Button btnUpdate;
    
    ObservableList<DichVu> list = FXCollections.observableArrayList();
    DichVuDAO dvd = new DichVuDAO();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();
		textFieldInit();
		btnSaveInit();
		btnUpdateInit();
		tableClickInit();
		anotherBtnInit();
	}
	
	public void anotherBtnInit() {
		btnRefresh.setOnAction(e -> {
			txtId.setStyle(null);
			txtName.setStyle(null);
			txtType.setStyle(null);
			txtPrice.setStyle(null);
			
			txtId.setText("");
			txtName.setText("");
			txtType.setText("");
			txtPrice.setText("");
		});
		
		btnExit.setOnAction(e -> {
			DangNhapController.stage.show();
			QuanLiIndexController.stage.close();
		});
		
		btnReload.setOnAction(e -> {
			resetTable();
			btnRefresh.fire();
		});
	}
	
	public void initTable() {
		list = FXCollections.observableArrayList(dvd.select());
		clmId.setCellValueFactory(new PropertyValueFactory<DichVu, String>("madv"));
		clmName.setCellValueFactory(new PropertyValueFactory<DichVu, String>("tendv"));
		clmType.setCellValueFactory(new PropertyValueFactory<DichVu, String>("loaidv"));
		clmPrice.setCellValueFactory(new PropertyValueFactory<DichVu, String>("giatienAF"));
		tblView.setItems(list);
	}
	
	public void btnSaveInit() {
		btnSave.setOnAction(e -> {
			txtId.setStyle(null);
			txtName.setStyle(null);
			txtType.setStyle(null);
			txtPrice.setStyle(null);
		
			if(standardCheck("Lưu dịch vụ") == false) {
				return;
			} else if (dvd.findById(txtId.getText()) != null) {
				CreateDialog.createStandarDialog("Lưu dịch vụ", "Lưu thất bại", "Mã dịch vụ này đã tồn tại, hãy chọn mã dịch vụ khác", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtId);
				return;
			} 
			
			DichVu dv = new DichVu();
			dv.setMadv(txtId.getText());
			dv.setTendv(txtName.getText());
			dv.setLoaidv(txtType.getText());
			dv.setGiatien(Double.parseDouble(txtPrice.getText()));
			dvd.insert(dv);
			resetTable();
			CreateDialog.createStandarDialog("Lưu dịch vụ", "Lưu thành công", "Dịch vụ đã được thêm vào danh sách", AlertType.INFORMATION);
		});
	}
	
	public void tableClickInit() {
		tblView.setOnMouseClicked(e -> {
			DichVu dv = tblView.getSelectionModel().getSelectedItem();
			if(dv != null) {
				txtId.setText(dv.getMadv());
				txtName.setText(dv.getTendv());
				txtType.setText(dv.getLoaidv());
				txtPrice.setText(dv.getGiatienAF());
			}
		});
	}
	
	public void btnUpdateInit() {
		
		btnUpdate.setOnAction(e -> {
			txtId.setStyle(null);
			txtName.setStyle(null);
			txtType.setStyle(null);
			txtPrice.setStyle(null);
			
			if(standardCheck("Cập nhật dịch vụ") == false) {
				return;
			} else if (dvd.findById(txtId.getText()) == null) {
				CreateDialog.createStandarDialog("Lưu dịch vụ", "Lưu thất bại", "Mã dịch vụ này không tồn tại, hãy nhập lại mã dịch vụ", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtId);
				return;
			} 
			
			DichVu dv = new DichVu();
			dv.setMadv(txtId.getText());
			dv.setTendv(txtName.getText());
			dv.setLoaidv(txtType.getText());
			dv.setGiatien(Double.parseDouble(txtPrice.getText()));
			dvd.update(dv);
			resetTable();
			CreateDialog.createStandarDialog("Cập nhật dịch vụ", "Cập nhật thành công", "Dịch vụ đã được cập nhật", AlertType.INFORMATION);
		});
		
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(dvd.select());
		tblView.setItems(list);
		tblView.refresh();
	}
	
	public boolean standardCheck(String title) {
		if(txtId.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title,"Không được để trống mã dịch vụ", "Vui điền dữ liệu vào mã dịch vụ", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtId);
			return false;
		} else if (txtName.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, "Không được để trống tên", "Vui lòng điền tên", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtName);
			return false;
		} else if (txtType.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, "Không được để trống loại dịch vụ", "Vui lòng điền loại dịch vụ", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtType);
			return false;
		} else if (txtPrice.getText().trim().isEmpty()) {
			CreateDialog.createStandarDialog(title, "Không được để trống giá dịch vụ", "Vui lòng điền giá dịch vụ", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtPrice);
			return false;
		} else if (Double.parseDouble(txtPrice.getText()) < 0) {
			CreateDialog.createStandarDialog(title, "Giá tiền không được nhỏ hơn 0", "Vui lòng điền lại giá tiền", AlertType.WARNING);
			new DialogCreator().makeThatTextfieldError(txtPrice);
			return false;
		}
		
		return true;
	}
	
	public void textFieldInit() {
		limitTextField(txtId, 8);
		limitTextField(txtName, 50);
		limitTextField(txtType, 50);
//		limitTextField(txtPrice, 8);
		txtPrice.textProperty().addListener((obs, ovl, nvl) -> {
			if (!nvl.matches("\\d*(\\.\\d*)?")) {
                txtPrice.setText(ovl);
            }
		});
	}
	
	public void limitTextField(TextField textField, int limit) {
		textField.textProperty().addListener((obs, ovl, nvl) -> {
			if(nvl.length() > limit) {
				textField.setText(ovl);
			}
		});
	}
	
	

}
