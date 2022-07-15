/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.PhieuNhapHH;

/**
 *
 * @author Bao Nhu
 */
public class PhieuNhapHHDAO {
    public static ArrayList<PhieuNhapHH>layDanhSachPhieuNhapHH(){
        ArrayList<PhieuNhapHH> dsPNHH = new ArrayList<PhieuNhapHH>();
       
        try {
            String sql = "Select * from PhieuNhapHH";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                PhieuNhapHH pn = new PhieuNhapHH();
                pn.setMaPN(resultSet.getString("mapn"));
                pn.setMaNCC(resultSet.getString("mancc"));
                pn.setNgayNhap(resultSet.getString("ngaynhap"));
                pn.setMaNV(resultSet.getString("manv"));
                pn.setTongTien(resultSet.getFloat("tongtien"));
                dsPNHH.add(pn);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPNHH;
    }
    
    public static boolean themPhieuNhapHH(PhieuNhapHH pn){
        boolean kq = false;
        String sql = String.format("insert into PhieuNhapHH(mapn, mancc, ngaynhap, manv, tongtien) values('%s', '%s', '%s', '%s', '%f');", pn.getMaPN(), pn.getMaNCC(), pn.getNgayNhap(), pn.getMaNV(), pn.getTongTien());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
 
    public static boolean xoaPhieuNhapHH(String mapn){
        boolean kq = false;
        String sql = String.format("delete from PhieuNhapHH where mapn='%s'", mapn);
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static ArrayList<PhieuNhapHH>timKiemPhieuNhapHHTheoMa(String mapn){
        ArrayList<PhieuNhapHH> dsPNHH = new ArrayList<PhieuNhapHH>();
        try {
            String sql = "Select * from PhieuNhapHH where mapn like '%" + mapn + " %'";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                PhieuNhapHH pn = new PhieuNhapHH();
                pn.setMaPN(resultSet.getString("mapn"));
                pn.setMaNCC(resultSet.getString("mancc"));
                pn.setNgayNhap(resultSet.getString("ngaynhap"));
                pn.setMaNV(resultSet.getString("manv"));
                pn.setTongTien(resultSet.getFloat("tongtien"));
                dsPNHH.add(pn);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPNHH;
    }
    
    public static boolean capNhatPhieuNhapHH(PhieuNhapHH pn){
        boolean kq = false;
        String sql = String.format("update PhieuNhapHH set mancc = '%s', ngaynhap = '%s', manv = '%s', tongtien = '%f' " + "where mapn= '%s'", pn.getMaNCC(), pn.getNgayNhap(), pn.getMaNV(), pn.getTongTien(), pn.getMaPN());
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
