<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/layout/header.jsp" %>
<section class="page-head">
    <div>
        <h1>修改密码</h1>
        <p>更新当前登录账号的密码，新密码长度不少于 6 位。</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/dashboard.action">返回工作台</a>
</section>

<section class="panel">
    <c:if test="${not empty errorMessage}">
        <div class="notice error">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="notice">${successMessage}</div>
    </c:if>
    <form action="${ctx}/admin/password-update.action" method="post" accept-charset="UTF-8">
        <input type="text" name="username" value="${sessionScope.loginUser.username}" autocomplete="username" hidden>
        <div class="form-grid">
            <div class="field">
                <label for="oldPassword">原密码</label>
                <input id="oldPassword" name="oldPassword" type="password" autocomplete="current-password" required>
            </div>
            <div></div>
            <div class="field">
                <label for="newPassword">新密码</label>
                <input id="newPassword" name="newPassword" type="password" minlength="6" autocomplete="new-password" required>
            </div>
            <div class="field">
                <label for="confirmPassword">确认新密码</label>
                <input id="confirmPassword" name="confirmPassword" type="password" minlength="6" autocomplete="new-password" required>
            </div>
        </div>
        <div class="actions">
            <button type="submit">保存新密码</button>
            <a class="button secondary" href="${ctx}/admin/dashboard.action">取消</a>
        </div>
    </form>
</section>
<%@ include file="/WEB-INF/jsp/admin/layout/footer.jsp" %>
