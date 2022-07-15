/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import qlbh.model.TaiKhoan;

/**
 *
 * @author Bao Nhu
 */
public class TaiKhoanDAOImpl implements TaiKhoanDAO{

    @Override
    public TaiKhoan login(String tdn, String mk) {
        TaiKhoan taiKhoan = null;
       
        try {
            String sql = "Select * from DangKy where tentk like '%"+ tdn +"%' and matkhau like '%"+ mk +"%' ";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTK(resultSet.getString("matk"));
                taiKhoan.setTenTK(resultSet.getString("tentk"));
                taiKhoan.setMatKhau(resultSet.getString("matkhau"));
                taiKhoan.setTinhTrang(resultSet.getBoolean("tinhtrang"));
            }
            provider.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }
    
}
