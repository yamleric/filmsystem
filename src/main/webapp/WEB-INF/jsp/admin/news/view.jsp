<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>${news.title}</h1>
        <p><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd HH:mm"/> · ${news.status == 'PUBLISHED' ? '已发布' : '草稿'}</p>
    </div>
    <div class="actions">
        <a class="button ghost" href="${ctx}/admin/news-edit.action?id=${news.id}">编辑</a>
        <a class="button secondary" href="${ctx}/admin/news-list.action">返回列表</a>
    </div>
</section>
<section class="panel">
    <p>${news.summary}</p>
    <div class="article"><c:out value="${news.content}"/></div>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
