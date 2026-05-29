<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>用户管理</h1>
        <p>管理后台登录账号，支持新增、修改、停用和删除。</p>
    </div>
    <a class="button" href="${ctx}/admin/user-add.action">新增用户</a>
</section>

<form class="search-bar" action="${ctx}/admin/user-list.action" method="get">
    <input name="keyword" value="${keyword}" placeholder="搜索用户名、昵称或角色">
    <input type="hidden" name="pageSize" value="${pageResult.pageSize}">
    <button type="submit">查询</button>
    <a class="button secondary" href="${ctx}/admin/user-list.action">重置</a>
</form>

<c:choose>
    <c:when test="${empty userList}">
        <div class="empty">暂无用户。</div>
    </c:when>
    <c:otherwise>
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>昵称</th>
                <th>角色</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${userList}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.username}</td>
                    <td>${item.nickname}</td>
                    <td>${item.role}</td>
                    <td><c:choose><c:when test="${item.enabled}">启用</c:when><c:otherwise>停用</c:otherwise></c:choose></td>
                    <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
                        <div class="ops">
                            <a class="button ghost" href="${ctx}/admin/user-edit.action?id=${item.id}">编辑</a>
                            <a class="button danger" data-confirm="确认删除该用户？" href="${ctx}/admin/user-delete.action?id=${item.id}">删除</a>
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
            <c:url var="userPrevUrl" value="/admin/user-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${pageResult.page - 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:url var="userNextUrl" value="/admin/user-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${pageResult.page + 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:if test="${pageResult.hasPrevious}">
                <a class="button secondary" href="${userPrevUrl}">上一页</a>
            </c:if>
            <c:if test="${pageResult.hasNext}">
                <a class="button secondary" href="${userNextUrl}">下一页</a>
            </c:if>
        </div>
    </div>
</c:if>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
