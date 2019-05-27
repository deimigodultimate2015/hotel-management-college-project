package controller.ui;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controller.ui.tempData.Index_Service;
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
import model.dao.DichVuCRUD;
import model.dao.DichVuDAO;
import model.entities.DichVu;
import model.helper.DialogCreator;
import model.helper.StringAndDate;

public class DatDichVuController implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private TableView<DichVu> tblList;

    @FXML
    private TableColumn<DichVu, String> clm1Name;

    @FXML
    private TableColumn<DichVu, String> clm1Type;

    @FXML
    private TableColumn<DichVu, String> clm1Price;

    @FXML
    private TextField txtReservId;

    @FXML
    private TextField txtDate;

    @FXML
    private Button btnSetServ;

    @FXML
    private TextField txtRoomNumber;

    @FXML
    private TextField txtGuestName;

    @FXML
    private TextField txtCMND;

    @FXML
    private TextField txtPhone;

    @FXML
    private TableView<DichVu> tblCustomer;

    @FXML
    private TableColumn<DichVu, String> clm2Name;

    @FXML
    private TableColumn<DichVu, String> clm2Type;

    @FXML
    private TableColumn<DichVu, String> clm2Price;

    @FXML
    private TableColumn<DichVu, Integer> clm2Amount;

    @FXML
    private Button btnReload;
    
    @FXML
    private Button btnGetAll;

    @FXML
    private Button btnGet;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnReturnAll;

    @FXML
    private Label lblTotal;
    
    DichVuDAO dvDAO = new DichVuDAO();
    
    ObservableList<DichVu> list1 = FXCollections.observableArrayList();
    ObservableList<DichVu> list2 = FXCollections.observableArrayList();
    double total = 0;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextFieldInit();
		TableInit();
		TableButtonInit();
		btnSetServInit();
		btnExitInit();
	}
	
	public void btnExitInit() {
		btnExit.setOnAction(e -> {
			IndexController.stage.close();
			DangNhapController.stage.show();
		});
		
		btnReload.setOnAction(e -> {
			list1 = FXCollections.observableArrayList(dvDAO.select());
			list2.clear();
			tblList.setItems(list1);
			tblCustomer.setItems(list2);
		});
	}
	
	public void btnSetServInit() {
		btnSetServ.setOnAction(e -> {
			if(list2.isEmpty()) {
				new DialogCreator().simpleDialog("Đặt dịch vụ thất bại", "Phải có ít nhất 1 dịch vụ", null, AlertType.WARNING);
				return;
			}
			
			new DichVuCRUD().datDichVu(txtReservId.getText(), StringAndDate.DateToLocalDate(StringAndDate.StringToDate(txtDate.getText())), list2);
			new DialogCreator().simpleDialog("Đặt dịch vụ thành công", "Dịch vụ đã được thêm vào kho dịch vụ của khách hàng", null, AlertType.INFORMATION);
			btnExit.fire();
		});
	}
	
	public void TableButtonInit() {
		btnGet.setOnAction(e -> {
			DichVu dvb = tblList.getSelectionModel().getSelectedItem();
			if(dvb != null) {
				if(list2.contains(dvb)) {
					list2.get(list2.indexOf(dvb)).setSoLuong(list2.get(list2.indexOf(dvb)).getSoLuong()+1);
				} else {
					list2.add(dvb);
				}
			}
			tblCustomer.setItems(list2);
			tblCustomer.refresh();
			toInvoice();
		});
		
		btnGetAll.setOnAction(e -> {
			list1.forEach(dvb -> {
				if(list2.contains(dvb)) {
					list2.get(list2.indexOf(dvb)).setSoLuong(list2.get(list2.indexOf(dvb)).getSoLuong()+1);
				} else {
					list2.add(dvb);
				}
			});
			
			tblCustomer.setItems(list2);
			tblCustomer.refresh();
			toInvoice();
		});
		
		btnReturn.setOnAction(e -> {
			DichVu dvb = tblCustomer.getSelectionModel().getSelectedItem();
			if(dvb != null) {
				if(dvb.getSoLuong() > 0) {
					list2.get(list2.indexOf(dvb)).setSoLuong(list2.get(list2.indexOf(dvb)).getSoLuong()-1);
				} else {
					list2.remove(dvb);
				}
				
				tblCustomer.setItems(list2);
				tblCustomer.refresh();
			}
			toInvoice();
		});
		
		btnReturnAll.setOnAction(e -> {
			list2.clear();
			tblCustomer.setItems(list2);
			tblCustomer.refresh();
			toInvoice();
		});
	}
	
	public void toInvoice() {
		total = 0;
		list2.forEach( esx-> {
			total += (esx.getGiatien()*esx.getSoLuong());
		});
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
		lblTotal.setText("Tổng cộng: "+df.format(total));
		
	}
	
	public void TextFieldInit() {
		txtReservId.setText(Index_Service.ttdatphong.getMaDP()+"");
		txtDate.setText(LocalDate.now()+"");
		txtRoomNumber.setText(Index_Service.soPhong);
		txtGuestName.setText(Index_Service.kh.getHoTen());
		txtCMND.setText(Index_Service.kh.getCMND());
		txtPhone.setText(Index_Service.kh.getDienThoai());
	}
	
	public void TableInit() {
		list1 = FXCollections.observableArrayList(dvDAO.select());
		tblList.setItems(list1);
		tblCustomer.setItems(list2);
		clm1Name.setCellValueFactory(new PropertyValueFactory<DichVu, String>("tendv"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<DichVu, String>("loaidv"));
		clm1Price.setCellValueFactory(new PropertyValueFactory<DichVu, String>("giatienAF"));
		clm2Name.setCellValueFactory(new PropertyValueFactory<DichVu, String>("tendv"));
		clm2Type.setCellValueFactory(new PropertyValueFactory<DichVu, String>("loaidv"));
		clm2Price.setCellValueFactory(new PropertyValueFactory<DichVu, String>("giatienAF"));
		clm2Amount.setCellValueFactory(new PropertyValueFactory<DichVu, Integer>("soLuong"));
	}

}
