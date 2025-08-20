package Servlet;

import Dao.BillDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private final BillDAO billDAO = new BillDAO();

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect("products"); return; }
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("CART");
        if (cart == null || cart.isEmpty()) { resp.sendRedirect("cart"); return; }

        String customerAccount = req.getParameter("customerAccount");
        try {
            int billId = billDAO.createBill(customerAccount, cart);
            session.removeAttribute("CART");
            resp.sendRedirect("bill.jsp?billId=" + billId);
        } catch (SQLException ex) {
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }
}