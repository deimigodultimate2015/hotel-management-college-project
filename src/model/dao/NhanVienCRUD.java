package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.NhanVien;
import model.helper.RandomNumber;

public class NhanVienCRUD {
	
	public NhanVien getNhanVienByEmail(String email) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select * from NhanVien where  Email = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			NhanVien nv = new NhanVien();
			while(rs.next()) {
				nv.setManv(rs.getString(1));
				nv.setMatKhau(rs.getString(2));
				nv.setHoten(rs.getString(3));
				nv.setEmail(rs.getString(4));
				nv.setSodienthoai(rs.getString(5));
				nv.setGioitinh(rs.getBoolean(6));
				nv.setPhanquyen(rs.getBoolean(7));
			}
			
			return nv;
			
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
	
	public String randomGenerateMaNV() {
		List<String> list = new ArrayList<>();
		String thatNumber = RandomNumber.getNumber();
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "select MaNV from NhanVien";
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
		
		return "EMP"+thatNumber;
	}
	
	public void updatePassword(NhanVien nv) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "update NhanVien set MatKhau = ? where MaNV = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nv.getMatKhau());
			ps.setString(2, nv.getManv());
			ps.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception exs) {
				exs.printStackTrace();
			}
		}
	}
}
