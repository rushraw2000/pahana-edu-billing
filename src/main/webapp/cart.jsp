<%@ page import="java.util.*, Dao.ProductDAO,model.Product,model.Customer" %>
<%
    Map<Integer,Integer> cart = (Map<Integer,Integer>) session.getAttribute("CART");
    if (cart == null) cart = new HashMap<>();
    ProductDAO pdao = new ProductDAO();
    Dao.CustomerDAO cdao = new Dao.CustomerDAO();
    List<Customer> customers = cdao.findAll();
%>
<html>
<head><title>Cart</title></head>
<body>
<h2>Your Cart</h2>
<a href="products">Back to products</a>
<% if (cart.isEmpty()) { %>
<p>Cart is empty.</p>
<% } else { %>
<form action="checkout" method="post">
    <table border="1">
        <tr><th>Product</th><th>Qty</th><th>Price</th><th>Subtotal</th></tr>
        <%
            double total = 0;
            for (Map.Entry<Integer,Integer> e : cart.entrySet()) {
                Product p = pdao.findById(e.getKey());
                int qty = e.getValue();
                double sub = p.getPrice() * qty;
                total += sub;
        %>
        <tr>
            <td><%=p.getProductName()%></td>
            <td><%=qty%></td>
            <td><%=p.getPrice()%></td>
            <td><%=String.format("%.2f", sub)%></td>
        </tr>
        <% } %>
        <tr><td colspan="3" align="right">Total</td><td><%=String.format("%.2f", total)%></td></tr>
    </table>

    <p>
        <label>Select Customer:</label>
        <select name="customerAccount">
            <% for (Customer c : customers) { %>
            <option value="<%=c.getAccountNumber()%>"><%=c.getCustomerName()%> (<%=c.getAccountNumber()%>)</option>
            <% } %>
        </select>
    </p>

    <button type="submit">Checkout</button>
</form>
<% } %>
</body>
</html>
