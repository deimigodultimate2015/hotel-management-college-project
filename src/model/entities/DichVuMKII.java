package model.entities;

import java.text.DecimalFormat;
import java.util.Date;

public class DichVuMKII {
	private Date ngayDat;
	private String madv;
    private String tendv;
    private String loaidv;
    private double giatien;
    private int soLuong = 1;
    private int mapdv;
    
    public DichVuMKII() {
    	
    }
    
    public DichVuMKII(Date ngayDat, String madv, String tendv, String loaidv, double giatien, int soLuong, int mapdv) {
		super();
		this.ngayDat = ngayDat;
		this.madv = madv;
		this.tendv = tendv;
		this.loaidv = loaidv;
		this.giatien = giatien;
		this.soLuong = soLuong;
		this.mapdv = mapdv;
	}

    public int getMapdv() {
		return mapdv;
	}

	public void setMapdv(int mapdv) {
		this.mapdv = mapdv;
	}

	public Date getNgayDat() {
		return ngayDat;
	}
	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
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
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public String getGiatienAF() {
    	DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
    	return df.format(this.giatien);
    }

	@Override
	public String toString() {
		return "DichVuMKII [ngayDat=" + ngayDat + ", madv=" + madv + ", tendv=" + tendv + ", loaidv=" + loaidv
				+ ", giatien=" + giatien + ", soLuong=" + soLuong + ", mapdv=" + mapdv + "]";
	}
	
	
    
}
