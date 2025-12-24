package com.myapp.dao;

import com.myapp.db.DBConnection;
import com.myapp.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public boolean addProject(Project p) {
        String sql = """
            INSERT INTO projects (title, client, location, description, chef_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getClient());
            ps.setString(3, p.getLocation());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getChefId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Project> getProjectsByChef(int chefId) {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE chef_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, chefId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project p = new Project();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setClient(rs.getString("client"));
                p.setLocation(rs.getString("location"));
                p.setDescription(rs.getString("description"));
                p.setChefId(rs.getInt("chef_id"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
