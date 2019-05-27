package controller.ui.tempData;

import model.dao.DatPhongCRUD;
import model.dao.KhachHangCRUD;
import model.entities.KhachHang;
import model.entities.ThongTinDatPhong;

public class Index_Service {
	public static KhachHang kh ;
	public static ThongTinDatPhong ttdatphong;
	public static String soPhong ;
	
	public static void insertTemp(String maKH, int madp, String SoPhong) {
		soPhong = SoPhong;
		KhachHangCRUD khCRUD = new KhachHangCRUD();
		kh = khCRUD.layKhachHangId(maKH);
		ttdatphong = new DatPhongCRUD().getThongTinDatPhongId(madp+"");
		
	}
}
