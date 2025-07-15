package ui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserLoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public UserLoginForm() {
        setTitle("User Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(loginBtn);
        add(backBtn);

        loginBtn.addActionListener(e -> handleLogin());
        backBtn.addActionListener(e -> {
            dispose();
            new MainUI();
        });

        setVisible(true);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        UserDAO userDAO = new UserDAO();
        boolean isValid = userDAO.validateUser(email, password);

        if (isValid) {
            JOptionPane.showMessageDialog(this, "✅ Login successful!");
            dispose();
            new UserDashboard(email); // Open dashboard with email context
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid credentials.");
        }
    }
}
