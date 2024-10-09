/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class TheLoaiDao {
     public static ArrayList<moli.com.modal.theLoai> layDanhSachTheLoai()
    {
        ArrayList<moli.com.modal.theLoai> ds= new ArrayList<moli.com.modal.theLoai>();
        try{
            String sql="select * from TheLoai";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            {
                moli.com.modal.theLoai s=new moli.com.modal.theLoai();
                s.setMaTL(resultSet.getString(1));
                s.setTenTL(resultSet.getString(2));
                ds.add(s);
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy dữ liệu thể loại: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
    
     public static ArrayList<String> layDanhSachTenTL()
    {
        ArrayList<String> ds= new ArrayList<String>();
        try{
            String sql="select TenTL from TheLoai";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            { 
                ds.add(resultSet.getString("TenTL"));
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy dữ liệu tên thể loại: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
     
     public static boolean themTL(moli.com.modal.theLoai tl)
        {
            boolean kq=false;
            String sql="{call themTL(N'"+tl.getTenTL()+"')}";
            
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            int i=provider.callStatementUpdate(sql);
            if(i==1)
                kq=true;
            provider.close();
            return kq;
        }
     
     public static boolean xoaTL(String ma)
        {
                poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
                 ketNoi.open();

             try {
                 // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
                 String sql = "DELETE FROM theloai where maTL = ?";

                 // Tạo PreparedStatement từ kết nối và câu lệnh SQL
                 PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);

                 // Thiết lập giá trị cho tham số của câu lệnh SQL
                 preparedStatement.setString(1, ma);

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
          public static boolean suaTL(String ma,String ten)
        {
            boolean kq=false;
            String sql="update TheLoai set TenTL=N'"+ten+"' where maTL='"+ma+"'";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            int i=provider.excuteUpdate(sql);
            if(i==1)
                kq=true;
            provider.close();
            return kq;

        }
          
           public static ArrayList<moli.com.modal.theLoai> timKiemTheLoai(String ten)
    {
        ArrayList<moli.com.modal.theLoai> ds= new ArrayList<moli.com.modal.theLoai>();
        try{
            String sql="select * from TheLoai where TenTL like N'%"+ten+"%'";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
              while(resultSet.next())
            {
                moli.com.modal.theLoai s=new moli.com.modal.theLoai();
                s.setMaTL(resultSet.getString(1));
                s.setTenTL(resultSet.getString(2));
                ds.add(s);
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi tìm kiếm thể loại: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
      
    
}
