package gui;

import backend.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardPage extends JFrame {

    String userEmail;

    JButton addMoneyButton;
    JButton balanceButton;
    JButton sendMoneyButton;
    JButton transactionButton;
    JButton insightsButton;
    JButton chartButton;
    JButton logoutButton;

    public DashboardPage(String email) {

        this.userEmail = email;

        setTitle("FinSight Dashboard");

        setSize(800, 900);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MAIN PANEL
        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(new Color(245, 245, 245));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBorder(new EmptyBorder(40, 120, 40, 120));

        // TITLE
        JLabel title = new JLabel("FinSight Dashboard");

        title.setFont(new Font("Arial", Font.BOLD, 42));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        title.setBorder(new EmptyBorder(20, 0, 50, 0));

        mainPanel.add(title);

        // BUTTONS
        addMoneyButton = createStyledButton("Add Money");

        balanceButton = createStyledButton("View Balance");

        sendMoneyButton = createStyledButton("Send Money");

        transactionButton = createStyledButton("Transactions");

        insightsButton = createStyledButton("AI Insights");

        chartButton = createStyledButton("Analytics Chart");

        logoutButton = createStyledButton("Logout");

        // ADD BUTTONS
        mainPanel.add(addMoneyButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(balanceButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(sendMoneyButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(transactionButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(insightsButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(chartButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        mainPanel.add(logoutButton);

        add(mainPanel);

        // BUTTON FUNCTIONS

        addMoneyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String amountStr =
                        JOptionPane.showInputDialog(
                                "Enter amount to add:");

                try {

                    double amount =
                            Double.parseDouble(amountStr);

                    String query =
                            "UPDATE Users SET wallet_balance = wallet_balance + ? WHERE email = ?";

                    Connection conn =
                            DBConnection.getConnection();

                    PreparedStatement pst =
                            conn.prepareStatement(query);

                    pst.setDouble(1, amount);

                    pst.setString(2, userEmail);

                    int rows = pst.executeUpdate();

                    if(rows > 0) {

                        JOptionPane.showMessageDialog(
                                null,
                                "✅ ₹" + amount + " added successfully!");
                    }

                }

                catch(Exception ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "❌ Invalid input!");
                }
            }
        });

        // VIEW BALANCE
        balanceButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String query =
                            "SELECT wallet_balance FROM Users WHERE email = ?";

                    Connection conn =
                            DBConnection.getConnection();

                    PreparedStatement pst =
                            conn.prepareStatement(query);

                    pst.setString(1, userEmail);

                    ResultSet rs =
                            pst.executeQuery();

                    if(rs.next()) {

                        double balance =
                                rs.getDouble("wallet_balance");

                        JOptionPane.showMessageDialog(
                                null,
                                "💰 Current Balance: ₹" + balance);
                    }

                }

                catch(SQLException ex) {

                    ex.printStackTrace();
                }
            }
        });

        // SEND MONEY
        sendMoneyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField receiverField =
                        new JTextField();

                JTextField amountField =
                        new JTextField();

                JTextField categoryField =
                        new JTextField();

                Object[] fields = {

                        "Receiver Email:", receiverField,

                        "Amount:", amountField,

                        "Category:", categoryField
                };

                int option =
                        JOptionPane.showConfirmDialog(
                                null,
                                fields,
                                "Send Money",
                                JOptionPane.OK_CANCEL_OPTION
                        );

                if(option == JOptionPane.OK_OPTION) {

                    try {

                        String receiver =
                                receiverField.getText();

                        double amount =
                                Double.parseDouble(
                                        amountField.getText());

                        String category =
                                categoryField.getText();

                        Connection conn =
                                DBConnection.getConnection();

                        // DEDUCT
                        String deductQuery =
                                "UPDATE Users SET wallet_balance = wallet_balance - ? WHERE email = ?";

                        PreparedStatement deductPst =
                                conn.prepareStatement(deductQuery);

                        deductPst.setDouble(1, amount);

                        deductPst.setString(2, userEmail);

                        deductPst.executeUpdate();

                        // ADD
                        String addQuery =
                                "UPDATE Users SET wallet_balance = wallet_balance + ? WHERE email = ?";

                        PreparedStatement addPst =
                                conn.prepareStatement(addQuery);

                        addPst.setDouble(1, amount);

                        addPst.setString(2, receiver);

                        addPst.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "✅ Money Sent Successfully!");

                    }

                    catch(Exception ex) {

                        JOptionPane.showMessageDialog(
                                null,
                                "❌ Transaction failed!");
                    }
                }
            }
        });

        // TRANSACTIONS
        transactionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new TransactionsPage();
            }
        });

        // INSIGHTS
        insightsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new InsightsPage();
            }
        });

        // CHARTS
        chartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new ChartPage();
            }
        });

        // LOGOUT
        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new LoginPage();
            }
        });

        setVisible(true);
    }

    // STYLED BUTTON METHOD
    public JButton createStyledButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.PLAIN, 28));

        button.setFocusPainted(false);

        button.setBackground(Color.WHITE);

        button.setMaximumSize(new Dimension(450, 70));

        button.setPreferredSize(new Dimension(450, 70));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }
}