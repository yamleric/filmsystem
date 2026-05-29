<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<c:set var="isEdit" value="${not empty user.id}"/>
<section class="page-head">
    <div>
        <h1>${isEdit ? '编辑用户' : '新增用户'}</h1>
        <p>${isEdit ? '修改后台账号资料。' : '创建新的后台账号，默认密码为空时为 123456。'}</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/user-list.action">返回列表</a>
</section>

<section class="panel">
    <c:if test="${not empty errorMessage}">
        <div class="notice error">${errorMessage}</div>
    </c:if>
    <form action="${ctx}/admin/${isEdit ? 'user-update' : 'user-save'}.action" method="post" accept-charset="UTF-8">
        <c:if test="${isEdit}">
            <input type="hidden" name="user.id" value="${user.id}">
        </c:if>
        <div class="form-grid">
            <div class="field">
                <label for="username">用户名</label>
                <input id="username" name="user.username" value="${user.username}" required>
            </div>
            <div class="field">
                <label for="nickname">昵称</label>
                <input id="nickname" name="user.nickname" value="${user.nickname}" required>
            </div>
            <div class="field">
                <label for="password">密码</label>
                <input id="password" name="password" type="password" placeholder="${isEdit ? '留空则不修改密码' : '留空则使用 123456'}">
            </div>
            <div class="field">
                <label for="role">角色</label>
                <input id="role" name="user.role" value="${empty user.role ? 'ADMIN' : user.role}">
            </div>
            <div class="field">
                <label for="enabled">状态</label>
                <select id="enabled" name="user.enabled">
                    <option value="true" ${user.enabled ? 'selected' : ''}>启用</option>
                    <option value="false" ${!user.enabled ? 'selected' : ''}>停用</option>
                </select>
            </div>
        </div>
        <div class="actions">
            <button type="submit">保存</button>
            <a class="button secondary" href="${ctx}/admin/user-list.action">取消</a>
        </div>
    </form>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
