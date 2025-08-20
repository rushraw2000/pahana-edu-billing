<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, Dao.ProductDAO, model.Product, Dao.CustomerDAO, model.Customer" %>
<%
    Map<Integer,Integer> cart = (Map<Integer,Integer>) session.getAttribute("CART");
    if (cart == null) cart = new HashMap<>();
    ProductDAO pdao = new ProductDAO();
    Dao.CustomerDAO cdao = new Dao.CustomerDAO();
    List<Customer> customers = cdao.findAll();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <style>
        body{font-family:Segoe UI,sans-serif;background:#f6f8fb;margin:0}
        header{background:#191970;color:#fff;padding:14px 24px;display:flex;justify-content:space-between;align-items:center}
        .wrap{max-width:1000px;margin:28px auto;padding:0 16px}
        table{width:100%;border-collapse:collapse;background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 6px 18px rgba(0,0,0,.06)}
        th,td{padding:12px;border-bottom:1px solid #eef2f7;text-align:center}
        th{background:#191970;color:#fff}
        .actions{display:flex;justify-content:flex-end;gap:12px;margin-top:20px}
        input,select,button{padding:10px;border:1px solid #cbd5e1;border-radius:8px}
        .primary{background:#2ecc71;color:#fff;border:none}
        .secondary{background:#191970;color:#fff;border:none;text-decoration:none;display:inline-block}
        .danger{background:#e74c3c;color:#fff;border:none}
        .link{color:#191970;text-decoration:none}
    </style>
</head>
<body>
<header>
    <div>🛒 Your Cart</div>
    <div>
        <a class="link" href="products">← Back to Products</a>
    </div>
</header>

<div class="wrap">
    <% if (cart.isEmpty()) { %>
    <p>Your cart is empty.</p>
    <a class="secondary" href="products" style="padding:10px 14px;border-radius:8px">Browse Products</a>
    <% } else { %>
    <table>
        <tr><th>Product</th><th>Qty</th><th>Unit Price</th><th>Subtotal</th></tr>
        <%
            double total = 0;
            for (Map.Entry<Integer,Integer> e : cart.entrySet()) {
                Product p = pdao.findById(e.getKey());
                int qty = e.getValue();
                double sub = p.getPrice()*qty;
                total += sub;
        %>
        <tr>
            <td><%= p.getProductName() %></td>
            <td><%= qty %></td>
            <td><%= String.format("%.2f", p.getPrice()) %></td>
            <td><%= String.format("%.2f", sub) %></td>
        </tr>
        <% } %>
        <tr><th colspan="3" style="text-align:right">Total</th><th><%= String.format("%.2f", total) %></th></tr>
    </table>

    <!-- Actions Section -->
    <div class="actions">
        <!-- Checkout -->
        <form action="checkout" method="post" style="display:flex;gap:10px;align-items:center">
            <select name="customerAccount" required>
                <option value="" disabled selected>Select Customer</option>
                <% for (Customer c : customers) { %>
                <option value="<%=c.getAccountNumber()%>"><%=c.getCustomerName()%> (<%=c.getAccountNumber()%>)</option>
                <% } %>
            </select>
            <button class="primary" type="submit">Checkout & Print Bill</button>
        </form>

        <!-- Clear Cart -->
        <form action="cart" method="post">
            <input type="hidden" name="action" value="clear">
            <button class="danger" type="submit">Clear Cart</button>
        </form>

        <!-- Continue Shopping -->
        <a class="secondary" href="products" style="padding:10px 14px;border-radius:8px">Continue Shopping</a>
    </div>
    <% } %>
</div>
</body>
</html>
