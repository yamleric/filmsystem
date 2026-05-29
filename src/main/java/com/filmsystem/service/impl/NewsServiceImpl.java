package com.filmsystem.service.impl;

import com.filmsystem.dao.BaseDao;
import com.filmsystem.entity.News;
import com.filmsystem.service.NewsService;
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
public class NewsServiceImpl implements NewsService {
    private BaseDao baseDao;

    @Autowired
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<News> list() {
        return baseDao.find("from News n order by n.createTime desc, n.id desc", null);
    }

    @Override
    public PageResult<News> search(String keyword, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        String where = buildKeywordWhere(keyword, params, "");
        long total = baseDao.count("select count(n.id) from News n" + where, params);
        List<News> records = baseDao.findPage("from News n" + where + " order by n.createTime desc, n.id desc", params, page, pageSize);
        return new PageResult<News>(records, total, page, pageSize);
    }

    @Override
    public List<News> publicList() {
        return baseDao.find("from News n where n.status = 'PUBLISHED' order by n.createTime desc, n.id desc", null);
    }

    @Override
    public PageResult<News> publicSearch(String keyword, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        String where = buildKeywordWhere(keyword, params, " where n.status = 'PUBLISHED'");
        long total = baseDao.count("select count(n.id) from News n" + where, params);
        List<News> records = baseDao.findPage("from News n" + where + " order by n.createTime desc, n.id desc", params, page, pageSize);
        return new PageResult<News>(records, total, page, pageSize);
    }

    @Override
    public News get(Long id) {
        return baseDao.get(News.class, id);
    }

    @Override
    public void save(News news) {
        validate(news);
        normalize(news);
        baseDao.save(news);
    }

    @Override
    public void update(News news) {
        if (news == null || news.getId() == null) {
            throw new IllegalArgumentException("请选择要修改的新闻");
        }
        validate(news);
        News persistent = get(news.getId());
        if (persistent == null) {
            throw new IllegalArgumentException("新闻不存在");
        }
        persistent.setTitle(news.getTitle().trim());
        persistent.setSummary(StringUtil.trimToNull(news.getSummary()));
        persistent.setContent(news.getContent().trim());
        persistent.setStatus(StringUtil.isBlank(news.getStatus()) ? "PUBLISHED" : news.getStatus().trim());
    }

    @Override
    public void delete(Long id) {
        baseDao.delete(get(id));
    }

    @Override
    public long count() {
        return baseDao.count("select count(n.id) from News n", null);
    }

    private void validate(News news) {
        if (news == null || StringUtil.isBlank(news.getTitle())) {
            throw new IllegalArgumentException("新闻标题不能为空");
        }
        if (StringUtil.isBlank(news.getContent())) {
            throw new IllegalArgumentException("新闻内容不能为空");
        }
    }

    private void normalize(News news) {
        news.setTitle(news.getTitle().trim());
        news.setSummary(StringUtil.trimToNull(news.getSummary()));
        news.setContent(news.getContent().trim());
        news.setStatus(StringUtil.isBlank(news.getStatus()) ? "PUBLISHED" : news.getStatus().trim());
    }

    private String buildKeywordWhere(String keyword, Map<String, Object> params, String baseWhere) {
        String where = baseWhere;
        if (!StringUtil.isBlank(keyword)) {
            where += StringUtil.isBlank(where) ? " where " : " and ";
            where += "(n.title like :keyword or n.summary like :keyword or n.content like :keyword)";
            params.put("keyword", "%" + keyword.trim() + "%");
        }
        return where;
    }
}
