package com.filmsystem.action;

import com.filmsystem.entity.FilmCategory;
import com.filmsystem.service.CategoryService;
import com.filmsystem.util.PageResult;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("categoryAction")
@Scope("prototype")
public class CategoryAction extends ActionSupport {
    private CategoryService categoryService;
    private Long id;
    private FilmCategory category = new FilmCategory();
    private List<FilmCategory> categoryList;
    private PageResult<FilmCategory> pageResult;
    private String keyword;
    private int page = 1;
    private int pageSize = 10;
    private String errorMessage;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String list() {
        pageResult = categoryService.search(keyword, page, pageSize);
        categoryList = pageResult.getRecords();
        return SUCCESS;
    }

    public String add() {
        category = new FilmCategory();
        category.setSortNo(0);
        return SUCCESS;
    }

    public String edit() {
        category = categoryService.get(id);
        return category == null ? INPUT : SUCCESS;
    }

    public String save() {
        try {
            categoryService.save(category);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public String update() {
        try {
            categoryService.update(category);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return INPUT;
        }
    }

    public String delete() {
        categoryService.delete(id);
        return SUCCESS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilmCategory getCategory() {
        return category;
    }

    public void setCategory(FilmCategory category) {
        this.category = category;
    }

    public List<FilmCategory> getCategoryList() {
        return categoryList;
    }

    public PageResult<FilmCategory> getPageResult() {
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
