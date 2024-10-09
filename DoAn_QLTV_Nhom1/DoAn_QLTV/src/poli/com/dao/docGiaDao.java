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
public class docGiaDao {
    public  static String layTen_AVT(String sothe)
     {
         String  nv = "";
           try {
               
               
               ArrayList<String> dsCVList = new ArrayList<String>();
               poli.com.dao.SQLServerProvider ketNOI = new SQLServerProvider();
               ketNOI.open();
               String sql =  "Select tendg from docgia where sothe='"+sothe+"'";
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
    public static String[] select_soThe() {
        String [] ds = null;
        ArrayList<String> dsCVList = new ArrayList<String>();

        try {
            String sql = "SELECT DISTINCT sothe FROM thethuvien";
            poli.com.dao.SQLServerProvider con = new SQLServerProvider();
            con.open();
            ResultSet resultSet = con.excuteQuery(sql);

            while (resultSet.next()) {
                String sothe = resultSet.getString(1);
                dsCVList.add(sothe);
            }

            // Chuyển danh sách thành mảng
            ds = dsCVList.toArray(new String[0]);
             
            
        } catch (SQLException ex) {
            Logger.getLogger(nhanVienDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng kết nối hoặc tài nguyên
           
            // (Không cần thiết nếu bạn đã đóng trong phương thức 'excuteQuery')
        }
        
        return ds; // Trả về null nếu có lỗi
    }
    public static boolean check_tinhTrang(String sothe) {
        boolean isOverdue = false;
        SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "SELECT tinhTrang FROM docgia WHERE sothe = ?"; // Thay TenBang bằng tên bảng chứa thông tin mã mượn trả
        try {
            PreparedStatement pstmt = knoi.prepareStatement(sql);
            pstmt.setString(1, sothe);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tinhTrang = rs.getString("tinhTrang");
                if (tinhTrang != null && tinhTrang.equals("Bị cấm")) {
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
     public static ArrayList<moli.com.modal.docGia>hienThiDSDocGia(){
        ArrayList<moli.com.modal.docGia> dsDG = new ArrayList<moli.com.modal.docGia>();
        try {
            String sqlSelect = "Select * from DocGia";
            SQLServerProvider provider = new SQLServerProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sqlSelect);
            while(resultSet.next()){
                moli.com.modal.docGia dg = new moli.com.modal.docGia();
                dg.setMaDocGia(resultSet.getString(1));
                dg.setTenDG(resultSet.getString(2));
                dg.setGioiTinh(resultSet.getString(3));
                dg.setNgaySinh(resultSet.getDate(4));
                dg.setDiaChi(resultSet.getString(5));
                dg.setSoDT(resultSet.getString(6));
                dg.setSoThe(resultSet.getString(7));
                dg.setTinhTrang(resultSet.getString(8));
                dsDG.add(dg);
            }
            provider.close();
        } catch (Exception e) {
        }
        return dsDG;
    }


    //Thêm độc giả
    public static int themDocGia(String tenDocGia, String gioiTinh, Date ngaySinh, String diaChi, String soDT, String soThe) {
    try {
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        String sql = "{call themDocGia(?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = provider.prepareCall(sql);
        cstmt.setString(1, tenDocGia);
        cstmt.setString(2, gioiTinh);
        cstmt.setDate(3, ngaySinh);
        cstmt.setString(4, diaChi);
        cstmt.setString(5, soDT);
        cstmt.setString(6, soThe);
        return cstmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return -1;
}

    //Xóa độc giả
    public static boolean XoaDocGia(String maDG) {
    poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();

        try {
            // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
            String sql = "DELETE FROM DocGia where maDG = ?";

            // Tạo PreparedStatement từ kết nối và câu lệnh SQL
            PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);

            // Thiết lập giá trị cho tham số của câu lệnh SQL
            preparedStatement.setString(1, maDG);

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
    
    //Cập nhật độc giả
    public static int capNhatDocGia(moli.com.modal.docGia dg) {
    try {
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        String sql = "Update DocGia set tendg = ?, gioitinh = ?, ngaysinh = ?, diachi = ?, sodt = ?, sothe = ?, tinhtrang = ? where madg=?";
        PreparedStatement preStatement = provider.prepareStatement(sql);

        preStatement.setString(1, dg.getTenDG());
        preStatement.setString(2, dg.getGioiTinh());
        java.sql.Date ngaySinh = new java.sql.Date(dg.getNgaySinh().getTime());
        preStatement.setDate(3, ngaySinh);
        preStatement.setString(4, dg.getDiaChi());
        preStatement.setString(5, dg.getSoDT());
        preStatement.setString(6, dg.getSoThe());
        preStatement.setString(7, dg.getTinhTrang());
        preStatement.setString(8, dg.getMaDocGia());

        int result = preStatement.executeUpdate();
        provider.close();
        return result;
    } catch (Exception ex) {
        ex.printStackTrace();
        return -1;
    } 
    }
    

    //Tìm độc giả theo mã và tên
    public static ArrayList<moli.com.modal.docGia> timkiemDocGiaTheoTenMa(String tenDG, String maDG) {
    ArrayList<moli.com.modal.docGia> dsDG = new ArrayList<moli.com.modal.docGia>();
    try {
        String sqlSelect = "SELECT * FROM DocGia WHERE TenDG LIKE ? OR MaDG LIKE ?";
        SQLServerProvider provider = new SQLServerProvider();
        provider.open();
        PreparedStatement preparedStatement = provider.prepareStatement(sqlSelect);
        preparedStatement.setString(1, "%" + tenDG + "%");
        preparedStatement.setString(2, "%" + maDG + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            moli.com.modal.docGia dg = new moli.com.modal.docGia();
            dg.setMaDocGia(resultSet.getString(1));
            dg.setTenDG(resultSet.getString(2));
            dg.setGioiTinh(resultSet.getString(3));
            dg.setNgaySinh(resultSet.getDate(4));
            dg.setDiaChi(resultSet.getString(5));
            dg.setSoDT(resultSet.getString(6));
            dg.setSoThe(resultSet.getString(7));
            dg.setTinhTrang(resultSet.getString(8));
            dsDG.add(dg);
        }
        provider.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return dsDG;
}

    //Lay the thu vien chua ton tai
    public static ArrayList<String>hienThiSoTheChuaTonTai(){
        ArrayList<String> ds = new ArrayList<String>();
        try {
            String sqlSelect = "{call DanhSachSoTheKhongTonTaiTrongDocGia}";
            SQLServerProvider provider = new SQLServerProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sqlSelect);
            while(resultSet.next()){
                String str = resultSet.getString(1);
                ds.add(str);
            }
            provider.close();
        } catch (Exception e) {
        }
        return ds;
    }
    //Kiểm tra trùng số thẻ
    public static boolean check_TrungSoThe(String ma_mt) {
        boolean exists = false;
        SQLServerProvider  ketNoi = new SQLServerProvider();
        ketNoi.open();
        String sql = "SELECT 1 FROM DocGia WHERE Sothe = ? ";
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
