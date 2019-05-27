package model.entities;

import java.text.DecimalFormat;
import java.util.Date;

public class InvoiceEntities {
	private Date ngayDat;
	private String maDV;
	private String tenDV;
	private String loaiDV;
	private double giaTien;
	private int soLuong;
	
	public InvoiceEntities() {
		
	}
	
	public InvoiceEntities(Date ngayDat, String maDV, String tenDV, String loaiDV, double giaTien, int soLuong) {
		super();
		this.ngayDat = ngayDat;
		this.maDV = maDV;
		this.tenDV = tenDV;
		this.loaiDV = loaiDV;
		this.giaTien = giaTien;
		this.soLuong = soLuong;
	}

	public Date getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}

	public String getMaDV() {
		return maDV;
	}

	public void setMaDV(String maDV) {
		this.maDV = maDV;
	}

	public String getTenDV() {
		return tenDV;
	}

	public void setTenDV(String tenDV) {
		this.tenDV = tenDV;
	}

	public String getLoaiDV() {
		return loaiDV;
	}

	public void setLoaiDV(String loaiDV) {
		this.loaiDV = loaiDV;
	}

	public double getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(double giaTien) {
		this.giaTien = giaTien;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public String getGiaTienAF() {
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
    	return df.format(this.giaTien);
	}
}
