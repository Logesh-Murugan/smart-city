package dao;

import model.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    // User: Submit complaint
    public boolean submitComplaint(Complaint complaint) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO complaints (user_email, title, description, status, is_read) VALUES (?, ?, ?, ?, false)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, complaint.getUserEmail());
            ps.setString(2, complaint.getTitle());
            ps.setString(3, complaint.getDescription());
            ps.setString(4, complaint.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // User: View their complaints
    public List<Complaint> getComplaintsByUser(String userEmail) {
        List<Complaint> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM complaints WHERE user_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complaint c = new Complaint();
                c.setId(rs.getInt("id"));
                c.setUserEmail(rs.getString("user_email"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Admin: View all complaints and mark them as read
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM complaints";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complaint c = new Complaint();
                c.setId(rs.getInt("id"));
                c.setUserEmail(rs.getString("user_email"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }

            // After fetching, mark all as read
            markAllComplaintsAsRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Admin: Update complaint status
    public boolean updateStatus(int id, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE complaints SET status = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Admin: Get count of unread complaints
    public int getUnreadComplaintCount() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM complaints WHERE is_read = false";
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

    // Admin: Mark all complaints as read
    public void markAllComplaintsAsRead() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE complaints SET is_read = true WHERE is_read = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reports: Get total number of complaints
    public int getTotalComplaints() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM complaints";
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

    // Reports: Get number of closed complaints
    public int getClosedComplaints() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM complaints WHERE status = 'Closed'";
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
