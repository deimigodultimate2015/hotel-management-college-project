package controller.ui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.ui.tempData.ThemKhach_DatPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DatPhongDAO;
import model.dao.PhongDAO;
import model.entities.Phong;
import model.entities.ThongTinDatPhong;
import model.helper.DialogCreator;
import model.helper.StringAndDate;

public class DatPhongController implements Initializable {

    @FXML
    private Button btnExit;
    
    @FXML
    private Button btnReload;

    @FXML
    private TableView<Phong> tblAvailable;

    @FXML
    private TableColumn<Phong, String> clm1RoomNumber;

    @FXML
    private TableColumn<Phong, String> clm1RoomType;

    @FXML
    private TableColumn<Phong, String> clm1Price;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtNote;

    @FXML
    private DatePicker datePckCOut;

    @FXML
    private DatePicker datePckCIn;

    @FXML
    private Button btnCIn;

    @FXML
    private Button btnCOut;

    @FXML
    private TextField txtTogether;

    @FXML
    private TableView<Phong> tblCustomer;

    @FXML
    private TableColumn<Phong, String> clm2RoomNumber;

    @FXML
    private TableColumn<Phong, String> clm2RoomType;

    @FXML
    private TableColumn<Phong, String> clm2Price;

    @FXML
    private Button btnGetAll;

    @FXML
    private Button btnGet;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnReturnAll;
    
    DialogCreator dialog = new DialogCreator();
    ObservableList<Phong> list1 = FXCollections.observableArrayList();
    ObservableList<Phong> list2 = FXCollections.observableArrayList();
    PhongDAO phongDAO = new PhongDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btn2DatePickerInit();
		btnSaveInit();
		tableInit();
		fieldInit();
		btnExitInit();
		tableButtonInit();
	}
	
	public void btnExitInit() {
		
		btnReload.setOnAction(e -> {
			resetTable();
			btnRefresh.fire();
		});
		
		btnExit.setOnAction(e -> {
			ThemKhachController.stage.close();
			IndexController.stage.show();
		});
		
		btnRefresh.setOnAction(e -> {
			datePckCIn.setValue(LocalDate.now());
			datePckCIn.setValue(LocalDate.now());
			txtTogether.setText("");
			txtNote.setText("");
		});
	}
	
	public void tableButtonInit() {
		btnGet.setOnAction(e -> {
			Phong phong = tblAvailable.getSelectionModel().getSelectedItem();
			if(phong != null) {
				list2.add(phong);
				list1.remove(phong);
				resetTableNonDB();
			}
		});
		
		btnGetAll.setOnAction(e -> {
			list1.forEach(esx -> {
				list2.add(esx);
			});
			list1.clear();
			resetTableNonDB();
		});
		
		btnReturn.setOnAction(e -> {
			Phong phong = tblCustomer.getSelectionModel().getSelectedItem();
			if(phong != null) {
				list1.add(phong);
				list2.remove(phong);
				resetTableNonDB();
			}
		});
		
		btnReturnAll.setOnAction(e -> {
			list2.forEach(esx -> {
				list1.add(esx);
			});
			list2.clear();
			resetTableNonDB();
		});
	}
	
	public void resetTable() {
		list1 = FXCollections.observableArrayList(phongDAO.selectBySTT());
		list2 = FXCollections.observableArrayList();
		tblAvailable.setItems(list1);
		tblCustomer.setItems(list2);
		tblAvailable.refresh();
		tblCustomer.refresh();
	}
	
	public void resetTableNonDB() {
		tblAvailable.setItems(list1);
		tblCustomer.setItems(list2);
		tblAvailable.refresh();
		tblCustomer.refresh();
	}
	
	public void fieldInit() {
		txtId.setText(ThemKhach_DatPhong.khachHang.getMaKH());
		txtName.setText(ThemKhach_DatPhong.khachHang.getHoTen());
		
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
	
	public void tableInit() {
		list1 = FXCollections.observableArrayList(phongDAO.selectBySTT());
		list2 = FXCollections.observableArrayList();
		tblAvailable.setItems(list1);
		tblCustomer.setItems(list2);
		
		clm1Price.setCellValueFactory(new PropertyValueFactory<Phong, String>("giaPhongAF"));
		clm1RoomNumber.setCellValueFactory(new PropertyValueFactory<Phong, String>("soPhong"));
		clm1RoomType.setCellValueFactory(new PropertyValueFactory<Phong, String>("loaiPhong"));
		
		clm2Price.setCellValueFactory(new PropertyValueFactory<Phong, String>("giaPhongAF"));
		clm2RoomNumber.setCellValueFactory(new PropertyValueFactory<Phong, String>("soPhong"));
		clm2RoomType.setCellValueFactory(new PropertyValueFactory<Phong, String>("loaiPhong"));
	}
	
	public void btn2DatePickerInit() {
		datePckCIn.setValue(LocalDate.now());
		btnCIn.setOnAction(e -> {
//			datePckCIn.show();
		});
		btnCOut.setOnAction(e -> {
			datePckCOut.show();
		});
	}
	
	public void btnSaveInit() {
		btnSave.setOnAction(e -> {
			try {
				if(standarCheck()==false) {
					return;
				} else if(list2.isEmpty()) {
					dialog.simpleDialog("Đặt phòng thất bại", "Phải có ít nhất 1 phòng", null, AlertType.WARNING);
					return;
				}
				
				DatPhongDAO dpDAO = new DatPhongDAO();
				ThongTinDatPhong ttdp = new ThongTinDatPhong();
				ttdp.setMaKH(txtId.getText());
				ttdp.setGhiChu(txtNote.getText());
				ttdp.setNgayDat(StringAndDate.LocalDateToDate(datePckCIn.getValue()));
				ttdp.setNgayTra(StringAndDate.LocalDateToDate(datePckCOut.getValue()));
				ttdp.setSoNguoiDiCung(Integer.parseInt(txtTogether.getText().trim().isEmpty()?"0":txtTogether.getText()));
				List<String> list = new ArrayList<>();
				list2.forEach(esx -> {
					list.add(esx.getSoPhong());
				});
				list.forEach(esxx -> {
					if(phongDAO.findById(esxx).getTrangThai() == false) {
						dialog.simpleDialog("Đặt phòng thất bại", "Phòng "+esxx+" đã được sử dụng", null, AlertType.WARNING);
						return;
					}
				});
				dpDAO.insert(ttdp, list);
				dialog.simpleDialog("Đặt phòng thành công", "Phòng của khách hàng "+txtId.getText()+" đã được đặt thành công", null, AlertType.INFORMATION);
				btnExit.fire();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	public boolean standarCheck() {
		String title = "Đặt phòng thất bại";
		LocalDate dateCIn =  datePckCIn.getValue();
		LocalDate dateCOut = datePckCOut.getValue();
		if(dateCIn == null) {
			dialog.simpleDialog(title, "Không được để trống ngày nhận phòng", null, AlertType.WARNING);
			dialog.makeThatDatePickerError(datePckCIn);
			return false;
		} else if(dateCIn.isBefore(LocalDate.now())) {
			dialog.simpleDialog(title, "Ngày nhận phòng phải là hôm nay hoặc thời gian tới, không phải quá khứ", null, AlertType.WARNING);
			dialog.makeThatDatePickerError(datePckCIn);
			return false;
		} else if (dateCOut == null) {
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

}
