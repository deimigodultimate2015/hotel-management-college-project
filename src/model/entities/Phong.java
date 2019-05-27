/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.text.DecimalFormat;

/**
 *
 * @author hoang
 */
public class Phong {

    private String soPhong;
    private String loaiPhong;
    private String ghiChu;
    private double giaphong;
    private boolean trangThai;

    public Phong() {
    }

    public Phong(String soPhong, String loaiPhong, String ghiChu, double giaphong, boolean trangThai) {
        this.soPhong = soPhong;
        this.loaiPhong = loaiPhong;
        this.ghiChu = ghiChu;
        this.giaphong = giaphong;
        this.trangThai = trangThai;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public double getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(double giaphong) {
        this.giaphong = giaphong;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    public String getGiaPhongAF() {
    	DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
		return df.format(giaphong);
    }

}
