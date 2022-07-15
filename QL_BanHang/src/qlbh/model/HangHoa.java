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
public class HangHoa {
   String maHH;
   String tenHH;
   String maLoai;
   int soLuong;
   String dVT;
   float gia;

   public HangHoa() {
   }

    public HangHoa(String maHH, String tenHH, String maLoai, int soLuong, String dVT, float gia) {
        this.maHH = maHH;
        this.tenHH = tenHH;
        this.maLoai = maLoai;
        this.soLuong = soLuong;
        this.dVT = dVT;
        this.gia = gia;
    }

    public String getMaHH() {
        return maHH;
    }

    public void setMaHH(String maHH) {
        this.maHH = maHH;
    }

    public String getTenHH() {
        return tenHH;
    }

    public void setTenHH(String tenHH) {
        this.tenHH = tenHH;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getdVT() {
        return dVT;
    }

    public void setdVT(String dVT) {
        this.dVT = dVT;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
   
}
