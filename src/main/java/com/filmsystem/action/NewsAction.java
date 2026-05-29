package com.filmsystem.action;

import com.filmsystem.entity.News;
import com.filmsystem.service.NewsService;
import com.filmsystem.util.PageResult;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("newsAction")
@Scope("prototype")
public class NewsAction extends ActionSupport {
    private NewsService newsService;
    private Long id;
    private News news = new News();
    private List<News> newsList;
    private PageResult<News> pageResult;
    private String keyword;
    private int page = 1;
    private int pageSize = 10;
    private String errorMessage;

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public String list() {
        pageResult = newsService.search(keyword, page, pageSize);
        newsList = pageResult.getRecords();
        return SUCCESS;
    }

    public String publicList() {
        pageResult = newsService.publicSearch(keyword, page, pageSize);
        newsList = pageResult.getRecords();
        return SUCCESS;
    }

    public String add() {
        news = new News();
        news.setStatus("PUBLISHED");
        return SUCCESS;
    }

    public String edit() {
        news = newsService.get(id);
        return news == null ? INPUT : SUCCESS;
    }

    public String save() {
        try {
            newsService.save(news);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public String update() {
        try {
            newsService.update(news);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public String delete() {
        newsService.delete(id);
        return SUCCESS;
    }

    public String view() {
        news = newsService.get(id);
        return news == null ? INPUT : SUCCESS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public PageResult<News> getPageResult() {
        return pageResult;
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
}
