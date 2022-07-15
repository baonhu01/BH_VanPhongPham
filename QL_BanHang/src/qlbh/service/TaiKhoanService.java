/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.service;

import qlbh.model.TaiKhoan;

/**
 *
 * @author Bao Nhu
 */
public interface TaiKhoanService {
    public TaiKhoan login(String tdn, String mk);
}
