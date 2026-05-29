package com.filmsystem.action;

import com.filmsystem.entity.SysUser;
import com.filmsystem.service.UserService;
import com.filmsystem.servlet.CaptchaServlet;
import com.filmsystem.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller("authAction")
@Scope("prototype")
public class AuthAction extends ActionSupport {
    private UserService userService;
    private String username;
    private String password;
    private String captcha;
    private String errorMessage;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String login() {
        if (StringUtil.isBlank(username) && StringUtil.isBlank(password)) {
            return INPUT;
        }
        String sessionCaptcha = (String) ServletActionContext.getRequest().getSession().getAttribute(CaptchaServlet.SESSION_KEY);
        if (StringUtil.isBlank(captcha) || sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha.trim())) {
            errorMessage = "验证码不正确";
            return INPUT;
        }
        SysUser user = userService.login(username, password);
        if (user == null) {
            errorMessage = "用户名或密码错误，或账号已停用";
            return INPUT;
        }
        ServletActionContext.getRequest().getSession().removeAttribute(CaptchaServlet.SESSION_KEY);
        ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
        return SUCCESS;
    }

    public String logout() {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
