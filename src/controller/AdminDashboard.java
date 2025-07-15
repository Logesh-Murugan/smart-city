package controller;

import dao.AdminDAO;
import dao.ComplaintDAO;
import dao.ServiceRequestDAO;
import model.Complaint;
import model.ServiceRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdminDAO adminDAO = new AdminDAO();
        ComplaintDAO complaintDAO = new ComplaintDAO();
        ServiceRequestDAO requestDAO = new ServiceRequestDAO();

        System.out.println("----- Admin Login -----");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (!adminDAO.login(username, password)) {
            System.out.println("❌ Invalid credentials. Access denied.");
            return;
        }

        // Show notifications after login
        int unreadComplaints = complaintDAO.getUnreadComplaintCount();
        int unreadRequests = requestDAO.getUnreadRequestCount();
        System.out.println("\n🔔 Notifications:");
        System.out.println("📬 Unread Complaints: " + unreadComplaints);
        System.out.println("🛠️ Unread Service Requests: " + unreadRequests);

        while (true) {
            System.out.println("\n----- Admin Dashboard -----");
            System.out.println("1. View All Complaints");
            System.out.println("2. Update Complaint Status");
            System.out.println("3. View All Service Requests");
            System.out.println("4. Update Service Request Status");
            System.out.println("5. View Admin Reports");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    List<Complaint> complaints = complaintDAO.getAllComplaints();
                    if (complaints.isEmpty()) {
                        System.out.println("❗ No complaints found.");
                    } else {
                        for (Complaint c : complaints) {
                            System.out.println("\nID: " + c.getId());
                            System.out.println("Email: " + c.getUserEmail());
                            System.out.println("Title: " + c.getTitle());
                            System.out.println("Description: " + c.getDescription());
                            System.out.println("Status: " + c.getStatus());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Complaint ID to update: ");
                    int compId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new status (Open/Closed): ");
                    String compStatus = sc.nextLine();
                    boolean compUpdated = complaintDAO.updateStatus(compId, compStatus);
                    System.out.println(compUpdated ? "✅ Complaint status updated!" : "❌ Failed to update complaint.");
                    break;

                case 3:
                    List<ServiceRequest> requests = requestDAO.getAllRequests();
                    if (requests.isEmpty()) {
                        System.out.println("❗ No service requests found.");
                    } else {
                        for (ServiceRequest r : requests) {
                            System.out.println("\nID: " + r.getId());
                            System.out.println("Email: " + r.getUserEmail());
                            System.out.println("Category: " + r.getCategory());
                            System.out.println("Description: " + r.getDescription());
                            System.out.println("Status: " + r.getStatus());
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter Request ID to update: ");
                    int reqId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new status (Open/Closed): ");
                    String reqStatus = sc.nextLine();
                    boolean reqUpdated = requestDAO.updateStatus(reqId, reqStatus);
                    System.out.println(reqUpdated ? "✅ Request status updated!" : "❌ Failed to update request.");
                    break;

                case 5:
                    // Admin Reports
                    int totalComplaints = complaintDAO.getAllComplaints().size();
                    int closedComplaints = (int) complaintDAO.getAllComplaints().stream()
                            .filter(c -> c.getStatus().equalsIgnoreCase("Closed")).count();
                    int totalRequests = requestDAO.getAllRequests().size();
                    int closedRequests = (int) requestDAO.getAllRequests().stream()
                            .filter(r -> r.getStatus().equalsIgnoreCase("Closed")).count();

                    System.out.println("\n📊 Admin Report Summary:");
                    System.out.println("📬 Total Complaints: " + totalComplaints);
                    System.out.println("✅ Closed Complaints: " + closedComplaints);
                    System.out.println("🛠️ Total Service Requests: " + totalRequests);
                    System.out.println("🔒 Closed Service Requests: " + closedRequests);

                    System.out.print("\nDo you want to export this report to a file? (yes/no): ");
                    String exportChoice = sc.nextLine().trim().toLowerCase();
                    if (exportChoice.equals("yes")) {
                        exportReport(totalComplaints, closedComplaints, totalRequests, closedRequests);
                    }
                    break;

                case 6:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    // Export report to text file
    private static void exportReport(int totalComplaints, int closedComplaints, int totalRequests, int closedRequests) {
        String fileName = "admin_report.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Admin Report Summary:\n");
            writer.write("---------------------\n");
            writer.write("Total Complaints: " + totalComplaints + "\n");
            writer.write("Closed Complaints: " + closedComplaints + "\n");
            writer.write("Total Service Requests: " + totalRequests + "\n");
            writer.write("Closed Service Requests: " + closedRequests + "\n");
            System.out.println("📁 Report exported successfully to '" + fileName + "'");
        } catch (IOException e) {
            System.out.println("❌ Failed to export report: " + e.getMessage());
        }
    }
}
