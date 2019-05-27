/*
  * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.Date;


/**
 *
 * @author hoang
 */
public class ThongTinDatPhong {
    private int maDP;
    private String maKH;
    private Date ngayDat;
    private Date ngayTra;
    private int soNguoiDiCung;
    private String ghiChu;
    private boolean hoaDon;

    public ThongTinDatPhong() {
    	
    }
    

	public ThongTinDatPhong(int maDP, String maKH, Date ngayDat, Date ngayTra, int soNguoiDiCung, String ghiChu,
			boolean hoaDon) {
		super();
		this.maDP = maDP;
		this.maKH = maKH;
		this.ngayDat = ngayDat;
		this.ngayTra = ngayTra;
		this.soNguoiDiCung = soNguoiDiCung;
		this.ghiChu = ghiChu;
		this.hoaDon = hoaDon;
	}


	

	public boolean getHoaDon() {
		return hoaDon;
	}


	public void setHoaDon(boolean hoaDon) {
		this.hoaDon = hoaDon;
	}


	public int getMaDP() {
		return maDP;
	}

	public void setMaDP(int maDP) {
		this.maDP = maDP;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public Date getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}

	public Date getNgayTra() {
		return ngayTra;
	}

	public void setNgayTra(Date ngayTra) {
		this.ngayTra = ngayTra;
	}

	public int getSoNguoiDiCung() {
		return soNguoiDiCung;
	}

	public void setSoNguoiDiCung(int soNguoiDiCung) {
		this.soNguoiDiCung = soNguoiDiCung;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	public String getHoaDonAF() {
		return hoaDon?"Đã xuất hóa đơn":"Chưa xuất hóa đơn";
	}

}
