package backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class UserService {

    Scanner sc = new Scanner(System.in);

    public void registerUser() {

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String query = "INSERT INTO Users(name, email, password, wallet_balance) VALUES (?, ?, ?, ?)";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setDouble(4, 0.0);

            int rows = pst.executeUpdate();

            if(rows > 0) {
                System.out.println("✅ User registered successfully!");
            }

        } catch (SQLException e) {
            System.out.println("❌ Registration failed!");
            e.printStackTrace();
        }
    }
    public boolean loginUser() {

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter email: ");
    String email = sc.nextLine();

    System.out.print("Enter password: ");
    String password = sc.nextLine();

    String query = "SELECT * FROM Users WHERE email = ? AND password = ?";

    try {

        Connection conn = DBConnection.getConnection();

        PreparedStatement pst = conn.prepareStatement(query);

        pst.setString(1, email);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if(rs.next()) {

            System.out.println("✅ Login successful!");
            System.out.println("Welcome, " + rs.getString("name"));

            return true;
        }

        else {
            System.out.println("❌ Invalid email or password!");
        }

    }

    catch(SQLException e) {
        e.printStackTrace();
    }

    return false;
}

}