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
public class nhanVien {
    private String hinhAnh;
    private String maNV;
    private String tenNv;
    private Date ngaySinh;
    private Date ngayVL;
    private String SDT;
    private String diaChi;
    private String chucVu;
    private String gioiTinh;

    public nhanVien() {
    }

    public nhanVien(String hinhAnh, String maNV, String tenNv, Date ngaySinh, Date ngayVL, String SDT, String diaChi, String gioiTinh,String chucVu) {
        this.hinhAnh = hinhAnh;
        this.maNV = maNV;
        this.tenNv = tenNv;
        this.ngaySinh = ngaySinh;
        this.ngayVL = ngayVL;
        this.SDT = SDT;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
    

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Date getNgayVL() {
        return ngayVL;
    }

    public void setNgayVL(Date ngayVL) {
        this.ngayVL = ngayVL;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    
    

   
    
    
    
}
