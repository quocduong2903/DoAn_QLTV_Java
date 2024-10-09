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
public class dsCam {
     private String maDG;
    private Date ngayCam;
    private Date ngayMo;

    public dsCam() {
    }

    public dsCam(String maDG, Date ngayCam, Date ngayMo) {
        this.maDG = maDG;
        this.ngayCam = ngayCam;
        this.ngayMo = ngayMo;
    }

    public String getMaDG() {
        return maDG;
    }

    public void setMaDG(String maDG) {
        this.maDG = maDG;
    }

    public Date getNgayCam() {
        return ngayCam;
    }

    public void setNgayCam(Date ngayCam) {
        this.ngayCam = ngayCam;
    }

    public Date getNgayMo() {
        return ngayMo;
    }

    public void setNgayMo(Date ngayMo) {
        this.ngayMo = ngayMo;
    }
    
    
}
