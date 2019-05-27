package controller.ui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import controller.RunApp;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.dao.IndexDAO;
import model.entities.IndexEntities;
import model.helper.CreateDialog;

public class QuanLiIndexController implements Initializable {

	@FXML
	private Button btnForgot;
	
	@FXML
	private Button btnReload;
	
    @FXML
    private Button btnExit;

    @FXML
    private Button btnCusManager;

    @FXML
    private Button btnReservDetailManager;

    @FXML
    private Button btnEmpManager;

    @FXML
    private Button btnServiceManager;

    @FXML
    private Button btnStatistic;

    @FXML
    private Button btnRoomManager;

    @FXML
    private TableView<IndexEntities> tblView;

    @FXML
    private TableColumn<IndexEntities, String> clmCustomer;

    @FXML
    private TableColumn<IndexEntities, String> clmRoomNumber;

    @FXML
    private TableColumn<IndexEntities, String> clmRoomType;

    @FXML
    private TableColumn<IndexEntities, Date> clmCheckIn;

    @FXML
    private TableColumn<IndexEntities, Date> clmCheckOut;
    
    double xOffset = 0;
    double yOffset = 0;
    public static Stage stage;
    ObservableList<IndexEntities> list = FXCollections.observableArrayList();
    IndexDAO idx = new IndexDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableInit();
		tableClickInit();
		mainButtonInit();
	}
	
	public void tableInit() {
		list = FXCollections.observableArrayList(idx.getAll());
		tblView.setItems(list);
		clmRoomNumber.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("roomName"));
		clmRoomType.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("roomType"));
		clmCustomer.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("maKH"));
		clmCheckIn.setCellValueFactory(new PropertyValueFactory<IndexEntities, Date>("CIn"));
		clmCheckOut.setCellValueFactory(new PropertyValueFactory<IndexEntities, Date>("COut"));
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(idx.getAll());
		tblView.setItems(list);
		tblView.refresh();
	}
	
	public void mainButtonInit() {
		btnReload.setOnAction(e -> {
			resetTable();
		});
		
		btnForgot.setOnAction(xse -> {
			try {
				stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/ChangePassword.fxml"));
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
				stage.showAndWait();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btnExit.setOnAction(e -> {
			boolean isExit = CreateDialog.createConfirmDiaglog("Đẵng xuất", "Bạn thật sự muốn đăng xuất ?", "Bạn sẽ được chuyển lại màn hình đăng nhập");
			if(isExit == false) {
				return;
			}
			DangNhapController.stage.close();
			RunApp.getStage().show();
		});
		btnCusManager.setOnAction(e -> {
			openFXML("QuanLyKhachHang.fxml");
		});
		
		btnServiceManager.setOnAction(e -> {
			openFXML("QuanLyDichVu.fxml");
		});
		
		btnRoomManager.setOnAction(e -> {
			openFXML("QuanLyPhong.fxml");
		});
		
		btnEmpManager.setOnAction(e -> {
			openFXML("QuanLyNhanVien.fxml");
		});
		
		btnStatistic.setOnAction(e -> {
			openFXML("ThongKe.fxml");
		});
		
		btnReservDetailManager.setOnAction(e -> {
			IndexEntities ien = tblView.getSelectionModel().getSelectedItem();
			if(ien != null) {
				QuanLiIndex_HoaDonChiTiet.inferx(ien.getMaDP()+"", ien.getMaKH());
				openFXML("DatPhongChiTiet.fxml");
			}
		});
	}
	
	public void tableClickInit() {
		tblView.setOnMouseClicked(e -> {
			btnReservDetailManager.setDisable(false);
		});
		
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
			DangNhapController.stage.close();
		} catch (Exception exs) {
			exs.printStackTrace();
		}
	}

}
