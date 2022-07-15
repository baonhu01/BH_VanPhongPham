/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.HangHoa;

/**
 *
 * @author Bao Nhu
 */
public class HangHoaDAO {
    public static ArrayList<HangHoa>layDanhSachHangHoa(){
        ArrayList<HangHoa> dshh = new ArrayList<HangHoa>();
       
        try {
            String sql = "Select * from hanghoa";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                HangHoa hh = new HangHoa();
                hh.setMaHH(resultSet.getString("mahh"));
                hh.setTenHH(resultSet.getString("tenhh"));
                hh.setMaLoai(resultSet.getString("maloai"));
                hh.setSoLuong(resultSet.getInt("soluong"));
                hh.setdVT(resultSet.getString("dvt"));
                hh.setGia(resultSet.getFloat("gia"));
                dshh.add(hh);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dshh;
    }
    
    public static boolean themHangHoa(HangHoa hh){
        boolean kq = false;
        String sql = String.format("insert into hanghoa(mahh, tenhh, maloai, soluong, dvt, gia) values('%s', N'%s', '%s', '%d', N'%s', '%f');", hh.getMaHH(), hh.getTenHH(), hh.getMaLoai(), hh.getSoLuong(), hh.getdVT(), hh.getGia());
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static boolean xoaHangHoa(String mahh){
        boolean kq = false;
        String sql = String.format("delete from hanghoa where mahh='%s'", mahh);
        SQLServerDataProvider provider = new SQLServerDataProvider();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if(n == 1){
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
    public static ArrayList<HangHoa>timKiemHangHoaTheoMaLoai(String maloai){
        ArrayList<HangHoa> dshh = new ArrayList<HangHoa>();
        try {
            String sql = "Select * from hanghoa where maloai like '%" + maloai + " %'";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                HangHoa hh = new HangHoa();
                hh.setMaHH(resultSet.getString("mahh"));
                hh.setTenHH(resultSet.getString("tenhh"));
                hh.setMaLoai(resultSet.getString("maloai"));
                hh.setSoLuong(resultSet.getInt("soluong"));
                hh.setdVT(resultSet.getString("dvt"));
                hh.setGia(resultSet.getFloat("gia"));
                dshh.add(hh);
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dshh;
    }
    
    public static boolean capNhatHangHoa(HangHoa hh){
        boolean kq = false;
        String sql = String.format("update hanghoa set tenhh = N'%s', maloai = '%s', soluong = '%d', dvt = N'%s', gia = '%f' " + "where mahh= '%s'", hh.getTenHH(), hh.getMaLoai(), hh.getSoLuong(), hh.getdVT(), hh.getGia(), hh.getMaHH());
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
