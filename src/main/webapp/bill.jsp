<%@ page import="java.sql.*,util.DBconnection" %>
<%
    String billIdStr = request.getParameter("billId");
    if (billIdStr == null) { System.out.println("No bill"); return; }
    int billId = Integer.parseInt(billIdStr);
    double total = 0;
    String date = "";
    try (Connection c = DBconnection.getConnection();
         PreparedStatement ps = c.prepareStatement("SELECT bill_date, total_amount FROM bills WHERE bill_id = ?")) {
        ps.setInt(1, billId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                date = rs.getString("bill_date");
                total = rs.getDouble("total_amount");
            }
        }
    } catch (Exception ex) { ex.printStackTrace(); }
%>
<html>
<head><title>Bill</title></head>
<body>
<h2>Bill #<%= billId %></h2>
<p>Date: <%= date %></p>
<p>Total: Rs. <%= String.format("%.2f", total) %></p>
<p><a href="products">Back to products</a></p>
</body>
</html>
