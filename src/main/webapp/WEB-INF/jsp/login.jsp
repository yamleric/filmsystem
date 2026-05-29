<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登录 - 电影后台管理系统</title>
    <link rel="stylesheet" href="${ctx}/assets/css/admin.css">
</head>
<body class="login-body">
<section class="login-panel">
    <h1>电影后台管理系统</h1>
    <p>请使用管理员账号进入后台。</p>
    <c:if test="${not empty errorMessage}">
        <div class="notice error">${errorMessage}</div>
    </c:if>
    <form action="${ctx}/login.action" method="post" accept-charset="UTF-8">
        <div class="field">
            <label for="username">用户名</label>
            <input id="username" name="username" value="${username}" autocomplete="username" required>
        </div>
        <div class="field">
            <label for="password">密码</label>
            <input id="password" name="password" type="password" autocomplete="current-password" required>
        </div>
        <div class="field">
            <label for="captcha">验证码</label>
            <div class="captcha-line">
                <input id="captcha" name="captcha" autocomplete="off" maxlength="4" required>
                <img src="${ctx}/captcha.jpg" alt="验证码" title="点击刷新" onclick="this.src='${ctx}/captcha.jpg?t=' + Date.now()">
            </div>
        </div>
        <button type="submit">登录</button>
    </form>
</section>
</body>
</html>
