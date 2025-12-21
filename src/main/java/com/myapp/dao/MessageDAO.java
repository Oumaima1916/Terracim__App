package com.myapp.dao;

import com.myapp.db.DBConnection;
import com.myapp.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /* ========== INSERT MESSAGE ========== */
    public void insert(int senderId, int receiverId, String content) {

        String sql = """
            INSERT INTO messages (sender_id, receiver_id, content)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertText(int senderId, int receiverId, String content) {

        String sql = """
        INSERT INTO messages (sender_id, receiver_id, content, type)
        VALUES (?, ?, ?, 'TEXT')
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertFile(int senderId, int receiverId, String fileName, String filePath) {

        String sql = """
        INSERT INTO messages (sender_id, receiver_id, content, type, file_path)
        VALUES (?, ?, ?, 'FILE', ?)
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, fileName);
            ps.setString(4, filePath);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* ========== GET CONVERSATION ========== */
    public List<Message> getConversation(int user1, int user2) {

        List<Message> list = new ArrayList<>();

        String sql = """
            SELECT * FROM messages
            WHERE (sender_id = ? AND receiver_id = ?)
               OR (sender_id = ? AND receiver_id = ?)
            ORDER BY created_at ASC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.setInt(3, user2);
            ps.setInt(4, user1);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setSenderId(rs.getInt("sender_id"));
                m.setReceiverId(rs.getInt("receiver_id"));
                m.setContent(rs.getString("content"));
                m.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
