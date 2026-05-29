<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>电影后台管理系统</title>
    <link rel="stylesheet" href="${ctx}/assets/css/admin.css">
</head>
<body>
<div class="shell">
    <aside class="sidebar">
        <a class="brand" href="${ctx}/admin/dashboard.action">
            <strong>电影后台</strong>
            <span>Struts2 · Spring5 · Hibernate5</span>
        </a>
        <nav class="nav">
            <a href="${ctx}/admin/dashboard.action">工作台</a>
            <a href="${ctx}/admin/user-list.action">用户管理</a>
            <a href="${ctx}/admin/news-list.action">新闻管理</a>
            <a href="${ctx}/admin/category-list.action">影片分类</a>
            <a href="${ctx}/admin/film-list.action">影片管理</a>
            <a href="${ctx}/admin/film-add.action">影片上传</a>
        </nav>
        <a class="public-link" href="${ctx}/news.action" target="_blank">浏览新闻前台</a>
    </aside>
    <section class="main">
        <header class="topbar">
            <div class="topbar-title">电影后台管理系统</div>
            <div class="topbar-user">
                <span>${sessionScope.loginUser.nickname}</span>
                <a href="${ctx}/admin/password.action">修改密码</a>
                <a class="button ghost" href="${ctx}/logout.action">退出</a>
            </div>
        </header>
        <main class="content">
