package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.DBConnection;
import model.entities.DichVu;
import model.entities.statistic.NameTypeIncome;

public class DichVuCRUD {
	public List<NameTypeIncome> incomeYearStatistic(int year) {
		String sql = "select dsdv.TenDV,dsdv.LoaiDV, dsdv.GiaTien*sum(ctdv.SoLuong) from  DichVu dv join ChiTietDichVu ctdv on ctdv.MaPDV = dv.MaPDV join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV where YEAR(dv.NgayDat) = ? group by dsdv.LoaiDV, dsdv.GiaTien, dsdv.TenDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setName(rs.getString(1));
				dv.setType(rs.getString(2));
				dv.setIncome(rs.getDouble(3));
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
	
	public List<NameTypeIncome> incomeMonthStatistic(int year, int month) {
		String sql = "select dsdv.TenDV,dsdv.LoaiDV, dsdv.GiaTien*sum(ctdv.SoLuong) from  DichVu dv join ChiTietDichVu ctdv on ctdv.MaPDV = dv.MaPDV join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV where YEAR(dv.NgayDat) = ? and MONTH(dv.NgayDat) = ? group by dsdv.LoaiDV, dsdv.GiaTien, dsdv.TenDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setName(rs.getString(1));
				dv.setType(rs.getString(2));
				dv.setIncome(rs.getDouble(3));
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
	
	public List<NameTypeIncome> typeQuantity(int year) {
		String sql = "select dsdv.LoaiDV, count(ctdv.SoLuong) from ChiTietDichVu ctdv join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV join DichVu dv on dv.MaPDV = ctdv.MaPDV where YEAR(dv.NgayDat) = ? group by dsdv.LoaiDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setType(rs.getString(1));
				dv.setIncome(rs.getDouble(2));
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
	
	public List<NameTypeIncome> typeQuantity(int year, int month) {
		String sql = "select dsdv.LoaiDV, count(ctdv.SoLuong) from ChiTietDichVu ctdv join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV join DichVu dv on dv.MaPDV = ctdv.MaPDV where YEAR(dv.NgayDat) = ? AND MONTH(dv.NgayDat) = ? group by dsdv.LoaiDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setType(rs.getString(1));
				dv.setIncome(rs.getDouble(2));
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
	
	public List<NameTypeIncome> typeIncome(int year) {
		String sql = "select dsdv.LoaiDV, sum(dsdv.GiaTien*ctdv.SoLuong) from ChiTietDichVu ctdv join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV join DichVu dv on dv.MaPDV = ctdv.MaPDV where YEAR(dv.NgayDat) = ? group by dsdv.LoaiDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setType(rs.getString(1));
				dv.setIncome(rs.getDouble(2));
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
	
	public List<NameTypeIncome> typeIncome(int year, int month) {
		String sql = "select dsdv.LoaiDV, sum(dsdv.GiaTien*ctdv.SoLuong) from ChiTietDichVu ctdv join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV join DichVu dv on dv.MaPDV = ctdv.MaPDV where YEAR(dv.NgayDat) = ? AND MONTH(dv.NgayDat) = ? group by dsdv.LoaiDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setType(rs.getString(1));
				dv.setIncome(rs.getDouble(2));
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
	
	public List<NameTypeIncome> QuantityStatistic(int year) {
		String sql = "select dsdv.TenDV,dsdv.LoaiDV, sum(ctdv.SoLuong) from  DichVu dv join ChiTietDichVu ctdv on ctdv.MaPDV = dv.MaPDV join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV where YEAR(dv.NgayDat) = ? group by dsdv.LoaiDV, dsdv.GiaTien, dsdv.TenDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setName(rs.getString(1));
				dv.setType(rs.getString(2));
				dv.setIncome(rs.getInt(3));
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
	
	public List<NameTypeIncome> QuantityStatistic(int year, int month) {
		String sql = "select dsdv.TenDV,dsdv.LoaiDV, dsdv.GiaTien*sum(ctdv.SoLuong) from  DichVu dv join ChiTietDichVu ctdv on ctdv.MaPDV = dv.MaPDV join DanhSachDichVu dsdv on dsdv.MaDV = ctdv.MaDV where YEAR(dv.NgayDat) = ? and MONTH(dv.NgayDat) = ? group by dsdv.LoaiDV, dsdv.GiaTien, dsdv.TenDV";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome dv = new NameTypeIncome();
				dv.setName(rs.getString(1));
				dv.setType(rs.getString(2));
				dv.setIncome(rs.getInt(3));
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
	
	public void datDichVu(String maDP, LocalDate ngayDat, List<DichVu> list) {
		String sql = "insert into DichVu(MaDP, NgayDat) values (?,?)";
		Connection conn = DBConnection.createConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, maDP);
			ps.setDate(2, java.sql.Date.valueOf(ngayDat));
			ps.executeUpdate();
			int index = getLatestId();
			themDichVuChiChit(list, index+"");
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
	
	public void themDichVuChiChit(List<DichVu> list, String maPDV) {
		Connection conn = DBConnection.createConnection();
		String sql = "insert into ChiTietDichVu values (?,?,?)";
		list.forEach(e -> {
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(maPDV));
				ps.setString(2, e.getMadv());
				ps.setInt(3, e.getSoLuong());
				ps.executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		try {
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public int getLatestId() {
		Connection conn = DBConnection.createConnection();
		List<Integer> list = new ArrayList<>();
		try {
			String sql = "select MaPDV from DichVu";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getInt(1));
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
		
		return Collections.max(list);
		
	}
}
