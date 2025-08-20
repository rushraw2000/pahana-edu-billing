<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu — Dashboard</title>
    <style>
        body{font-family:Segoe UI,sans-serif;background:#f5f7fb;margin:0}
        header{background:#191970;color:#fff;padding:14px 24px;display:flex;justify-content:space-between;align-items:center}
        .wrap{max-width:1000px;margin:28px auto;padding:0 16px}
        .grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:16px}
        .card{background:#fff;border-radius:14px;box-shadow:0 6px 18px rgba(0,0,0,.06);padding:18px}
        .card a{display:block;text-decoration:none;color:#2c3e50;font-weight:600}
        .muted{color:#6b7280;font-size:12px;margin-top:4px}
        .logout{background:#e74c3c;color:#fff;border-radius:8px;padding:8px 12px;text-decoration:none}
    </style>
</head>
<body>
<header>
    <div>📚 Pahana Edu — Welcome, <b><%= user.getUsername() %></b></div>
    <a class="logout" href="<%=request.getContextPath()%>/LogoutServlet">Logout</a>
</header>

<div class="wrap">
    <div class="grid">
        <div class="card"><a href="products">🛍 Products / Items</a><div class="muted">Add / Update / Delete</div></div>
        <div class="card"><a href="customers">👤 Customers</a><div class="muted">Add & Edit accounts</div></div>
        <div class="card"><a href="cart.jsp">🛒 View Cart</a><div class="muted">Checkout & bill</div></div>
        <div class="card"><a href="help.jsp">❓ Help</a><div class="muted">How to use the system</div></div>
    </div>
</div>
</body>
</html>
