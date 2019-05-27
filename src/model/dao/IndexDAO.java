package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.IndexEntities;

public class IndexDAO {
	public List<IndexEntities> SearchByDate(LocalDate beginDate, LocalDate endDate)  {
		Connection conn = DBConnection.createConnection();
		List<IndexEntities> list = new ArrayList<IndexEntities>();
		try {
			String sql = "select ttdp.MaKH, ttp.SoPhong, ttp.LoaiPhong, ttdp.NgayDat, ttdp.NgayTra, ttdp.MaDP, ttdp.DaXuatHoaDon from ThongTinPhong ttp join ThongTinDatPhongChiTiet ttdpct on ttdpct.SoPhong = ttp.SoPhong join ThongTinDatPhong ttdp on ttdp.MaDP = ttdpct.MaDP where not (ttdp.NgayDat > ? and ttdp.NgayDat > ?)  and not (ttdp.NgayTra > ? and ttdp.NgayTra > ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, java.sql.Date.valueOf(beginDate));
			ps.setDate(2, java.sql.Date.valueOf(endDate));
			ps.setDate(3, java.sql.Date.valueOf(beginDate));
			ps.setDate(4, java.sql.Date.valueOf(endDate));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				IndexEntities ie = new IndexEntities();
				ie.setMaKH(rs.getString(1));
				ie.setRoomName(rs.getString(2));
				ie.setRoomType(rs.getString(3));
				ie.setCIn(rs.getDate(4));
				ie.setCOut(rs.getDate(5));
				ie.setMaDP(rs.getInt(6));
				ie.setInvoice(rs.getBoolean(7));
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
	
	public List<IndexEntities> getAllRealOn()  {
		Connection conn = DBConnection.createConnection();
		List<IndexEntities> list = new ArrayList<IndexEntities>();
		try {
			String sql = "select ttdp.MaKH, ttp.SoPhong, ttp.LoaiPhong, ttdp.NgayDat, ttdp.NgayTra, ttdp.MaDP, ttdp.DaXuatHoaDon from ThongTinPhong ttp join ThongTinDatPhongChiTiet ttdpct on ttdpct.SoPhong = ttp.SoPhong join ThongTinDatPhong ttdp on ttdp.MaDP = ttdpct.MaDP";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				IndexEntities ie = new IndexEntities();
				ie.setMaKH(rs.getString(1));
				ie.setRoomName(rs.getString(2));
				ie.setRoomType(rs.getString(3));
				ie.setCIn(rs.getDate(4));
				ie.setCOut(rs.getDate(5));
				ie.setMaDP(rs.getInt(6));
				ie.setInvoice(rs.getBoolean(7));
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
	
	public List<IndexEntities> getAll()  {
		Connection conn = DBConnection.createConnection();
		List<IndexEntities> list = new ArrayList<IndexEntities>();
		try {
			String sql = "select ttdp.MaKH, ttp.SoPhong, ttp.LoaiPhong, ttdp.NgayDat, ttdp.NgayTra, ttdp.MaDP from ThongTinPhong ttp join ThongTinDatPhongChiTiet ttdpct on ttdpct.SoPhong = ttp.SoPhong join ThongTinDatPhong ttdp on ttdp.MaDP = ttdpct.MaDP where ttdp.NgayTra >= ? and ttdp.DaXuatHoaDon = 0";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				IndexEntities ie = new IndexEntities();
				ie.setMaKH(rs.getString(1));
				ie.setRoomName(rs.getString(2));
				ie.setRoomType(rs.getString(3));
				ie.setCIn(rs.getDate(4));
				ie.setCOut(rs.getDate(5));
				ie.setMaDP(rs.getInt(6));
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
		new IndexDAO().getAll().forEach(System.out::println);
		
		IndexDAO idx = new IndexDAO();
		for(IndexEntities esx : idx.getAll()) {
			System.out.println(esx.toString());
		}
		
	}
}
