/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.awt.Font;
import java.awt.Image;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import moli.com.modal.ct_MuonTra;
import moli.com.modal.muonTra;
import moli.com.modal.nhanVien;

/**
 *
 * @author HP
 */
public class muonTraDao {
 public static boolean themPhieuMuon(String soThe, String maNV, Date ngayMuon, Date ngayHenTra) {
      java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
       
      if (ngayHenTra.compareTo(now) < 0) {
          JOptionPane.showMessageDialog(null, "Ngày hẹn trả phải lớn hơn ngày hẹn tại !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
          return false;   
      }
    if(poli.com.dao.soTheDao.check_tinhTrang(soThe))
         {
              JOptionPane.showMessageDialog(null, "Thẻ đã hết hạn!!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
              return false;   
         }
     
         if (ngayMuon.compareTo(ngayHenTra) > 0) {
          JOptionPane.showMessageDialog(null, "Ngày hẹn trả phải lớn hơn ngày mượn !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
          return false;                 
        }
        
        if(poli.com.dao.docGiaDao.check_tinhTrang(soThe))
        {
             JOptionPane.showMessageDialog(null, "Độc giả này đang bị cấm!!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return false;   
        }
           boolean check = poli.com.dao.muonTraDao.check_the(soThe);
        if(check==true)
        {
           int choice = JOptionPane.showConfirmDialog(null, "Độc giả này hiện đã có phiếu mượn bạn có muốn lập thêm?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
            if (choice == JOptionPane.YES_OPTION) {
                
                if(check_tinhTrang_soThe(soThe)==true)
                {
                    
                     JOptionPane.showMessageDialog(null, "Độc giả này chưa trả hết sách hoặc đang ở tình trạng quá hạn không thể lập thêm phiếu!!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else
                {
                     SQLServerProvider ketNoi = new SQLServerProvider();
                      ketNoi.open();

                      try {
                          // Tạo câu lệnh gọi stored procedure
                          String sql = "{call ThemMuonTra(?, ?, ?, ?)}";

                          // Tạo CallableStatement từ kết nối và câu lệnh SQL
                          CallableStatement callableStatement = ketNoi.prepareCall(sql);

                          // Thiết lập giá trị cho các tham số của stored procedure
                          callableStatement.setString(1, soThe);
                          callableStatement.setString(2, maNV);
                          callableStatement.setDate(3, ngayMuon);
                          callableStatement.setDate(4, ngayHenTra);

                          // Thực thi stored procedure
                          int rowsAffected = callableStatement.executeUpdate();

                          // Đóng kết nối
                          ketNoi.close();





                          // Nếu có ít nhất một hàng được thêm vào, trả về true
                          if(rowsAffected>0)
                          {
                                JOptionPane.showMessageDialog(null, "Thêm thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
                               return true;
                          }
                      } catch (SQLException ex) {
                           JOptionPane.showMessageDialog(null, "Thất bại !!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                          return false;   
                      }
                }
                
            }else
            {
                return false;
            }
        }
        else
        {
              SQLServerProvider ketNoi = new SQLServerProvider();
                      ketNoi.open();

                      try {
                          // Tạo câu lệnh gọi stored procedure
                          String sql = "{call ThemMuonTra(?, ?, ?, ?)}";

                          // Tạo CallableStatement từ kết nối và câu lệnh SQL
                          CallableStatement callableStatement = ketNoi.prepareCall(sql);

                          // Thiết lập giá trị cho các tham số của stored procedure
                          callableStatement.setString(1, soThe);
                          callableStatement.setString(2, maNV);
                          callableStatement.setDate(3, ngayMuon);
                          callableStatement.setDate(4, ngayHenTra);

                          // Thực thi stored procedure
                          int rowsAffected = callableStatement.executeUpdate();

                          // Đóng kết nối
                          ketNoi.close();





                          // Nếu có ít nhất một hàng được thêm vào, trả về true
                          if(rowsAffected>0)
                          {
                                JOptionPane.showMessageDialog(null, "Thêm thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
                               return true;
                          }
                      } catch (SQLException ex) {
                           JOptionPane.showMessageDialog(null, "Thất bại !!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                          return false;   
                      }
        }
     
  
    return false;
}
 public static void UpdateStatusAndAddToDSQuaHan(String maMT, Date ngayHenTra) {
        SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();

        try {
            // Tạo câu lệnh gọi stored procedure
            String sql = "{call UpdateStatusAndAddToDSQuaHan(?, ?)}";

            // Tạo CallableStatement từ kết nối và câu lệnh SQL
            CallableStatement callableStatement = ketNoi.prepareCall(sql);

            // Thiết lập giá trị cho các tham số của stored procedure
            callableStatement.setString(1, maMT);
            callableStatement.setDate(2, new java.sql.Date(ngayHenTra.getTime()));

            // Thực thi stored procedure
            int rowsAffected = callableStatement.executeUpdate();

            // Đóng kết nối
            ketNoi.close();

            // Nếu có ít nhất một hàng được thêm vào, trả về true
            
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
    }
 public static ArrayList<moli.com.modal.muonTra> hienThi_ALL()
    {
        ArrayList<moli.com.modal.muonTra> ds= new ArrayList<moli.com.modal.muonTra>();
        String sql_select ="select * from muonTra";
        ResultSet resultSet = null;
        try {
            poli.com.dao.SQLServerProvider lServerProvider = new SQLServerProvider();
            lServerProvider.open();
            resultSet =  lServerProvider.excuteQuery(sql_select);
            while(resultSet.next())
            {
                moli.com.modal.muonTra s = new muonTra();
                s.setMaMT(resultSet.getString(1));
                s.setSoThe(resultSet.getString(2));
                s.setMaNV(resultSet.getString(3));
                s.setNgayMuon(resultSet.getDate(4));
                s.setNgayHenTra(resultSet.getDate(5));
                s.setTinhTrang(resultSet.getString(6));
              
                ds.add(s);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return ds;
    }

  public static void createDesignedTable(ArrayList<muonTra> dsx, JTable tb_sach) {
        // Tạo một DefaultTableModel không cho phép chỉnh sửa
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };
        
        // Thêm cột cho bảng
        model.addColumn("Mã mượn trả");
        model.addColumn("Số thẻ"); 
        model.addColumn("Mã nhân viên"); 
        model.addColumn("Ngày mượn"); 
        model.addColumn("Ngày hẹn trả"); 
        model.addColumn("Tình trạng"); 

        // Thêm dữ liệu từ danh sách vào bảng
        for(muonTra s : dsx) {
            model.addRow(new Object[]{s.getMaMT(), s.getSoThe(), s.getMaNV(), s.getNgayMuon(), s.getNgayHenTra(), s.getTinhTrang()});
        }

        // Gán mô hình cho bảng
        tb_sach.setModel(model);
    }
  public static boolean check_the(String soThe) {
    boolean exists = false;
    poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
    knoi.open();
    String sql = "SELECT 1 FROM muontra WHERE sothe =?";
    try {
        PreparedStatement pstmt = knoi.prepareStatement(sql);
        pstmt.setString(1, soThe);
        
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
  public static boolean check_tinhTrang(String mamt) {
        boolean isOverdue = false;
        SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "SELECT tinhTrang FROM muontra WHERE MaMT = ?"; // Thay TenBang bằng tên bảng chứa thông tin mã mượn trả
        try {
            PreparedStatement pstmt = knoi.prepareStatement(sql);
            pstmt.setString(1, mamt);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tinhTrang = rs.getString("tinhTrang");
                if (tinhTrang != null && tinhTrang.equals("Quá hạn")) {
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
   public static boolean check_tinhTrang_soThe(String soThe) {
        boolean isOverdue = false;
        SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "SELECT tinhTrang FROM muontra WHERE sothe = ?"; // Thay TenBang bằng tên bảng chứa thông tin mã mượn trả
        try {
            PreparedStatement pstmt = knoi.prepareStatement(sql);
            pstmt.setString(1, soThe);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                 String tinhTrang = rs.getString("tinhTrang");
                if ( tinhTrang.equals("Quá hạn")||(tinhTrang.equals("Chưa trả"))) {
                   return true;
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
    
       public static boolean SuaMT(String manv, String mamt, Date henTra) {
           
    String sql = "UPDATE MuonTra SET manv = ?, ngayHenTra = ? WHERE mamt = ?";
    try {
        // Mở kết nối đến cơ sở dữ liệu
        SQLServerProvider sqlServerProvider = new SQLServerProvider();
        sqlServerProvider.open();

        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = sqlServerProvider.prepareStatement(sql);

        // Thiết lập giá trị cho các tham số của câu lệnh SQL
        preparedStatement.setString(1, manv);
        preparedStatement.setDate(2, henTra);
        preparedStatement.setString(3, mamt);
    

        // Thực thi câu lệnh SQL
        int numRowsUpdated = preparedStatement.executeUpdate();

        // Đóng kết nối
        sqlServerProvider.close();

        // Trả về true nếu có ít nhất một hàng được cập nhật thành công
        return numRowsUpdated > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

    public static boolean Xoa(String mamt, String tinhTrang) {
    if (tinhTrang.equals("Quá hạn")) {
        JOptionPane.showMessageDialog(null, "Quá hạn không được xóa!!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if (tinhTrang.equals("Chưa trả")&&(poli.com.dao.ct_MuonTraDao.check_tonTai(mamt))) {
        JOptionPane.showMessageDialog(null, "Chưa trả không thể xóa !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh gọi stored procedure
        String sql = "{call Delete_MuonTra(?)}";

        // Tạo CallableStatement từ kết nối và câu lệnh SQL
        CallableStatement callableStatement = ketNoi.prepareCall(sql);

        // Thiết lập giá trị cho tham số mamt của stored procedure
        callableStatement.setString(1, mamt);

        // Thực thi stored procedure
        int rowsAffected = callableStatement.executeUpdate();

        // Đóng kết nối
        ketNoi.close();

        // Xử lý thông báo dựa trên số dòng bị ảnh hưởng
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Xóa thành công !!!", "Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu nào bị xóa !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Xóa thất bại !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
    return false;
}


     public static void LOAD_TinhTRANG( String mamt, String tinhT) {
         if(tinhT.equals("Quá hạn"))
         {
             
              return; 
         }
         
         
       
        SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();

        try {
            // Tạo câu lệnh gọi stored procedure
            String sql = "{call UpdateMuonTraStatus(?)}";

            // Tạo CallableStatement từ kết nối và câu lệnh SQL
            CallableStatement callableStatement = ketNoi.prepareCall(sql);

            // Thiết lập giá trị cho các tham số của stored procedure
            callableStatement.setString(1, mamt);          

            // Thực thi stored procedure
            int rowsAffected = callableStatement.executeUpdate();

            // Đóng kết nối
            ketNoi.close();

            // Nếu có ít nhất một hàng được thêm vào, trả về true
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            {
               
                return; 
            }
        }
         }
     
 public static ArrayList<moli.com.modal.muonTra> thongKeDsMuonTheoTuan() {
    ArrayList<moli.com.modal.muonTra> ds = new ArrayList<moli.com.modal.muonTra>();
     poli.com.dao.SQLServerProvider knoi =  new SQLServerProvider();
    try {
       
        knoi.open(); // Mở kết nối đến cơ sở dữ liệu

        String sql = "{call ThongKePhieuMuonTrongTuan()}"; // Chuẩn bị câu lệnh gọi stored procedure

        CallableStatement callStatement = knoi.prepareCall(sql); // Chuẩn bị gọi stored procedure
        ResultSet resultSet = callStatement.executeQuery(); // Thực thi stored procedure

        while (resultSet.next()) { // Xử lý kết quả trả về
            moli.com.modal.muonTra s = new moli.com.modal.muonTra();
            s.setMaMT(resultSet.getString(1));
            s.setSoThe(resultSet.getString(2));
            s.setMaNV(resultSet.getString(3));
            s.setNgayMuon(resultSet.getDate(4));
            s.setNgayHenTra(resultSet.getDate(5));
            s.setTinhTrang(resultSet.getString(6));
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
  public static ArrayList<moli.com.modal.muonTra> thongKeDsMuonTheoTuan_ChuaTra() {
    ArrayList<moli.com.modal.muonTra> ds = new ArrayList<moli.com.modal.muonTra>();
     poli.com.dao.SQLServerProvider knoi =  new SQLServerProvider();
    try {
       
        knoi.open(); // Mở kết nối đến cơ sở dữ liệu

        String sql = "{call ThongKePhieuMuonTrongTuanChuaTra()}"; // Chuẩn bị câu lệnh gọi stored procedure

        CallableStatement callStatement = knoi.prepareCall(sql); // Chuẩn bị gọi stored procedure
        ResultSet resultSet = callStatement.executeQuery(); // Thực thi stored procedure

        while (resultSet.next()) { // Xử lý kết quả trả về
            moli.com.modal.muonTra s = new moli.com.modal.muonTra();
            s.setMaMT(resultSet.getString(1));
            s.setSoThe(resultSet.getString(2));
            s.setMaNV(resultSet.getString(3));
            s.setNgayMuon(resultSet.getDate(4));
            s.setNgayHenTra(resultSet.getDate(5));
            s.setTinhTrang(resultSet.getString(6));
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
   public static ArrayList<moli.com.modal.muonTra> thongKeDsMuonTheoTuan_DaTra() {
    ArrayList<moli.com.modal.muonTra> ds = new ArrayList<moli.com.modal.muonTra>();
     poli.com.dao.SQLServerProvider knoi =  new SQLServerProvider();
    try {
       
        knoi.open(); // Mở kết nối đến cơ sở dữ liệu

        String sql = "{call ThongKePhieuMuonTrongTuanDaTra()}"; // Chuẩn bị câu lệnh gọi stored procedure

        CallableStatement callStatement = knoi.prepareCall(sql); // Chuẩn bị gọi stored procedure
        ResultSet resultSet = callStatement.executeQuery(); // Thực thi stored procedure

        while (resultSet.next()) { // Xử lý kết quả trả về
            moli.com.modal.muonTra s = new moli.com.modal.muonTra();
            s.setMaMT(resultSet.getString(1));
            s.setSoThe(resultSet.getString(2));
            s.setMaNV(resultSet.getString(3));
            s.setNgayMuon(resultSet.getDate(4));
            s.setNgayHenTra(resultSet.getDate(5));
            s.setTinhTrang(resultSet.getString(6));
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
       public static ArrayList<moli.com.modal.muonTra> timKiemMuonTra(String tk) {
    ArrayList<moli.com.modal.muonTra> mt = new ArrayList<moli.com.modal.muonTra>();
    try {
        String sqlSelect = "SELECT * FROM muontra WHERE mamt LIKE ? OR manv LIKE ? OR tinhtrang LIKE ? or sothe LIKE ?";
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        PreparedStatement preparedStatement = provider.prepareStatement(sqlSelect);
        preparedStatement.setString(1, "%" + tk + "%");
        preparedStatement.setString(2, "%" + tk + "%");
         preparedStatement.setString(3, "%" + tk + "%");
          preparedStatement.setString(4, "%" + tk + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            moli.com.modal.muonTra s = new moli.com.modal.muonTra();
             s.setMaMT(resultSet.getString(1));
            s.setSoThe(resultSet.getString(2));
            s.setMaNV(resultSet.getString(3));
            s.setNgayMuon(resultSet.getDate(4));
            s.setNgayHenTra(resultSet.getDate(5));
            s.setTinhTrang(resultSet.getString(6));
            mt.add(s);
        }
        provider.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return mt;
}
          public static ArrayList<moli.com.modal.muonTra> timkiemTT(String tk) {
    ArrayList<moli.com.modal.muonTra> mt = new ArrayList<moli.com.modal.muonTra>();
    try {
        String sqlSelect = "SELECT * FROM muontra WHERE  tinhtrang LIKE ?";
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        PreparedStatement preparedStatement = provider.prepareStatement(sqlSelect);
        preparedStatement.setString(1, "%" + tk + "%");
      
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            moli.com.modal.muonTra s = new moli.com.modal.muonTra();
             s.setMaMT(resultSet.getString(1));
            s.setSoThe(resultSet.getString(2));
            s.setMaNV(resultSet.getString(3));
            s.setNgayMuon(resultSet.getDate(4));
            s.setNgayHenTra(resultSet.getDate(5));
            s.setTinhTrang(resultSet.getString(6));
            mt.add(s);
        }
        provider.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return mt;
}
   public static ArrayList<String> laySL_Muon() {
      ArrayList<String> dsSO = new ArrayList<String>();
     poli.com.dao.SQLServerProvider knoi =  new SQLServerProvider();
    try {
       
        knoi.open(); // Mở kết nối đến cơ sở dữ liệu

        String sql = "{call CountMuonTraByWeekday()}"; // Chuẩn bị câu lệnh gọi stored procedure

        CallableStatement callStatement = knoi.prepareCall(sql); // Chuẩn bị gọi stored procedure
        ResultSet resultSet = callStatement.executeQuery(); // Thực thi stored procedure

        while (resultSet.next()) { // Xử lý kết quả trả về
            int so = 0;
            so = resultSet.getInt(2);
            dsSO.add(so+"");
        }

        resultSet.close(); // Đóng ResultSet
        callStatement.close(); // Đóng CallableStatement
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        knoi.close(); // Đóng kết nối
    }
    return dsSO;
}
}
    
 
