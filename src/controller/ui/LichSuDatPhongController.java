package controller.ui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import controller.ui.tempData.Index_Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.dao.IndexDAO;
import model.entities.IndexEntities;
import model.helper.CreateDialog;

public class LichSuDatPhongController implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnReload;

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

    @FXML
    private TableColumn<IndexEntities, String> clmState;

    @FXML
    private Button btnInvoice;

    @FXML
    private DatePicker datePckCOut;

    @FXML
    private DatePicker datePckCIn;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnCIn;

    @FXML
    private Button btnCOut;
    
    public static Stage stage;
    double yOffset;
    double xOffset;
    
    ObservableList<IndexEntities> list = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnInit();
		tableInit();
		tblClickInit();
	}
	
	public void tblClickInit() {
		tblView.setOnMouseClicked(e -> {
			IndexEntities ie = tblView.getSelectionModel().getSelectedItem();
			if(ie != null) {
				btnInvoice.setDisable(false);
			}
		});
		
		btnSearch.setOnAction(e -> {
			if(datePckCIn.getValue() == null || datePckCOut.getValue() == null) {
				CreateDialog.createStandarDialog("Tìm kiếm", "Tìm kiếm thất bại", "Không được để trống ngày băt đầu và kết thúc", AlertType.INFORMATION);
				return;
			}
			
			list = FXCollections.observableArrayList(new IndexDAO().SearchByDate(datePckCIn.getValue(), datePckCOut.getValue()));
			tblView.setItems(list);
			tblView.refresh();
		});
	}
	
	public void btnInit() {
		btnExit.setOnAction(e -> {
			IndexController.stage.close();
			DangNhapController.stage.show();
		});
		
		btnReload.setOnAction(e -> {
			resetTable();
		});
		
		btnInvoice.setOnAction(e -> {
			IndexEntities ie = tblView.getSelectionModel().getSelectedItem();
			if(ie != null) {
				Index_Invoice.transferData(ie.getMaDP()+"");
				openFXML("HoaDon.fxml");
			}
		});
		
		btnCIn.setOnAction(e -> {
			datePckCIn.show();
		});
		btnCOut.setOnAction(e -> {
			datePckCOut.show();
		});
	}
	
	public void resetTable() {
		list = FXCollections.observableArrayList(new IndexDAO().getAllRealOn());
		tblView.setItems(list);
		tblView.refresh();
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
	
	public void tableInit() {
		list = FXCollections.observableArrayList(new IndexDAO().getAllRealOn());
		tblView.setItems(list);
		clmRoomNumber.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("roomName"));
		clmRoomType.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("roomType"));
		clmCustomer.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("maKH"));
		clmCheckIn.setCellValueFactory(new PropertyValueFactory<IndexEntities, Date>("CIn"));
		clmCheckOut.setCellValueFactory(new PropertyValueFactory<IndexEntities, Date>("COut"));
		clmState.setCellValueFactory(new PropertyValueFactory<IndexEntities, String>("invoiceAF"));
	}

}
