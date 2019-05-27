package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.ui.tempData.QuanLiIndex_HoaDonChiTiet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.dao.HoaDonCRUD;
import model.dao.PhongDAO;
import model.entities.InvoiceEntities;
import model.entities.KhachHang;
import model.entities.Phong;
import model.entities.ThongTinDatPhong;

public class TTDatPhongChiTietController implements Initializable{

    @FXML
    private TableView<InvoiceEntities> tblServ;
    
    @FXML
    private Button btnExit;

    @FXML
    private TableColumn<InvoiceEntities, String> clm1Name;

    @FXML
    private TableColumn<InvoiceEntities, String> clm1Type;

    @FXML
    private TableColumn<InvoiceEntities, String> clm1Price;

    @FXML
    private TableColumn<InvoiceEntities, Integer> clm1Amount;

    @FXML
    private Button btnChangeService;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCMND;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtNote;

    @FXML
    private TextField txtCusName2;

    @FXML
    private TextField txtRoomAmount;

    @FXML
    private TextArea txtNote2;

    @FXML
    private TextField txtTogether;

    @FXML
    private TextField txtCheckIn;

    @FXML
    private TextField txtCheckOut;

    @FXML
    private Button btnChangeReservInfo;
    
    @FXML
    private Button btnReload;

    @FXML
    private TableView<Phong> tblRoom;

    @FXML
    private TableColumn<Phong, String> clm2Number;

    @FXML
    private TableColumn<Phong, String> clm2Type;

    @FXML
    private TableColumn<Phong, String> clm2Price;

    ObservableList<InvoiceEntities> list1 = FXCollections.observableArrayList();
    
    ObservableList<Phong> list2 = FXCollections.observableArrayList();
    
    HoaDonCRUD hdc = new HoaDonCRUD();
    PhongDAO pdao = new PhongDAO();
    
    double xOffset = 0;
    double yOffset = 0;
    
    public static Stage stage;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableInit();
		customerInfoInit();
		ButtonFernix();
	}
	
	public void ButtonFernix() {
		btnExit.setOnAction(e -> {
			QuanLiIndexController.stage.close();
			DangNhapController.stage.show();
		});
		
		btnChangeReservInfo.setOnAction(e -> {
			openFXML("QuanLiDatPhong.fxml");
		});
		
		btnChangeService.setOnAction(e -> {
			openFXML("QuanLyDichVuKhachHang.fxml");
		});
		
		btnReload.setOnAction(e-> {
			QuanLiIndex_HoaDonChiTiet.inferx(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP()+"", QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaKH() );
			tableInit();
			customerInfoInit();
		});
	}
	
	public void tableInit() {
		list1 = FXCollections.observableArrayList(hdc.getDichVu(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP()+""));
		list2 = FXCollections.observableArrayList(pdao.layDanhSachPhongMaDP(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP()+""));
		tblServ.setItems(list1);
		tblRoom.setItems(list2);
		clm1Amount.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, Integer>("soLuong"));
		clm1Name.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("tenDV"));
		clm1Price.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("giaTienAF"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<InvoiceEntities, String>("loaiDV"));
		clm2Number.setCellValueFactory(new PropertyValueFactory<Phong, String>("soPhong"));
		clm2Price.setCellValueFactory(new PropertyValueFactory<Phong, String>("giaPhongAF"));
		clm2Type.setCellValueFactory(new PropertyValueFactory<Phong, String>("loaiPhong"));
	}
	
	public void customerInfoInit() {
		KhachHang kh = QuanLiIndex_HoaDonChiTiet.khachhang;
		ThongTinDatPhong ttdatphong = QuanLiIndex_HoaDonChiTiet.ttdatphong;
		
		txtCusName.setText(kh.getHoTen());
		txtCusName2.setText(kh.getHoTen());
		txtCMND.setText(kh.getCMND());
		txtPhone.setText(kh.getDienThoai());
		txtEmail.setText(kh.getEmail());
		txtNote.setText(kh.getGhiChu());
		
		txtRoomAmount.setText(hdc.roomQuantity(ttdatphong.getMaDP()+"")+"");
		txtTogether.setText(ttdatphong.getSoNguoiDiCung()+"");
		txtCheckIn.setText(ttdatphong.getNgayDat()+"");
		txtCheckOut.setText(ttdatphong.getNgayTra()+"");
		txtNote2.setText(ttdatphong.getGhiChu());
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
			QuanLiIndexController.stage.close();
			
		} catch (Exception exs) {
			exs.printStackTrace();
		}
	}

}
