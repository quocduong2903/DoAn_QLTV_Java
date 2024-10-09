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
public class theThuVien {
    private String soThe;
    private Date ngayBD;
    private Date ngayKT;
    private String tinhTrang;

    public theThuVien() {
    }

    public theThuVien(String soThe, Date ngayBD, Date ngayKT, String tinhTrang) {
        this.soThe = soThe;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.tinhTrang = tinhTrang;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    

    

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(Date ngayKT) {
        this.ngayKT = ngayKT;
    }
    
     
}
