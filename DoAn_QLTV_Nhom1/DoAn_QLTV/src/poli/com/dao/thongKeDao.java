/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poli.com.dao;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HP
 */
public class thongKeDao {
     public static void setDataToChart1(JPanel jpnItem) {
         ArrayList<String> ds  = poli.com.dao.muonTraDao.laySL_Muon();
         int mod = Integer.valueOf(ds.get(0)), ted =Integer.valueOf(ds.get(1)) , wed =Integer.valueOf(ds.get(2)),thd = Integer.valueOf(ds.get(3)),frd = Integer.valueOf(ds.get(4)),sad = Integer.valueOf(ds.get(5)),sud = Integer.valueOf(ds.get(6));
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(mod, "Số lượng phiếu mượn", "Thứ 2");
        dataset.addValue(ted, "Số lượng phiếu mượn", "Thứ 3");
        dataset.addValue(wed, "Số lượng phiếu mượn", "Thứ 4");
        dataset.addValue(thd, "Số lượng phiếu mượn", "Thứ 5");
        dataset.addValue(frd, "Số lượng phiếu mượn", "Thứ 6");
        dataset.addValue(sad, "Số lượng phiếu mượn", "Thứ 7");
        dataset.addValue(sud, "Số lượng phiếu mượn", "Chủ nhật");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ thống kê danh sách mượn sách trong tuần".toUpperCase(),
                "Thời gian", "Số lượng",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));

        jpnItem.removeAll();
        jpnItem.setLayout(new CardLayout());
        jpnItem.add(chartPanel);
        jpnItem.validate();
        jpnItem.repaint();
    }
      public static void exportTongMuonReport(String tenNv) {
        String user ="sa";
        String pass = "123";
        String database ="QL_ThuVien";
        String severName ="DESKTOP-4GMS5N8";
        try {
            String connectionURL = "jdbc:sqlserver://" + severName +
                    ":1433;databaseName=" + database +
                    ";user=" + user +
                    ";password=" + pass;
            Connection connection = DriverManager.getConnection(connectionURL);
            
            // Khởi tạo map để truyền tham số vào báo cáo
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NguoiXuatDon", tenNv);
            
                JasperReport rpt = JasperCompileManager.compileReport("src\\poli\\com\\gui\\DanhSachTongMuon_RP.jrxml");
            JasperPrint p = JasperFillManager.fillReport(rpt, parameters, connection);
            JasperViewer.viewReport(p, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
