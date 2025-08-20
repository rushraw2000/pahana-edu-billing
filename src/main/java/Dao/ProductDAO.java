package com.onlinepahana.dao;

import com.onlinepahana.db.DBconnection;
import com.onlinepahana.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT product_id,name,price,stock FROM products";
        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Product findById(int id, Connection conn) throws SQLException {
        String sql = "SELECT product_id,name,price,stock FROM products WHERE product_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4));
                return null;
            }
        }
    }

    public void reduceStock(int id,int qty, Connection conn) throws SQLException {
        String sql = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty); ps.setInt(2, id); ps.executeUpdate();
        }
    }
}
