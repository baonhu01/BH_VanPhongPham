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
public class ChucVu {
  String maCV;
  String tenCV;
  float luong;

  public ChucVu(){
      
  }  
  
  public ChucVu(String maCV, String tenCV, float luong){
      this.maCV = maCV;
      this.tenCV = tenCV;
      this.luong = luong;
  }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public float getLuong() {
        return luong;
    }

    public void setLuong(float luong) {
        this.luong = luong;
    }
  
  
}
