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
public class ct_MuonTra {
    private String maMT;
    private String maSach;
    private String ghiChu;
    private Date ngayTra;
    private int soLuong;

    public ct_MuonTra() {
    }

    public ct_MuonTra(String maMT, String maSach, String ghiChu, Date ngayTra, int soLuong) {
        this.maMT = maMT;
        this.maSach = maSach;
        this.ghiChu = ghiChu;
        this.ngayTra = ngayTra;
        this.soLuong = soLuong;
    }

    public String getMaMT() {
        return maMT;
    }

    public void setMaMT(String maMT) {
        this.maMT = maMT;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    
    
}
