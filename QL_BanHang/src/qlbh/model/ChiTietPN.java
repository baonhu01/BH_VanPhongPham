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
public class ChiTietPN {
    String maPN;
    String maHH;
    int sLNhap;
    float giaNhap;
    
    public ChiTietPN(){
        
    }
    
    public ChiTietPN(String maPN, String maHH, int sLNhap, float giaNhap){
        this.maPN = maPN;
        this.maHH = maHH;
        this.sLNhap = sLNhap;
        this.giaNhap = giaNhap;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaHH() {
        return maHH;
    }

    public void setMaHH(String maHH) {
        this.maHH = maHH;
    }

    public int getsLNhap() {
        return sLNhap;
    }

    public void setsLNhap(int sLNhap) {
        this.sLNhap = sLNhap;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }
    
    
}
