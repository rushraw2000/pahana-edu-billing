package Servlet;

import Dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private final CustomerDAO dao = new CustomerDAO();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Customer> list = dao.findAll();
        req.setAttribute("customers", list);
        req.getRequestDispatcher("/customers.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                Servlet.DaoUtil.execUpdate(
                        "INSERT INTO customers (account_number, customer_name, address, telephone, email) VALUES (?,?,?,?,?)",
                        ps -> {
                            ps.setString(1, req.getParameter("account"));
                            ps.setString(2, req.getParameter("name"));
                            ps.setString(3, req.getParameter("address"));
                            ps.setString(4, req.getParameter("tel"));
                            ps.setString(5, req.getParameter("email"));
                        });
            } else if ("update".equals(action)) {
                Servlet.DaoUtil.execUpdate(
                        "UPDATE customers SET customer_name=?, address=?, telephone=?, email=? WHERE account_number=?",
                        ps -> {
                            ps.setString(1, req.getParameter("name"));
                            ps.setString(2, req.getParameter("address"));
                            ps.setString(3, req.getParameter("tel"));
                            ps.setString(4, req.getParameter("email"));
                            ps.setString(5, req.getParameter("account"));
                        });
            } else if ("delete".equals(action)) {
                Servlet.DaoUtil.execUpdate(
                        "DELETE FROM customers WHERE account_number=?",
                        ps -> ps.setString(1, req.getParameter("account")));
            }
            resp.sendRedirect("customers");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
