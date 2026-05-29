package com.filmsystem.service;

import com.filmsystem.entity.News;
import com.filmsystem.util.PageResult;

import java.util.List;

public interface NewsService {
    List<News> list();

    PageResult<News> search(String keyword, int page, int pageSize);

    List<News> publicList();

    PageResult<News> publicSearch(String keyword, int page, int pageSize);

    News get(Long id);

    void save(News news);

    void update(News news);

    void delete(Long id);

    long count();
}
