package com.filmsystem.service.impl;

import com.filmsystem.service.CategoryService;
import com.filmsystem.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements InitializingBean {
    private UserService userService;
    private CategoryService categoryService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void afterPropertiesSet() {
        userService.initDefaultAdmin();
        categoryService.initDefaultCategories();
    }
}
