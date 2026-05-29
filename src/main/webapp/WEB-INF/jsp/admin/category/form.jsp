<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<c:set var="isEdit" value="${not empty category.id}"/>
<section class="page-head">
    <div>
        <h1>${isEdit ? '编辑分类' : '新增分类'}</h1>
        <p>分类名称不可重复，排序值越小越靠前。</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/category-list.action">返回列表</a>
</section>

<section class="panel">
    <c:if test="${not empty errorMessage}">
        <div class="notice error">${errorMessage}</div>
    </c:if>
    <form action="${ctx}/admin/${isEdit ? 'category-update' : 'category-save'}.action" method="post" accept-charset="UTF-8">
        <c:if test="${isEdit}">
            <input type="hidden" name="category.id" value="${category.id}">
        </c:if>
        <div class="form-grid">
            <div class="field">
                <label for="name">分类名称</label>
                <input id="name" name="category.name" value="${category.name}" required>
            </div>
            <div class="field">
                <label for="sortNo">排序</label>
                <input id="sortNo" name="category.sortNo" type="number" value="${empty category.sortNo ? 0 : category.sortNo}">
            </div>
            <div class="field full">
                <label for="description">说明</label>
                <textarea id="description" name="category.description"><c:out value="${category.description}"/></textarea>
            </div>
        </div>
        <div class="actions">
            <button type="submit">保存</button>
            <a class="button secondary" href="${ctx}/admin/category-list.action">取消</a>
        </div>
    </form>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
