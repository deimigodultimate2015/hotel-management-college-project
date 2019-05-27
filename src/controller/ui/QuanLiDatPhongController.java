	package controller.ui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controller.ui.tempData.QuanLiIndex_HoaDonChiTiet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DatPhongDAO;
import model.dao.PhongDAO;
import model.entities.Phong;
import model.entities.ThongTinDatPhong;
import model.helper.DialogCreator;
import model.helper.StringAndDate;

public class QuanLiDatPhongController implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtNote;

    @FXML
    private DatePicker datePckCOut;

    @FXML
    private Button btnCOut;

    @FXML
    private TextField txtTogether;

    @FXML
    private TextField datePckCIn;

    @FXML
    private TableView<Phong> tblAvailable;

    @FXML
    private TableColumn<Phong, String> clm1RoomNumber;

    @FXML
    private TableColumn<Phong, String> clm1RoomType;

    @FXML
    private TableColumn<Phong, String> clm1Price;
    
    DialogCreator dialog = new DialogCreator();
    ObservableList<Phong> list1 = FXCollections.observableArrayList();
    PhongDAO phongDAO = new PhongDAO();
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	tableInit();
		btnExitInit();
		fieldInit();
		btnUpdateInit();
	}
    
    public void btnUpdateInit() {
		btnUpdate.setOnAction(e -> {
			try {
				DatPhongDAO dpDAO = new DatPhongDAO();
				ThongTinDatPhong ttdp = new ThongTinDatPhong();
				ttdp.setMaDP(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP());
				ttdp.setMaKH(txtId.getText());
				ttdp.setGhiChu(txtNote.getText());
				ttdp.setNgayDat(StringAndDate.StringToDate(datePckCIn.getText()));
				ttdp.setNgayTra(StringAndDate.LocalDateToDate(datePckCOut.getValue()));
				ttdp.setSoNguoiDiCung(Integer.parseInt(txtTogether.getText().trim().isEmpty()?"0":txtTogether.getText()));
				dpDAO.update(ttdp);
				dialog.simpleDialog("Sửa thông tin đặt phòng thành công", "Thông tin đặt phòng của khách hàng "+txtId.getText()+" đã được cập nhật thành công", null, AlertType.INFORMATION);
				QuanLiIndex_HoaDonChiTiet.inferx(ttdp.getMaDP()+"", ttdp.getMaKH());
				btnExit.fire();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
    
    public boolean standarCheck() {
		String title = "Sửa phòng thất bại";
		LocalDate dateCIn = StringAndDate.DateToLocalDate(StringAndDate.StringToDate(datePckCIn.getText()));
		LocalDate dateCOut = datePckCOut.getValue();
		if (dateCOut == null) {
			dialog.simpleDialog(title, "Không được để trống ngày trả phòng", null, AlertType.WARNING);
			dialog.makeThatDatePickerError(datePckCOut);
			return false;
		} else if(dateCOut.isBefore(dateCIn)) {
			dialog.simpleDialog(title, "Ngày trả phòng phải là trước hoặc trong ngày nhận phòng", null, AlertType.WARNING);
			dialog.makeThatDatePickerError(datePckCOut);
			return false;
		} else if(Integer.parseInt(txtTogether.getText().trim().isEmpty()?"0":txtTogether.getText()) < 0) {
			dialog.simpleDialog(title, "Số người đi cùng không được ít hơn 0", null, AlertType.WARNING);
			return false;
		}
		return true;
	}
    
    public void tableInit() {
		list1 = FXCollections.observableArrayList(phongDAO.layDanhSachPhongMaDP(QuanLiIndex_HoaDonChiTiet.ttdatphong.getMaDP()+""));
		tblAvailable.setItems(list1);
		
		clm1Price.setCellValueFactory(new PropertyValueFactory<Phong, String>("giaPhongAF"));
		clm1RoomNumber.setCellValueFactory(new PropertyValueFactory<Phong, String>("soPhong"));
		clm1RoomType.setCellValueFactory(new PropertyValueFactory<Phong, String>("loaiPhong"));
	}
    
    public void fieldInit() {
		txtId.setText(QuanLiIndex_HoaDonChiTiet.khachhang.getMaKH());
		txtName.setText(QuanLiIndex_HoaDonChiTiet.khachhang.getHoTen());
		datePckCIn.setText(QuanLiIndex_HoaDonChiTiet.ttdatphong.getNgayTra()+"");
		datePckCOut.setValue(StringAndDate.DateToLocalDate(QuanLiIndex_HoaDonChiTiet.ttdatphong.getNgayDat()));
		txtNote.setText(QuanLiIndex_HoaDonChiTiet.ttdatphong.getGhiChu());
		txtTogether.setText(QuanLiIndex_HoaDonChiTiet.ttdatphong.getSoNguoiDiCung()+"");
		
		txtTogether.textProperty().addListener((obs, oldValue, newValue)-> {
			if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtTogether.setText(oldValue);
            }
		});
		
		txtNote.textProperty().addListener((obs, oldValue, newValue) -> {
			if(txtNote.getText().length() > 500) {
				String cut = txtNote.getText().substring(0, 500);
				txtNote.setText(cut);
			}
		});
		
		txtTogether.textProperty().addListener((obs, oldValue, newValue) -> {
			if(txtTogether.getText().length() > 500) {
				String cut = txtTogether.getText().substring(0, 500);
				txtTogether.setText(cut);
			}
		});
	}

	public void btnExitInit() {
		btnCOut.setOnAction(e -> {
			datePckCOut.show();
		});
		
		btnExit.setOnAction(e-> {
			TTDatPhongChiTietController.stage.close();
			QuanLiIndexController.stage.show();
		});
		
		btnRefresh.setOnAction(e -> {
			txtName.setText("");
			txtTogether.setText("");
			txtNote.setText("");
		});
	}
}
