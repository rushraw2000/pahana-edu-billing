package Servlet;

import Dao.ProductDAO;
import Dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDAO.findAll());
        req.setAttribute("customers", customerDAO.findAll()); // for drop-down in cart/checkout
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
