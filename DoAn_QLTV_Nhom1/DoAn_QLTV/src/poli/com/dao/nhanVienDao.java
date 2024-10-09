/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.awt.Font;
import java.awt.Image;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;
import moli.com.modal.nhanVien;
import moli.com.modal.sach;
import poli.com.dao.SQLServerProvider;
import poli.com.dao.model;

/**
 *
 * @author HP
 */
public class nhanVienDao {
       public static ArrayList<moli.com.modal.nhanVien> hienThiSach_ALL()
    {
        ArrayList<moli.com.modal.nhanVien> ds_nv= new ArrayList<moli.com.modal.nhanVien>();
        String sql_select ="select * from nhanvien";
        ResultSet resultSet = null;
        try {
            poli.com.dao.SQLServerProvider lServerProvider = new SQLServerProvider();
            lServerProvider.open();
            resultSet =  lServerProvider.excuteQuery(sql_select);
            while(resultSet.next())
            {
                moli.com.modal.nhanVien s = new nhanVien();
                s.setHinhAnh(resultSet.getString(1));
                s.setMaNV(resultSet.getString(2));
                s.setTenNv(resultSet.getString(3));
                s.setGioiTinh(resultSet.getString(4));
                s.setNgaySinh(resultSet.getDate(5));
                s.setDiaChi(resultSet.getString(6));
                s.setNgayVL(resultSet.getDate(7));
                s.setSDT(resultSet.getString(8));
                s.setChucVu(resultSet.getString(9));
                ds_nv.add(s);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return ds_nv;
    }
    public static void createDesignedTable(ArrayList<moli.com.modal.nhanVien> dsnv, JTable tb_sach) {
              
        String[] t = {"Hình ảnh","Mã nhân viên","Tên nhân viên","Giới tính","Ngày sinh","Địa chỉ","Ngày vào làm", "Số điện thoại","Chức vụ"};
        Object[][] row = new Object[dsnv.size()][9];
        for (int i = 0; i < dsnv.size(); i++) {
             String imagePath = dsnv.get(i).getHinhAnh();
             if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                if (imageIcon != null && imageIcon.getImage() != null) {
                    Image scaledImage = imageIcon.getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    row[i][0] = scaledImageIcon;
                } else {
                    row[i][0] = null;
                }
            } else {
                row[i][0] = null;
            }
            row[i][1] = dsnv.get(i).getMaNV();
            row[i][2] = dsnv.get(i).getTenNv();
             row[i][3] = dsnv.get(i).getGioiTinh();
            row[i][4] = dsnv.get(i).getNgaySinh();
            row[i][5] = dsnv.get(i).getDiaChi();
            row[i][6] = dsnv.get(i).getNgayVL();
            row[i][7] = dsnv.get(i).getSDT();
            row[i][8] = dsnv.get(i).getChucVu();
            
                                                    
        }
        poli.com.dao.model model = new model(t, row); 
        
        tb_sach.setModel(model);
        tb_sach.setRowHeight(160);
        tb_sach.getColumnModel().getColumn(0).setPreferredWidth(36);
        JTableHeader header = tb_sach.getTableHeader();
        header.setPreferredSize(new java.awt.Dimension(header.getWidth(), 40));
        header.setFont(new Font("Arial", Font.BOLD, 20));    
        for (int i = 0; i < tb_sach.getColumnCount(); i++) {
            tb_sach.getColumnModel().getColumn(i).setResizable(false);
        }
    }
    public static String chooseFilePath(JFrame parentFrame) {
        // Tạo một đối tượng JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Thiết lập bộ lọc cho tệp để chỉ hiển thị các tệp hình ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Ảnh", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        // Hiển thị hộp thoại chọn tệp và lắng nghe sự kiện người dùng chọn tệp
        int returnVal = fileChooser.showOpenDialog(parentFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn của tệp đã chọn
            return fileChooser.getSelectedFile().getPath();
        } else {
            // Người dùng không chọn tệp, hoặc hủy bỏ hộp thoại
            return "";
        }
    }
    public static String[] select_ChucVu() {
        String [] dscv = null;
        ArrayList<String> dsCVList = new ArrayList<String>();

        try {
            String sql = "SELECT DISTINCT ChucVu FROM NhanVien;";
            poli.com.dao.SQLServerProvider con = new SQLServerProvider();
            con.open();
            ResultSet resultSet = con.excuteQuery(sql);

            while (resultSet.next()) {
                String chucVu = resultSet.getString(1);
                dsCVList.add(chucVu);
            }

            // Chuyển danh sách thành mảng
            dscv = dsCVList.toArray(new String[0]);
             
            
        } catch (SQLException ex) {
            Logger.getLogger(nhanVienDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng kết nối hoặc tài nguyên
           
            // (Không cần thiết nếu bạn đã đóng trong phương thức 'excuteQuery')
        }
        
        return dscv; // Trả về null nếu có lỗi
    }
        public static boolean insertNhanVien_FULL(String anh, String ma, String ten, Date ngaySinh, Date ngayVL, String sdt, String gioiTinh, String diaChi, String chucVu) {
        String sql = "INSERT INTO NhanVien VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            // Mở kết nối đến cơ sở dữ liệu
            SQLServerProvider sqlServerProvider = new SQLServerProvider();
            sqlServerProvider.open();

            // Tạo PreparedStatement từ kết nối và câu lệnh SQL
            PreparedStatement preparedStatement = sqlServerProvider.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số của câu lệnh SQL

            preparedStatement.setString(1, anh);
            preparedStatement.setString(2, ma);
            preparedStatement.setString(3, ten);
            preparedStatement.setString(4,gioiTinh);
            preparedStatement.setDate(5, ngaySinh);

            preparedStatement.setString(6, diaChi);
            preparedStatement.setDate(7, ngayVL);
            preparedStatement.setString(8, sdt);
            preparedStatement.setString(9, chucVu);

            // Thực thi câu lệnh SQL
            int numRowsInserted = preparedStatement.executeUpdate();

            // Đóng kết nối
            sqlServerProvider.close();

            // Trả về true nếu có ít nhất một hàng được chèn thành công
            return numRowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
}
    public static boolean insertNhanVien_NOT_IMAGE( String ma, String ten, Date ngaySinh, Date ngayVL, String sdt, String gioiTinh, String diaChi, String chucVu) {
    String sql = "INSERT INTO NhanVien(manv,tennv,gioitinh,ngaysinh,diachi,ngayvl,sodt,chucvu) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        // Mở kết nối đến cơ sở dữ liệu
        SQLServerProvider sqlServerProvider = new SQLServerProvider();
        sqlServerProvider.open();
        
        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = sqlServerProvider.prepareStatement(sql);
        
        // Thiết lập giá trị cho các tham số của câu lệnh SQL
        
        
        preparedStatement.setString(1, ma);
        preparedStatement.setString(2, ten);
        preparedStatement.setString(3,gioiTinh);
        preparedStatement.setDate(4, ngaySinh);

        preparedStatement.setString(5, diaChi);
        preparedStatement.setDate(6, ngayVL);
        preparedStatement.setString(7, sdt);
        preparedStatement.setString(8, chucVu);
        
        // Thực thi câu lệnh SQL
        int numRowsInserted = preparedStatement.executeUpdate();
        
        // Đóng kết nối
        sqlServerProvider.close();
        
        // Trả về true nếu có ít nhất một hàng được chèn thành công
        return numRowsInserted > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}
    public static boolean check_maNV(String manv)
    {
        String sql = "Select *from Nhanvien where manv ='"+manv+"'";
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();
        ResultSet resultSet = ketNoi.excuteQuery(sql);
        try {
            if(resultSet.next())
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(nhanVienDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean updateNhanVien(String anh, String ma, String ten, Date ngaySinh, Date ngayVL, String sdt, String gioiTinh, String diaChi, String chucVu) {
        boolean checktk = poli.com.dao.taiKhoanDao.kiemTraKT(ma);
        if(checktk == true)
        {
            poli.com.dao.taiKhoanDao.CapNhatQuyen(ma, chucVu);
        }
    String sql = "UPDATE NhanVien SET HinhAnh = ?, TenNV = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, NgayVL = ?, SoDT = ?, ChucVu = ? WHERE MaNV = ?";
    try {
        // Mở kết nối đến cơ sở dữ liệu
        SQLServerProvider sqlServerProvider = new SQLServerProvider();
        sqlServerProvider.open();

        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = sqlServerProvider.prepareStatement(sql);

        // Thiết lập giá trị cho các tham số của câu lệnh SQL
        preparedStatement.setString(1, anh);
        preparedStatement.setString(2, ten);
        preparedStatement.setString(3, gioiTinh);
        preparedStatement.setDate(4, ngaySinh);
        preparedStatement.setString(5, diaChi);
        preparedStatement.setDate(6, ngayVL);
        preparedStatement.setString(7, sdt);
        preparedStatement.setString(8, chucVu);
        preparedStatement.setString(9, ma);

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
     public static boolean updateNhanVien_NOT_IMG( String ma, String ten, Date ngaySinh, Date ngayVL, String sdt, String gioiTinh, String diaChi, String chucVu) {
          boolean checktk = poli.com.dao.taiKhoanDao.kiemTraKT(ma);
        if(checktk == true)
        {
            poli.com.dao.taiKhoanDao.CapNhatQuyen(ma, chucVu);
        }
    String sql = "UPDATE NhanVien SET  TenNV = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, NgayVL = ?, SoDT = ?, ChucVu = ? WHERE MaNV = ?";
    try {
        // Mở kết nối đến cơ sở dữ liệu
        SQLServerProvider sqlServerProvider = new SQLServerProvider();
        sqlServerProvider.open();

        // Tạo PreparedStatement từ kết nối và câu lệnh SQL
        PreparedStatement preparedStatement = sqlServerProvider.prepareStatement(sql);

        // Thiết lập giá trị cho các tham số của câu lệnh SQL
      
        preparedStatement.setString(1, ten);
        preparedStatement.setString(2, gioiTinh);
        preparedStatement.setDate(3, ngaySinh);
        preparedStatement.setString(4, diaChi);
        preparedStatement.setDate(5, ngayVL);
        preparedStatement.setString(6, sdt);
        preparedStatement.setString(7, chucVu);
        preparedStatement.setString(8, ma);

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
     public  static boolean xoa_NhanVien(String maNV)
     {
      
     poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();

        try {
            // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
            String sql = "DELETE FROM nhanvien where manv = ?";

            // Tạo PreparedStatement từ kết nối và câu lệnh SQL
            PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);

            // Thiết lập giá trị cho tham số của câu lệnh SQL
            preparedStatement.setString(1, maNV);

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
     public  static String []layTen_AVT(String manv)
     {
         String [] nv = null;
           try {
               
               
               ArrayList<String> dsCVList = new ArrayList<String>();
               poli.com.dao.SQLServerProvider ketNOI = new SQLServerProvider();
               ketNOI.open();
               String sql =  "Select tennv,hinhanh, chucvu from nhanvien where manv ='"+manv+"'";
               ResultSet resultSet = ketNOI.excuteQuery(sql);
               while(resultSet.next())
               {
                   
                   dsCVList.add(resultSet.getString(1));
                   dsCVList.add(resultSet.getString(2));
                    dsCVList.add(resultSet.getString(3));
                   
               }
                 nv = dsCVList.toArray(new String[0]);
                 ketNOI.close();
           } catch (SQLException ex) {
               Logger.getLogger(nhanVienDao.class.getName()).log(Level.SEVERE, null, ex);
           }
             return nv;
         
     }
      public static boolean check_Quyen_maNV(String manv)
    {
         boolean isOverdue = false;
        SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "SELECT chucvu FROM nhanvien WHERE manv = ?"; // Thay TenBang bằng tên bảng chứa thông tin mã mượn trả
        try {
            PreparedStatement pstmt = knoi.prepareStatement(sql);
            pstmt.setString(1, manv);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tinhTrang = rs.getString("chucvu");
                if (tinhTrang != null && tinhTrang.equals("Nhân viên")) {
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
      public  static String layTen(String manv)
     {
         String  nv = "";
           try {
               
               
               
               poli.com.dao.SQLServerProvider ketNOI = new SQLServerProvider();
               ketNOI.open();
               String sql =  "Select tennv from nhanvien where manv ='"+manv+"'";
               ResultSet resultSet = ketNOI.excuteQuery(sql);
               while(resultSet.next())
               {
                   
                  nv = resultSet.getString(1);
                
                   
               }
                 
                 ketNOI.close();
           } catch (SQLException ex) {
               Logger.getLogger(nhanVienDao.class.getName()).log(Level.SEVERE, null, ex);
           }
             return nv;
         
     }
      
        public static ArrayList<moli.com.modal.nhanVien> hienThiNV()
    {
        ArrayList<moli.com.modal.nhanVien> ds_nv= new ArrayList<moli.com.modal.nhanVien>();
        String sql_select ="select * from nhanvien where chucvu = N'Nhân viên'";
        ResultSet resultSet = null;
        try {
            poli.com.dao.SQLServerProvider lServerProvider = new SQLServerProvider();
            lServerProvider.open();
            resultSet =  lServerProvider.excuteQuery(sql_select);
            while(resultSet.next())
            {
                moli.com.modal.nhanVien s = new nhanVien();
                s.setHinhAnh(resultSet.getString(1));
                s.setMaNV(resultSet.getString(2));
                s.setTenNv(resultSet.getString(3));
                s.setGioiTinh(resultSet.getString(4));
                s.setNgaySinh(resultSet.getDate(5));
                s.setDiaChi(resultSet.getString(6));
                s.setNgayVL(resultSet.getDate(7));
                s.setSDT(resultSet.getString(8));
                s.setChucVu(resultSet.getString(9));
                ds_nv.add(s);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return ds_nv;
    }
         public static ArrayList<moli.com.modal.nhanVien> timKiemNV(String maNV, String tenNV) {
    ArrayList<moli.com.modal.nhanVien> nv = new ArrayList<moli.com.modal.nhanVien>();
    try {
        String sqlSelect = "SELECT * FROM nhanvien WHERE tennv LIKE ? OR manv LIKE ?";
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        PreparedStatement preparedStatement = provider.prepareStatement(sqlSelect);
        preparedStatement.setString(1, "%" + tenNV + "%");
        preparedStatement.setString(2, "%" + maNV + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            moli.com.modal.nhanVien s = new moli.com.modal.nhanVien();
                s.setHinhAnh(resultSet.getString(1));
                s.setMaNV(resultSet.getString(2));
                s.setTenNv(resultSet.getString(3));
                s.setGioiTinh(resultSet.getString(4));
                s.setNgaySinh(resultSet.getDate(5));
                s.setDiaChi(resultSet.getString(6));
                s.setNgayVL(resultSet.getDate(7));
                s.setSDT(resultSet.getString(8));
                s.setChucVu(resultSet.getString(9));
            nv.add(s);
        }
        provider.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nv;
}
         
         
         
                  public static ArrayList<moli.com.modal.nhanVien> timKiemNV_NV(String maNV, String tenNV) {
    ArrayList<moli.com.modal.nhanVien> nv = new ArrayList<moli.com.modal.nhanVien>();
    try {
        String sqlSelect = "SELECT * FROM nhanvien WHERE tennv LIKE ? and chucvu = 'Nhân viên' OR manv LIKE ? and chucvu = 'Nhân viên'";
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        PreparedStatement preparedStatement = provider.prepareStatement(sqlSelect);
        preparedStatement.setString(1, "%" + tenNV + "%");
        preparedStatement.setString(2, "%" + maNV + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            moli.com.modal.nhanVien s = new moli.com.modal.nhanVien();
                s.setHinhAnh(resultSet.getString(1));
                s.setMaNV(resultSet.getString(2));
                s.setTenNv(resultSet.getString(3));
                s.setGioiTinh(resultSet.getString(4));
                s.setNgaySinh(resultSet.getDate(5));
                s.setDiaChi(resultSet.getString(6));
                s.setNgayVL(resultSet.getDate(7));
                s.setSDT(resultSet.getString(8));
                s.setChucVu(resultSet.getString(9));
            nv.add(s);
        }
        provider.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nv;
}
         
    
}
