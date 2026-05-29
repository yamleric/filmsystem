package com.filmsystem.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object loginUser = ActionContext.getContext().getSession().get("loginUser");
        if (loginUser == null) {
            return Action.LOGIN;
        }
        return invocation.invoke();
    }
}
