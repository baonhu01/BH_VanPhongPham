/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.NhanVien;

/**
 *
 * @author Bao Nhu
 */
public class NhanVienDAO {
    public static ArrayList<NhanVien>layDanhSachNhanVien(){
        ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
       
        try {
            String sql = "Select * from nhanvien";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                NhanVien nv = new NhanVien();
                nv.setMaNV(resultSet.getString("manv"));
                nv.setTenNV(resultSet.getString("tennv"));
                nv.setNgSinh(resultSet.getString("ngsinh"));
                nv.setgTinh(resultSet.getString("gtinh"));
                nv.setMaCV(resultSet.getString("macv"));
                nv.setMaTK(resultSet.getString("matk"));
                dsnv.add(nv);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsnv;
    }
    
    public static boolean themNhanVien(NhanVien nv){
        boolean kq = false;
        String sql = String.format("insert into nhanvien(manv, tennv, ngsinh, gtinh, macv, matk) values('%s', N'%s', '%s', N'%s', '%s', '%s');", nv.getMaNV(), nv.getTenNV(), nv.getNgSinh(), nv.getgTinh(), nv.getMaCV(), nv.getMaTK());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
 
    public static boolean xoaNhanVien(String manv){
        boolean kq = false;
        String sql = String.format("delete from nhanvien where manv='%s'", manv);
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static ArrayList<NhanVien>timKiemNhanVienTheoMa(String manv){
        ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
        try {
            String sql = "Select * from nhanvien where manv like '%" + manv + " %'";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                NhanVien nv = new NhanVien();
                nv.setMaNV(resultSet.getString("manv"));
                nv.setTenNV(resultSet.getString("tennv"));
                nv.setNgSinh(resultSet.getString("ngsinh"));
                nv.setgTinh(resultSet.getString("gtinh"));
                nv.setMaCV(resultSet.getString("macv"));
                nv.setMaTK(resultSet.getString("matk"));  
                dsnv.add(nv);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsnv;
    }
    
    public static boolean capNhatNhanVien(NhanVien nv){
        boolean kq = false;
        String sql = String.format("update nhanvien set tennv = N'%s', ngsinh = '%s', gtinh = N'%s', macv = '%s', matk = '%s' " + "where manv= '%s'", nv.getTenNV(), nv.getNgSinh(), nv.getgTinh(), nv.getMaCV(), nv.getMaTK(), nv.getMaNV());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
}
