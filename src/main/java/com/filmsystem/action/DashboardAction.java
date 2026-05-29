package com.filmsystem.action;

import com.filmsystem.service.CategoryService;
import com.filmsystem.service.FilmService;
import com.filmsystem.service.NewsService;
import com.filmsystem.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("dashboardAction")
@Scope("prototype")
public class DashboardAction extends ActionSupport {
    private UserService userService;
    private NewsService newsService;
    private CategoryService categoryService;
    private FilmService filmService;
    private long userCount;
    private long newsCount;
    private long categoryCount;
    private long filmCount;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public String execute() {
        userCount = userService.count();
        newsCount = newsService.count();
        categoryCount = categoryService.count();
        filmCount = filmService.count();
        return SUCCESS;
    }

    public long getUserCount() {
        return userCount;
    }

    public long getNewsCount() {
        return newsCount;
    }

    public long getCategoryCount() {
        return categoryCount;
    }

    public long getFilmCount() {
        return filmCount;
    }
}
