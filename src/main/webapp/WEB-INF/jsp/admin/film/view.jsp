<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>${film.name}</h1>
        <p>${film.category.name} · <fmt:formatDate value="${film.releaseDate}" pattern="yyyy-MM-dd"/></p>
    </div>
    <div class="actions">
        <a class="button ghost" href="${ctx}/admin/film-edit.action?id=${film.id}">编辑</a>
        <a class="button secondary" href="${ctx}/admin/film-list.action">返回列表</a>
    </div>
</section>

<section class="panel">
    <div class="form-grid">
        <div>
            <c:if test="${not empty film.posterPath}">
                <img class="poster-large" src="${ctx}${film.posterPath}" alt="${film.name}" data-fallback="封面缺失">
            </c:if>
        </div>
        <div>
            <p><strong>导演：</strong>${film.director}</p>
            <p><strong>主演：</strong>${film.actor}</p>
            <p><strong>地区：</strong>${film.region}</p>
            <p><strong>语言：</strong>${film.language}</p>
            <p><strong>片长：</strong>${film.durationMinutes} 分钟</p>
            <c:if test="${not empty film.videoPath}">
                <p><strong>影片文件：</strong><a href="${ctx}${film.videoPath}" target="_blank">打开文件</a></p>
            </c:if>
        </div>
        <div class="field full">
            <c:if test="${not empty film.videoPath}">
                <h2>影片预览</h2>
                <video class="video-preview" src="${ctx}${film.videoPath}" controls preload="metadata" data-fallback="影片文件缺失或无法预览"></video>
            </c:if>
        </div>
        <div class="field full">
            <h2>影片简介</h2>
            <div class="article"><c:out value="${film.description}"/></div>
        </div>
    </div>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
