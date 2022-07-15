/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.ChucVu;

/**
 *
 * @author Bao Nhu
 */
public class ChucVuDAO {
    public static ArrayList<ChucVu>layDanhSachChucVu(){
        ArrayList<ChucVu> dscv = new ArrayList<ChucVu>();
       
        try {
            String sql = "Select * from ChucVu";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                ChucVu cv = new ChucVu();
                cv.setMaCV(resultSet.getString("MACV"));
                cv.setTenCV(resultSet.getString("TENCV"));
                cv.setLuong(resultSet.getFloat("LUONG"));
                dscv.add(cv);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dscv;
    }
    
    
    public static boolean themChucVu(ChucVu cv){
        boolean kq = false;
        String sql = String.format("insert into chucvu(macv, tencv, luong) values('%s', N'%s', '%f');", cv.getMaCV(), cv.getTenCV(), cv.getLuong());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
 
    public static boolean xoaChucVu(String macv){
        boolean kq = false;
        String sql = String.format("delete from chucvu where macv='%s'", macv);
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static ArrayList<ChucVu>timKiemChucVuTheoMa(String macv){
        ArrayList<ChucVu> dscv = new ArrayList<ChucVu>();
        try {
            String sql = "Select * from chucvu where macv like '%" + macv + " %'";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                ChucVu cv = new ChucVu();
                cv.setMaCV(resultSet.getString("macv"));
                cv.setTenCV(resultSet.getString("tencv"));
                cv.setLuong(resultSet.getFloat("luong"));
                dscv.add(cv);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dscv;
    }
    
    public static boolean capNhatChucVu(ChucVu cv){
        boolean kq = false;
        String sql = String.format("update chucVu set tencv = N'%s', luong = '%f' " + "where macv= '%s'", cv.getTenCV(), cv.getLuong(), cv.getMaCV());
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
