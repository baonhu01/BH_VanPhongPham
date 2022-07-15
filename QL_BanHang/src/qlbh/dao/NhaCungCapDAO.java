/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.NhaCungCap;

/**
 *
 * @author Bao Nhu
 */
public class NhaCungCapDAO {
    public static ArrayList<NhaCungCap>layDanhSachNhaCungCap(){
        ArrayList<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
       
        try {
            String sql = "Select * from NhaCungCap";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(resultSet.getString("mancc"));
                ncc.setTenNCC(resultSet.getString("tenncc"));
                ncc.setDiaChi(resultSet.getString("diachi"));
                ncc.setsDT(resultSet.getString("sdt"));
                dsncc.add(ncc);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsncc;
    }
    
    public static boolean themNhaCungCap(NhaCungCap ncc){
        boolean kq = false;
        String sql = String.format("insert into nhacungcap(mancc, tenncc, diachi, sdt) values('%s', N'%s', N'%s', '%s');", ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getsDT());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static boolean xoaNhaCungCap(String mancc){
        boolean kq = false;
        String sql = String.format("delete from nhacungcap where mancc='%s'", mancc);
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static ArrayList<NhaCungCap>timKiemNhaCungCapTheoMa(String mancc){
        ArrayList<NhaCungCap> dscv = new ArrayList<NhaCungCap>();
        try {
            String sql = "Select * from nhacungcap where mancc like '%" + mancc + " %'";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(resultSet.getString("mancc"));
                ncc.setTenNCC(resultSet.getString("tenncc"));
                ncc.setDiaChi(resultSet.getString("diachi"));
                ncc.setsDT(resultSet.getString("sdt"));
                dscv.add(ncc);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dscv;
    }
    
    public static boolean capNhatNhaCungCap(NhaCungCap ncc){
        boolean kq = false;
        String sql = String.format("update nhacungcap set tenncc = N'%s', diachi = N'%s', sdt = '%s' " + "where mancc= '%s'", ncc.getTenNCC(), ncc.getDiaChi(),ncc.getsDT(), ncc.getMaNCC());
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
