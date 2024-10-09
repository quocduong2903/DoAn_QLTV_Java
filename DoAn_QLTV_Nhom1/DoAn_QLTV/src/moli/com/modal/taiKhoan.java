/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moli.com.modal;

/**
 *
 * @author HP
 */
public class taiKhoan {
    private String maNV;
    private String matKhau;
    private int quyen;

    public taiKhoan() {
    }
    

    public taiKhoan(String maNV, String matKhau, int quyen) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.quyen = quyen;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }
    
    
}
