/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moli.com.modal;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class docGia {
    private String maDocGia;
    private String tenDG;
    private String diaChi;
    private String soDT;
    private String soThe;
    private String gioiTinh;
    private Date ngaySinh;
    private String tinhTrang;

    public docGia() {
    }

    public docGia(String maDocGia, String tenDG, String diaChi, String soDT, String soThe, String gioiTinh, Date ngaySinh, String tinhTrang) {
        this.maDocGia = maDocGia;
        this.tenDG = tenDG;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.soThe = soThe;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.tinhTrang = tinhTrang;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }



    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getTenDG() {
        return tenDG;
    }

    public void setTenDG(String tenDG) {
        this.tenDG = tenDG;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    

   
}
