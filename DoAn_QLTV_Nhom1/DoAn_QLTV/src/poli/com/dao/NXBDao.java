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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class NXBDao {
    public static ArrayList<moli.com.modal.nhaXuatBan> layDanhSachNXB()
    {
        ArrayList<moli.com.modal.nhaXuatBan> ds= new ArrayList<moli.com.modal.nhaXuatBan>();
        try{
            String sql="select * from NXB";
            poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            {
                moli.com.modal.nhaXuatBan s=new moli.com.modal.nhaXuatBan();
                s.setMaNXB(resultSet.getString(1));
                s.setTenNXB(resultSet.getString(2));
                s.setDiaChi(resultSet.getString(3));
                s.setEmail(resultSet.getString(4));
                s.setSdt(resultSet.getString(5));

                ds.add(s);
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy dữ liệu nhà xuất bản: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
     
     
       public static ArrayList<String> layDanhSachTenNXB()
    {
        ArrayList<String> ds= new ArrayList<String>();
        try{
            String sql="select Tennxb from NXB";
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            { 
                ds.add(resultSet.getString("Tennxb"));
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy dữ liệu tên nxb: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
       
       
       public static boolean themNXB(moli.com.modal.nhaXuatBan p)
        {
            boolean kq=false;
            String sql="{call themNXB(N'"+p.getTenNXB()+"',N'"+p.getDiaChi()+"','"+p.getEmail()+"','"+p.getSdt()+"')}";

             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
            provider.open();
            int i=provider.callStatementUpdate(sql);
            if(i==1)
                kq=true;
            provider.close();
            return kq;
        }
      
        public static boolean xoaNXB(String ma)
        {
            poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();

        try {
            // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
            String sql = "DELETE FROM NXB where maNXB = ?";

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
          public static boolean suaNXB(moli.com.modal.nhaXuatBan p)
        {
            boolean kq=false;
            String sql="update NXB set TenNXB=N'"+p.getTenNXB()+"',DiaChi=N'"+p.getDiaChi()+"',Email=N'"+p.getEmail()+"',Sodt=N'"+p.getSdt()+"' where maNXB='"+p.getMaNXB()+"'";
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
            provider.open();
            int i=provider.excuteUpdate(sql);
            if(i==1)
                kq=true;
            provider.close();
            return kq;

        }
          
           public static ArrayList<moli.com.modal.nhaXuatBan> timKiemNXB(String ten)
    {
        ArrayList<moli.com.modal.nhaXuatBan> ds= new ArrayList<moli.com.modal.nhaXuatBan>();
        try{
            String sql="select * from NXB where TenNXB like N'%"+ten+"%'";
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
              while(resultSet.next())
            {
                moli.com.modal.nhaXuatBan s=new moli.com.modal.nhaXuatBan();
                s.setMaNXB(resultSet.getString(1));
                s.setTenNXB(resultSet.getString(2));
                s.setDiaChi(resultSet.getString(3));
                s.setEmail(resultSet.getString(4));
                s.setSdt(resultSet.getString(5));

                ds.add(s);
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi tìm kiếm nhà xuất: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
    
}
