/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.entities.DichVu;
import model.helper.JdbcHelper;

/**
 *
 * @author ASUS
 */
public class DichVuDAO 
{
    public void insert(DichVu model) {
        String sql = "INSERT INTO DanhSachDichVu(MaDV, TenDV, LoaiDV, GiaTien) VALUES(?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMadv(),
                model.getTendv(),
                model.getLoaidv(),
                model.getGiatien());
    }
    
    public void update(DichVu model) {
        String sql = "UPDATE DanhSachDichVu SET TenDV=?, LoaiDV=?, GiaTien=? WHERE MaDV=?";
        JdbcHelper.executeUpdate(sql,
                model.getTendv(),
                model.getLoaidv(),
                model.getGiatien(),
                model.getMadv());
    }
    
    public void delete(String MaDV) {
        String sql = "DELETE FROM DanhSachDichVu WHERE MaDV=?";
        JdbcHelper.executeUpdate(sql, MaDV);
    }
    
    public List<DichVu> select() {
        String sql = "SELECT * FROM DanhSachDichVu";
        return select(sql);
    }
    private List<DichVu> select(String sql, Object... args) {
        List<DichVu> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DichVu model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    public DichVu findById(String madv) {
        String sql = "SELECT * FROM DanhSachDichVu WHERE MaDV=?";
        List<DichVu> list = select(sql, madv);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    private DichVu readFromResultSet(ResultSet rs) throws SQLException {
        DichVu model = new DichVu();
        model.setMadv(rs.getString("MaDV"));
        model.setTendv(rs.getString("TenDV"));
        model.setLoaidv(rs.getString("LoaiDV"));
        model.setGiatien(rs.getDouble("GiaTien"));
        return model;
    }
    
    public static void main(String[] args) {
    	Random rnd = new Random();
        for(int i = 0; i < 20; i++) {
        	new DichVuDAO().insert(new DichVu("SER000"+i,"Dich Vu "+i,"Loai "+i,rnd.nextInt(999999)));
        }
    }
}
