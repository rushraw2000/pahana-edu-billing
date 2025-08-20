package Servlet;

import Dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ItemServlet extends HttpServlet {
    private final ProductDAO dao = new ProductDAO();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> items = dao.findAll();
        req.setAttribute("products", items);
        req.getRequestDispatcher("products.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                Product p = new Product(
                        0,
                        req.getParameter("name"),
                        Double.parseDouble(req.getParameter("price")),
                        Integer.parseInt(req.getParameter("stock")),
                        req.getParameter("desc")
                );
                // simple add via SQL (implement an insert in your ProductDAO if not present)
                // For brevity, reuse a tiny inline insert:
                DaoUtil.execUpdate("INSERT INTO products(product_name,price,stock_quantity,description) VALUES(?,?,?,?)",
                        ps -> {
                            ps.setString(1, p.getProductName());
                            ps.setDouble(2, p.getPrice());
                            ps.setInt(3, p.getStockQuantity());
                            ps.setString(4, p.getDescription());
                        });
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                DaoUtil.execUpdate("UPDATE products SET product_name=?, price=?, stock_quantity=?, description=? WHERE product_id=?",
                        ps -> {
                            ps.setString(1, req.getParameter("name"));
                            ps.setDouble(2, Double.parseDouble(req.getParameter("price")));
                            ps.setInt(3, Integer.parseInt(req.getParameter("stock")));
                            ps.setString(4, req.getParameter("desc"));
                            ps.setInt(5, id);
                        });
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                DaoUtil.execUpdate("DELETE FROM products WHERE product_id=?", ps -> ps.setInt(1, id));
            }
            resp.sendRedirect("products");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
