/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

/**
 *
 * @author Nhat
 */
public class NhanVien {
    private String Manv;
    private String MatKhau;
    private String Hoten;
    private String Email;
    private String Sodienthoai;
    private boolean Gioitinh;
    private boolean Phanquyen;

    public NhanVien() {
    }

    public NhanVien(String Manv, String MatKhau, String Hoten, String Email, String Sodienthoai, boolean Gioitinh,boolean Phanquyen) {
        this.Manv = Manv;
        this.MatKhau = MatKhau;
        this.Hoten = Hoten;
        this.Email = Email;
        this.Sodienthoai = Sodienthoai;
        this.Gioitinh = Gioitinh;
        this.Phanquyen = Phanquyen;
    }

    public boolean getPhanquyen() {
        return Phanquyen;
    }

    public void setPhanquyen(boolean Phanquyen) {
        this.Phanquyen = Phanquyen;
    }

    public String getManv() {
        return Manv;
    }

    public void setManv(String Manv) {
        this.Manv = Manv;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String Hoten) {
        this.Hoten = Hoten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String Sodienthoai) {
        this.Sodienthoai = Sodienthoai;
    }

    public boolean getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(boolean Gioitinh) {
        this.Gioitinh = Gioitinh;
    }
    
    public String getPhanquyenSTR() {
    	return Phanquyen ? "Quản lí":"Nhân viên";
    }
    
    
    
}
