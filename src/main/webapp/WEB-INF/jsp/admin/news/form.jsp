<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<c:set var="isEdit" value="${not empty news.id}"/>
<section class="page-head">
    <div>
        <h1>${isEdit ? '编辑新闻' : '发布新闻'}</h1>
        <p>填写新闻标题、摘要和正文内容。</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/news-list.action">返回列表</a>
</section>

<section class="panel">
    <c:if test="${not empty errorMessage}">
        <div class="notice error">${errorMessage}</div>
    </c:if>
    <form action="${ctx}/admin/${isEdit ? 'news-update' : 'news-save'}.action" method="post" accept-charset="UTF-8">
        <c:if test="${isEdit}">
            <input type="hidden" name="news.id" value="${news.id}">
        </c:if>
        <div class="form-grid">
            <div class="field">
                <label for="title">标题</label>
                <input id="title" name="news.title" value="${news.title}" required>
            </div>
            <div class="field">
                <label for="status">状态</label>
                <select id="status" name="news.status">
                    <option value="PUBLISHED" ${news.status == 'PUBLISHED' ? 'selected' : ''}>发布</option>
                    <option value="DRAFT" ${news.status == 'DRAFT' ? 'selected' : ''}>草稿</option>
                </select>
            </div>
            <div class="field full">
                <label for="summary">摘要</label>
                <input id="summary" name="news.summary" value="${news.summary}">
            </div>
            <div class="field full">
                <label for="content">正文</label>
                <textarea id="content" name="news.content" required><c:out value="${news.content}"/></textarea>
            </div>
        </div>
        <div class="actions">
            <button type="submit">保存</button>
            <a class="button secondary" href="${ctx}/admin/news-list.action">取消</a>
        </div>
    </form>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
