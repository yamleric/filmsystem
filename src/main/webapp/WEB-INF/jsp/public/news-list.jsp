<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新闻浏览 - 电影后台管理系统</title>
    <link rel="stylesheet" href="${ctx}/assets/css/admin.css">
</head>
<body>
<main class="public-shell">
    <section class="page-head">
        <div>
            <h1>新闻浏览</h1>
            <p>查看后台发布的新闻内容。</p>
        </div>
        <a class="button ghost" href="${ctx}/login.action">后台登录</a>
    </section>
    <form class="search-bar" action="${ctx}/news.action" method="get">
        <input name="keyword" value="${keyword}" placeholder="搜索新闻标题或内容">
        <button type="submit">查询</button>
        <a class="button secondary" href="${ctx}/news.action">重置</a>
    </form>
    <section class="public-list">
        <c:choose>
            <c:when test="${empty newsList}">
                <div class="empty">暂无已发布新闻。</div>
            </c:when>
            <c:otherwise>
                <c:forEach var="item" items="${newsList}">
                    <article class="public-item">
                        <h2><a href="${ctx}/news-view.action?id=${item.id}">${item.title}</a></h2>
                        <p>${item.summary}</p>
                        <p><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm"/></p>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </section>
    <c:if test="${not empty pageResult}">
        <div class="pager">
            <span>共 ${pageResult.total} 条，第 ${pageResult.page} / ${pageResult.totalPages == 0 ? 1 : pageResult.totalPages} 页</span>
            <div class="actions">
                <c:url var="publicNewsPrevUrl" value="/news.action">
                    <c:param name="keyword" value="${keyword}"/>
                    <c:param name="page" value="${pageResult.page - 1}"/>
                    <c:param name="pageSize" value="${pageResult.pageSize}"/>
                </c:url>
                <c:url var="publicNewsNextUrl" value="/news.action">
                    <c:param name="keyword" value="${keyword}"/>
                    <c:param name="page" value="${pageResult.page + 1}"/>
                    <c:param name="pageSize" value="${pageResult.pageSize}"/>
                </c:url>
                <c:if test="${pageResult.hasPrevious}">
                    <a class="button secondary" href="${publicNewsPrevUrl}">上一页</a>
                </c:if>
                <c:if test="${pageResult.hasNext}">
                    <a class="button secondary" href="${publicNewsNextUrl}">下一页</a>
                </c:if>
            </div>
        </div>
    </c:if>
</main>
</body>
</html>
