package ui;

import dao.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    public MainUI() {
        setTitle("Smart City Management System");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton userLoginBtn = new JButton("👤 User Login");
        JButton userRegisterBtn = new JButton("📝 Register");
        JButton adminLoginBtn = new JButton("🔐 Admin Login");
        JButton exitBtn = new JButton("❌ Exit");

        add(userLoginBtn);
        add(userRegisterBtn);
        add(adminLoginBtn);
        add(exitBtn);

        // ✅ User Login
        userLoginBtn.addActionListener(e -> {
            dispose();
            new UserLoginForm(); // Launch User Login Form
        });

        // ✅ User Registration
        userRegisterBtn.addActionListener(e -> {
            dispose();
            new UserRegistrationForm(); // Launch User Registration Form
        });

        // ✅ Admin Login
        adminLoginBtn.addActionListener(e -> {
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                    "Username:", usernameField,
                    "Password:", passwordField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                AdminDAO adminDAO = new AdminDAO();
                if (adminDAO.login(username, password)) {
                    dispose();
                    new AdminDashboardUI(); // Launch Admin UI
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Invalid admin credentials.");
                }
            }
        });

        // ✅ Exit Button
        exitBtn.addActionListener(e -> {
            dispose();
            System.out.println("👋 Application exited.");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainUI();
    }
}
