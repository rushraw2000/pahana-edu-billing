<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h2>Login</h2>
<form method="post" action="login">
    <label>Username:</label><input name="username"/><br/>
    <label>Password:</label><input type="password" name="password"/><br/>
    <button type="submit">Login</button>
</form>
<c:if test="${param.error != null}">Invalid credentials</c:if>
</body></html>
