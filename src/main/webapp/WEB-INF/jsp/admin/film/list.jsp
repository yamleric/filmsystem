<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>影片管理</h1>
        <p>管理已上传影片的分类、封面、视频文件和详细信息。</p>
    </div>
    <a class="button" href="${ctx}/admin/film-add.action">上传影片</a>
</section>

<form class="search-bar" action="${ctx}/admin/film-list.action" method="get">
    <input name="keyword" value="${keyword}" placeholder="搜索片名、导演、主演或地区">
    <select name="categoryId">
        <option value="">全部分类</option>
        <c:forEach var="category" items="${categoryList}">
            <option value="${category.id}" ${category.id == categoryId ? 'selected' : ''}>${category.name}</option>
        </c:forEach>
    </select>
    <input type="hidden" name="pageSize" value="${pageResult.pageSize}">
    <button type="submit">查询</button>
    <a class="button secondary" href="${ctx}/admin/film-list.action">重置</a>
</form>

<c:choose>
    <c:when test="${empty filmList}">
        <div class="empty">暂无影片。</div>
    </c:when>
    <c:otherwise>
        <table class="table">
            <thead>
            <tr>
                <th>封面</th>
                <th>片名</th>
                <th>分类</th>
                <th>导演</th>
                <th>上映日期</th>
                <th>上传时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${filmList}">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty item.posterPath}">
                                <img class="poster-thumb" src="${ctx}${item.posterPath}" alt="${item.name}" data-fallback="封面缺失">
                            </c:when>
                            <c:otherwise>
                                <span class="poster-thumb media-missing">无封面</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${item.name}</td>
                    <td>${item.category.name}</td>
                    <td>${item.director}</td>
                    <td><fmt:formatDate value="${item.releaseDate}" pattern="yyyy-MM-dd"/></td>
                    <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
                        <div class="ops">
                            <a class="button ghost" href="${ctx}/admin/film-detail.action?id=${item.id}">查看</a>
                            <a class="button ghost" href="${ctx}/admin/film-edit.action?id=${item.id}">编辑</a>
                            <a class="button danger" data-confirm="确认删除该影片？" href="${ctx}/admin/film-delete.action?id=${item.id}">删除</a>
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
            <c:url var="filmPrevUrl" value="/admin/film-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="categoryId" value="${categoryId}"/>
                <c:param name="page" value="${pageResult.page - 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:url var="filmNextUrl" value="/admin/film-list.action">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="categoryId" value="${categoryId}"/>
                <c:param name="page" value="${pageResult.page + 1}"/>
                <c:param name="pageSize" value="${pageResult.pageSize}"/>
            </c:url>
            <c:if test="${pageResult.hasPrevious}">
                <a class="button secondary" href="${filmPrevUrl}">上一页</a>
            </c:if>
            <c:if test="${pageResult.hasNext}">
                <a class="button secondary" href="${filmNextUrl}">下一页</a>
            </c:if>
        </div>
    </div>
</c:if>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
