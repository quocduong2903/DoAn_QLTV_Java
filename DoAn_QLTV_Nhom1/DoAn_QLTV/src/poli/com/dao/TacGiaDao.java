/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class TacGiaDao {
      public static ArrayList<moli.com.modal.tacGia> layDanhSachTacGia()
        {
            ArrayList<moli.com.modal.tacGia> ds= new ArrayList<moli.com.modal.tacGia>();
            try{
                String sql="select * from TacGia";
                poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
                provider.open();
                ResultSet resultSet=provider.excuteQuery(sql);

                while(resultSet.next())
                {
                    moli.com.modal.tacGia s=new moli.com.modal.tacGia();
                    s.setMaTG(resultSet.getString(1));
                    s.setTenTG(resultSet.getString(2));
                    s.setGioiTinh(resultSet.getString(3));
                    s.setTieuSu(resultSet.getString(4));

                    s.setNgaySinh(resultSet.getDate(5));

                    s.setQueQuan(resultSet.getString(6));
                    ds.add(s);
                }
                provider.close();
            }
            catch(Exception ex)
            {
                 System.err.println("Lỗi khi lấy dữ liệu tác giả: " + ex.getMessage());  // Ghi log lỗi
            }
            return ds;
        }
    
        public static ArrayList<String> layDanhSachTenTacGia()
        {
            ArrayList<String> ds= new ArrayList<String>();
            try{
                String sql="select TenTG from TacGia";
                poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
                provider.open();
                ResultSet resultSet=provider.excuteQuery(sql);

                while(resultSet.next())
                {


                    ds.add(resultSet.getString("TenTG"));
                }
                provider.close();
            }
            catch(Exception ex)
            {
                 System.err.println("Lỗi khi lấy dữ liệu tên tác giả: " + ex.getMessage());  // Ghi log lỗi
            }
            return ds;
        }
     
        public static boolean themTG(String ten,String gt,String tieuSu,Date ngaySinh,String queQuan)
        {
                    try {
                SQLServerProvider provider = new SQLServerProvider();
                provider.open();
                String sql = "{call themTG(?, ?, ?, ?, ?)}";
                CallableStatement cstmt = provider.prepareCall(sql);
                cstmt.setString(1, ten);
                cstmt.setString(2, gt);
                
                cstmt.setString(3, tieuSu);
                cstmt.setDate(4, ngaySinh);
                cstmt.setString(5, queQuan);
                int i =  cstmt.executeUpdate();
                if(i>0)
                    return true;
                else
                {
                    return false;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
      
        public static boolean xoaTG(String ma)
        {
           poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();

        try {
            // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
            String sql = "DELETE FROM tacgia where maTG = ?";

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
          public static boolean suaTG(String ma,String ten,String gt,String tieuSu,Date ngaySinh,String queQuan)
        {
            boolean kq=false;
            String sql="update TacGia set TenTG=N'"+ten+"',GioiTinh=N'"+gt+"',TieuSu=N'"+tieuSu+"',NgaySinh='"+ngaySinh+"' where maTG='"+ma+"'";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            int i=provider.excuteUpdate(sql);
            if(i==1)
                kq=true;
            provider.close();
            return kq;

        }
          
           public static ArrayList<moli.com.modal.tacGia> timKiemTacGia(String ten)
    {
        ArrayList<moli.com.modal.tacGia> ds= new ArrayList<moli.com.modal.tacGia>();
        try{
            String sql="select * from TacGia where TenTG like N'%"+ten+"%'";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
             while(resultSet.next())
                {
                    moli.com.modal.tacGia s=new moli.com.modal.tacGia();
                    s.setMaTG(resultSet.getString(1));
                    s.setTenTG(resultSet.getString(2));
                    s.setGioiTinh(resultSet.getString(3));
                    s.setTieuSu(resultSet.getString(4));

                    s.setNgaySinh(resultSet.getDate(5));

                    s.setQueQuan(resultSet.getString(6));
                    ds.add(s);
                }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi tìm kiếm tác giả: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
    
}
