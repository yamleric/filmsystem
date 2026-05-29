package com.filmsystem.service;

import com.filmsystem.entity.SysUser;
import com.filmsystem.util.PageResult;

import java.util.List;

public interface UserService {
    SysUser login(String username, String password);

    List<SysUser> list();

    PageResult<SysUser> search(String keyword, int page, int pageSize);

    SysUser get(Long id);

    void save(SysUser user, String rawPassword);

    void update(SysUser user, String rawPassword);

    void delete(Long id, Long currentUserId);

    void changePassword(Long id, String oldPassword, String newPassword, String confirmPassword);

    long count();

    void initDefaultAdmin();
}
