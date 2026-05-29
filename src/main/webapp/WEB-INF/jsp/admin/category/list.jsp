<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>影片分类管理</h1>
        <p>维护影片类别，上传影片时会使用这里的分类。</p>
    </div>
    <a class="button" href="${ctx}/admin/category-add.action">新增分类</a>
</section>

<form class="search-bar" action="${ctx}/admin/category-list.action" method="get">
    <input name="keyword" value="${keyword}" placeholder="搜索分类名称或说明">
    <input type="hidden" name="pageSize" value="${pageResult.pageSize}">
    <button type="submit">查询</button>
    <a class="button secondary" href="${ctx}/admin/category-list.action">重置</a>
</form>

<c:choose>
    <c:when test="${empty categoryList}">
        <div class="empty">暂无分类。</div>
    </c:when>
    <c:otherwise>
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>分类名称</th>
                <th>排序</th>
                <th>说明</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${categoryList}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.sortNo}</td>
                    <td>${item.description}</td>
                    <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
                        <div class="ops">
                            <a class="button ghost" href="${ctx}/admin/category-edit.action?id=${item.id}">编辑</a>
                            <a class="button danger" data-confirm="确认删除该分类？分类被影片使用时不能删除。" href="${ctx}/admin/category-delete.action?id=${item.id}">删除</a>
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
            <c:url var="categoryPrevUrl" value="/admin/category-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${pageResult.page - 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:url var="categoryNextUrl" value="/admin/category-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${pageResult.page + 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:if test="${pageResult.hasPrevious}">
                <a class="button secondary" href="${categoryPrevUrl}">上一页</a>
            </c:if>
            <c:if test="${pageResult.hasNext}">
                <a class="button secondary" href="${categoryNextUrl}">下一页</a>
            </c:if>
        </div>
    </div>
</c:if>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
