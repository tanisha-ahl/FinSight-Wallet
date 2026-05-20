package gui;

import backend.DBConnection;

import javax.swing.*;

import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsightsPage extends JFrame {

    JLabel categoryLabel;

    JLabel amountLabel;

    JTextArea recommendationArea;

    public InsightsPage() {

        setTitle("AI Spending Insights");

        setSize(500, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        JLabel title =
                new JLabel("AI Spending Insights");

        title.setFont(new Font("Arial", Font.BOLD, 24));

        title.setBounds(100, 20, 300, 30);

        add(title);

        categoryLabel = new JLabel();

        categoryLabel.setBounds(50, 100, 400, 30);

        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        add(categoryLabel);

        amountLabel = new JLabel();

        amountLabel.setBounds(50, 150, 400, 30);

        amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        add(amountLabel);

        recommendationArea = new JTextArea();

        recommendationArea.setBounds(50, 220, 380, 100);

        recommendationArea.setLineWrap(true);

        recommendationArea.setWrapStyleWord(true);

        recommendationArea.setEditable(false);

        add(recommendationArea);

        loadInsights();

        setVisible(true);
    }

    public void loadInsights() {

        String query =
                "SELECT category, SUM(amount) AS total_spent " +
                "FROM Transactions " +
                "GROUP BY category " +
                "ORDER BY total_spent DESC " +
                "LIMIT 1";

        try {

            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    conn.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            if(rs.next()) {

                String category =
                        rs.getString("category");

                double amount =
                        rs.getDouble("total_spent");

                categoryLabel.setText(
                        " Top Spending Category: " + category);

                amountLabel.setText(
                        " Total Spent: ₹" + amount);

                // SMART RECOMMENDATIONS
                if(category.equalsIgnoreCase("Shopping")) {

                    recommendationArea.setText(
                            " Insight:\n" +
                            "Your shopping expenses are high.\n\n" +
                            " Recommendation:\n" +
                            "Reduce impulse purchases and set monthly limits."
                    );
                }

                else if(category.equalsIgnoreCase("Food")) {

                    recommendationArea.setText(
                            " Insight:\n" +
                            "Food spending dominates your transactions.\n\n" +
                            " Recommendation:\n" +
                            "Track weekly food budgets carefully."
                    );
                }

                else if(category.equalsIgnoreCase("Travel")) {

                    recommendationArea.setText(
                            " Insight:\n" +
                            "Travel expenses are significant.\n\n" +
                            " Recommendation:\n" +
                            "Plan travel budgets in advance."
                    );
                }

                else {

                    recommendationArea.setText(
                            " Spending patterns analyzed successfully."
                    );
                }
            }

        }

        catch(SQLException e) {

            e.printStackTrace();
        }
    }
}