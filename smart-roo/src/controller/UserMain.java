package controller;

import dao.UserDAO;
import dao.ComplaintDAO;
import dao.ServiceRequestDAO;
import model.User;
import model.Complaint;
import model.ServiceRequest;

import java.util.List;
import java.util.Scanner;

public class UserMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        ComplaintDAO complaintDAO = new ComplaintDAO();
        ServiceRequestDAO requestDAO = new ServiceRequestDAO();

        System.out.println("----- User Login -----");
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (!userDAO.validateUser(email, password)) {
            System.out.println("❌ Invalid credentials.");
            return;
        }

        System.out.println("✅ Login successful!");

        while (true) {
            System.out.println("\n----- User Dashboard -----");
            System.out.println("1. Submit Complaint");
            System.out.println("2. View My Complaints");
            System.out.println("3. Submit Service Request");
            System.out.println("4. View My Service Requests");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Complaint Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = sc.nextLine();

                    Complaint complaint = new Complaint(email, title, desc);
                    if (complaintDAO.submitComplaint(complaint)) {
                        System.out.println("✅ Complaint submitted.");
                    } else {
                        System.out.println("❌ Submission failed.");
                    }
                    break;

                case 2:
                    List<Complaint> complaints = complaintDAO.getComplaintsByUser(email);
                    if (complaints.isEmpty()) {
                        System.out.println("⚠️ No complaints found.");
                    } else {
                        for (Complaint c : complaints) {
                            System.out.println("\nID: " + c.getId());
                            System.out.println("Title: " + c.getTitle());
                            System.out.println("Description: " + c.getDescription());
                            System.out.println("Status: " + c.getStatus());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Service Category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter Service Description: ");
                    String serviceDesc = sc.nextLine();

                    ServiceRequest request = new ServiceRequest();
                    request.setUserEmail(email);
                    request.setCategory(category);
                    request.setDescription(serviceDesc);
                    request.setStatus("Pending");

                    if (requestDAO.submitRequest(request)) {
                        System.out.println("✅ Service request submitted.");
                    } else {
                        System.out.println("❌ Submission failed.");
                    }
                    break;

                case 4:
                    List<ServiceRequest> requests = requestDAO.getRequestsByUser(email);
                    if (requests.isEmpty()) {
                        System.out.println("⚠️ No service requests found.");
                    } else {
                        for (ServiceRequest r : requests) {
                            System.out.println("\nID: " + r.getId());
                            System.out.println("Category: " + r.getCategory());
                            System.out.println("Description: " + r.getDescription());
                            System.out.println("Status: " + r.getStatus());
                        }
                    }
                    break;

                case 5:
                    System.out.println("👋 Logged out.");
                    return;

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
