package dao;

import model.ServiceRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAO {

    // User: Submit service request
    public boolean submitRequest(ServiceRequest request) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO service_requests (user_email, category, description, status, is_read) VALUES (?, ?, ?, ?, false)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, request.getUserEmail());
            ps.setString(2, request.getCategory());
            ps.setString(3, request.getDescription());
            ps.setString(4, request.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // User: View their service requests
    public List<ServiceRequest> getRequestsByUser(String userEmail) {
        List<ServiceRequest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM service_requests WHERE user_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ServiceRequest r = new ServiceRequest();
                r.setId(rs.getInt("id"));
                r.setUserEmail(rs.getString("user_email"));
                r.setCategory(rs.getString("category"));
                r.setDescription(rs.getString("description"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Admin: View all service requests and mark them as read
    public List<ServiceRequest> getAllRequests() {
        List<ServiceRequest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM service_requests";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ServiceRequest r = new ServiceRequest();
                r.setId(rs.getInt("id"));
                r.setUserEmail(rs.getString("user_email"));
                r.setCategory(rs.getString("category"));
                r.setDescription(rs.getString("description"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }

            // Mark all requests as read after viewing
            markAllRequestsAsRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Admin: Update request status
    public boolean updateStatus(int id, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE service_requests SET status = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Admin: Get count of unread service requests
    public int getUnreadRequestCount() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM service_requests WHERE is_read = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Admin: Mark all service requests as read
    public void markAllRequestsAsRead() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE service_requests SET is_read = true WHERE is_read = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reports: Get total number of service requests
    public int getTotalRequests() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM service_requests";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Reports: Get number of closed service requests
    public int getClosedRequests() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM service_requests WHERE status = 'Closed'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
