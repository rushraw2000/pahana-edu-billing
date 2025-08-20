package filter;

import model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter({"/*"})
public class AuthFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        String uri = r.getRequestURI();
        boolean publicAsset =
                uri.endsWith("login.jsp") || uri.endsWith("/login") ||
                        uri.endsWith(".css") || uri.endsWith(".js") || uri.contains("/assets/");

        if (publicAsset) { chain.doFilter(req, res); return; }

        HttpSession session = r.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");

        if (user == null) { p.sendRedirect(r.getContextPath() + "/login.jsp"); return; }
        chain.doFilter(req, res);
    }
}
