package gui;
import gui.DashboardPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import backend.DBConnection;
import gui.*;
import java.sql.*;

public class LoginPage extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton registerButton;

    public LoginPage() {

        setTitle("FinSight Wallet");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel titleLabel = new JLabel("FinSight Wallet");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        titleLabel.setBounds(100, 20, 250, 30);

        add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");

        emailLabel.setBounds(50, 80, 100, 30);

        add(emailLabel);

        emailField = new JTextField();

        emailField.setBounds(150, 80, 180, 30);

        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(50, 130, 100, 30);

        add(passwordLabel);

        passwordField = new JPasswordField();

        passwordField.setBounds(150, 130, 180, 30);

        add(passwordField);

        loginButton = new JButton("Login");

        loginButton.setBounds(70, 200, 100, 40);

        add(loginButton);

        registerButton = new JButton("Register");

        registerButton.setBounds(210, 200, 100, 40);

        add(registerButton);

        loginButton.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {

        String email = emailField.getText();

        String password = new String(passwordField.getPassword());

        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";

        try {

            Connection conn = DBConnection.getConnection();
            if(conn == null) {

                JOptionPane.showMessageDialog(null,
                "Database connection failed!");

                return;
            }
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, email);

            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                JOptionPane.showMessageDialog(null,
                "✅ Login Successful! Welcome " + rs.getString("name"));

                new DashboardPage(email);

                dispose();
            }

            else {

                JOptionPane.showMessageDialog(null,
                        "❌ Invalid email or password!");
            }

        }

        catch(SQLException ex) {

            ex.printStackTrace();
        }
    }
});

        setVisible(true);
    }

    public static void main(String[] args) {

        new LoginPage();
    }
}