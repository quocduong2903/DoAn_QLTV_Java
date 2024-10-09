/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class SQLServerProvider {
    public static String check_nhan_vien ="";
    private Connection connection ;
    public static String check_ma_sach_ctMT ="";
    public static String check_ma_MT ="";
    public static int slCu_check = 0;
    public static boolean checkTT = false;
    public static boolean checkDV = true;
    public  static boolean checkQLTTV = true;
    public static String nameSV ="DESKTOP-4GMS5N8";
    public  void open()
    {
        String user ="sa";
        String pass = "123";
        String database ="QL_ThuVien";
        String severName =nameSV;
        try {
             String connectionURL = "jdbc:sqlserver://" + severName +
                ":1433;databaseName=" + database +
                ";user=" + user +
                ";password=" + pass;
             connection = DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void close()
    {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet excuteQuery(String sql)
    {
        ResultSet resultSet = null;
        try {
            Statement s = connection.createStatement();
            resultSet = s.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public  int excuteUpdate(String sql)
    {
        int n =  -1;
        try {
            Statement sm = connection.createStatement();
            n = sm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }     
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    public CallableStatement prepareCall(String sql) throws SQLException {
    return connection.prepareCall(sql);
   
}
         public int callStatementUpdate(String sql)
    {
        int n=-1;
        try {
            CallableStatement callStatement=connection.prepareCall(sql);
            n=callStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
    