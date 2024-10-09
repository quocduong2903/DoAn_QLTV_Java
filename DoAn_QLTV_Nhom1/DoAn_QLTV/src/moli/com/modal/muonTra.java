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
public class muonTra {
    private String maMT;
    private String soThe;
    private String maNV;
    private String tinhTrang;
    private Date ngayMuon;
    private Date ngayHenTra;

    public muonTra() {
    }
    

    public muonTra(String maMT, String soThe, String maNV, String tinhTrang, Date ngayMuon, Date ngayHenTra) {
        this.maMT = maMT;
        this.soThe = soThe;
        this.maNV = maNV;
        this.tinhTrang = tinhTrang;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
    }

    public String getMaMT() {
        return maMT;
    }

    public void setMaMT(String maMT) {
        this.maMT = maMT;
    }

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayHenTra() {
        return ngayHenTra;
    }

    public void setNgayHenTra(Date ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
    
    
    
}
