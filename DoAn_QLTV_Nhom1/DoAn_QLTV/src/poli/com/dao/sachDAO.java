    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.awt.Component;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import moli.com.modal.sach;

/**
 *
 * @author HP
 */
public class sachDAO {
    public static ArrayList<moli.com.modal.sach> hienThiSach_ALL()
    {
        ArrayList<moli.com.modal.sach> ds_sach = new ArrayList<moli.com.modal.sach>();
        String sql_select ="select * from sach";
        ResultSet resultSet = null;
        try {
            poli.com.dao.SQLServerProvider lServerProvider = new SQLServerProvider();
            lServerProvider.open();
            resultSet =  lServerProvider.excuteQuery(sql_select);
            while(resultSet.next())
            {
                moli.com.modal.sach s = new sach();
                s.setAnh(resultSet.getString(1));
                s.setMaSach(resultSet.getString(2));
                s.setTenSach(resultSet.getString(3));
                s.setMoTa(resultSet.getString(4));
                s.setMaTL(resultSet.getString(5));
                s.setMaTG(resultSet.getString(6));
                s.setMaNXB(resultSet.getString(7));
                s.setSoLuong(resultSet.getInt(8));
                s.setNamXB(resultSet.getInt(9));
                ds_sach.add(s);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return ds_sach;
    }
    public static DefaultTableModel load_sach(ArrayList<moli.com.modal.sach> dsSach)
    {
        
        DefaultTableModel ds = new DefaultTableModel();
        ds.addColumn("Mã sách");
        ds.addColumn("Tên sách"); 
        ds.addColumn("Mô tả"); 
        ds.addColumn("Mã thể loại"); 
        ds.addColumn("Mã tác giả"); 
        ds.addColumn("Mã nhà xuất bản"); 
        ds.addColumn("Số lượng"); 
        ds.addColumn("Năm sản xuất"); 
       
        ds.setRowCount(0);
        
                         
        for(moli.com.modal.sach s : dsSach)
        {
            Vector<Object> pbx = new Vector<Object>();
            pbx.add(s.getMaSach());
            pbx.add(s.getTenSach());
            pbx.add(s.getMoTa());
            pbx.add(s.getMaTL());
            pbx.add(s.getMaTG());
            pbx.add(s.getMaNXB());
            pbx.add(s.getSoLuong());
            pbx.add(s.getNamXB());
    
            ds.addRow(pbx);          
        }
        return ds;
                      
    }
    public static void createDesignedTable(ArrayList<moli.com.modal.sach> dsSach, JTable tb_sach) {
              
        String[] t = {"Hình ảnh","Mã sách","Tên sách","Mô tả","Mã thể loại","Mã tác giả","Mã nhà xuất bản", "Số lượng còn","Năm sản xuất"};
        Object[][] row = new Object[dsSach.size()][9];
        for (int i = 0; i < dsSach.size(); i++) {
             String imagePath = dsSach.get(i).getAnh();
             if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                if (imageIcon != null && imageIcon.getImage() != null) {
                    Image scaledImage = imageIcon.getImage().getScaledInstance(100, 172, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    row[i][0] = scaledImageIcon;
                } else {
                    row[i][0] = null;
                }
            } else {
                row[i][0] = null;
            }
            row[i][1] = dsSach.get(i).getMaSach();
            row[i][2] = dsSach.get(i).getTenSach();
             row[i][3] = dsSach.get(i).getMoTa();
            row[i][4] = dsSach.get(i).getMaTL();
            row[i][5] = dsSach.get(i).getMaTG();
            row[i][6] = dsSach.get(i).getMaNXB();
            row[i][7] = dsSach.get(i).getSoLuong();
            row[i][8] = dsSach.get(i).getNamXB();                                          
        }
        poli.com.dao.model model = new model(t, row);
        tb_sach.setModel(model);
        tb_sach.setRowHeight(172);
        tb_sach.getColumnModel().getColumn(0).setPreferredWidth(100);

        
       
    }
    public static String[] select_maSach() {
        String [] ds = null;
        ArrayList<String> dsCVList = new ArrayList<String>();

        try {
            String sql = "SELECT maSach from Sach;";
            poli.com.dao.SQLServerProvider con = new SQLServerProvider();
            con.open();
            ResultSet resultSet = con.excuteQuery(sql);

            while (resultSet.next()) {
                String ma = resultSet.getString(1);
                dsCVList.add(ma);
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
    public static String[] select_sach_ma(String ma) {
            SQLServerProvider provider = new SQLServerProvider();
            provider.open();
            ArrayList<String> bookInfoList = new ArrayList<>();
            String [] ds = null;

            try {
                // Tạo một đối tượng CallableStatement từ SQLServerProvider
                CallableStatement cstmt = provider.prepareCall("{call GetBookInfoByMaSach(?)}");

                // Thiết lập giá trị cho tham số của stored procedure
                cstmt.setString(1, ma);

                // Thực thi stored procedure
                ResultSet rs = cstmt.executeQuery();

                // Xử lý kết quả trả về
                while (rs.next()) {
                   bookInfoList.add(rs.getString("HinhAnh"));
                   bookInfoList.add(rs.getString("TenSach"));
                   bookInfoList.add(rs.getString("TenTacGia"));
                   bookInfoList.add(rs.getString("TenTheLoai"));
                   bookInfoList.add(rs.getInt("NamXB")+"");
                   bookInfoList.add(rs.getString("TenNXB"));
                   bookInfoList.add(rs.getInt("SoLuong")+"");

                    ds = bookInfoList.toArray(new String[0]);

                    // Thêm thông tin sách vào danh sách

                }

                // Đóng kết nối
                rs.close();
                cstmt.close();
                provider.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Chuyển danh sách thông tin sách thành mảng và trả về
            return ds;
        }
    public static boolean lapPhieuMuon(String maSach, int sl)
    {
        int n = -1;
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
        ketNoi.open();
        String sql ="Update Sach set soluong ='"+sl+"'"+"where masach ='"+maSach+"'";
        n = ketNoi.excuteUpdate(sql);
        if(n>0)
        {
            return true;
        }
        return false;
    }
    public static int select_sl_sach(String maSach)
    {
        int sl = 0;
        poli.com.dao.SQLServerProvider knoi = new SQLServerProvider();
        knoi.open();
        String sql = "Select soluong from sach where masach ='"+maSach+"'";
        ResultSet rs = knoi.excuteQuery(sql);
        try {
            while(rs.next())
            {
                sl =  rs.getInt(1);
            }
            knoi.close();
        } catch (SQLException ex) {
            Logger.getLogger(sachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sl;
    }     
    public static ArrayList<moli.com.modal.sach> select_sach_theoTen(String ma) 
    {       
    ArrayList<moli.com.modal.sach> sachList = new ArrayList<moli.com.modal.sach>();
    
    SQLServerProvider provider = new SQLServerProvider();
    provider.open();

    try {
        // Tạo một đối tượng CallableStatement từ SQLServerProvider
        CallableStatement cstmt = provider.prepareCall("{call SearchBookByName(?)}");

        // Thiết lập giá trị cho tham số của stored procedure
        cstmt.setString(1, ma);

        // Thực thi stored procedure
        ResultSet rs = cstmt.executeQuery();

        // Xử lý kết quả trả về
        while (rs.next()) {
            moli.com.modal.sach sach = new sach();
            sach.setAnh(rs.getString("HinhAnh"));
             sach.setMaSach(rs.getString("MaSach"));
            sach.setTenSach(rs.getString("TenSach"));
           sach.setMoTa(rs.getString("MoTa"));
              sach.setMaTL(rs.getString("MaTL"));
            sach.setMaTG(rs.getString("MaTG"));
           sach.setMaNXB(rs.getString("MaNXB"));
            sach.setSoLuong(rs.getInt("SoLuong"));
            sach.setNamXB(rs.getInt("NamXB"));
            
           
            
            
            // Thêm sách vào danh sách
            sachList.add(sach);
        }

        // Đóng kết nối
        rs.close();
        cstmt.close();
        provider.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Trả về danh sách sách
    return sachList;
}
    public static ArrayList<moli.com.modal.sach> select_sach_theoMA(String ma) 
    {       
    ArrayList<moli.com.modal.sach> sachList = new ArrayList<moli.com.modal.sach>();
    
    SQLServerProvider provider = new SQLServerProvider();
    provider.open();

    try {
        // Tạo một đối tượng CallableStatement từ SQLServerProvider
        CallableStatement cstmt = provider.prepareCall("{call SearchBookByMasach(?)}");

        // Thiết lập giá trị cho tham số của stored procedure
        cstmt.setString(1, ma);

        // Thực thi stored procedure
        ResultSet rs = cstmt.executeQuery();

        // Xử lý kết quả trả về
        while (rs.next()) {
            moli.com.modal.sach sach = new sach();
            sach.setAnh(rs.getString("HinhAnh"));
             sach.setMaSach(rs.getString("MaSach"));
            sach.setTenSach(rs.getString("TenSach"));
           sach.setMoTa(rs.getString("MoTa"));
              sach.setMaTL(rs.getString("MaTL"));
            sach.setMaTG(rs.getString("MaTG"));
           sach.setMaNXB(rs.getString("MaNXB"));
            sach.setSoLuong(rs.getInt("SoLuong"));
            sach.setNamXB(rs.getInt("NamXB"));
            
           
            
            
            // Thêm sách vào danh sách
            sachList.add(sach);
        }

        // Đóng kết nối
        rs.close();
        cstmt.close();
        provider.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Trả về danh sách sách
    return sachList;
}
      public static ArrayList<moli.com.modal.sach> layDanhSachSachFull()
    {
        ArrayList<moli.com.modal.sach> ds= new ArrayList<moli.com.modal.sach>();
        try{
            String sql="select HinhAnh,MaSach,TenSach,MoTa,SoLuong,NamXB,TenTG,TenTL,TenNXB,MaTL,MaNXB,MaTG from Sach,TheLoai,TacGia,NXB where Sach.MaTL=TheLoai.MaTL and Sach.MaTG=TacGia.MaTG and Sach.MaNXB  =NXB.MaNXB ";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            {
                moli.com.modal.sach s=new moli.com.modal.sach();
                s.setAnh(resultSet.getString("HinhAnh"));
                s.setMaSach(resultSet.getString("MaSach"));
                s.setTenSach(resultSet.getString("TenSach"));
                s.setMoTa(resultSet.getString("MoTa"));
                s.setSoLuong(resultSet.getInt("SoLuong"));
                s.setNamXB(resultSet.getInt("NamXB"));
                s.setTenTG(resultSet.getString("TenTG"));
                s.setTenTL(resultSet.getString("TenTL"));
                s.setTenNXB(resultSet.getString("TenNXB"));
                s.setMaTG(resultSet.getString("MaTG"));
                s.setMaTL(resultSet.getString("MaTL"));
                s.setMaNXB(resultSet.getString("MaNXB"));
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
    public static ArrayList<moli.com.modal.sach> layDanhSachSach()
    {
        ArrayList<moli.com.modal.sach> ds= new ArrayList<moli.com.modal.sach>();
        try{
            String sql="select HinhAnh,MaSach,TenSach,MoTa,SoLuong,NamXB,TenTG,TenTL,TenNXB from Sach,TheLoai,TacGia,NXB where Sach.MaTL=TheLoai.MaTL and Sach.MaTG=TacGia.MaTG and Sach.MaNXB  =NXB.MaNXB ";
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            {
                moli.com.modal.sach s=new moli.com.modal.sach();
                s.setAnh(resultSet.getString("HinhAnh"));
                s.setMaSach(resultSet.getString("MaSach"));
                s.setTenSach(resultSet.getString("TenSach"));
                s.setMoTa(resultSet.getString("MoTa"));
                s.setSoLuong(resultSet.getInt("SoLuong"));
                s.setNamXB(resultSet.getInt("NamXB"));
                s.setTenTG(resultSet.getString("TenTG"));
                s.setTenTL(resultSet.getString("TenTL"));
                s.setTenNXB(resultSet.getString("TenNXB"));
                
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
    //Tìm kiếm
     public static ArrayList<moli.com.modal.sach> timKiemSach(String ten,String maLoai)
    {
        ArrayList<moli.com.modal.sach> ds= new ArrayList<moli.com.modal.sach>();
        try{
            String sql="select HinhAnh,MaSach,TenSach,MoTa,SoLuong,NamXB,TenTG,TenTL,TenNXB from Sach,TheLoai,TacGia,NXB where Sach.MaTL=TheLoai.MaTL and Sach.MaTG=TacGia.MaTG and Sach.MaNXB=NXB.MaNXB and tenSach LIKE N'%"+ten+"%' and Sach.maTL like '%"+maLoai+"%'";
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
            
            while(resultSet.next())
            {
                moli.com.modal.sach s=new moli.com.modal.sach();
                s.setAnh(resultSet.getString("HinhAnh"));
                s.setMaSach(resultSet.getString("MaSach"));
                s.setTenSach(resultSet.getString("TenSach"));
                s.setMoTa(resultSet.getString("MoTa"));
                s.setSoLuong(resultSet.getInt("SoLuong"));
                s.setNamXB(resultSet.getInt("NamXB"));
                s.setTenTG(resultSet.getString("TenTG"));
                s.setTenTL(resultSet.getString("TenTL"));
                s.setTenNXB(resultSet.getString("TenNXB"));
                
                ds.add(s);
            }
            provider.close();
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi tìm kiếm sách: " + ex.getMessage());  // Ghi log lỗi
        }
        return ds;
    }
     public static boolean xoaSach(String maSach)
    {
        poli.com.dao.SQLServerProvider ketNoi = new SQLServerProvider();
            ketNoi.open();

        try {
            // Tạo câu lệnh SQL DELETE để xóa tài khoản dựa trên mã nhân viên
            String sql = "DELETE FROM Sach where masach = ?";

            // Tạo PreparedStatement từ kết nối và câu lệnh SQL
            PreparedStatement preparedStatement = ketNoi.prepareStatement(sql);

            // Thiết lập giá trị cho tham số của câu lệnh SQL
            preparedStatement.setString(1, maSach);

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
    public static String layHinhTheoMaSach(String ma)
    {
        String hinh="";
      
        try{
           
            
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
             String sql = "SELECT HinhAnh FROM Sach WHERE MaSach = '"+ma+"'";
             
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
         
            if (resultSet.next()) {
            hinh = resultSet.getString(1);
        } else {
            System.out.println("Không tìm thấy hình ảnh cho MaSach: " + ma);
        }
            provider.close();
             // Kiểm tra xem có tìm thấy bản ghi nào không
   
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy hình dựa vào mã sách: " + ex.getMessage());  // Ghi log lỗi
        }
        return hinh;
    }
    public static String layTGTheoMaSach(String ma)
    {
        String tg="";
      
        try{
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
             String sql = "SELECT tenTG FROM Sach, TacGia WHERE sach.matg=tacgia.matg and MaSach = '"+ma+"'";
             
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
         
            if (resultSet.next()) {
            tg = resultSet.getString(1);
        } else {
            System.out.println("Không tìm thấy tên tác giả cho MaSach: " + ma);
        }
            provider.close();
             // Kiểm tra xem có tìm thấy bản ghi nào không
   
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy tên tác giả dựa vào mã sách: " + ex.getMessage());  // Ghi log lỗi
        }
        return tg;
    }
     public static String layTLTheoMaSach(String ma)
    {
        String tg="";
      
        try{
             poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
             String sql = "SELECT tenTL FROM Sach, TheLoai WHERE sach.matl=theloai.matl and MaSach = '"+ma+"'";
             
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
         
            if (resultSet.next()) {
            tg = resultSet.getString(1);
        } else {
            System.out.println("Không tìm thấy tên thể loại cho MaSach: " + ma);
        }
            provider.close();
             // Kiểm tra xem có tìm thấy bản ghi nào không
   
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy tên thể loại dựa vào mã sách: " + ex.getMessage());  // Ghi log lỗi
        }
        return tg;
    }
      public static String layNXBThaoMaSach(String ma)
    {
        String tg="";
      
        try{
            poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
             String sql = "SELECT tenNXB FROM Sach, NXB WHERE sach.manxb =nxb.manxb and MaSach = '"+ma+"'";
             
            provider.open();
            ResultSet resultSet=provider.excuteQuery(sql);
         
            if (resultSet.next()) {
            tg = resultSet.getString(1);
        } else {
            System.out.println("Không tìm thấy tên NXB cho MaSach: " + ma);
        }
            provider.close();
             // Kiểm tra xem có tìm thấy bản ghi nào không
   
        }
        catch(Exception ex)
        {
             System.err.println("Lỗi khi lấy tên nxb dựa vào mã sách: " + ex.getMessage());  // Ghi log lỗi
        }
        return tg;
    }
      public static boolean themSach(moli.com.modal.sach s)
    {
        boolean kq=false;
        String sql="{call themSach('"+s.getAnh()+"',N'"+s.getTenSach()+"',N'"+s.getMoTa()+"','"+s.getMaTL()+"','"+s.getMaTG()+"','"+s.getMaNXB()+"','"+s.getSoLuong()+"','"+s.getNamXB()+"')}";
         poli.com.dao.SQLServerProvider provider=new  poli.com.dao.SQLServerProvider();
        provider.open();
        int i=provider.callStatementUpdate(sql);
        if(i==1)
            kq=true;
        provider.close();
        return kq;
                    
    }
       public static boolean suaSach(moli.com.modal.sach s)
    {
        boolean kq=false;
        String sql="{call CapNhatSach('"+s.getAnh()+"','"+s.getMaSach()+"',N'"+s.getTenSach()+"',N'"+s.getMoTa()+"','"+s.getMaTL()+"','"+s.getMaTG()+"','"+s.getMaNXB()+"','"+s.getSoLuong()+"','"+s.getNamXB()+"')}";
        poli.com.dao.SQLServerProvider provider=new poli.com.dao.SQLServerProvider();
        provider.open();
        int i=provider.callStatementUpdate(sql);
        if(i==1)
            kq=true;
        provider.close();
        return kq;
                    
    }

    
      
    
}
