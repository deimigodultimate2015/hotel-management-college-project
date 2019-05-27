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

import model.entities.NhanVien;
import model.helper.JdbcHelper;

/**
 *
 * @author Nhat
 */
public class NhanVienDAO {
    public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien (MaNV, MatKhau, HoTen, Email, SoDienThoai, GioiTinh, PhanQuyen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getManv(),
                model.getMatKhau(),
                model.getHoten(),
                model.getEmail(),
                model.getSodienthoai(),
                model.getGioitinh(),
                model.getPhanquyen());
    }
    
    public void update(NhanVien model){
        String sql="UPDATE NhanVien SET MatKhau=?, HoTen=?, Email=?, SoDienThoai=?, GioiTinh=?, PhanQuyen=? WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql,                
                model.getMatKhau(),
                model.getHoten(),
                model.getEmail(),
                model.getSodienthoai(),
                model.getGioitinh(),
                model.getPhanquyen(),
                model.getManv());
    }
    
    
    public List<NhanVien> select(){
        String sql="SELECT * FROM NhanVien";
        return select(sql);
    } 
    
    private List<NhanVien> select(String sql, Object...args){
        List<NhanVien> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    NhanVien model=readFromResultSet(rs);
                    list.add(model);
                }
            } 
            finally{
                rs.getStatement().getConnection().close();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    private NhanVien readFromResultSet(ResultSet rs) throws SQLException{
        NhanVien model=new NhanVien();
        model.setManv(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoten(rs.getString("HoTen"));
        model.setEmail(rs.getString("Email"));
        model.setSodienthoai(rs.getString("SoDienThoai"));
        model.setGioitinh(rs.getBoolean("GioiTinh"));
        model.setPhanquyen(rs.getBoolean("PhanQuyen"));
        return model;
    }
    
     public NhanVien findById(String manv){
        String sql="SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }   
    
     public static void main(String[]args) {
    	 new NhanVienDAO().insert(new NhanVien("EMP5555","orga000000","Thái Xuân Thọ","thotx1999@gmail.com","0364427840",false,true));
    	 new NhanVienDAO().insert(new NhanVien("EMP0000","orga000000","Demonic Satan","deimigodultimate2015@gmail.com","0364427840",false,false));
     }
}
