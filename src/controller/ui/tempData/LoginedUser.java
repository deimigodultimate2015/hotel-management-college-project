package controller.ui.tempData;

import model.dao.NhanVienDAO;
import model.entities.NhanVien;

public class LoginedUser {
	public static NhanVien log ;
	public static void insert(String manv) {
		log = new NhanVienDAO().findById(manv);
	}
}
