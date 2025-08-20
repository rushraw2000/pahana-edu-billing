package Dao;

import model.Product;
import util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT product_id, product_name, price, stock_quantity, description FROM products ORDER BY product_id";
        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public Product findById(int id) {
        String sql = "SELECT product_id, product_name, price, stock_quantity, description FROM products WHERE product_id = ?";
        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    // use connection passed (transaction)
    public void reduceStock(int productId, int qty, Connection conn) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            int updated = ps.executeUpdate();
            if (updated == 0) throw new SQLException("Insufficient stock for product " + productId);
        }
    }
}