package controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.dao.DatPhongCRUD;
import model.dao.DichVuCRUD;
import model.dao.PhongDAO;
import model.entities.statistic.NameTypeIncome;

public class IncomeStatisticController implements Initializable{

	@FXML
    private Button btnExit;

    @FXML
    private BarChart<String, Double> chart1;

    @FXML
    private CategoryAxis y1;

    @FXML
    private NumberAxis x1;

    @FXML
    private ComboBox<Integer> txt1Year;

    @FXML
    private Button btn1Submit;

    @FXML
    private ComboBox<String> txt1Choice;

    @FXML
    private ComboBox<String> txt1Month;

    @FXML
    private TableView<NameTypeIncome> tblView1;

    @FXML
    private TableColumn<NameTypeIncome, String> clm1Name;

    @FXML
    private TableColumn<NameTypeIncome, String> clm1Type;

    @FXML
    private TableColumn<NameTypeIncome, String> clm1Income;

    @FXML
    private ComboBox<String> txt1Type;
    
    @FXML
    private PieChart chart2;
    
    @FXML
    private Pane pane2;
    
    @FXML
    private Pane pane1;
    
    ObservableList<NameTypeIncome> list = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		chart1.setAnimated(false);
		comboboxInit();
		ButtonInit();
	}
	
	public void ButtonInit() {
		btnExit.setOnAction(e -> {
			QuanLiIndexController.stage.close();
			DangNhapController.stage.show();
		});
		
		btn1Submit.setOnAction(e -> {
			if(txt1Type.getSelectionModel().getSelectedItem().equalsIgnoreCase("thống kê doanh thu")) {
				pane1.setVisible(true);
				pane2.setVisible(false);
				if(txt1Month.getSelectionModel().getSelectedItem().equalsIgnoreCase("Cả năm")) {
					if(txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("phòng")) {
						roomCharYearIgnite(txt1Year.getSelectionModel().getSelectedItem());
					} else if (txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("dịch vụ")){
						serviceCharYearIgnite(txt1Year.getSelectionModel().getSelectedItem());
					} else {
						serviceTypeCharYearIgnite(txt1Year.getSelectionModel().getSelectedItem());
					}
				} else  {
					if(txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("phòng")) {
						roomCharMonthIgnite(txt1Year.getSelectionModel().getSelectedItem(), Integer.parseInt(txt1Month.getSelectionModel().getSelectedItem()));
					} else if(txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("dịch vụ")) {
						serviceCharMonthIgnite(txt1Year.getSelectionModel().getSelectedItem(), Integer.parseInt(txt1Month.getSelectionModel().getSelectedItem()));
					} else {
						serviceTypeCharYearIgnite(txt1Year.getSelectionModel().getSelectedItem(), Integer.parseInt(txt1Month.getSelectionModel().getSelectedItem()));
					}
				}
			} else {
				pane1.setVisible(false);
				pane2.setVisible(true);
				if(txt1Month.getSelectionModel().getSelectedItem().equalsIgnoreCase("Cả năm")) {
					if(txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("phòng")) {
						roomCharYearIgnite2(txt1Year.getSelectionModel().getSelectedItem());
					} else if(txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("dịch vụ")) {
						serviceCharYearIgnite2(txt1Year.getSelectionModel().getSelectedItem());
					} else {
						serviceTypeCharYearIgnite2(txt1Year.getSelectionModel().getSelectedItem());
					}
				} else {
					if(txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("phòng")) {
						roomCharMonthIgnite2(txt1Year.getSelectionModel().getSelectedItem(), Integer.parseInt(txt1Month.getSelectionModel().getSelectedItem()));
					} else if (txt1Choice.getSelectionModel().getSelectedItem().equalsIgnoreCase("dịch vụ")){
						serviceCharMonthIgnite2(txt1Year.getSelectionModel().getSelectedItem(), Integer.parseInt(txt1Month.getSelectionModel().getSelectedItem()));
					} else {
						serviceTypeCharYearIgnite2(txt1Year.getSelectionModel().getSelectedItem(), Integer.parseInt(txt1Month.getSelectionModel().getSelectedItem()));
					}
				}
			}
		});
	}
	
	public void comboboxInit() {
		txt1Month.setItems(FXCollections.observableArrayList("Cả năm","1","2","3","4","5","6","7","8","9","10","11","12"));
		txt1Year.setItems(FXCollections.observableArrayList(new DatPhongCRUD().yearCanStatistic()));
		txt1Choice.setItems(FXCollections.observableArrayList("Phòng","Dịch vụ", "Loại dịch vụ"));
		txt1Year.getSelectionModel().select(0);
		txt1Choice.getSelectionModel().select(0);
		txt1Month.getSelectionModel().select(0);
		txt1Type.setItems(FXCollections.observableArrayList("thống kê doanh thu","Thống kê Số lượng đặt"));
		txt1Type.getSelectionModel().select(0);
	}
	
	public void tblRoomInit(int year, int month) {
		clm1Income.setText("Doanh thu");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new PhongDAO().doanhThuThang(year, month));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblServiceInit(int year, int month) {
		clm1Income.setText("Doanh thu");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new DichVuCRUD().incomeMonthStatistic(year, month));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblRoomInit(int year) {
		clm1Income.setText("Doanh thu");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new PhongDAO().doanhThuNam(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblServiceInit(int year) {
		clm1Income.setText("Doanh thu");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new DichVuCRUD().incomeYearStatistic(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblTypeInit(int year, int month) {
		clm1Income.setText("Doanh thu");
		clm1Name.setVisible(false);
		list = FXCollections.observableArrayList(new DichVuCRUD().typeIncome(year, month));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblTypeInit(int year) {
		clm1Income.setText("Doanh thu");
		clm1Name.setVisible(false);
		list = FXCollections.observableArrayList(new DichVuCRUD().typeIncome(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void serviceCharMonthIgnite(int year, int month) {
		
		chart1.getData().clear();
		XYChart.Series<String, Double> serires = new XYChart.Series<>();
		new DichVuCRUD().incomeMonthStatistic(year, month).forEach(e -> {
			serires.getData().add(new XYChart.Data<String, Double>(e.getName(),e.getIncome()));
		});
		chart1.getData().add(serires);
		
		tblServiceInit(year, month);
	}
	
	public void roomCharMonthIgnite(int year, int month) {
		y1.setLabel("Phòng");
		chart1.getData().clear();
		XYChart.Series<String, Double> serires = new XYChart.Series<>();
		new PhongDAO().doanhThuThang(year, month).forEach(e -> {
			serires.getData().add(new XYChart.Data<String, Double>(e.getName(),e.getIncome()));
		});
		chart1.getData().add(serires);
		
		tblRoomInit(year, month);
	}
 	
	public void serviceCharYearIgnite(int year) {
		
		chart1.getData().clear();
		XYChart.Series<String, Double> serires = new XYChart.Series<>();
		new DichVuCRUD().incomeYearStatistic(year).forEach(e -> {
			serires.getData().add(new XYChart.Data<String, Double>(e.getName(),e.getIncome()));
		});
		chart1.getData().add(serires);
		
		tblServiceInit(year);
	}
	
	public void roomCharYearIgnite(int year) {
		y1.setLabel("Phòng");
		chart1.getData().clear();
		XYChart.Series<String, Double> serires = new XYChart.Series<>();
		new PhongDAO().doanhThuNam(year).forEach(e -> {
			serires.getData().add(new XYChart.Data<String, Double>(e.getName(),e.getIncome()));
		});
		chart1.getData().add(serires);
		
		tblRoomInit(year);
	}
	
	public void serviceTypeCharYearIgnite(int year) {
		
		chart1.getData().clear();
		XYChart.Series<String, Double> serires = new XYChart.Series<>();
		new DichVuCRUD().typeIncome(year).forEach(e -> {
			serires.getData().add(new XYChart.Data<String, Double>(e.getType(),e.getIncome()));
		});
		chart1.getData().add(serires);
		
		tblTypeInit(year);
	}
	
	public void serviceTypeCharYearIgnite(int year, int month) {
		
		chart1.getData().clear();
		XYChart.Series<String, Double> serires = new XYChart.Series<>();
		new DichVuCRUD().typeIncome(year, month).forEach(e -> {
			serires.getData().add(new XYChart.Data<String, Double>(e.getType(),e.getIncome()));
		});
		chart1.getData().add(serires);
		
		tblTypeInit(year, month);
	}
	
	//============================================================ PIE CHART AREA (PANE2) ================================================//
	
	public void tblRoomInit2(int year, int month) {
		clm1Income.setText("Số lần đặt");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new PhongDAO().soLanDat(year, month));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblServiceInit2(int year, int month) {
		clm1Income.setText("Số lần đặt");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new DichVuCRUD().QuantityStatistic(year, month));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblRoomInit2(int year) {
		clm1Income.setText("Số lần đặt");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new PhongDAO().soLanDat(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblServiceInit2(int year) {
		clm1Income.setText("Số lần đặt");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new DichVuCRUD().QuantityStatistic(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblTypeIni2t(int year, int month) {
		clm1Income.setText("Số lượng");
		clm1Name.setVisible(true);
		list = FXCollections.observableArrayList(new DichVuCRUD().typeQuantity(year, month));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblTypeInit2(int year) {
		clm1Income.setText("Số lượng");
		clm1Name.setVisible(false);
		list = FXCollections.observableArrayList(new DichVuCRUD().typeQuantity(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void tblTypeInit2(int year, int month) {
		clm1Income.setText("Số lượng");
		clm1Name.setVisible(false);
		list = FXCollections.observableArrayList(new DichVuCRUD().typeQuantity(year));
		clm1Name.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("name"));
		clm1Type.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("type"));
		clm1Income.setCellValueFactory(new PropertyValueFactory<NameTypeIncome, String>("incomeAF"));
		
		tblView1.setItems(list);
	}
	
	public void serviceCharMonthIgnite2(int year, int month) {
		
		chart2.getData().clear();
		new DichVuCRUD().QuantityStatistic(year, month).forEach(e -> {
			chart2.getData().add(new PieChart.Data(e.getName(),e.getIncome()));
		});
		tblServiceInit2(year, month);
	}
	
	public void roomCharMonthIgnite2(int year, int month) {
		chart2.getData().clear();
		new PhongDAO().soLanDat(year, month).forEach(e -> {
			chart2.getData().add(new PieChart.Data(e.getName(),e.getIncome()));
		});
		tblRoomInit2(year, month);
	}
 	
	public void serviceCharYearIgnite2(int year) {
		
		chart2.getData().clear();
		new DichVuCRUD().QuantityStatistic(year).forEach(e -> {
			chart2.getData().add(new PieChart.Data(e.getName(),e.getIncome()));
		});
		
		tblServiceInit2(year);
	}
	
	public void roomCharYearIgnite2(int year) {
		chart2.getData().clear();
		new PhongDAO().soLanDat(year).forEach(e -> {
			chart2.getData().add(new PieChart.Data(e.getName(),e.getIncome()));
		});
		tblRoomInit2(year);
	}
	
	public void serviceTypeCharYearIgnite2(int year) {
		
		chart2.getData().clear();
		new DichVuCRUD().typeQuantity(year).forEach(e -> {
			chart2.getData().add(new PieChart.Data(e.getType(),e.getIncome()));
		});
		
		tblTypeInit2(year);
	}
	
	public void serviceTypeCharYearIgnite2(int year, int month) {
		
		chart2.getData().clear();
		new DichVuCRUD().typeQuantity(year).forEach(e -> {
			chart2.getData().add(new PieChart.Data(e.getType(),e.getIncome()));
		});
		
		tblTypeInit2(year, month);
	}

}
