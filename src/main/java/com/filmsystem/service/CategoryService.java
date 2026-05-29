package com.filmsystem.service;

import com.filmsystem.entity.FilmCategory;
import com.filmsystem.util.PageResult;

import java.util.List;

public interface CategoryService {
    List<FilmCategory> list();

    PageResult<FilmCategory> search(String keyword, int page, int pageSize);

    FilmCategory get(Long id);

    void save(FilmCategory category);

    void update(FilmCategory category);

    void delete(Long id);

    long count();

    void initDefaultCategories();
}
