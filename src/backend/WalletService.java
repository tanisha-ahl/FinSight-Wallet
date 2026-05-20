package backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class WalletService {

    Scanner sc = new Scanner(System.in);

    public void addMoney() {

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        System.out.print("Enter amount to add: ");
        double amount = sc.nextDouble();

        String query = "UPDATE Users SET wallet_balance = wallet_balance + ? WHERE email = ?";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setDouble(1, amount);
            pst.setString(2, email);

            int rows = pst.executeUpdate();

            if(rows > 0) {
                System.out.println("✅ Money added successfully!");
            }

            else {
                System.out.println("❌ User not found!");
            }

        }

        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBalance() {

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        String query = "SELECT wallet_balance FROM Users WHERE email = ?";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                System.out.println("💰 Wallet Balance: ₹" + rs.getDouble("wallet_balance"));
            }

            else {
                System.out.println("❌ User not found!");
            }

        }

        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendMoney() {

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter sender email: ");
    String sender = sc.nextLine();

    System.out.print("Enter receiver email: ");
    String receiver = sc.nextLine();

    System.out.print("Enter amount: ");
    double amount = sc.nextDouble();
    sc.nextLine();

    System.out.print("Enter category (Food/Travel/Shopping/etc): ");
    String category = sc.nextLine();

    try {

        Connection conn = DBConnection.getConnection();

        // CHECK SENDER BALANCE
        String balanceQuery = "SELECT wallet_balance FROM Users WHERE email = ?";

        PreparedStatement balancePst = conn.prepareStatement(balanceQuery);

        balancePst.setString(1, sender);

        ResultSet rs = balancePst.executeQuery();

        if(rs.next()) {

            double currentBalance = rs.getDouble("wallet_balance");

            if(currentBalance < amount) {
                System.out.println("❌ Insufficient balance!");
                return;
            }
        }

        else {
            System.out.println("❌ Sender not found!");
            return;
        }

        // DEDUCT MONEY
        String deductQuery = "UPDATE Users SET wallet_balance = wallet_balance - ? WHERE email = ?";

        PreparedStatement deductPst = conn.prepareStatement(deductQuery);

        deductPst.setDouble(1, amount);
        deductPst.setString(2, sender);

        deductPst.executeUpdate();

        // ADD MONEY TO RECEIVER
        String addQuery = "UPDATE Users SET wallet_balance = wallet_balance + ? WHERE email = ?";

        PreparedStatement addPst = conn.prepareStatement(addQuery);

        addPst.setDouble(1, amount);
        addPst.setString(2, receiver);

        int rows = addPst.executeUpdate();

        if(rows == 0) {
            System.out.println("❌ Receiver not found!");
            return;
        }

        // STORE TRANSACTION
        String txnQuery = "INSERT INTO Transactions(sender_email, receiver_email, amount, category) VALUES (?, ?, ?, ?)";

        PreparedStatement txnPst = conn.prepareStatement(txnQuery);

        txnPst.setString(1, sender);
        txnPst.setString(2, receiver);
        txnPst.setDouble(3, amount);
        txnPst.setString(4, category);

        txnPst.executeUpdate();

        System.out.println("✅ Money sent successfully!");

    }

    catch(SQLException e) {
        e.printStackTrace();
    }
}
public void viewTransactions() {

    String query = "SELECT * FROM Transactions";

    try {

        Connection conn = DBConnection.getConnection();

        PreparedStatement pst = conn.prepareStatement(query);

        ResultSet rs = pst.executeQuery();

        System.out.println("\n===== TRANSACTION HISTORY =====");

        while(rs.next()) {

            System.out.println("------------------------------");

            System.out.println("Transaction ID: " + rs.getInt(1));

            System.out.println("Sender ID: " + rs.getInt(2));

            System.out.println("Receiver ID: " + rs.getInt(3));

            System.out.println("Amount: ₹" + rs.getDouble(4));

            System.out.println("Category: " + rs.getString(5));

            System.out.println("Timestamp: " + rs.getTimestamp(6));
        }

    }

    catch(SQLException e) {
        e.printStackTrace();
    }
}

}