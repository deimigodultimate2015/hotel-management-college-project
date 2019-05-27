package controller.ui.tempData;

import model.dao.DatPhongCRUD;
import model.dao.KhachHangCRUD;
import model.entities.KhachHang;
import model.entities.ThongTinDatPhong;

public class Index_Invoice {
	public static KhachHang kh;
	public static ThongTinDatPhong ttdatphong;
	
	public static void transferData(String maDP) {
		ttdatphong = new DatPhongCRUD().getThongTinDatPhongId(maDP);
		kh = new KhachHangCRUD().layKhachHangId(ttdatphong.getMaKH());
	}
}
