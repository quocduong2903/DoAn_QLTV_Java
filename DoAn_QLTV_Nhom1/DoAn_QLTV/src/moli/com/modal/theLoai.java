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
public class theLoai {
    private String maTL;
    private String tenTL;

    public theLoai() {
    }
    

    public theLoai(String maTL, String tenTL) {
        this.maTL = maTL;
        this.tenTL = tenTL;
    }

    public String getMaTL() {
        return maTL;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    @Override
    public String toString() {
        return this.tenTL;
    }
    
    
}
