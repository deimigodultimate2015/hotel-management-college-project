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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.DBConnection;
import model.entities.ThongTinDatPhong;
import model.helper.DateHelper;
import model.helper.JdbcHelper;
import model.helper.StringAndDate;

/**
 *
 * @author hoang
 */
public class DatPhongDAO {

	KhachHangCRUD khCRUD = new KhachHangCRUD();
	PhongDAO phongDAO = new PhongDAO();
    public void insert(ThongTinDatPhong model, List<String> soPhong) {
        String sql = "INSERT INTO ThongtinDatPhong (MaKH, NgayDat, NgayTra, SoNguoiDiCung, GhiChu) "
                + "VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                DateHelper.toString(model.getNgayDat(), "yyyy-MM-dd"),
                DateHelper.toString(model.getNgayTra(), "yyyy-MM-dd"),
                model.getSoNguoiDiCung(),
                model.getGhiChu());

        khCRUD.setCheckCustomer(model.getMaKH(), true);
        soPhong.forEach(esx -> {
        	phongDAO.setPhongState(esx, false);
        });
        insertRoom(soPhong, getLatestId());
    }
    
    public void update(ThongTinDatPhong model) {
    	String sql = "UPDATE ThongTinDatPhong SET NgayTra = ?, SoNguoiDiCung = ?, GhiChu = ? where MaDP = ?";
    	Connection conn = DBConnection.createConnection();
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setDate(1, java.sql.Date.valueOf(StringAndDate.DateToLocalDate(model.getNgayTra())));
    		ps.setInt(2, model.getSoNguoiDiCung());
    		ps.setString(3, model.getGhiChu());
    		ps.setInt(4, model.getMaDP());
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

    public void insert(ThongTinDatPhong model) {
        String sql = "INSERT INTO ThongtinDatPhong (MaKH, NgayDat, NgayTra, SoNguoiDiCung, GhiChu) "
                + "VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                DateHelper.toString(model.getNgayDat(), "yyyy-MM-dd"),
                DateHelper.toString(model.getNgayTra(), "yyyy-MM-dd"),
                model.getSoNguoiDiCung(),
                model.getGhiChu());
    }

    public int getLatestId() {
        String sql = "select MaDP from ThongtinDatPhong";
        Connection conn = DBConnection.createConnection();
        List<Integer> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
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

        return Collections.max(list);
    }

    public void insertRoom(List<String> soPhong, int Id) {
        String sql = "insert into ThongTinDatPhongChiTiet values (?,?)";
        Connection conn = DBConnection.createConnection();
        soPhong.forEach(e -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Id);
                ps.setString(2, e);
                ps.executeQuery();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public List<ThongTinDatPhong> select(String sql, Object... args) {
        List<ThongTinDatPhong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ThongTinDatPhong model = readFromResultSet(rs);
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

    public ThongTinDatPhong readFromResultSet(ResultSet rs) throws SQLException {
        ThongTinDatPhong model = new ThongTinDatPhong();
        model.setMaDP(rs.getInt("MaDP"));
        model.setMaKH(rs.getString("MaKH"));
        model.setNgayDat(rs.getDate("NgayDat"));
        model.setNgayTra(rs.getDate("NgayTra"));
        model.setSoNguoiDiCung(rs.getInt("SoNguoiDiCung"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
    
//    public static void main(String[] args) {
//        DatPhongDAO ttdp = new DatPhongDAO();
//        System.out.println(ttdp.getLatestId());
//        List<String> list = new ArrayList<>();
//        list.add("RM01");
//        list.add("RM02");
//        ttdp.insert(new ThongTinDatPhong(1,"CUS00001",new Date(),new Date(),10,"10"),list);
//    }

}
