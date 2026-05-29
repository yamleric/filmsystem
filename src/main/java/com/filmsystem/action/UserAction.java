package com.filmsystem.action;

import com.filmsystem.entity.SysUser;
import com.filmsystem.service.UserService;
import com.filmsystem.util.PageResult;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {
    private UserService userService;
    private Long id;
    private SysUser user = new SysUser();
    private List<SysUser> userList;
    private PageResult<SysUser> pageResult;
    private String password;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String keyword;
    private int page = 1;
    private int pageSize = 10;
    private String errorMessage;
    private String successMessage;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String list() {
        pageResult = userService.search(keyword, page, pageSize);
        userList = pageResult.getRecords();
        return SUCCESS;
    }

    public String add() {
        user = new SysUser();
        user.setEnabled(Boolean.TRUE);
        user.setRole("ADMIN");
        return SUCCESS;
    }

    public String edit() {
        user = userService.get(id);
        return user == null ? INPUT : SUCCESS;
    }

    public String save() {
        try {
            userService.save(user, password);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public String update() {
        try {
            userService.update(user, password);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public String delete() {
        SysUser loginUser = (SysUser) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        userService.delete(id, loginUser == null ? null : loginUser.getId());
        return SUCCESS;
    }

    public String passwordForm() {
        return SUCCESS;
    }

    public String changePassword() {
        SysUser loginUser = (SysUser) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        try {
            userService.changePassword(loginUser == null ? null : loginUser.getId(), oldPassword, newPassword, confirmPassword);
            successMessage = "密码修改成功";
            oldPassword = null;
            newPassword = null;
            confirmPassword = null;
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<SysUser> getUserList() {
        return userList;
    }

    public PageResult<SysUser> getPageResult() {
        return pageResult;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
