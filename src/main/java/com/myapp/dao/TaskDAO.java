package com.myapp.dao;

import com.myapp.db.DBConnection;
import com.myapp.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    /* ========== INSERT ========== */
    public int insert(Task task) {

        String sql = """
            INSERT INTO tasks (project_id, name, budget_prevu, budget_utilise, resume)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            ps.setInt(1, task.getProjectId());
            ps.setString(2, task.getName());
            ps.setDouble(3, task.getBudgetPrevu());
            ps.setDouble(4, task.getBudgetUtilise());
            ps.setString(5, task.getResume());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /* ========== UPDATE (EDIT MODE) ========== */
    public void update(int id,
                       String name,
                       double budgetPrevu,
                       double budgetUtilise,
                       String resume) {

        String sql = """
            UPDATE tasks
            SET name = ?, budget_prevu = ?, budget_utilise = ?, resume = ?
            WHERE id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, budgetPrevu);
            ps.setDouble(3, budgetUtilise);
            ps.setString(4, resume);
            ps.setInt(5, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ========= SELECT BY PROJECT ========= */
    public List<Task> getTasksByProject(int projectId) {

        List<Task> list = new ArrayList<>();

        String sql = """
            SELECT * FROM tasks
            WHERE project_id = ?
            ORDER BY id DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setProjectId(projectId);
                t.setName(rs.getString("name"));
                t.setBudgetPrevu(rs.getDouble("budget_prevu"));
                t.setBudgetUtilise(rs.getDouble("budget_utilise"));
                t.setResume(rs.getString("resume"));
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
