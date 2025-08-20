<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="model.User, java.util.*, model.Product" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Products - Pahana Edu</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 20px;
        }
        header {
            background: #191970;
            padding: 15px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .logout-btn {
            background: #e74c3c;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
        }
        .container {
            max-width: 900px;
            margin: 30px auto;
            background: white;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th { background-color: #191970; color: white; }
        .btn-buy {
            background-color: #2ecc71;
            border: none;
            padding: 6px 12px;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-buy:hover { background-color: #27ae60; }
    </style>
</head>
<body>
<header>
    <h2>📚 Pahana Edu - Welcome, <%= user.getUsername() %>!</h2>
    <a href="LogoutServlet" class="logout-btn">Logout</a>
</header>

<div class="container">
    <h1>Available Products</h1>
    <a href="cart.jsp">🛒 View Cart</a>

    <table>
        <tr>
            <th>ID</th><th>Name</th><th>Price (LKR)</th><th>Stock</th><th>Buy</th>
        </tr>
        <% for (Product p : products) { %>
        <tr>
            <td><%= p.getProductId() %></td>
            <td><%= p.getProductName() %></td>
            <td><%= p.getPrice() %></td>
            <td><%= p.getStockQuantity() %></td>
            <td>
                <form action="cart" method="post" style="margin:0;">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="pid" value="<%= p.getProductId() %>">
                    <input type="number" name="qty" value="1" min="1" max="<%= p.getStockQuantity() %>">
                    <button type="submit" class="btn-buy">Add</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
