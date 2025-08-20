package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getCart(HttpSession session) {
        Object obj = session.getAttribute("CART");
        if (obj == null) {
            Map<Integer, Integer> cart = new HashMap<>();
            session.setAttribute("CART", cart);
            return cart;
        }
        return (Map<Integer, Integer>) obj;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        Map<Integer, Integer> cart = getCart(session);
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int pid = parseInt(req.getParameter("pid"), -1);
            int qty = Math.max(1, parseInt(req.getParameter("qty"), 1));
            if (pid > 0) cart.put(pid, cart.getOrDefault(pid, 0) + qty);
            resp.sendRedirect("products");
            return;
        }

        if ("clear".equals(action)) {
            cart.clear();
            resp.sendRedirect("cart");
            return;
        }

        resp.sendRedirect("cart");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
