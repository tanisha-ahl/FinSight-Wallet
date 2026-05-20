package backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsightsService {

    public void generateInsights() {

       String query = "SELECT category, SUM(amount) AS total_spent " +
               "FROM Transactions " +
               "GROUP BY category " +
               "ORDER BY total_spent DESC " +
               "LIMIT 1";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            System.out.println("\n===== AI SPENDING INSIGHTS =====");

            if(rs.next()) {

                String category = rs.getString("category");

                double amount = rs.getDouble("total_spent");

                System.out.println(" Highest Spending Category: " + category);

                System.out.println(" Total Spent: ₹" + amount);

                // SMART INSIGHTS
                if(category.equalsIgnoreCase("Shopping")) {

                    System.out.println(" Insight: Your shopping expenses are high.");
                    System.out.println(" Recommendation: Consider reducing impulse purchases.");
                }

                else if(category.equalsIgnoreCase("Food")) {

                    System.out.println(" Insight: Food spending dominates your transactions.");
                    System.out.println(" Recommendation: Try budgeting weekly food expenses.");
                }

                else if(category.equalsIgnoreCase("Travel")) {

                    System.out.println(" Insight: Significant spending on travel detected.");
                    System.out.println(" Recommendation: Use travel budgeting strategies.");
                }

                else {

                    System.out.println(" Insight: Spending patterns analyzed successfully.");
                }
            }

            else {
                System.out.println("No transaction data found!");
            }

        }

        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}