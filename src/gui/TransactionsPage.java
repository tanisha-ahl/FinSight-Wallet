package gui;

import backend.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionsPage extends JFrame {

    JTable table;

    DefaultTableModel model;

    public TransactionsPage() {

        setTitle("Transaction History");

        setSize(1000, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 245, 245));

        String[] columns = {
                "Sender",
                "Receiver",
                "Amount",
                "Category",
                "Timestamp"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        // TABLE STYLE
        table.setRowHeight(35);

        table.setFont(new Font("Arial", Font.PLAIN, 18));

        table.setGridColor(new Color(230, 230, 230));

        table.setShowVerticalLines(false);

        table.setSelectionBackground(new Color(220, 235, 255));

        // HEADER STYLE
        JTableHeader header = table.getTableHeader();

        header.setFont(new Font("Arial", Font.BOLD, 20));

        header.setBackground(Color.WHITE);

        header.setPreferredSize(new Dimension(100, 45));

        // CENTER ALIGNMENT
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < table.getColumnCount(); i++) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20));

        add(scrollPane);

        loadTransactions();

        setVisible(true);
    }

    public void loadTransactions() {

        String query =
                "SELECT sender_id, receiver_id, amount, category, timestamp FROM Transactions";

        try {

            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    conn.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                Object[] row = {

                        rs.getString("sender_id"),

                        rs.getString("receiver_id"),

                        "₹" + rs.getDouble("amount"),

                        rs.getString("category"),

                        rs.getString("timestamp")
                };

                model.addRow(row);
            }

        }

        catch(SQLException e) {

            e.printStackTrace();
        }
    }
}