/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import moli.com.modal.muonTra;

/**
 *
 * @author HP
 */
public class dsQuaHanDao {
    public static ArrayList<moli.com.modal.dsQuaHan> hienThi_ALL()
    {
        ArrayList<moli.com.modal.dsQuaHan> ds= new ArrayList<moli.com.modal.dsQuaHan>();
        String sql_select ="select * from dsquahan";
        ResultSet resultSet = null;
        try {
            poli.com.dao.SQLServerProvider lServerProvider = new SQLServerProvider();
            lServerProvider.open();
            resultSet =  lServerProvider.excuteQuery(sql_select);
            while(resultSet.next())
            {
                moli.com.modal.dsQuaHan s = new moli.com.modal.dsQuaHan();
                s.setMamt(resultSet.getString(1));
                s.setSoNgay(resultSet.getInt(2));
                s.setNgayQH(resultSet.getDate(3));
                s.setTinhTrang(resultSet.getString(4));
               
              
                ds.add(s);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return ds;
    }
       public static void createDesignedTable(ArrayList<moli.com.modal.dsQuaHan> dsx, JTable tb) {
              
       DefaultTableModel ds = new DefaultTableModel();
        ds.addColumn("Mã mượn trả");
        ds.addColumn("Số ngày quá hạn"); 
        ds.addColumn("Ngày quá hạn"); 
        ds.addColumn("Tình trạng"); 
     
      
        ds.setRowCount(0);
        
                         
        for(moli.com.modal.dsQuaHan s : dsx)
        {
            Vector<Object> pbx = new Vector<Object>();
            pbx.add(s.getMamt());          
            pbx.add(s.getSoNgay());           
            pbx.add(s.getNgayQH());
            pbx.add(s.getTinhTrang());
       
    
    
            ds.addRow(pbx);          
        }
        tb.setModel(ds);
        
       
       
    }
        public static void UpdateDATE_DS_QH(String maMT, Date ngayQH,String tinhTrang) {
        SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();

        try {
            // Tạo câu lệnh gọi stored procedure
            String sql = "{call CapNhatSoNgayQuaHan(?, ?,?)}";

            // Tạo CallableStatement từ kết nối và câu lệnh SQL
            CallableStatement callableStatement = ketNoi.prepareCall(sql);

            // Thiết lập giá trị cho các tham số của stored procedure
            callableStatement.setString(1, maMT);
             callableStatement.setString(2, tinhTrang);
            callableStatement.setDate(3, new java.sql.Date(ngayQH.getTime()));

            // Thực thi stored procedure
            int rowsAffected = callableStatement.executeUpdate();

            // Đóng kết nối
            ketNoi.close();

            // Nếu có ít nhất một hàng được thêm vào, trả về true
            
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
    }
     public static void updateQuaHanAndMuonTra(String maMT) {
         
    SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh gọi stored procedure
        String sql = "{call UpdateQuaHanAndMuonTra(?)}";

        // Tạo CallableStatement từ kết nối và câu lệnh SQL
        CallableStatement callableStatement = ketNoi.prepareCall(sql);

        // Thiết lập giá trị cho tham số của stored procedure
        callableStatement.setString(1, maMT);

        // Thực thi stored procedure
        callableStatement.execute();

        // Đóng kết nối
        ketNoi.close();

        JOptionPane.showMessageDialog(null, "Xử lí thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Xử lí thất bại !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}
          public static void XoaQH(String maMT) {
         
    SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh gọi stored procedure
        String sql = "Delete dsquahan where mamt  =?";

        // Tạo CallableStatement từ kết nối và câu lệnh SQL
        CallableStatement callableStatement = ketNoi.prepareCall(sql);

        // Thiết lập giá trị cho tham số của stored procedure
        callableStatement.setString(1, maMT);

        // Thực thi stored procedure
        callableStatement.execute();

        // Đóng kết nối
        ketNoi.close();

        JOptionPane.showMessageDialog(null, "Xóa thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Xóa thất bại !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}
           public  static ArrayList<moli.com.modal.dsQuaHan> thongKeDsMuonTheoTuan_QH() {
    ArrayList<moli.com.modal.dsQuaHan> ds = new ArrayList<moli.com.modal.dsQuaHan>();
     poli.com.dao.SQLServerProvider knoi =  new SQLServerProvider();
    try {
       
        knoi.open(); // Mở kết nối đến cơ sở dữ liệu

        String sql = "{call ThongKePhieuMuonTrongTuanQuaHan()}"; // Chuẩn bị câu lệnh gọi stored procedure

        CallableStatement callStatement = knoi.prepareCall(sql); // Chuẩn bị gọi stored procedure
        ResultSet resultSet = callStatement.executeQuery(); // Thực thi stored procedure

        while (resultSet.next()) { // Xử lý kết quả trả về
            moli.com.modal.dsQuaHan s = new moli.com.modal.dsQuaHan();
            s.setMamt(resultSet.getString(1));
            s.setSoNgay(resultSet.getInt(2));
            s.setNgayQH(resultSet.getDate(3));
            s.setTinhTrang(resultSet.getString(4));
            
            ds.add(s);
        }

        resultSet.close(); // Đóng ResultSet
        callStatement.close(); // Đóng CallableStatement
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        knoi.close(); // Đóng kết nối
    }
    return ds;
}
     
    
}
