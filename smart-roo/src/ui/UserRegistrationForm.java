package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class UserRegistrationForm extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public UserRegistrationForm() {
        setTitle("User Registration");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        roleBox = new JComboBox<>(new String[]{"citizen", "guest"}); // Add more roles if needed

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        add(nameLabel); add(nameField);
        add(emailLabel); add(emailField);
        add(passwordLabel); add(passwordField);
        add(roleLabel); add(roleBox);
        add(registerBtn); add(backBtn);

        registerBtn.addActionListener(e -> handleRegistration());
        backBtn.addActionListener(e -> {
            dispose();
            new MainUI();
        });

        setVisible(true);
    }

    private void handleRegistration() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ All fields are required.");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.registerUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Registered successfully!");
            dispose();
            new MainUI();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Registration failed. Try again.");
        }
    }
}
