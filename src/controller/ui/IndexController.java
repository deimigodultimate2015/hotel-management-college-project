package controller.ui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import controller.RunApp;
import controller.ui.tempData.Index_Service;
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

public class IndexController implements Initializable {

    @FXML
    private Button btnExit;
    
    @FXML
    private Button btnReload;
    
    @FXML
    private Button btnChangePass;

    @FXML
    private TableView<IndexEntities> tblView;

    @FXML
    private TableColumn<IndexEntities, String> clmRoomNumber;
    
    @FXML
    private TableColumn<IndexEntities, String> clmCustomer;

    @FXML
    private TableColumn<IndexEntities, String> clmRoomType;

    @FXML
    private TableColumn<IndexEntities, Date> clmCheckIn;

    @FXML
    private TableColumn<IndexEntities, Date> clmCheckOut;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnInvoice;

    @FXML
    private Button btnAddService;
    
    private double xOffset = 0;
	private double yOffset = 0;
    
    public static Stage stage;
    ObservableList<IndexEntities> list = FXCollections.observableArrayList();	
    IndexDAO idx = new IndexDAO();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAddCustomerInit();
		btnExitReloadInit();
		tableInit();
		tableClickInit();
		btnInvoiceInit();
		btnReservInit();
		
	}
	
	public void btnAddCustomerInit() {
		btnAddCustomer.setOnAction(ex -> {
			openFXML("ThemKhach.fxml");
		});
	}
	
	public void btnExitReloadInit() {
		btnChangePass.setOnAction(esx -> {
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
		
		btnReload.setOnAction(e -> {
			list = FXCollections.observableArrayList(idx.getAll());
			tblView.setItems(list);
			tblView.refresh();
		});
	}
	
	public void btnReservInit() {
		btnAddService.setOnAction(e -> {
			IndexEntities ie = tblView.getSelectionModel().getSelectedItem();
			if(ie != null) {
				Index_Service.insertTemp(ie.getMaKH(), ie.getMaDP(), ie.getRoomName());
				openFXML("DatDichVu.fxml");
			}
		});
	}
	
	public void btnInvoiceInit() {
		btnInvoice.setOnAction(e -> {
			openFXML("LichSuDangKi.fxml");
		});
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
	
	public void tableClickInit() {
		tblView.setOnMouseClicked(e -> {
			btnAddService.setDisable(false);
			btnInvoice.setDisable(false);
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
