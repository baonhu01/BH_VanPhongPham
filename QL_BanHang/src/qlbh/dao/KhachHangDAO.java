/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.KhachHang;

/**
 *
 * @author Bao Nhu
 */
public class KhachHangDAO {
    public static ArrayList<KhachHang>layDanhSachKhachHang(){
        ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
       
        try {
            String sql = "Select * from khachhang";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(resultSet.getString("makh"));
                kh.setTenKH(resultSet.getString("tenkh"));
                kh.setgTinh(resultSet.getString("gtinh"));
                kh.setsDT(resultSet.getString("sdt"));
                kh.setDiaChi(resultSet.getString("diachi"));
                kh.setMaTK(resultSet.getString("matk"));
                dskh.add(kh);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dskh;
    }
    
    public static boolean themKhachHang(KhachHang kh){
        boolean kq = false;
        String sql = String.format("insert into khachhang(makh, tenkh, gtinh, sdt, diachi, matk) values('%s', N'%s', N'%s', '%s', N'%s', '%s');", kh.getMaKH(), kh.getTenKH(), kh.getgTinh(), kh.getsDT(), kh.getDiaChi(), kh.getMaTK());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
 
    public static boolean xoaKhachHang(String makh){
        boolean kq = false;
        String sql = String.format("delete from khachhang where makh='%s'", makh);
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static ArrayList<KhachHang>timKiemKhachHangTheoMa(String makh){
        ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
        try {
            String sql = "Select * from khachhang where makh like '%" + makh + " %'";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                KhachHang kh= new KhachHang();
                kh.setMaKH(resultSet.getString("makh"));
                kh.setTenKH(resultSet.getString("tenkh"));
                kh.setgTinh(resultSet.getString("gtinh"));
                kh.setsDT(resultSet.getString("sdt"));
                kh.setDiaChi(resultSet.getString("diachi"));
                kh.setMaTK(resultSet.getString("matk"));
                dskh.add(kh);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dskh;
    }
    
    public static boolean capNhatKhachHang(KhachHang kh){
        boolean kq = false;
        String sql = String.format("update khachhang set tenkh = N'%s', gtinh = N'%s', sdt = '%s', diachi = N'%s', matk = '%s' " + "where makh= '%s'", kh.getTenKH(), kh.getgTinh(), kh.getsDT(), kh.getDiaChi(), kh.getMaTK(), kh.getMaKH());
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
