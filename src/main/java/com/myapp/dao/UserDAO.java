package com.myapp.dao;

import com.myapp.db.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public String getPasswordHash(int userId) {

        String sql = "SELECT password FROM users WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updatePassword(int userId, String newPassword) {

        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hashed);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
