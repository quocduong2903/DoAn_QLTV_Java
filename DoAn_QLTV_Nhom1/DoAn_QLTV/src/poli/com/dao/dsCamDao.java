/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import poli.com.dao.SQLServerProvider;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class dsCamDao {
    public static void updateBannedStatus(String maMT, int soNgayCam) {
    SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh gọi stored procedure
        String sql = "{call UpdateBannedStatus(?, ?)}";

        // Tạo CallableStatement từ kết nối và câu lệnh SQL
        CallableStatement callableStatement = ketNoi.prepareCall(sql);

        // Thiết lập giá trị cho tham số của stored procedure
        callableStatement.setString(1, maMT);
        callableStatement.setInt(2, soNgayCam);

        // Thực thi stored procedure
        callableStatement.execute();

        // Đóng kết nối
        ketNoi.close();

        JOptionPane.showMessageDialog(null, "Đưa vào danh sách cấm thành công!!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Đưa vào danh sách cấm thất bại !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}
        public static ArrayList<moli.com.modal.dsCam>hienThiDSCam(){
        ArrayList<moli.com.modal.dsCam> dsCam = new ArrayList<moli.com.modal.dsCam>();
        try {
            String sqlSelect = "Select * from DSCam";
            SQLServerProvider provider = new SQLServerProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sqlSelect);
            while(resultSet.next()){
               moli.com.modal.dsCam cam = new moli.com.modal.dsCam();
                cam.setMaDG(resultSet.getString(1));
                cam.setNgayCam(resultSet.getDate(2));
                cam.setNgayMo(resultSet.getDate(3));
                dsCam.add(cam);
            }
            provider.close();
        } catch (Exception e) {
        }
        return dsCam;
    }
    
    //Xóa độc giả khỏi danh sách cấm
    public static boolean XoaDocGiaKhoiDsCam(String maDG) {
    try 
    {
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        String sql = "DELETE FROM DsCam WHERE MaDG = ?";
        PreparedStatement pstm = provider.prepareStatement(sql);
        pstm.setString(1, maDG);
        int rowsAffected = pstm.executeUpdate();
        provider.close();
        return rowsAffected > 0;
    } catch (SQLException ex) {
        ex.printStackTrace(); 
        return false;
        }
    }
    
    //Proc xóa độc giả khỏi danh sách cấm khi đến Ngày mở
    public static void xoaDocGiaKhoiDsCamKhiDenNgayMo(String maDG) { 
        try {
            SQLServerProvider sQLServerDataProvider = new SQLServerProvider();
            sQLServerDataProvider.open();
            String sql = "{call xoaDocGiaTrongDsCam(?)}";
            CallableStatement callableStatement = sQLServerDataProvider.prepareCall(sql);
            callableStatement.setString(1, maDG);
            callableStatement.executeUpdate();
            sQLServerDataProvider.close();
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }
    
}
