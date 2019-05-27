/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.entities.Phong;
import model.entities.statistic.NameTypeIncome;
import model.helper.JdbcHelper;

/**
 *
 * @author hoang
 */
public class PhongDAO {
	public List<NameTypeIncome> doanhThuNam(int year) {
		String sql = "select ttdpct.SoPhong, ttp.LoaiPhong, sum(GiaPhong) from ThongTinDatPhong ttdp join ThongTinDatPhongChiTiet ttdpct on ttdpct.MaDP = ttdp.MaDP join ThongTinPhong ttp on ttp.SoPhong = ttdpct.SoPhong where YEAR(ttdp.NgayDat) = ? group by ttdpct.SoPhong, ttp.LoaiPhong";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome phong = new NameTypeIncome();
				phong.setName(rs.getString(1));
				phong.setType(rs.getString(2));
				phong.setIncome(rs.getDouble(3));
				list.add(phong);
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
	
	public List<NameTypeIncome> doanhThuThang(int year, int month) {
		String sql = "select ttdpct.SoPhong, ttp.LoaiPhong, sum(GiaPhong) from ThongTinDatPhong ttdp join ThongTinDatPhongChiTiet ttdpct on ttdpct.MaDP = ttdp.MaDP join ThongTinPhong ttp on ttp.SoPhong = ttdpct.SoPhong where YEAR(ttdp.NgayDat) = ? and MONTH(ttdp.NgayDat) = ? group by ttdpct.SoPhong, ttp.LoaiPhong";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome phong = new NameTypeIncome();
				phong.setName(rs.getString(1));
				phong.setType(rs.getString(2));
				phong.setIncome(rs.getDouble(3));
				list.add(phong);
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
	
	public List<NameTypeIncome> soLanDat(int year) {
		String sql = "select ttdpct.SoPhong, ttp.LoaiPhong, count(ttdpct.SoPhong) from ThongTinDatPhong ttdp join ThongTinDatPhongChiTiet ttdpct on ttdpct.MaDP = ttdp.MaDP join ThongTinPhong ttp on ttp.SoPhong = ttdpct.SoPhong where YEAR(ttdp.NgayDat) = ? group by ttdpct.SoPhong, ttp.LoaiPhong";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome phong = new NameTypeIncome();
				phong.setName(rs.getString(1));
				phong.setType(rs.getString(2));
				phong.setIncome(rs.getDouble(3));
				list.add(phong);
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
	
	public List<NameTypeIncome> soLanDat(int year, int month) {
		String sql = "select ttdpct.SoPhong, ttp.LoaiPhong, count(ttdpct.SoPhong) from ThongTinDatPhong ttdp join ThongTinDatPhongChiTiet ttdpct on ttdpct.MaDP = ttdp.MaDP join ThongTinPhong ttp on ttp.SoPhong = ttdpct.SoPhong where YEAR(ttdp.NgayDat) = ? and MONTH(ttdp.NgayDat) = ? group by ttdpct.SoPhong, ttp.LoaiPhong";
		Connection conn = DBConnection.createConnection();
		List<NameTypeIncome> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NameTypeIncome phong = new NameTypeIncome();
				phong.setName(rs.getString(1));
				phong.setType(rs.getString(2));
				phong.setIncome(rs.getDouble(3));
				list.add(phong);
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
	
	public List<Phong> layDanhSachPhongMaDP(String maDP) {
		String sql = "select ttp.* from ThongTinPhong ttp join ThongTinDatPhongChiTiet ttdp on ttdp.SoPhong = ttp.SoPhong where ttdp.MaDP = ?";
		Connection conn = DBConnection.createConnection();
		List<Phong> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(maDP));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Phong phong = new Phong();
				phong.setSoPhong(rs.getString(1));
				phong.setLoaiPhong(rs.getString(2));
				phong.setGhiChu(rs.getString(3));
				phong.setGiaphong(rs.getDouble(4));
				phong.setTrangThai(rs.getBoolean(5));
				list.add(phong);
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
	
	public void setPhongState(String id, boolean state) {
		Connection conn = DBConnection.createConnection();
		try {
			String sql = "update ThongTinPhong set TrangThai = ? where SoPhong = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, state);
			ps.setString(2, id);
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

    public List<Phong> select() {
        String sql = "SELECT * FROM ThongTinPhong";
        return select(sql);
    }

    public List<Phong> selectBySTT() {
        String sql = "SELECT * FROM ThongTinPhong WHERE TrangThai=1";
        return select(sql);
    }

    public List<Phong> selectBySTF() {
        String sql = "SELECT * FROM ThongTinPhong WHERE TrangThai=0";
        return select(sql);
    }

    public Phong findById(String sophong) {
        String sql = "SELECT * FROM ThongTinPhong WHERE SoPhong=?";
        List<Phong> list = select(sql, sophong);
        return list.size() > 0 ? list.get(0) : null;
    }

    public void update(Phong model) {
        String sql = "UPDATE ThongTinPhong SET LoaiPhong=?, GhiChu=?, GiaPhong=?, TrangThai=? WHERE SoPhong=?";
        JdbcHelper.executeUpdate(sql,
                model.getLoaiPhong(),
                model.getGhiChu(),
                model.getGiaphong(),
                model.getTrangThai(),
                model.getSoPhong());
    }

    public List<Phong> select(String sql, Object... args) {
        List<Phong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Phong model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Phong readFromResultSet(ResultSet rs) throws SQLException {
        Phong model = new Phong();
        model.setSoPhong(rs.getString("SoPhong"));
        model.setLoaiPhong(rs.getString("LoaiPhong"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setGiaphong(rs.getDouble("GiaPhong"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        return model;
    }
    
    public static void main(String[]args) {

    }

}
