package com.filmsystem.service.impl;

import com.filmsystem.dao.BaseDao;
import com.filmsystem.entity.SysUser;
import com.filmsystem.service.UserService;
import com.filmsystem.util.PageResult;
import com.filmsystem.util.PasswordUtil;
import com.filmsystem.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private BaseDao baseDao;

    @Autowired
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public SysUser login(String username, String password) {
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username.trim());
        SysUser user = baseDao.findOne("from SysUser u where u.username = :username and u.enabled = true", params);
        if (user == null) {
            return null;
        }
        return PasswordUtil.md5(password).equals(user.getPassword()) ? user : null;
    }

    @Override
    public List<SysUser> list() {
        return baseDao.find("from SysUser u order by u.createTime desc, u.id desc", null);
    }

    @Override
    public PageResult<SysUser> search(String keyword, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        String where = "";
        if (!StringUtil.isBlank(keyword)) {
            where = " where u.username like :keyword or u.nickname like :keyword or u.role like :keyword";
            params.put("keyword", "%" + keyword.trim() + "%");
        }
        long total = baseDao.count("select count(u.id) from SysUser u" + where, params);
        List<SysUser> records = baseDao.findPage("from SysUser u" + where + " order by u.createTime desc, u.id desc", params, page, pageSize);
        return new PageResult<SysUser>(records, total, page, pageSize);
    }

    @Override
    public SysUser get(Long id) {
        return baseDao.get(SysUser.class, id);
    }

    @Override
    public void save(SysUser user, String rawPassword) {
        validate(user);
        if (findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        user.setUsername(user.getUsername().trim());
        user.setNickname(user.getNickname().trim());
        user.setPassword(PasswordUtil.md5(StringUtil.isBlank(rawPassword) ? "123456" : rawPassword));
        baseDao.save(user);
    }

    @Override
    public void update(SysUser user, String rawPassword) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("请选择要修改的用户");
        }
        validate(user);
        SysUser persistent = get(user.getId());
        if (persistent == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        SysUser sameName = findByUsername(user.getUsername());
        if (sameName != null && !sameName.getId().equals(user.getId())) {
            throw new IllegalArgumentException("用户名已存在");
        }
        persistent.setUsername(user.getUsername().trim());
        persistent.setNickname(user.getNickname().trim());
        persistent.setRole(StringUtil.isBlank(user.getRole()) ? "ADMIN" : user.getRole().trim());
        persistent.setEnabled(Boolean.TRUE.equals(user.getEnabled()));
        if (!StringUtil.isBlank(rawPassword)) {
            persistent.setPassword(PasswordUtil.md5(rawPassword));
        }
    }

    @Override
    public void delete(Long id, Long currentUserId) {
        if (id == null) {
            return;
        }
        if (id.equals(currentUserId)) {
            throw new IllegalArgumentException("不能删除当前登录用户");
        }
        if (count() <= 1) {
            throw new IllegalArgumentException("系统至少保留一个管理员用户");
        }
        baseDao.delete(get(id));
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword, String confirmPassword) {
        if (id == null) {
            throw new IllegalArgumentException("登录状态已失效");
        }
        if (StringUtil.isBlank(oldPassword)) {
            throw new IllegalArgumentException("原密码不能为空");
        }
        if (StringUtil.isBlank(newPassword) || newPassword.trim().length() < 6) {
            throw new IllegalArgumentException("新密码长度不能少于6位");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("两次输入的新密码不一致");
        }
        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("新密码不能与原密码相同");
        }
        SysUser user = get(id);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!PasswordUtil.md5(oldPassword).equals(user.getPassword())) {
            throw new IllegalArgumentException("原密码不正确");
        }
        user.setPassword(PasswordUtil.md5(newPassword));
    }

    @Override
    public long count() {
        return baseDao.count("select count(u.id) from SysUser u", null);
    }

    @Override
    public void initDefaultAdmin() {
        if (count() > 0) {
            return;
        }
        SysUser admin = new SysUser();
        admin.setUsername("admin");
        admin.setNickname("系统管理员");
        admin.setRole("ADMIN");
        admin.setEnabled(Boolean.TRUE);
        admin.setPassword(PasswordUtil.md5("admin"));
        baseDao.save(admin);
    }

    private SysUser findByUsername(String username) {
        if (StringUtil.isBlank(username)) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username.trim());
        return baseDao.findOne("from SysUser u where u.username = :username", params);
    }

    private void validate(SysUser user) {
        if (user == null || StringUtil.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (StringUtil.isBlank(user.getNickname())) {
            throw new IllegalArgumentException("昵称不能为空");
        }
    }
}
