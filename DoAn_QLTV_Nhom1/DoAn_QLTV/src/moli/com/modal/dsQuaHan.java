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
public class dsQuaHan {
    String mamt;
    int soNgay;
    Date ngayQH;
    String tinhTrang;

    public dsQuaHan() {
    }

    public dsQuaHan(String mamt, int soNgay, Date ngayQH, String tinhTrang) {
        this.mamt = mamt;
        this.soNgay = soNgay;
        this.ngayQH = ngayQH;
        this.tinhTrang = tinhTrang;
    }

    public String getMamt() {
        return mamt;
    }

    public void setMamt(String mamt) {
        this.mamt = mamt;
    }

    public int getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(int soNgay) {
        this.soNgay = soNgay;
    }

    public Date getNgayQH() {
        return ngayQH;
    }

    public void setNgayQH(Date ngayQH) {
        this.ngayQH = ngayQH;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
    
}
