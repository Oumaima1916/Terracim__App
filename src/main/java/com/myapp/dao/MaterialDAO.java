package com.myapp.dao;

import com.myapp.db.DBConnection;
import com.myapp.model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    /* ========== INSERT ========== */
    public void insert(int taskId,
                       String material,
                       String supplier,
                       double quantity,
                       double unitPrice) {

        String sql = """
            INSERT INTO materials (task_id, material, supplier, quantity, unit_price)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.setString(2, material);
            ps.setString(3, supplier);
            ps.setDouble(4, quantity);
            ps.setDouble(5, unitPrice);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ========== SELECT ========== */
    public List<Material> getByTaskId(int taskId) {

        List<Material> list = new ArrayList<>();

        String sql = "SELECT * FROM materials WHERE task_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Material m = new Material();
                m.setId(rs.getInt("id"));
                m.setTaskId(taskId);
                m.setMaterial(rs.getString("material"));
                m.setSupplier(rs.getString("supplier"));
                m.setQuantity(rs.getDouble("quantity"));
                m.setUnitPrice(rs.getDouble("unit_price"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* ========== DELETE ========== */
    public void deleteByTaskId(int taskId) {

        String sql = "DELETE FROM materials WHERE task_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
