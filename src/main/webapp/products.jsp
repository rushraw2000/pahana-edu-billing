<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String err = (String) request.getAttribute("error");
%>

<%
    Map<Integer,Integer> cartTop = (Map<Integer,Integer>) session.getAttribute("CART");
    int cartCount = 0;
    if (cartTop != null) for (Integer q : cartTop.values()) cartCount += q;
%>

<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
    <style>
        body{font-family:Segoe UI,sans-serif;background:#f6f8fb;margin:0}
        header{background:#191970;color:#fff;padding:14px 24px;display:flex;justify-content:space-between;align-items:center}
        .wrap{max-width:1100px;margin:28px auto;padding:0 16px}
        table{width:100%;border-collapse:collapse;background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 6px 18px rgba(0,0,0,.06)}
        th,td{padding:12px;border-bottom:1px solid #eef2f7;text-align:center}
        th{background:#191970;color:#fff}
        .row{display:flex;gap:12px;margin:16px 0}
        input,button{padding:10px;border:1px solid #cbd5e1;border-radius:8px}
        .primary{background:#2ecc71;color:#fff;border:none;cursor:pointer}
        .danger{background:#e74c3c;color:#fff;border:none;cursor:pointer}
        .btnCart{background:#191970;color:#fff;padding:8px 12px;border-radius:8px;text-decoration:none;margin-left:10px}
        .link{color:#fff;text-decoration:none;margin-left:20px}
    </style>
</head>
<body>
<header>
    <div>🛍 Products</div>
    <div>
        <a class="link" href="dashboard">Dashboard</a>
        <a class="btnCart" href="cart">View Cart <%= cartCount>0? "(" + cartCount + ")" : "" %></a>
    </div>
</header>

<div class="wrap">

    <% if (err != null) { %>
    <p style="color:#e74c3c"><%= err %></p>
    <% } %>

    <!-- Add new product -->
    <form class="row" action="products" method="post">
        <input type="hidden" name="action" value="add">
        <input name="name" placeholder="Name" required>
        <input name="price" type="number" step="0.01" placeholder="Price" required>
        <input name="stock" type="number" min="0" placeholder="Stock" required>
        <input name="desc" placeholder="Description">
        <button class="primary" type="submit">Add Item</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Name</th><th>Price</th><th>Stock</th>
            <th>Description</th><th>Add to Cart</th>
        </tr>
        <% for (Product p : products) { %>
        <tr>
            <td><%=p.getProductId()%></td>
            <td><%=p.getProductName()%></td>
            <td><%=p.getPrice()%></td>
            <td><%=p.getStockQuantity()%></td>
            <td><%=p.getDescription()%></td>

            <!-- Add to Cart -->
            <td>
                <form action="cart" method="post" style="display:flex;gap:6px;justify-content:center">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="pid" value="<%=p.getProductId()%>">
                    <input type="number" name="qty" value="1" min="1" max="<%=p.getStockQuantity()%>" style="width:70px">
                    <button class="primary" type="submit">Add</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>

</div>
</body>
</html>
