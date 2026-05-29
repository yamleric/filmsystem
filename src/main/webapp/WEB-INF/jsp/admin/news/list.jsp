<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>新闻管理</h1>
        <p>维护新闻信息，并提供前台浏览页面。</p>
    </div>
    <a class="button" href="${ctx}/admin/news-add.action">发布新闻</a>
</section>

<form class="search-bar" action="${ctx}/admin/news-list.action" method="get">
    <input name="keyword" value="${keyword}" placeholder="搜索标题、摘要或正文">
    <input type="hidden" name="pageSize" value="${pageResult.pageSize}">
    <button type="submit">查询</button>
    <a class="button secondary" href="${ctx}/admin/news-list.action">重置</a>
</form>

<c:choose>
    <c:when test="${empty newsList}">
        <div class="empty">暂无新闻。</div>
    </c:when>
    <c:otherwise>
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>标题</th>
                <th>摘要</th>
                <th>状态</th>
                <th>发布时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${newsList}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.title}</td>
                    <td>${item.summary}</td>
                    <td>${item.status == 'PUBLISHED' ? '已发布' : '草稿'}</td>
                    <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
                        <div class="ops">
                            <a class="button ghost" href="${ctx}/admin/news-detail.action?id=${item.id}">查看</a>
                            <a class="button ghost" href="${ctx}/admin/news-edit.action?id=${item.id}">编辑</a>
                            <a class="button danger" data-confirm="确认删除该新闻？" href="${ctx}/admin/news-delete.action?id=${item.id}">删除</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
<c:if test="${not empty pageResult}">
    <div class="pager">
        <span>共 ${pageResult.total} 条，第 ${pageResult.page} / ${pageResult.totalPages == 0 ? 1 : pageResult.totalPages} 页</span>
        <div class="actions">
            <c:url var="newsPrevUrl" value="/admin/news-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${pageResult.page - 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:url var="newsNextUrl" value="/admin/news-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${pageResult.page + 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:if test="${pageResult.hasPrevious}">
                <a class="button secondary" href="${newsPrevUrl}">上一页</a>
            </c:if>
            <c:if test="${pageResult.hasNext}">
                <a class="button secondary" href="${newsNextUrl}">下一页</a>
            </c:if>
        </div>
    </div>
</c:if>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
