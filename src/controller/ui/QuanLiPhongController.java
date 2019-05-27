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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.PhongDAO;
import model.entities.Phong;
import model.helper.CreateDialog;
import model.helper.DialogCreator;

public class QuanLiPhongController implements Initializable{

    @FXML
    private Button btnExit;

    @FXML
    private TableView<Phong> tblView;

    @FXML
    private TableColumn<Phong, String> clmNumber;

    @FXML
    private TableColumn<Phong, String> clmType;

    @FXML
    private TableColumn<Phong, String> clmNote;

    @FXML
    private TableColumn<Phong, String> clmPrice;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextArea txtNote;
    
    ObservableList<Phong> list = FXCollections.observableArrayList();
    PhongDAO rod = new PhongDAO();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableInit();
		textFieldInit();
		btnUpdateInit();
		tableClickInit();
		btnExitInit();
	}
	
	public void btnExitInit() {
		btnExit.setOnAction(e -> {
			QuanLiIndexController.stage.close();
			DangNhapController.stage.show();
			
		});
		
		btnRefresh.setOnAction(e -> {
			txtPrice.setStyle(null);
			txtType.setStyle(null);
			txtNote.setText("");
			txtPrice.setText("");
			txtType.setText("");
		});
	}
	
	public void btnUpdateInit() {
		btnUpdate.setOnAction(e -> {
			txtPrice.setStyle(null);
			txtType.setStyle(null);
			
			if(txtNumber.getText().trim().isEmpty()) {
				CreateDialog.createStandarDialog("Sửa thông tin phòng", "Sửa thông tin phòng thất bại", "Không được để trống số phòng", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtNumber);
				return;
			} else if (txtPrice.getText().trim().isEmpty()) {
				CreateDialog.createStandarDialog("Sửa thông tin phòng", "Sửa thông tin phòng thất bại", "Không được để trống giá phòng", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtPrice);
				return;
			} else if (Double.parseDouble(txtPrice.getText().trim()) < 0) {
				CreateDialog.createStandarDialog("Sửa thông tin phòng", "Sửa thông tin phòng thất bại", "Giá phòng không được nhỏ hơn 0", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtPrice);
				return;
			}
			
			Phong phong = new Phong();
			phong.setSoPhong(txtNumber.getText());
			phong.setGiaphong(Double.parseDouble(txtPrice.getText()));
			phong.setLoaiPhong(txtType.getText());
			phong.setGhiChu(txtNote.getText());
			rod.update(phong);
			resetTable();
			CreateDialog.createStandarDialog("Sửa thông tin phòng", "Sửa thông tin phòng thành công", "Thông tin phòng đã được cập nhật", AlertType.INFORMATION);
		});
	}
	
	public void tableClickInit() {
		tblView.setOnMouseClicked(e -> {
			Phong phong = tblView.getSelectionModel().getSelectedItem();
			if(phong != null) {
				txtNumber.setText(phong.getSoPhong());
				txtType.setText(phong.getLoaiPhong());
				txtPrice.setText(phong.getGiaPhongAF());
				txtNote.setText(phong.getGhiChu());
			}
		});
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(rod.select());
		tblView.setItems(list);
		tblView.refresh();
	}
	
	public void tableInit() {
		list = FXCollections.observableArrayList(rod.select());
		tblView.setItems(list);
		clmNumber.setCellValueFactory(new PropertyValueFactory<Phong, String>("soPhong"));
		clmType.setCellValueFactory(new PropertyValueFactory<Phong, String>("loaiPhong"));
		clmPrice.setCellValueFactory(new PropertyValueFactory<Phong, String>("giaPhongAF"));
		clmNote.setCellValueFactory(new PropertyValueFactory<Phong, String>("ghiChu"));
	}
	
	public void textFieldInit() {
		txtType.textProperty().addListener((obs, ovl, nvl) -> {
			if(nvl.length() > 50) {
				txtType.setText(ovl);
			}
		});
		
		txtPrice.textProperty().addListener((obs, ovl, nvl) -> {
			if (!nvl.matches("\\d*(\\.\\d*)?")) {
                txtPrice.setText(ovl);
            }
		});
	}

}
