/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import moli.com.modal.ct_MuonTra;

/**
 *
 * @author HP
 */
public class ct_MuonTraDao {
    public static boolean lapCTPhieuMuon(String ma, String maSach, int sl, int slcapnhat) {
        boolean check = kiemTra_Khoa(ma, maSach);
        boolean checkTinhTrang = poli.com.dao.muonTraDao.check_tinhTrang(ma);
        if(checkTinhTrang)
        {
            JOptionPane.showMessageDialog(null, "Phiếu này đã quá hạn không thể thêm mới!!!", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             return false;
        }
        if(check==true)
        {
              JOptionPane.showMessageDialog(null, "Sách này đã mượn rồi chỉ được sửa... ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             return false;
        }
        boolean success = false;
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();
         boolean check1 = poli.com.dao.sachDAO.lapPhieuMuon(maSach, slcapnhat);
         if(check1 == false)
         {
              JOptionPane.showMessageDialog(null, "Lỗi cập nhật sách ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             return false;
         }
        String sql = "INSERT INTO ctmuontra (mamt, masach, soluong, ghichu) VALUES (?, ?, ?, N'Chưa trả')";
        try {
            PreparedStatement pstmt = ketNoi.prepareStatement(sql);
            pstmt.setString(1, ma);
            pstmt.setString(2, maSach);
            pstmt.setInt(3, sl);
            
            // Thực thi lệnh SQL
            int result = pstmt.executeUpdate();
            if (result > 0) {
                success = true;
            }
            
            // Đóng kết nối và tài nguyên
            pstmt.close();
            ketNoi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public static boolean kiemTra_Khoa(String ma_mt, String maSach) {
        boolean exists = false;
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();
        String sql = "SELECT 1 FROM CTMuonTra WHERE MaMT = ? AND MaSach = ?";
        try {
            PreparedStatement pstmt = ketNoi.prepareStatement(sql);
            pstmt.setString(1, ma_mt);
            pstmt.setString(2, maSach);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                exists = true;
            }
            // Đóng tài nguyên
            rs.close();
            pstmt.close();
            ketNoi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    public static ArrayList<moli.com.modal.ct_MuonTra> load_Phieu_Theo_Ma(String ma) {
    ArrayList<moli.com.modal.ct_MuonTra> ds  = new ArrayList<ct_MuonTra>();
    poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
    knoi.open();
    String sql = "Select masach, ngaytra, soluong, ghichu from ctmuontra where mamt ='" + ma + "'";
    ResultSet resultSet = knoi.excuteQuery(sql);
    try {
        if (resultSet != null) { // Kiểm tra xem ResultSet có null không trước khi truy xuất dữ liệu
            while (resultSet.next()) {
                ct_MuonTra ct =  new ct_MuonTra();
                ct.setMaSach(resultSet.getString(1));
                ct.setNgayTra(resultSet.getDate(2));
                ct.setSoLuong(resultSet.getInt(3));
                ct.setGhiChu(resultSet.getString(4));
                ds.add(ct);
            }
        }
        if (knoi != null) { // Kiểm tra xem đối tượng SQLServerProvider có null không trước khi gọi close()
            knoi.close();
        }
    } catch (SQLException ex) {
        Logger.getLogger(ct_MuonTraDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    return ds;
}
    public static void createDesignedTable(ArrayList<moli.com.modal.ct_MuonTra> dsx, JTable tb_sach) {
              
       DefaultTableModel ds = new DefaultTableModel();
        ds.addColumn("Mã sách");
        ds.addColumn("Số lượng"); 
        ds.addColumn("Ngày trả"); 
        ds.addColumn("Ghi chú"); 
      
        ds.setRowCount(0);
        
                         
        for(ct_MuonTra s : dsx)
        {
            Vector<Object> pbx = new Vector<Object>();
            pbx.add(s.getMaSach());
          
            pbx.add(s.getSoLuong());
            
              pbx.add(s.getNgayTra());
            pbx.add(s.getGhiChu());
    
    
            ds.addRow(pbx);          
        }
        tb_sach.setModel(ds);
        
       
       
    }
 public static boolean check_ghi_chu(String mamt, String masach, String dk) {
    boolean exists = false;
    poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
    knoi.open();
    String sql = "SELECT 1 FROM ctmuontra WHERE mamt = ? AND masach = ? AND ghichu = ?";
    try {
        PreparedStatement pstmt = knoi.prepareStatement(sql);
        pstmt.setString(1, mamt);
        pstmt.setString(2, masach);
        pstmt.setString(3, dk);
        ResultSet rs = pstmt.executeQuery();
        // Kiểm tra xem kết quả trả về có dòng dữ liệu hay không
        exists = rs.next();
        // Đóng tài nguyên
        rs.close();
        pstmt.close();
        knoi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return exists;
}
    public static void capNhatCt_MT(String ma, String masach, int sl, int slcu , int slgoc)
    {
        int tong = slcu+slgoc; 
       if(tong>=sl)
       {
           boolean check = poli.com.dao.sachDAO.lapPhieuMuon(masach, tong);
            boolean check1 = poli.com.dao.sachDAO.lapPhieuMuon(masach, tong-sl);
            if(check1&&check)
            {
                poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();        
            String sql = "Update ctmuontra set soluong =? where mamt =? and masach =?";
            try {
                PreparedStatement pstmt = ketNoi.prepareStatement(sql);
                pstmt.setInt(1, sl);
                pstmt.setString(2, ma);
                pstmt.setString(3, masach);

                // Thực thi lệnh SQL
                int result = pstmt.executeUpdate();
                if (result > 0) {
                     JOptionPane.showMessageDialog(null, "Sửa thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
                     return;
                }

                // Đóng kết nối và tài nguyên
                pstmt.close();
                ketNoi.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
       
        
            } 
            else
            {
                 JOptionPane.showMessageDialog(null, "Lỗi cập nhật sách ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return ;
            }
       }
       else
       {
            JOptionPane.showMessageDialog(null, "Lỗi!! không đủ sách ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             return ;
       }
       
    }
    
     public static void capNhatCt_MT_Khac(String ma, String masach, String masach_old, int sl, int slcu , int slgoc)
    {
        int sl_sach_old = poli.com.dao.sachDAO.select_sl_sach(masach_old);
        int tong = slcu+sl_sach_old;
         boolean check = poli.com.dao.sachDAO.lapPhieuMuon(masach_old, tong);
       if(check)
       {    
           
           
            boolean check1 = poli.com.dao.ct_MuonTraDao.kiemTra_Khoa(ma,masach);
            if(check1==false)
            {
                poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();        
            String sql = "Update ctmuontra set maSach =?, soLuong = ? where mamt =? and masach =?";
            try {
                PreparedStatement pstmt = ketNoi.prepareStatement(sql);
                pstmt.setString(1, masach);
                pstmt.setInt(2, sl);
                pstmt.setString(3, ma);
                pstmt.setString(4, masach_old);
               
                boolean check_sua_sach = poli.com.dao.sachDAO.lapPhieuMuon(masach, slgoc-sl);

                // Thực thi lệnh SQL
                int result = pstmt.executeUpdate();
                if (result > 0) {
                     JOptionPane.showMessageDialog(null, "Sửa thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
                     return;
                }

                // Đóng kết nối và tài nguyên
                pstmt.close();
                ketNoi.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
       
        
            } 
            else
            {
                 JOptionPane.showMessageDialog(null, "Sách này đã mượn rồi mà", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return ;
            }
       }
       else
       {
            JOptionPane.showMessageDialog(null, "Lỗi!! Trả sách ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             return ;
       }
       
    }
    public static void capNhatTra(String mamt, String masach, int slMuon) {
     int slGoc = poli.com.dao.sachDAO.select_sl_sach(masach);
     slMuon = slMuon + slGoc;
     boolean check = poli.com.dao.sachDAO.lapPhieuMuon(masach, slMuon);
     if (check) {
         try {
             poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
             knoi.open();
             String sql = "update ctmuontra set ghichu = ?, ngaytra = ? where mamt = ? and masach = ?";
             PreparedStatement pstmt = knoi.prepareStatement(sql);
             pstmt.setString(1, "Đã trả"); // Giả sử ghichu sẽ được cập nhật thành "Đã trả"
             pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Ngày hiện tại
             pstmt.setString(3, mamt);
             pstmt.setString(4, masach);
             int result = pstmt.executeUpdate();
             if (result > 0) {
                 JOptionPane.showMessageDialog(null, "Cập nhật thông tin trả sách thành công");
             } else {
                 JOptionPane.showMessageDialog(null, "Lỗi trong quá trình cập nhật thông tin trả sách", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             }
             pstmt.close();
             knoi.close();
         } catch (SQLException ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(null, "Lỗi trong quá trình cập nhật thông tin trả sách", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
         }
     } else {
         JOptionPane.showMessageDialog(null, "Lỗi!! Trả sách ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
     }
    }
    public static void xoaMuonTra(String mamt, String masach) {
    
        boolean check = poli.com.dao.ct_MuonTraDao.check_ghi_chu(mamt, masach, "Đã trả");
        if(check)
        {
             try {
        poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "DELETE FROM ctmuontra WHERE mamt = ? AND masach = ?";
        PreparedStatement pstmt = knoi.prepareStatement(sql);
        pstmt.setString(1, mamt);
        pstmt.setString(2, masach);
        int result = pstmt.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Xóa thông tin mượn trả thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu để xóa", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
        }
        pstmt.close();
        knoi.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình xóa dữ liệu mượn trả", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
        }
        }
        else
        {
             JOptionPane.showMessageDialog(null, "Chưa trả hoặc quá hạn không thể xóa", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
             return;
        }
       
    }
     public static boolean check_tonTai(String ma_mt) {
        boolean exists = false;
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();
        String sql = "SELECT 1 FROM CTMuonTra WHERE MaMT = ? ";
        try {
            PreparedStatement pstmt = ketNoi.prepareStatement(sql);
            pstmt.setString(1, ma_mt);
           
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                exists = true;
            }
            // Đóng tài nguyên
            rs.close();
            pstmt.close();
            ketNoi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

                
        
}
    

    

