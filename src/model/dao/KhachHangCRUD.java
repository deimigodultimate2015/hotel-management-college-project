package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.KhachHang;
import model.helper.RandomNumber;

public class KhachHangCRUD {
	
    public void setCheckCustomer(String id, boolean state) {
    	Connection conn = DBConnection.createConnection();
    	try {
    		String sql = "update ThongTinKhachHang set TrangThai = ? where MaKH = ?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setBoolean(1, state);
    		ps.setString(2, id);
    		ps.executeUpdate();
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
	
	public void themKhachHang(KhachHang kh) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "insert into ThongTinKhachHang values (?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, kh.getMaKH());
			ps.setString(2, kh.getHoTen());
			ps.setString(3, kh.getCMND());
			ps.setString(4, kh.getEmail());
			ps.setString(5, kh.getDienThoai());
			ps.setString(6, kh.getGhiChu());
			ps.setBoolean(7, kh.isTrangThai());
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
	
	public void xoaKhachHang(String id) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "delete from ThongTinKhachHang where MaKH = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
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
	
	public KhachHang layKhachHangId(String id) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select * from ThongTinKhachHang where MaKH = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			KhachHang kh = new KhachHang();
			while(rs.next()) {
				kh.setMaKH(rs.getString(1));
				kh.setHoTen(rs.getString(2));
				kh.setCMND(rs.getString(3));
				kh.setEmail(rs.getString(4));
				kh.setDienThoai(rs.getString(5));
				kh.setGhiChu(rs.getString(6));
				kh.setTrangThai(rs.getBoolean(7));
			}
			
			return kh;
		} catch(Exception ex) {
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
	
	public List<KhachHang> layKhachHangs() {
		Connection conn = DBConnection.createConnection();
		List<KhachHang> list = new ArrayList<KhachHang>();
		try {
			String sql = "select * from ThongTinKhachHang";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				KhachHang kh = new KhachHang();
				kh.setMaKH(rs.getString(1));
				kh.setHoTen(rs.getString(2));
				kh.setCMND(rs.getString(3));
				kh.setEmail(rs.getString(4));
				kh.setDienThoai(rs.getString(5));
				kh.setGhiChu(rs.getString(6));
				kh.setTrangThai(rs.getBoolean(7));
				list.add(kh);
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
		
		return list;
	}
	
	public void capNhatKhachHang(KhachHang kh) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "update ThongTinKhachHang set HoTen = ?, Email = ?, SoDienThoai = ?, CMND = ?, GhiChu = ?, TrangThai = ? where MaKH = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, kh.getHoTen());
			ps.setString(2, kh.getEmail());
			ps.setString(3, kh.getDienThoai());
			ps.setString(4, kh.getCMND());
			ps.setString(5, kh.getGhiChu());
			ps.setBoolean(6, kh.isTrangThai());
			ps.setString(7, kh.getMaKH());
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
	
	public String randomGenerateMaKH() {
		List<String> list = new ArrayList<>();
		String thatNumber = RandomNumber.getNumber();
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select MaKH from ThongTinKhachHang";
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
		
		return "CUS"+thatNumber;
	}
	
	public static void main(String[]args) {
		KhachHangCRUD khCRUD = new KhachHangCRUD();
//		KhachHang kh = new KhachHang("CUS00001","Thai Xuan Tho","123456789","tho@gmail.com","01664427840","Khach hang da tung dung nhieu lan",false);
//		khCRUD.themKhachHang(kh);
//		khCRUD.capNhatKhachHang(kh);
//		khCRUD.xoaKhachHang("CUS00001");
//		khCRUD.layKhachHangs().forEach(System.out::println);
//		System.out.println(khCRUD.randomGenerateMaKH());
		System.out.println(khCRUD.layKhachHangId("CUS0004"));
	}
}
