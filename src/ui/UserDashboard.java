package ui;

import dao.ComplaintDAO;
import dao.ServiceRequestDAO;
import model.Complaint;
import model.ServiceRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserDashboard extends JFrame {
    private String userEmail;
    private ComplaintDAO complaintDAO = new ComplaintDAO();
    private ServiceRequestDAO requestDAO = new ServiceRequestDAO();

    public UserDashboard(String userEmail) {
        this.userEmail = userEmail;
        setTitle("User Dashboard - " + userEmail);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        JLabel welcome = new JLabel("Welcome, " + userEmail, SwingConstants.CENTER);
        panel.add(welcome);

        JButton submitComplaint = new JButton("Submit Complaint");
        submitComplaint.addActionListener(this::handleSubmitComplaint);
        panel.add(submitComplaint);

        JButton viewComplaints = new JButton("View My Complaints");
        viewComplaints.addActionListener(this::handleViewComplaints);
        panel.add(viewComplaints);

        JButton submitRequest = new JButton("Submit Service Request");
        submitRequest.addActionListener(this::handleSubmitRequest);
        panel.add(submitRequest);

        JButton viewRequests = new JButton("View My Service Requests");
        viewRequests.addActionListener(this::handleViewRequests);
        panel.add(viewRequests);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            dispose();
            new MainUI();
        });
        panel.add(logout);

        add(panel);
        setVisible(true);
    }

    private void handleSubmitComplaint(ActionEvent e) {
        JTextField titleField = new JTextField();
        JTextArea descArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(descArea);

        Object[] fields = {
            "Complaint Title:", titleField,
            "Description:", scrollPane
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Submit Complaint", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Complaint complaint = new Complaint(userEmail, titleField.getText(), descArea.getText());
            boolean success = complaintDAO.submitComplaint(complaint);
            JOptionPane.showMessageDialog(this, success ? "✅ Submitted!" : "❌ Failed to submit.");
        }
    }

    private void handleViewComplaints(ActionEvent e) {
        List<Complaint> list = complaintDAO.getComplaintsByUser(userEmail);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No complaints found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Complaint c : list) {
            sb.append("ID: ").append(c.getId())
              .append("\nTitle: ").append(c.getTitle())
              .append("\nStatus: ").append(c.getStatus())
              .append("\nDescription: ").append(c.getDescription())
              .append("\n\n");
        }
        showTextArea("My Complaints", sb.toString());
    }

    private void handleSubmitRequest(ActionEvent e) {
        JTextField categoryField = new JTextField();
        JTextArea descArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(descArea);

        Object[] fields = {
            "Service Category:", categoryField,
            "Description:", scrollPane
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Submit Service Request", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            ServiceRequest request = new ServiceRequest();
            request.setUserEmail(userEmail);
            request.setCategory(categoryField.getText());
            request.setDescription(descArea.getText());
            request.setStatus("Pending");
            boolean success = requestDAO.submitRequest(request);
            JOptionPane.showMessageDialog(this, success ? "✅ Request submitted!" : "❌ Failed to submit.");
        }
    }

    private void handleViewRequests(ActionEvent e) {
        List<ServiceRequest> list = requestDAO.getRequestsByUser(userEmail);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No requests found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (ServiceRequest r : list) {
            sb.append("ID: ").append(r.getId())
              .append("\nCategory: ").append(r.getCategory())
              .append("\nStatus: ").append(r.getStatus())
              .append("\nDescription: ").append(r.getDescription())
              .append("\n\n");
        }
        showTextArea("My Service Requests", sb.toString());
    }

    private void showTextArea(String title, String content) {
        JTextArea area = new JTextArea(content, 15, 40);
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
