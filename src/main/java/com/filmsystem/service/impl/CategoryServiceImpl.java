package com.filmsystem.service.impl;

import com.filmsystem.dao.BaseDao;
import com.filmsystem.entity.FilmCategory;
import com.filmsystem.service.CategoryService;
import com.filmsystem.util.PageResult;
import com.filmsystem.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private BaseDao baseDao;

    @Autowired
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<FilmCategory> list() {
        return baseDao.find("from FilmCategory c order by c.sortNo asc, c.id asc", null);
    }

    @Override
    public PageResult<FilmCategory> search(String keyword, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        String where = "";
        if (!StringUtil.isBlank(keyword)) {
            where = " where c.name like :keyword or c.description like :keyword";
            params.put("keyword", "%" + keyword.trim() + "%");
        }
        long total = baseDao.count("select count(c.id) from FilmCategory c" + where, params);
        List<FilmCategory> records = baseDao.findPage("from FilmCategory c" + where + " order by c.sortNo asc, c.id asc", params, page, pageSize);
        return new PageResult<FilmCategory>(records, total, page, pageSize);
    }

    @Override
    public FilmCategory get(Long id) {
        return baseDao.get(FilmCategory.class, id);
    }

    @Override
    public void save(FilmCategory category) {
        validate(category);
        if (findByName(category.getName()) != null) {
            throw new IllegalArgumentException("分类名称已存在");
        }
        normalize(category);
        baseDao.save(category);
    }

    @Override
    public void update(FilmCategory category) {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("请选择要修改的分类");
        }
        validate(category);
        FilmCategory persistent = get(category.getId());
        if (persistent == null) {
            throw new IllegalArgumentException("分类不存在");
        }
        FilmCategory sameName = findByName(category.getName());
        if (sameName != null && !sameName.getId().equals(category.getId())) {
            throw new IllegalArgumentException("分类名称已存在");
        }
        persistent.setName(category.getName().trim());
        persistent.setDescription(StringUtil.trimToNull(category.getDescription()));
        persistent.setSortNo(category.getSortNo() == null ? 0 : category.getSortNo());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", id);
        long used = baseDao.count("select count(f.id) from Film f where f.category.id = :categoryId", params);
        if (used > 0) {
            throw new IllegalArgumentException("该分类下已有影片，不能删除");
        }
        baseDao.delete(get(id));
    }

    @Override
    public long count() {
        return baseDao.count("select count(c.id) from FilmCategory c", null);
    }

    @Override
    public void initDefaultCategories() {
        if (count() > 0) {
            return;
        }
        String[] names = {"动作", "喜剧", "爱情", "科幻", "剧情", "悬疑", "战争", "动画"};
        for (int i = 0; i < names.length; i++) {
            FilmCategory category = new FilmCategory();
            category.setName(names[i]);
            category.setDescription(names[i] + "类型影片");
            category.setSortNo(i + 1);
            baseDao.save(category);
        }
    }

    private FilmCategory findByName(String name) {
        if (StringUtil.isBlank(name)) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name.trim());
        return baseDao.findOne("from FilmCategory c where c.name = :name", params);
    }

    private void validate(FilmCategory category) {
        if (category == null || StringUtil.isBlank(category.getName())) {
            throw new IllegalArgumentException("分类名称不能为空");
        }
    }

    private void normalize(FilmCategory category) {
        category.setName(category.getName().trim());
        category.setDescription(StringUtil.trimToNull(category.getDescription()));
        category.setSortNo(category.getSortNo() == null ? 0 : category.getSortNo());
    }
}
