/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.model;

/**
 *
 * @author DELL
 */
public class KhachHang {
    String maKH;
    String tenKH;
    String gTinh;
    String sDT;
    String diaChi;
    String maTK;

    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String gTinh, String sDT, String diaChi, String maTK) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gTinh = gTinh;
        this.sDT = sDT;
        this.diaChi = diaChi;
        this.maTK = maTK;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getgTinh() {
        return gTinh;
    }

    public void setgTinh(String gTinh) {
        this.gTinh = gTinh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }
    
    
}
