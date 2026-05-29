<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<c:set var="isEdit" value="${not empty film.id}"/>
<section class="page-head">
    <div>
        <h1>${isEdit ? '编辑影片' : '影片上传'}</h1>
        <p>${isEdit ? '修改影片详细资料，可重新上传封面或影片文件。' : '上传新的影片文件并填写影片资料。'}</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/film-list.action">返回列表</a>
</section>

<section class="panel">
    <c:if test="${not empty errorMessage}">
        <div class="notice error">${errorMessage}</div>
    </c:if>
    <form action="${ctx}/admin/${isEdit ? 'film-update' : 'film-save'}.action" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <c:if test="${isEdit}">
            <input type="hidden" name="film.id" value="${film.id}">
        </c:if>
        <div class="form-grid">
            <div class="field">
                <label for="name">影片名称</label>
                <input id="name" name="film.name" value="${film.name}" required>
            </div>
            <div class="field">
                <label for="categoryId">影片分类</label>
                <select id="categoryId" name="categoryId" required>
                    <option value="">请选择分类</option>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.id}" ${category.id == categoryId ? 'selected' : ''}>${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field">
                <label for="director">导演</label>
                <input id="director" name="film.director" value="${film.director}">
            </div>
            <div class="field">
                <label for="actor">主演</label>
                <input id="actor" name="film.actor" value="${film.actor}">
            </div>
            <div class="field">
                <label for="region">地区</label>
                <input id="region" name="film.region" value="${film.region}">
            </div>
            <div class="field">
                <label for="language">语言</label>
                <input id="language" name="film.language" value="${film.language}">
            </div>
            <div class="field">
                <label for="releaseDate">上映日期</label>
                <input id="releaseDate" name="releaseDate" type="date" value="${releaseDate}">
            </div>
            <div class="field">
                <label for="duration">片长（分钟）</label>
                <input id="duration" name="film.durationMinutes" type="number" min="0" value="${film.durationMinutes}">
            </div>
            <div class="field">
                <label for="poster">影片封面</label>
                <input id="poster" name="poster" type="file" accept=".jpg,.jpeg,.png,.gif,.webp">
                <c:if test="${isEdit && not empty film.posterPath}">
                    <p>当前封面：<a href="${ctx}${film.posterPath}" target="_blank">查看</a></p>
                </c:if>
            </div>
            <div class="field">
                <label for="upload">影片文件</label>
                <input id="upload" name="upload" type="file" accept=".mp4,.avi,.mkv,.mov,.wmv,.flv">
                <c:if test="${isEdit && not empty film.videoPath}">
                    <p>当前文件：<a href="${ctx}${film.videoPath}" target="_blank">查看</a></p>
                </c:if>
            </div>
            <div class="field full">
                <label for="description">影片简介</label>
                <textarea id="description" name="film.description"><c:out value="${film.description}"/></textarea>
            </div>
        </div>
        <div class="actions">
            <button type="submit">保存</button>
            <a class="button secondary" href="${ctx}/admin/film-list.action">取消</a>
        </div>
    </form>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
