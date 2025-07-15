package ui;

import dao.ComplaintDAO;
import dao.ServiceRequestDAO;
import model.Complaint;
import model.ServiceRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminDashboardUI extends JFrame {
    private ComplaintDAO complaintDAO = new ComplaintDAO();
    private ServiceRequestDAO requestDAO = new ServiceRequestDAO();

    public AdminDashboardUI() {
        setTitle("Admin Dashboard - Smart City");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Notifications
        int unreadComplaints = complaintDAO.getUnreadComplaintCount();
        int unreadRequests = requestDAO.getUnreadRequestCount();
        JLabel notificationLabel = new JLabel("🔔 Unread Complaints: " + unreadComplaints +
                " | 🛠 Unread Service Requests: " + unreadRequests);
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton viewComplaintsBtn = new JButton("📬 View All Complaints");
        JButton updateComplaintBtn = new JButton("✏️ Update Complaint Status");
        JButton viewRequestsBtn = new JButton("🔍 View All Service Requests");
        JButton updateRequestBtn = new JButton("✏️ Update Request Status");
        JButton logoutBtn = new JButton("🚪 Logout");

        buttonPanel.add(viewComplaintsBtn);
        buttonPanel.add(updateComplaintBtn);
        buttonPanel.add(viewRequestsBtn);
        buttonPanel.add(updateRequestBtn);
        buttonPanel.add(logoutBtn);

        add(notificationLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Event handlers
        viewComplaintsBtn.addActionListener(e -> viewAllComplaints());
        updateComplaintBtn.addActionListener(e -> updateComplaintStatus());
        viewRequestsBtn.addActionListener(e -> viewAllRequests());
        updateRequestBtn.addActionListener(e -> updateRequestStatus());
        logoutBtn.addActionListener(e -> {
            dispose();
            new MainUI(); // Go back to main menu
        });

        setVisible(true);
    }

    private void viewAllComplaints() {
        List<Complaint> list = complaintDAO.getAllComplaints();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No complaints found.");
            return;
        }
        StringBuilder sb = new StringBuilder("All Complaints:\n");
        for (Complaint c : list) {
            sb.append("ID: ").append(c.getId()).append("\n")
              .append("Email: ").append(c.getUserEmail()).append("\n")
              .append("Title: ").append(c.getTitle()).append("\n")
              .append("Desc: ").append(c.getDescription()).append("\n")
              .append("Status: ").append(c.getStatus()).append("\n\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void updateComplaintStatus() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Complaint ID to update:");
        if (idStr == null) return;
        String newStatus = JOptionPane.showInputDialog(this, "Enter new status (Open/Closed):");
        if (newStatus == null) return;

        boolean updated = complaintDAO.updateStatus(Integer.parseInt(idStr), newStatus);
        JOptionPane.showMessageDialog(this, updated ? "✅ Status updated!" : "❌ Update failed.");
    }

    private void viewAllRequests() {
        List<ServiceRequest> list = requestDAO.getAllRequests();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No service requests found.");
            return;
        }
        StringBuilder sb = new StringBuilder("All Service Requests:\n");
        for (ServiceRequest r : list) {
            sb.append("ID: ").append(r.getId()).append("\n")
              .append("Email: ").append(r.getUserEmail()).append("\n")
              .append("Category: ").append(r.getCategory()).append("\n")
              .append("Desc: ").append(r.getDescription()).append("\n")
              .append("Status: ").append(r.getStatus()).append("\n\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void updateRequestStatus() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Request ID to update:");
        if (idStr == null) return;
        String newStatus = JOptionPane.showInputDialog(this, "Enter new status (Open/Closed):");
        if (newStatus == null) return;

        boolean updated = requestDAO.updateStatus(Integer.parseInt(idStr), newStatus);
        JOptionPane.showMessageDialog(this, updated ? "✅ Status updated!" : "❌ Update failed.");
    }
}
