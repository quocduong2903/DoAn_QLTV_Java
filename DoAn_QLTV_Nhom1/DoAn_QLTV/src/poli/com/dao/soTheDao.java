/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import poli.com.dao.SQLServerProvider;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author HP
 */
public class soTheDao {
     public static boolean check_tinhTrang(String sothe) {
        boolean isOverdue = false;
        SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "SELECT tinhtrang FROM thethuvien WHERE sothe = ?"; // Thay TenBang bằng tên bảng chứa thông tin mã mượn trả
        try {
            PreparedStatement pstmt = knoi.prepareStatement(sql);
            pstmt.setString(1, sothe);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tinhTrang = rs.getString("tinhTrang");
                if (tinhTrang != null && tinhTrang.equals("Hết hạn")) {
                    isOverdue = true;
                }
            }
            // Đóng tài nguyên
            rs.close();
            pstmt.close();
            knoi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOverdue;
    }
      public static ArrayList<moli.com.modal.theThuVien>hienThiDSTheThuVien(){
        ArrayList<moli.com.modal.theThuVien> dsTTV = new ArrayList<moli.com.modal.theThuVien>();
        try {
            String sqlSelect = "Select * from TheThuVien";
            SQLServerProvider provider = new SQLServerProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sqlSelect);
            while(resultSet.next()){
                moli.com.modal.theThuVien ttv = new moli.com.modal.theThuVien();
                ttv.setSoThe(resultSet.getString(1));
                ttv.setNgayBD(resultSet.getDate(2));
                ttv.setNgayKT(resultSet.getDate(3));
                ttv.setTinhTrang(resultSet.getString(4));
                dsTTV.add(ttv);
            }
            provider.close();
        } catch (Exception e) {
        }
        return dsTTV;
    }
    
    //Thêm thẻ thư viện
    public static int themTheThuVien() {
        try {
            SQLServerProvider sQLServerDataProvider = new SQLServerProvider();
            sQLServerDataProvider.open();
            String sql = "{call themTheThuVien()}";
            CallableStatement cstmt = sQLServerDataProvider.prepareCall(sql);
            return cstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    //Xóa thẻ thư viện
    public static boolean xoaTheThuVien(String sothe){
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();

        try {
            // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
            String sql = "DELETE FROM thethuvien where sothe = ?";

            // Tạo PreparedStatement từ kết nối và câu lệnh SQL
            PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);

            // Thiết lập giá trị cho tham số của câu lệnh SQL
            preparedStatement.setString(1, sothe);

            // Thực thi câu lệnh SQL
            int rowsAffected = preparedStatement.executeUpdate();

            // Kiểm tra xem có bản ghi nào bị ảnh hưởng không
            if (rowsAffected > 0) {

                return true; // Trả về true nếu xóa thành công
            } else {

                return false; // Trả về false nếu không tìm thấy bản ghi để xóa
            }
        } catch (SQLException ex) {

              JOptionPane.showMessageDialog(null, "Lỗi trùng khóa!!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
          return false;                 
        } finally {
            // Đóng kết nối
            ketNoi.close();
        }
    }
    
    //Cập nhật thẻ thư viện
    public static int capNhatTheThuVien(moli.com.modal.theThuVien ttv) {
        try {
                SQLServerProvider sQLServerDataProvider = new SQLServerProvider();
                sQLServerDataProvider.open();
                String sql = "Update TheThuVien set ngayBD = ?, ngayKT = ?, tinhTrang = ? where sothe = ?";
                PreparedStatement preStatement = sQLServerDataProvider.prepareStatement(sql);

                java.sql.Date ngayBD= new java.sql.Date(ttv.getNgayBD().getTime());
                preStatement.setDate(1, ngayBD);
                java.sql.Date ngayKT= new java.sql.Date(ttv.getNgayKT().getTime());
                preStatement.setDate(2, ngayKT);
                preStatement.setString(3, ttv.getTinhTrang());
                preStatement.setString(4, ttv.getSoThe());

                int result = preStatement.executeUpdate();
                sQLServerDataProvider.close();
                return result;
            } catch (Exception ex) {
                    ex.printStackTrace();
                    return -1;
            } 
    }
    
    //Tìm kiếm thẻ thư viện
    public static ArrayList<moli.com.modal.theThuVien>timKiemTheThuVien(String soThe){
        ArrayList<moli.com.modal.theThuVien> dsTTV = new ArrayList<moli.com.modal.theThuVien>();
    try {
        String sqlSelect = "SELECT * FROM TheThuVien WHERE SoThe LIKE ?";
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        PreparedStatement preparedStatement = provider.prepareStatement(sqlSelect);
        preparedStatement.setString(1, "%" + soThe + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            moli.com.modal.theThuVien ttv = new moli.com.modal.theThuVien();
            ttv.setSoThe(resultSet.getString(1));
            ttv.setNgayBD(resultSet.getDate(2));
            ttv.setNgayKT(resultSet.getDate(3));
            ttv.setTinhTrang(resultSet.getString(4));
            dsTTV.add(ttv);
        }
        provider.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
        return dsTTV;
    }
    
    //Proc kiểm tra quá hạn thẻ
    public static void kiemTraQuaHan(String soThe, Date ngayBD, Date ngayKT, String tinhTrang) { 
        try {
            SQLServerProvider sQLServerDataProvider = new SQLServerProvider();
            sQLServerDataProvider.open();
            String sql = "{call kiemTraTinhTrangThe(?, ?, ?, ?)}";
            CallableStatement callableStatement = sQLServerDataProvider.prepareCall(sql);
            callableStatement.setString(1, soThe);
            callableStatement.setDate(2, ngayBD);
            callableStatement.setDate(3, ngayKT);
            callableStatement.setString(4, tinhTrang);
            callableStatement.executeUpdate();
            sQLServerDataProvider.close();
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }
   
    
}
