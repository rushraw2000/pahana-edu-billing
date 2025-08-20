<%@ page import="java.sql.*,util.DBconnection" %>
<%
    String billIdStr = request.getParameter("billId");
    if (billIdStr == null) { response.sendRedirect("products"); return; }
    int billId = Integer.parseInt(billIdStr);
    double total = 0; String date = "";

    try (Connection c = DBconnection.getConnection();
         PreparedStatement ps = c.prepareStatement("SELECT bill_date, total_amount FROM bills WHERE bill_id=?")) {
        ps.setInt(1, billId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                date = rs.getString("bill_date");
                total = rs.getDouble("total_amount");
            }
        }
    } catch (Exception ex) { ex.printStackTrace(); }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bill #<%=billId%></title>
    <style>
        body{font-family:Segoe UI,sans-serif;margin:24px;background:#f9fafb}
        .box{max-width:720px;margin:auto;border:1px solid #e5e7eb;border-radius:12px;padding:24px;background:#fff}
        .row{display:flex;justify-content:space-between;align-items:center}
        table{width:100%;border-collapse:collapse;margin-top:16px}
        th,td{border:1px solid #e5e7eb;padding:8px;text-align:left}
        th{background:#f1f5f9}
        .btn{padding:10px 14px;border-radius:8px;border:1px solid #cbd5e1;text-decoration:none;color:#111827;background:#f9fafb;cursor:pointer}
        .btn:hover{background:#e5e7eb}
    </style>
</head>
<body>
<div class="box">
    <div class="row">
        <h2>Invoice — Pahana Edu</h2>
        <div>
            Bill #: <b><%=billId%></b><br/>
            Date: <%=date%>
        </div>
    </div>
    <hr/>

    <h3>Items</h3>
    <table>
        <tr>
            <th>Product</th>
            <th>Qty</th>
            <th>Unit Price (Rs.)</th>
            <th>Subtotal (Rs.)</th>
        </tr>
        <%
            try (Connection c = DBconnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "SELECT p.product_name, i.quantity, i.unit_price, i.subtotal " +
                                 "FROM bill_items i JOIN products p ON i.product_id = p.product_id " +
                                 "WHERE i.bill_id=?")) {
                ps.setInt(1, billId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("product_name") %></td>
            <td><%= rs.getInt("quantity") %></td>
            <td><%= String.format("%.2f", rs.getDouble("unit_price")) %></td>
            <td><%= String.format("%.2f", rs.getDouble("subtotal")) %></td>
        </tr>
        <%
                    }
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        %>
    </table>

    <hr/>
    <h3>Total: Rs. <%= String.format("%.2f", total) %></h3>

    <div style="margin-top:18px" class="row">
        <a class="btn" href="products">Back to Products</a>
        <button class="btn" onclick="window.print()">Print</button>
    </div>
</div>
</body>
</html>
