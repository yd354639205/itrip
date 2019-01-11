<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2019/1/5
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
    <form action="/regist" method="post">
        <table>
            <tr><td>用户名：</td><td><input type="text" name="username"/></td></tr>
            <tr><td>密码：</td><td><input type="password" name="password"/></td></tr>
            <tr><td>邮箱：</td><td><input type="text" name="email"/></td></tr>
            <tr><td colspan="2"><input type="submit" value="注册"/></td></tr>
        </table>
    </form>
</body>
</html>
