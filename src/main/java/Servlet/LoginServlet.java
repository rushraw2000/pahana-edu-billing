package Servlet;

import Dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        User user = userDAO.authenticate(u, p);
        if (user != null) {
            HttpSession s = req.getSession(true);
            s.setAttribute("user", user);
            resp.sendRedirect("dashboard");  // go to main menu
        } else {
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}
