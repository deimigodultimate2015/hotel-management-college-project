package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.DichVuMKII;
import model.helper.StringAndDate;

public class DichVuMKIICRUD {
	public List<DichVuMKII> layDichVuTuMaDP(String madp) {
		Connection conn = DBConnection.createConnection();
		List<DichVuMKII> list = new ArrayList<DichVuMKII>();
		
		try {
			String sql = "select dv.NgayDat, dsdv.TenDV, dsdv.LoaiDV, dsdv.GiaTien, ccdv.SoLuong, ccdv.MaDV, dv.MaPDV from ChiTietDichVu ccdv join DichVu dv on dv.MaPDV = ccdv.MaPDV join DanhSachDichVu dsdv on dsdv.MaDV = ccdv.MaDV where MaDP = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(madp));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DichVuMKII dv = new DichVuMKII();
				dv.setNgayDat(rs.getDate(1));
				dv.setTendv(rs.getString(2));
				dv.setLoaidv(rs.getString(3));
				dv.setGiatien(rs.getDouble(4));
				dv.setSoLuong(rs.getInt(5));
				dv.setMadv(rs.getString(6));
				dv.setMapdv(rs.getInt(7));
				list.add(dv);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public void capNhatSoLuongDichVu(DichVuMKII dvmkII) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "update ChiTietDichVu set SoLuong = ? from ChiTietDichVu ctdv join DichVu dv on dv.MaPDV = ctdv.MaPDV where dv.NgayDat = ? and ctdv.MaDV = ? and dv.MaPDV = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dvmkII.getSoLuong());
			ps.setDate(2, java.sql.Date.valueOf(StringAndDate.DateToLocalDate(dvmkII.getNgayDat())));
			ps.setString(3, dvmkII.getMadv());
			ps.setInt(4, dvmkII.getMapdv());
			System.out.println(dvmkII.toString());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
