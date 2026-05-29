<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${news.title} - 新闻浏览</title>
    <link rel="stylesheet" href="${ctx}/assets/css/admin.css">
</head>
<body>
<main class="public-shell">
    <section class="page-head">
        <div>
            <h1>${news.title}</h1>
            <p><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd HH:mm"/></p>
        </div>
        <a class="button secondary" href="${ctx}/news.action">返回新闻列表</a>
    </section>
    <section class="panel">
        <p>${news.summary}</p>
        <div class="article"><c:out value="${news.content}"/></div>
    </section>
</main>
</body>
</html>
