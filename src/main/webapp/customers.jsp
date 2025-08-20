<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Customer" %>
<%
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    String err = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Customers</title>
    <style>
        body{font-family:Segoe UI,sans-serif;background:#f6f8fb;margin:0}
        header{background:#191970;color:#fff;padding:14px 24px;display:flex;justify-content:space-between;align-items:center}
        .wrap{max-width:1100px;margin:28px auto;padding:0 16px}
        table{width:100%;border-collapse:collapse;background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 6px 18px rgba(0,0,0,.06)}
        th,td{padding:12px;border-bottom:1px solid #eef2f7;text-align:center}
        th{background:#191970;color:#fff}
        .row{display:flex;gap:12px;margin:16px 0}
        input,button{padding:10px;border:1px solid #cbd5e1;border-radius:8px}
        .primary{background:#2ecc71;color:#fff;border:none}
        .danger{background:#e74c3c;color:#fff;border:none}
        .link{color:#191970;text-decoration:none}
    </style>
</head>
<body>
<header>
    <div>👤 Customers</div>
    <div><a class="link" href="dashboard">Dashboard</a></div>
</header>

<div class="wrap">
    <% if (err != null) { %><p style="color:#e74c3c"><%=err%></p><% } %>

    <form class="row" action="customers" method="post">
        <input type="hidden" name="action" value="add">
        <input name="account" placeholder="Account No." required>
        <input name="name" placeholder="Name" required>
        <input name="address" placeholder="Address">
        <input name="tel" placeholder="Telephone">
        <input name="email" type="email" placeholder="Email">
        <button class="primary" type="submit">Add Customer</button>
    </form>

    <table>
        <tr><th>Account</th><th>Name</th><th>Address</th><th>Telephone</th><th>Email</th><th>Actions</th></tr>
        <% for (Customer c : customers) { %>
        <tr>
            <td><%=c.getAccountNumber()%></td>
            <td><%=c.getCustomerName()%></td>
            <td><%=c.getAddress()%></td>
            <td><%=c.getTelephone()%></td>
            <td><%=c.getEmail()%></td>
            <td>
                <form action="customers" method="post" style="display:flex;gap:6px;justify-content:center">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="account" value="<%=c.getAccountNumber()%>">
                    <input name="name" value="<%=c.getCustomerName()%>" style="width:120px">
                    <input name="address" value="<%=c.getAddress()%>" style="width:150px">
                    <input name="tel" value="<%=c.getTelephone()%>" style="width:120px">
                    <input name="email" value="<%=c.getEmail()%>" style="width:150px">
                    <button class="primary" type="submit">Save</button>
                </form>
                <form action="customers" method="post" style="margin-top:6px">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="account" value="<%=c.getAccountNumber()%>">
                    <button class="danger" onclick="return confirm('Delete customer?')" type="submit">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
