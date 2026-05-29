package com.filmsystem.service;

import com.filmsystem.entity.Film;
import com.filmsystem.util.PageResult;

import java.util.List;

public interface FilmService {
    List<Film> list();

    PageResult<Film> search(String keyword, Long categoryId, int page, int pageSize);

    Film get(Long id);

    void save(Film film);

    void update(Film film);

    void delete(Long id);

    long count();
}
