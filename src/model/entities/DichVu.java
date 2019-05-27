/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.text.DecimalFormat;

/**
 *
 * @author ASUS
 */
public class DichVu 
{
    private String madv;
    private String tendv;
    private String loaidv;
    private double giatien;
    private int soLuong = 1;
    
    public DichVu()
    {
    	
    }
    
    public DichVu (String madv, String tendv, String loaidv, double giatien)
    {
        this.madv = madv;
        this.tendv = tendv;
        this.loaidv = loaidv;
        this.giatien = giatien;
    }
    
    

    public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getMadv() {
        return madv;
    }

    public void setMadv(String madv) {
        this.madv = madv;
    }

    public String getTendv() {
        return tendv;
    }

    public void setTendv(String tendv) {
        this.tendv = tendv;
    }

    public String getLoaidv() {
        return loaidv;
    }

    public void setLoaidv(String loaidv) {
        this.loaidv = loaidv;
    }

    public double getGiatien() {
        return giatien;
    }

    public void setGiatien(double giatien) {
        this.giatien = giatien;
    }
    
    
    
    @Override
	public String toString() {
		return "DichVu [madv=" + madv + ", tendv=" + tendv + ", loaidv=" + loaidv + ", giatien=" + giatien
				+ ", soLuong=" + soLuong + "]";
	}

	public String getGiatienAF() {
    	DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
    	return df.format(this.giatien);
    }
    
}
