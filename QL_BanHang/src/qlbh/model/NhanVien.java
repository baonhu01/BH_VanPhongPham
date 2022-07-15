/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.model;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class NhanVien {
    String maNV;
    String tenNV;
    String ngSinh;
    String gTinh;
    String maCV;
    String maTK;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String ngSinh, String gTinh, String maCV, String maTK) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngSinh = ngSinh;
        this.gTinh = gTinh;
        this.maCV = maCV;
        this.maTK = maTK;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNgSinh() {
        return ngSinh;
    }

    public void setNgSinh(String ngSinh) {
        this.ngSinh = ngSinh;
    }

    public String getgTinh() {
        return gTinh;
    }

    public void setgTinh(String gTinh) {
        this.gTinh = gTinh;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }
    
}
