package com.myapp.dao;

import com.myapp.db.DBConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    public void insert(int taskId, File file) {

        String sql = """
            INSERT INTO documents (task_id, filename, path)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.setString(2, file.getName());
            ps.setString(3, file.getAbsolutePath());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<File> getByTaskId(int taskId) {

        List<File> files = new ArrayList<>();
        String sql = "SELECT path FROM documents WHERE task_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
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

        String sql = "DELETE FROM documents WHERE task_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
