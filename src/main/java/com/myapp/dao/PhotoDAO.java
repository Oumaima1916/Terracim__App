package com.myapp.dao;

import com.myapp.db.DBConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhotoDAO {

    public void insert(int taskId, String type, File file) {

        String sql = """
            INSERT INTO photos (task_id, type, path)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.setString(2, type);
            ps.setString(3, file.getAbsolutePath());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<File> getByTaskId(int taskId, String type) {

        List<File> files = new ArrayList<>();
        String sql = "SELECT path FROM photos WHERE task_id = ? AND type = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                files.add(new File(rs.getString("path")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return files;
    }

    public void deleteByTaskId(int taskId) {

        String sql = "DELETE FROM photos WHERE task_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
