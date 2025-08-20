package Dao;

import model.Customer;
import util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT account_number, customer_name, address, telephone, email FROM customers ORDER BY customer_name";
        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(
                        rs.getString("account_number"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }
}
