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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import moli.com.modal.muonTra;
import moli.com.modal.taiKhoan;

/**
 *
 * @author HP
 */
public class taiKhoanDao {
   public static int checkDangNhap(String manv, String pass) {
    int count = -1;
    SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh SQL SELECT để kiểm tra đăng nhập
        String sql = "SELECT quyen FROM taikhoan WHERE MaNV = ? AND Matkhau = ?";
        
        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);
        
        // Thiết lập giá trị cho các tham số của câu lệnh SQL
        preparedStatement.setString(1, manv);
        preparedStatement.setString(2, pass);
      
        // Thực thi câu lệnh SQL
        ResultSet resultSet = preparedStatement.executeQuery();
        
        // Kiểm tra kết quả trả về từ ResultSet
        if(resultSet.next()) {
            count = resultSet.getInt("quyen");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        // Đóng kết nối
        ketNoi.close();
    }
    
    return count;
}
   public static boolean xoaTaiKhoan(String manv) {
    // Mở kết nối đến cơ sở dữ liệu
    poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
        String sql = "DELETE FROM taikhoan WHERE MaNV = ?";
        
        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);
        
        // Thiết lập giá trị cho tham số của câu lệnh SQL
        preparedStatement.setString(1, manv);
        
        // Thực thi câu lệnh SQL
        int rowsAffected = preparedStatement.executeUpdate();
       
        // Kiểm tra xem có bản ghi nào bị ảnh hưởng không
        if (rowsAffected > 0) {
            
            return true; // Trả về true nếu xóa thành công
        } else {
           
            return false; // Trả về false nếu không tìm thấy bản ghi để xóa
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false; // Trả về false nếu có lỗi xảy ra
    } finally {
        // Đóng kết nối
        ketNoi.close();
       
    }
}
   public  static boolean kiemTraKT(String manv)
   {
        boolean count = false;
        SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();

        try {
            // Tạo câu lệnh SQL SELECT để kiểm tra đăng nhập
            String sql = "SELECT * FROM taikhoan WHERE MaNV = ?";

            // Tạo PreparedStatement từ kết nối và câu lệnh SQL
            PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số của câu lệnh SQL
            preparedStatement.setString(1, manv);
            

            // Thực thi câu lệnh SQL
            ResultSet resultSet = preparedStatement.executeQuery();

            // Kiểm tra kết quả trả về từ ResultSet
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Đóng kết nối
            ketNoi.close();
        }

         return count;
   }
    public static boolean themTK(String manv, String pass, int quyen) {
    // Mở kết nối đến cơ sở dữ liệu
    poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
        String sql = "INSERT INTO taikhoan VALUES (?, ?, ?)";
        
        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);
        
        // Thiết lập giá trị cho tham số của câu lệnh SQL
        preparedStatement.setString(1, manv);
        preparedStatement.setString(2, pass);
        preparedStatement.setInt(3, quyen);
        
        // Thực thi câu lệnh SQL
        int rowsAffected = preparedStatement.executeUpdate();
        
        // Kiểm tra xem có bản ghi nào bị ảnh hưởng không
        if (rowsAffected > 0) {
            
            return true; // Trả về true nếu xóa thành công
        } else {
           
            return false; // Trả về false nếu không tìm thấy bản ghi để xóa
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false; // Trả về false nếu có lỗi xảy ra
    } finally {
        // Đóng kết nối
        ketNoi.close();
    }
}
    public static boolean CapNhatQuyen(String manv, String chucVu) {
    int quyen = chucVu.equals("Quản lí") ? 0 : 1;

    poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        String sql = "UPDATE taikhoan SET quyen = ? WHERE manv = ?";
        
        PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);
        
        preparedStatement.setInt(1, quyen);
        preparedStatement.setString(2, manv);
        
        int rowsAffected = preparedStatement.executeUpdate();
        
        return rowsAffected > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    } finally {
        ketNoi.close();
    }
}
    public static ArrayList<moli.com.modal.taiKhoan> xuatDSTT_ALL()
    {
        ArrayList<moli.com.modal.taiKhoan> dstt = new ArrayList<moli.com.modal.taiKhoan>();
        String sql = "Select *from taikhoan";
        poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        ResultSet resultSet = knoi.excuteQuery(sql);
       try {
           while(resultSet.next())
           {
               moli.com.modal.taiKhoan a = new taiKhoan();
               a.setMaNV(resultSet.getString(1));
               a.setMatKhau(resultSet.getString(2));
               a.setQuyen(resultSet.getInt(3));
               dstt.add(a);
           }
       } catch (SQLException ex) {
           Logger.getLogger(taiKhoanDao.class.getName()).log(Level.SEVERE, null, ex);
       }
       return dstt;
    }
       public static ArrayList<moli.com.modal.taiKhoan> xuatDSTT_NV()
    {
        ArrayList<moli.com.modal.taiKhoan> dstt = new ArrayList<moli.com.modal.taiKhoan>();
        String sql = "Select *from taikhoan where quyen = 1";
        poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        ResultSet resultSet = knoi.excuteQuery(sql);
       try {
           while(resultSet.next())
           {
               moli.com.modal.taiKhoan a = new taiKhoan();
               a.setMaNV(resultSet.getString(1));
               a.setMatKhau(resultSet.getString(2));
               a.setQuyen(resultSet.getInt(3));
               dstt.add(a);
           }
       } catch (SQLException ex) {
           Logger.getLogger(taiKhoanDao.class.getName()).log(Level.SEVERE, null, ex);
       }
       return dstt;
    }
        public static void createDesignedTable(ArrayList<taiKhoan> dsx, JTable tbTT) {
        // Tạo một DefaultTableModel không cho phép chỉnh sửa
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };
        
        // Thêm cột cho bảng
        model.addColumn("Mã nhân viên");
        model.addColumn("Tên nhân viên"); 
        model.addColumn("Mật khẩu"); 
        model.addColumn("Quyền");      
        // Thêm dữ liệu từ danh sách vào bảng
        for(taiKhoan s : dsx) {
            model.addRow(new Object[]{s.getMaNV(), poli.com.dao.nhanVienDao.layTen(s.getMaNV()), s.getMatKhau(), s.getQuyen() == 1 ? "Nhân viên" : "Quản lí"});
        }

        // Gán mô hình cho bảng
        tbTT.setModel(model);
    }
   public static boolean CapNhatPASSWORD(String manv, String matkhau) {
    

    poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
    ketNoi.open();

    try {
        String sql = "UPDATE taikhoan SET matkhau = ? WHERE manv = ?";
        
        PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);
        
        preparedStatement.setString(1, matkhau);
        preparedStatement.setString(2, manv);
        
        int rowsAffected = preparedStatement.executeUpdate();
        
        return rowsAffected > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    } finally {
        ketNoi.close();
    }
}

    
}
