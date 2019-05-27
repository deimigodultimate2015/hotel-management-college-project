package model.dao;

import model.entities.NhanVien;

public class DangNhap {
	public String logined(String username, String password) {
		NhanVienDAO nv = new NhanVienDAO();
		NhanVien nvr = nv.findById(username);
		if(nvr != null) {
			if(nvr.getMatKhau().equals(password)) {
				return nvr.getPhanquyen()+"";
			} else {
				return "non_access";
			}
		} else {
			return "non_access";
		}
	}
	
	public static NhanVien nhanvien;
	
	public static void main(String[]args) {
		DangNhap dangPhap = new DangNhap();
		System.out.println(dangPhap.logined("EMP00002", "orga000000"));
	}
}
