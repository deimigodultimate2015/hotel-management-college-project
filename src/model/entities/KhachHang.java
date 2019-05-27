package model.entities;

public class KhachHang {
	private String maKH ;
	private String hoTen;
	private String CMND;
	private String email;
	private String dienThoai;
	private String ghiChu;
	private boolean trangThai;
//	@SuppressWarnings("unused")
//	private String trangThaiReal;
	
	public KhachHang() {
		
	}

	public KhachHang(String maKH, String hoTen, String cMND, String email, String dienThoai, String ghiChu,
			boolean trangThai) {
		super();
		this.maKH = maKH;
		this.hoTen = hoTen;
		CMND = cMND;
		this.email = email;
		this.dienThoai = dienThoai;
		this.ghiChu = ghiChu;
		this.trangThai = trangThai;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getCMND() {
		return CMND;
	}

	public void setCMND(String cMND) {
		CMND = cMND;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	
	public String getTrangThaiReal() {
		return this.trangThai == false ? "Check out" : "Check in";
	}

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", hoTen=" + hoTen + ", CMND=" + CMND + ", email=" + email + ", dienThoai="
				+ dienThoai + ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + "]";
	}
	
	
	
}
