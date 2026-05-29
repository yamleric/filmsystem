package com.filmsystem.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao {
    <T> T get(Class<T> entityClass, Serializable id);

    <T> List<T> find(String hql, Map<String, Object> params);

    <T> List<T> findPage(String hql, Map<String, Object> params, int page, int pageSize);

    <T> T findOne(String hql, Map<String, Object> params);

    long count(String hql, Map<String, Object> params);

    void save(Object entity);

    void delete(Object entity);
}
