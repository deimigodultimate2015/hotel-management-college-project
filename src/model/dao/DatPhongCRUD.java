package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.ThongTinDatPhong;

public class DatPhongCRUD {
	public List<Integer> yearCanStatistic() {
		String sql = "select YEAR(ThongTinDatPhong.NgayDat) from ThongTinDatPhong group by YEAR(ThongTinDatPhong.NgayDat)";
		List<Integer> list = new ArrayList<>();
		Connection conn = DBConnection.createConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getInt(1));
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
	
	public void release(String madp) {
		String sql3 = "update ThongTinDatPhong set DaXuatHoaDon = 1 where MaDP = ?";
		String sql1 = "update ThongTinPhong set TrangThai = 1  from ThongTinPhong ttp join ThongTinDatPhongChiTiet ttdpct on ttdpct.SoPhong = ttp.SoPhong where ttdpct.MaDP = ?";
		String sql2 = "update ThongTinKhachHang set TrangThai = 0  from ThongTinKhachHang ttkh join ThongTinDatPhong ttdp on ttdp.MaKH = ttkh.MaKH where ttdp.MaDP = ?";
		Connection conn = DBConnection.createConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps1.setString(1, madp);
			ps2.setString(1, madp);
			ps3.setString(1, madp);
			ps1.executeUpdate();
			ps2.executeUpdate();
			ps3.executeUpdate();
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
	
	public ThongTinDatPhong getThongTinDatPhongId(String id) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select * from ThongTinDatPhong where MaDP = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(id));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ThongTinDatPhong ttdatphong = new ThongTinDatPhong();
				ttdatphong.setMaDP(rs.getInt(1));
				ttdatphong.setMaKH(rs.getString(2));
				ttdatphong.setNgayDat(rs.getDate(3));
				ttdatphong.setNgayTra(rs.getDate(4));
				ttdatphong.setSoNguoiDiCung(rs.getInt(5));
				ttdatphong.setGhiChu(rs.getString(6));
				return ttdatphong;
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
		
		return null;
	}
	
	public static void main(String[]args) {

	}
}
