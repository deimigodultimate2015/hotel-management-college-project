package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.InvoiceEntities;
import model.helper.RandomNumber;

public class HoaDonCRUD {
	public int roomQuantity(String madp) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select count(SoPhong) from ThongTinDatPhongChiTiet where MaDP = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(madp));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public String randomGenerateMaHD() {
		List<String> list = new ArrayList<>();
		String thatNumber = RandomNumber.getNumber();
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select MaHD from HoaDon";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString(1).substring(3));
			}
			
			for(int i = 0; i < list.size(); i++) {
				if(thatNumber.equals(list.get(i))) {
					thatNumber = RandomNumber.getNumber();
					i = 0;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "INV"+thatNumber;
	}
	
	public List<InvoiceEntities> getDichVu(String maDP) {
		String sql = "select dv.NgayDat, dsdv.*, ctdv.SoLuong from ChiTietDichVu ctdv join DichVu dv on dv.MaPDV = ctdv.MaPDV join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV join ThongTinDatPhong ttdp on ttdp.MaDP = dv.MaDP where ttdp.MaDP = ?";
		Connection conn = DBConnection.createConnection();
		List<InvoiceEntities> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(maDP));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				InvoiceEntities ie = new InvoiceEntities();
				ie.setNgayDat(rs.getDate(1));
				ie.setMaDV(rs.getString(2));
				ie.setTenDV(rs.getString(3));
				ie.setLoaiDV(rs.getString(4));
				ie.setGiaTien(rs.getDouble(5));
				ie.setSoLuong(rs.getInt(6));
				list.add(ie);
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
	
	public static void main(String[]args) {
		System.out.println(new HoaDonCRUD().randomGenerateMaHD());
	}
}
