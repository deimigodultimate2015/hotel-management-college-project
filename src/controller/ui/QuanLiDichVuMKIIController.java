package controller.ui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ResourceBundle;

import controller.ui.tempData.QuanLiIndex_HoaDonChiTiet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DichVuMKIICRUD;
import model.entities.DichVuMKII;
import model.helper.CreateDialog;
import model.helper.DialogCreator;

public class QuanLiDichVuMKIIController implements Initializable {

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<DichVuMKII> tblView;

    @FXML
    private TableColumn<DichVuMKII, Date> clmId;

    @FXML
    private TableColumn<DichVuMKII, String> clmName;

    @FXML
    private TableColumn<DichVuMKII, String> clmType;

    @FXML
    private TableColumn<DichVuMKII, String> clmPrice;

    @FXML
    private TableColumn<DichVuMKII, Integer> clmAmount;

    @FXML
    private Button btnExit;
    
    @FXML
    private Button btnReload;
    
    @FXML
    private Label lblTotal;
    
    double total = 0;
    
    ObservableList<DichVuMKII> list = FXCollections.observableArrayList();
    DichVuMKIICRUD dvcrud = new DichVuMKIICRUD();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableInit();
		tableClickInit();
		textFieldInit();
		btnUpdateInit();
		btnInit();
	}
	
	public void tableInit() {
		list = FXCollections.observableArrayList(dvcrud.layDichVuTuMaDP(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP()+""));
		tblView.setItems(list);
		clmId.setCellValueFactory(new PropertyValueFactory<DichVuMKII, Date>("ngayDat"));
		clmName.setCellValueFactory(new PropertyValueFactory<DichVuMKII, String>("tendv"));
		clmType.setCellValueFactory(new PropertyValueFactory<DichVuMKII, String>("loaidv"));
		clmPrice.setCellValueFactory(new PropertyValueFactory<DichVuMKII, String>("giatienAF"));
		clmAmount.setCellValueFactory(new PropertyValueFactory<DichVuMKII, Integer>("soLuong"));
		total = 0;
		list.forEach(e -> {
			total += e.getGiatien();
		});
    	DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
    	lblTotal.setText("Thành tiền: "+df.format(total));
		
	}
	
	public void btnInit() {
		btnExit.setOnAction(e->{
			TTDatPhongChiTietController.stage.close();
			QuanLiIndexController.stage.show();
		});
		
		btnReload.setOnAction(e-> {
			resetTable();
			txtQuantity.setText(0+"");
		});
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(dvcrud.layDichVuTuMaDP(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP()+""));
		tblView.setItems(list);
		tblView.refresh();
		total = 0;
		list.forEach(e -> {
			total += e.getGiatien();
		});
    	DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
    	lblTotal.setText("Thành tiền: "+df.format(total));
	}
	
	public void btnUpdateInit() {
		btnUpdate.setOnAction(e -> {
			txtQuantity.setStyle(null);
			DichVuMKII dvmkII = tblView.getSelectionModel().getSelectedItem();
			if(dvmkII == null) {
				CreateDialog.createStandarDialog("Cập nhật", "Cập nhật thất bại", "Phải chọn một dòng trên danh sách để cập nhật", AlertType.WARNING);
				return;
			} else if (txtQuantity.getText().trim().isEmpty()) {
				CreateDialog.createStandarDialog("Cập nhật", "Cập nhật thất bại", "Không được để trống số lượng", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtQuantity);
				return;
			} 
			
			dvmkII.setSoLuong(Integer.parseInt(txtQuantity.getText()));
			dvcrud.capNhatSoLuongDichVu(dvmkII);
			tblView.refresh();
			CreateDialog.createStandarDialog("Cập nhật", "Cập nhật thành công", "Số lượng dịch vụ đã được cập nhật lại", AlertType.WARNING);
		});
	}
	
	public void tableClickInit() {
		tblView.setOnMouseClicked(e -> {
			DichVuMKII dvmkII = tblView.getSelectionModel().getSelectedItem();
			if(dvmkII != null) {
				txtDate.setText(dvmkII.getNgayDat()+"");
				txtName.setText(dvmkII.getTendv());
				txtType.setText(dvmkII.getLoaidv());
				txtQuantity.setText(dvmkII.getSoLuong()+"");
				txtPrice.setText(dvmkII.getGiatienAF());
			}
		});
	}
	
	public void textFieldInit() {
		txtQuantity.textProperty().addListener((obs, ovl, nvl) -> {
			if(!nvl.matches("\\d*")) {
				txtQuantity.setText(ovl);
			}
			
			if(nvl.trim().length() > 8) {
				txtQuantity.setText(ovl);
			}
		});
		
	}

}
