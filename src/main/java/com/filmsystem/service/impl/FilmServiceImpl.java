package com.filmsystem.service.impl;

import com.filmsystem.dao.BaseDao;
import com.filmsystem.entity.Film;
import com.filmsystem.service.FilmService;
import com.filmsystem.util.PageResult;
import com.filmsystem.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {
    private BaseDao baseDao;

    @Autowired
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Film> list() {
        return baseDao.find("from Film f left join fetch f.category order by f.createTime desc, f.id desc", null);
    }

    @Override
    public PageResult<Film> search(String keyword, Long categoryId, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        String where = "";
        if (!StringUtil.isBlank(keyword)) {
            where += " where (f.name like :keyword or f.director like :keyword or f.actor like :keyword or f.region like :keyword)";
            params.put("keyword", "%" + keyword.trim() + "%");
        }
        if (categoryId != null) {
            where += StringUtil.isBlank(where) ? " where " : " and ";
            where += "f.category.id = :categoryId";
            params.put("categoryId", categoryId);
        }
        long total = baseDao.count("select count(f.id) from Film f" + where, params);
        List<Film> records = baseDao.findPage("from Film f left join fetch f.category" + where + " order by f.createTime desc, f.id desc", params, page, pageSize);
        return new PageResult<Film>(records, total, page, pageSize);
    }

    @Override
    public Film get(Long id) {
        return baseDao.get(Film.class, id);
    }

    @Override
    public void save(Film film) {
        validate(film);
        normalize(film);
        baseDao.save(film);
    }

    @Override
    public void update(Film film) {
        if (film == null || film.getId() == null) {
            throw new IllegalArgumentException("请选择要修改的影片");
        }
        validate(film);
        Film persistent = get(film.getId());
        if (persistent == null) {
            throw new IllegalArgumentException("影片不存在");
        }
        persistent.setName(film.getName().trim());
        persistent.setCategory(film.getCategory());
        persistent.setDirector(StringUtil.trimToNull(film.getDirector()));
        persistent.setActor(StringUtil.trimToNull(film.getActor()));
        persistent.setRegion(StringUtil.trimToNull(film.getRegion()));
        persistent.setLanguage(StringUtil.trimToNull(film.getLanguage()));
        persistent.setReleaseDate(film.getReleaseDate());
        persistent.setDurationMinutes(film.getDurationMinutes());
        persistent.setDescription(StringUtil.trimToNull(film.getDescription()));
        if (!StringUtil.isBlank(film.getPosterPath())) {
            persistent.setPosterPath(film.getPosterPath());
        }
        if (!StringUtil.isBlank(film.getVideoPath())) {
            persistent.setVideoPath(film.getVideoPath());
        }
    }

    @Override
    public void delete(Long id) {
        baseDao.delete(get(id));
    }

    @Override
    public long count() {
        return baseDao.count("select count(f.id) from Film f", null);
    }

    private void validate(Film film) {
        if (film == null || StringUtil.isBlank(film.getName())) {
            throw new IllegalArgumentException("影片名称不能为空");
        }
        if (film.getCategory() == null || film.getCategory().getId() == null) {
            throw new IllegalArgumentException("请选择影片分类");
        }
    }

    private void normalize(Film film) {
        film.setName(film.getName().trim());
        film.setDirector(StringUtil.trimToNull(film.getDirector()));
        film.setActor(StringUtil.trimToNull(film.getActor()));
        film.setRegion(StringUtil.trimToNull(film.getRegion()));
        film.setLanguage(StringUtil.trimToNull(film.getLanguage()));
        film.setDescription(StringUtil.trimToNull(film.getDescription()));
    }
}
