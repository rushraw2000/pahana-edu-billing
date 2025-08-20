package Dao;

import util.DBconnection;

import java.sql.*;
import java.util.Map;

public class BillDAO {

    // create bill and bill_items in a single transaction
    // cart: productId -> qty
    public int createBill(String customerAccount, Map<Integer, Integer> cart) throws SQLException {
        String insertBill = "INSERT INTO bills (customer_account, total_amount) VALUES (?, ?)";
        String insertItem = "INSERT INTO bill_items (bill_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

        ProductDAO productDAO = new ProductDAO();

        try (Connection conn = DBconnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement billStmt = conn.prepareStatement(insertBill, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement itemStmt = conn.prepareStatement(insertItem)) {

                double total = 0.0;
                // calculate total and check stock
                for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
                    int pid = e.getKey();
                    int qty = e.getValue();
                    var p = productDAO.findById(pid);
                    if (p == null) throw new SQLException("Product not found: " + pid);
                    if (p.getStockQuantity() < qty) throw new SQLException("Not enough stock for product " + pid);
                    total += p.getPrice() * qty;
                }

                // insert bill
                billStmt.setString(1, customerAccount);
                billStmt.setDouble(2, total);
                billStmt.executeUpdate();
                int billId;
                try (ResultSet keys = billStmt.getGeneratedKeys()) {
                    keys.next();
                    billId = keys.getInt(1);
                }

                // insert items and reduce stock
                for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
                    int pid = e.getKey();
                    int qty = e.getValue();
                    var p = productDAO.findById(pid);
                    double unitPrice = p.getPrice();
                    double subtotal = unitPrice * qty;

                    itemStmt.setInt(1, billId);
                    itemStmt.setInt(2, pid);
                    itemStmt.setInt(3, qty);
                    itemStmt.setDouble(4, unitPrice);
                    itemStmt.setDouble(5, subtotal);
                    itemStmt.addBatch();

                    // reduce stock using same connection
                    productDAO.reduceStock(pid, qty, conn);
                }
                itemStmt.executeBatch();

                conn.commit();
                return billId;
            } catch (Exception ex) {
                conn.rollback();
                throw ex instanceof SQLException ? (SQLException) ex : new SQLException(ex);
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}