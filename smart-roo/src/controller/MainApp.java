package controller;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----- Welcome to Smart City Management System -----");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    UserMain.main(null); // launch User module
                    break;
                case 2:
                    AdminDashboard.main(null); // launch Admin module
                    break;
                case 3:
                    System.out.println("👋 Exiting. Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }

            System.out.println(); // spacing
        }
    }
}
