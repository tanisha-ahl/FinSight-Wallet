package gui;

import backend.DBConnection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChartPage extends JFrame {

    public ChartPage() {

        setTitle("Spending Analytics");

        setSize(700, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultPieDataset dataset =
                new DefaultPieDataset();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String query =
                    "SELECT category, SUM(amount) AS total " +
                    "FROM Transactions " +
                    "GROUP BY category";

            PreparedStatement pst =
                    conn.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                String category =
                        rs.getString("category");

                double total =
                        rs.getDouble("total");

                dataset.setValue(category, total);
            }

        }

        catch(SQLException e) {

            e.printStackTrace();
        }

        JFreeChart chart =
                ChartFactory.createPieChart(
                        "Spending by Category",
                        dataset,
                        true,
                        true,
                        false
                );

        ChartPanel panel =
                new ChartPanel(chart);

        setContentPane(panel);

        setVisible(true);
    }
}