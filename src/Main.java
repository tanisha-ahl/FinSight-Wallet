

import backend.InsightsService;
import backend.UserService;
import backend.WalletService;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(true) {

System.out.println("\n===== FinSight Wallet =====");
System.out.println("1. Register");
System.out.println("2. Login");
System.out.println("3. Add Money");
System.out.println("4. View Balance");
System.out.println("5. Send Money");
System.out.println("6. View Transactions");
System.out.println("7. AI Spending Insights");
System.out.println("8. Exit");

            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch(choice) {

                case 1:

                    UserService userService = new UserService();
                    userService.registerUser();

                    break;

                case 2:

                    UserService loginService = new UserService();
                    loginService.loginUser();

                    break;

                case 3:

                    WalletService walletService = new WalletService();
                    walletService.addMoney();

                    break;

                case 4:

                    WalletService walletService2 = new WalletService();
                    walletService2.viewBalance();

                    break;

                case 5:

                    WalletService walletService3 = new WalletService();
                    walletService3.sendMoney();

                    break;  
                    
                case 6:

                    WalletService walletService4 = new WalletService();
                    walletService4.viewTransactions();

                    break;

                case 7:

    InsightsService insights = new InsightsService();
    insights.generateInsights();

    break;

                case 8:
                    System.out.println("Thank you for using FinSight Wallet!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}