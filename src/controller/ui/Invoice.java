package controller.ui;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import controller.ui.tempData.Index_Invoice;
import controller.ui.tempData.LoginedUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DatPhongCRUD;
import model.dao.HoaDonCRUD;
import model.dao.PhongDAO;
import model.entities.InvoiceEntities;
import model.entities.Phong;
import model.helper.CreateDialog;
import model.helper.StringAndDate;

public class Invoice implements Initializable{

	@FXML
	private Button btnExport;
	
    @FXML
    private Button btnExit;
    
    @FXML
    private Label lblTotalDay;

    @FXML
    private Label lblCusId;

    @FXML
    private Label lblCusName;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblReservId;

    @FXML
    private Label lblCOut;

    @FXML
    private Label lblCIn;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblTogether;

    @FXML
    private Label lblInvId;

    @FXML
    private Label lblInvDate;

    @FXML
    private Label lblEmpId;

    @FXML
    private TableView<Phong> tblRoom;

    @FXML
    private TableColumn<Phong, String> clm1RoomNumber;

    @FXML
    private TableColumn<Phong, String> clm1Type;

    @FXML
    private TableColumn<Phong, String> clm1Note;

    @FXML
    private TableColumn<Phong, String> clm1Price;

    @FXML
    private Label lbl1Total;

    @FXML
    private TableView<InvoiceEntities> tblServ;

    @FXML
    private TableColumn<InvoiceEntities, Date> clm2Date;

    @FXML
    private TableColumn<InvoiceEntities, String> clm2Id;

    @FXML
    private TableColumn<InvoiceEntities, String> clm2Name;

    @FXML
    private TableColumn<InvoiceEntities, String> clm2Type;

    @FXML
    private TableColumn<InvoiceEntities, String> clm2Price;

    @FXML
    private TableColumn<InvoiceEntities, Integer> clm2Amount;

    @FXML
    private Label lbl2Total;

    long totalday = 0;
    double total1 = 0;
    double total2 = 0;
    
    ObservableList<Phong> list1 = FXCollections.observableArrayList();
    ObservableList<InvoiceEntities> list2 = FXCollections.observableArrayList();
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableInit();
		labelInit();
		btnExitInit();
	}
	
	public void btnExitInit() {
		btnExit.setOnAction(e -> {
			LichSuDatPhongController.stage.close();
			IndexController.stage.show();
		});
		
		btnExport.setOnAction(e -> {
			new DatPhongCRUD().release(Index_Invoice.ttdatphong.getMaDP()+"");
			CreateDialog.createStandarDialog("Xác nhận xuất hóa đơn", "Xác nhận xuất hóa đơn thành công",null, AlertType.INFORMATION);
			btnExit.fire();
		});
	}
	
	public void tableInit() {
		list1 = FXCollections.observableArrayList( new PhongDAO().layDanhSachPhongMaDP(Index_Invoice.ttdatphong.getMaDP()+""));
		list2 = FXCollections.observableArrayList(new HoaDonCRUD().getDichVu(Index_Invoice.ttdatphong.getMaDP()+""));
		tblRoom.setSelectionModel(null);
		tblServ.setSelectionModel(null);
		tblRoom.setItems(list1);
		tblServ.setItems(list2);
		clm1RoomNumber.setCellValueFactory(new PropertyValueFactory<Phong, String>("soPhong"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<Phong, String>("loaiPhong"));
		clm1Note.setCellValueFactory(new PropertyValueFactory<Phong, String>("ghiChu"));
		clm1Price.setCellValueFactory(new PropertyValueFactory<Phong, String>("giaPhongAF"));
		
		clm2Date.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, Date>("ngayDat"));
		clm2Id.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("maDV"));
		clm2Name.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("tenDV"));
		clm2Type.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("loaiDV"));
		clm2Price.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("giaTienAF"));
		clm2Amount.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, Integer>("soLuong"));
	}
	
	public void labelInit() {
		lblCusId.setText("Mã: "+Index_Invoice.kh.getMaKH());
		lblCusName.setText("Tên: "+Index_Invoice.kh.getHoTen());
		lblPhone.setText("Số điện thoại: "+Index_Invoice.kh.getDienThoai());
		lblEmail.setText("Email: "+Index_Invoice.kh.getEmail());
		
		lblReservId.setText("Mã: "+Index_Invoice.ttdatphong.getMaDP());
		lblCIn.setText("Ngày nhận: "+Index_Invoice.ttdatphong.getNgayDat());
		lblCOut.setText("Ngày trả: "+Index_Invoice.ttdatphong.getNgayTra());
		totalday = Duration.between(StringAndDate.DateToLocalDate(Index_Invoice.ttdatphong.getNgayDat()).atStartOfDay(), StringAndDate.DateToLocalDate(Index_Invoice.ttdatphong.getNgayTra()).atStartOfDay()).toDays()+1;
		lblTotalDay.setText("Tổng số ngày ở: "+(totalday)+"");
		lblTogether.setText("Số người đi cùng: "+Index_Invoice.ttdatphong.getSoNguoiDiCung());
		
		lblInvId.setText("Mã: "+ new HoaDonCRUD().randomGenerateMaHD());
		lblInvDate.setText("Ngày in: "+LocalDate.now());
		lblEmpId.setText("Mã nhân viên: "+LoginedUser.log.getManv());
		
		list1.forEach(e -> {
			total1 += e.getGiaphong()*totalday;
		});
		
		list2.forEach(e -> {
			total2 += e.getGiaTien()*e.getSoLuong();
		});
		
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
		
		lbl1Total.setText("Tổng cộng: "+df.format(total1));
		lbl2Total.setText("Tổng cộng: "+df.format(total2));
	}
	
	public void InvoiceToExcel() {
		
	}
	
	

}
