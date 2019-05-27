package controller.ui.tempData;

import model.dao.DatPhongCRUD;
import model.dao.KhachHangCRUD;
import model.entities.KhachHang;
import model.entities.ThongTinDatPhong;

public class QuanLiIndex_HoaDonChiTiet {
	public static ThongTinDatPhong ttdatphong;
	public static KhachHang khachhang;
	
	public static void inferx(String madp, String makh) {
		ttdatphong = new DatPhongCRUD().getThongTinDatPhongId(madp);
		khachhang = new KhachHangCRUD().layKhachHangId(makh);
	}
}
