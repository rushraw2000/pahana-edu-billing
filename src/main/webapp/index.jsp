<%@ page import="java.util.*,model.Product,model.Customer" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null) products = new ArrayList<>();
%>
<html>
<head><title>Products</title></head>
<body>
<h2>Products</h2>
<p><a href="cart.jsp">View Cart</a></p>
<table border="1">
    <tr><th>ID</th><th>Name</th><th>Price</th><th>Stock</th><th>Buy</th></tr>
    <%
        for (Product p : products) {
    %>
    <tr>
        <td><%= p.getProductId() %></td>
        <td><%= p.getProductName() %></td>
        <td><%= p.getPrice() %></td>
        <td><%= p.getStockQuantity() %></td>
        <td>
            <form method="post" action="cart" style="margin:0">
                <input type="hidden" name="action" value="add"/>
                <input type="hidden" name="pid" value="<%= p.getProductId() %>"/>
                <input type="number" name="qty" value="1" min="1" max="<%= p.getStockQuantity() %>"/>
                <button type="submit">Add</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
