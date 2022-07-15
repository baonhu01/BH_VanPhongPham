/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.service;

import qlbh.dao.TaiKhoanDAO;
import qlbh.dao.TaiKhoanDAOImpl;
import qlbh.model.TaiKhoan;

/**
 *
 * @author Bao Nhu
 */
public class TaiKhoanServiceImpl implements TaiKhoanService{
    
    private TaiKhoanDAO taiKhoanDAO = null;

    public TaiKhoanServiceImpl() {
        taiKhoanDAO = new TaiKhoanDAOImpl();
    }
    

    @Override
    public TaiKhoan login(String tdn, String mk) {
        return taiKhoanDAO.login(tdn, mk);
    }
    
}
