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
public class tacGia {
    private String maTG;
    private String tenTG;
    private String tieuSu;
    private String queQuan;
    private Date ngaySinh;
    private String gioiTinh;

    public tacGia() {
    }

    public tacGia(String maTG, String tenTG, String tieuXu, String queQuan, Date ngaySinh, String gioiTinh) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.tieuSu = tieuXu;
        this.queQuan = queQuan;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public String getTieuSu() {
        return tieuSu;
    }

    public void setTieuSu(String tieuXu) {
        this.tieuSu = tieuXu;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return this.tenTG;
    }

   
    
   
    
    
    
}
