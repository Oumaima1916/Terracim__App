package com.myapp.dao;

import com.myapp.db.DBConnection;
import com.myapp.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    /* ================= INSERT ================= */

    public void insert(Notification n) {

        String sql = """
            INSERT INTO notifications
            (user_id, title, message, type, related_id, is_read)
            VALUES (?, ?, ?, ?, ?, false)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, n.getUserId());
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getMessage());
            ps.setString(4, n.getType());
            ps.setInt(5, n.getRelatedId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= GET UNREAD ================= */

    public List<Notification> getUnreadByUser(int userId) {

        List<Notification> list = new ArrayList<>();

        String sql = """
            SELECT * FROM notifications
            WHERE user_id = ? AND is_read = false
            ORDER BY created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* ================= COUNT ================= */

    public int countUnread(int userId) {

        String sql = """
            SELECT COUNT(*) FROM notifications
            WHERE user_id = ? AND is_read = false
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /* ================= UPDATE ================= */

    public void markAsRead(int id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement("UPDATE notifications SET is_read = true WHERE id = ?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAllAsRead(int userId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement("UPDATE notifications SET is_read = true WHERE user_id = ?")) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= MAP ================= */

    private Notification map(ResultSet rs) throws Exception {

        Notification n = new Notification();
        n.setId(rs.getInt("id"));
        n.setUserId(rs.getInt("user_id"));
        n.setTitle(rs.getString("title"));
        n.setMessage(rs.getString("message"));
        n.setType(rs.getString("type"));
        n.setRelatedId(rs.getInt("related_id"));
        n.setRead(rs.getBoolean("is_read"));
        n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return n;
    }

}
