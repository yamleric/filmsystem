<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>工作台</h1>
        <p>查看后台核心数据，并快速进入常用管理功能。</p>
    </div>
</section>

<section class="stats">
    <div class="stat">
        <span>用户</span>
        <strong>${userCount}</strong>
    </div>
    <div class="stat">
        <span>新闻</span>
        <strong>${newsCount}</strong>
    </div>
    <div class="stat">
        <span>影片分类</span>
        <strong>${categoryCount}</strong>
    </div>
    <div class="stat">
        <span>影片</span>
        <strong>${filmCount}</strong>
    </div>
</section>

<section class="panel">
    <div class="page-head">
        <div>
            <h1>快捷操作</h1>
            <p>常用功能集中入口。</p>
        </div>
    </div>
    <div class="actions">
        <a class="button" href="${ctx}/admin/film-add.action">上传影片</a>
        <a class="button secondary" href="${ctx}/admin/news-add.action">发布新闻</a>
        <a class="button secondary" href="${ctx}/admin/category-add.action">新增分类</a>
        <a class="button ghost" href="${ctx}/news.action" target="_blank">浏览新闻</a>
    </div>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
