package com.filmsystem.dao.impl;

import com.filmsystem.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BaseDaoImpl implements BaseDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> T get(Class<T> entityClass, Serializable id) {
        if (id == null) {
            return null;
        }
        return currentSession().get(entityClass, id);
    }

    @Override
    public <T> List<T> find(String hql, Map<String, Object> params) {
        Query<T> query = currentSession().createQuery(hql);
        bindParams(query, params);
        return query.list();
    }

    @Override
    public <T> List<T> findPage(String hql, Map<String, Object> params, int page, int pageSize) {
        int safePage = page < 1 ? 1 : page;
        int safePageSize = pageSize < 1 ? 10 : pageSize;
        Query<T> query = currentSession().createQuery(hql);
        bindParams(query, params);
        query.setFirstResult((safePage - 1) * safePageSize);
        query.setMaxResults(safePageSize);
        return query.list();
    }

    @Override
    public <T> T findOne(String hql, Map<String, Object> params) {
        List<T> list = find(hql, params);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public long count(String hql, Map<String, Object> params) {
        Query<?> query = currentSession().createQuery(hql);
        bindParams(query, params);
        Object value = query.uniqueResult();
        return value == null ? 0L : ((Number) value).longValue();
    }

    @Override
    public void save(Object entity) {
        currentSession().save(entity);
    }

    @Override
    public void delete(Object entity) {
        if (entity != null) {
            currentSession().delete(entity);
        }
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    private void bindParams(Query<?> query, Map<String, Object> params) {
        Map<String, Object> safeParams = params == null ? Collections.<String, Object>emptyMap() : params;
        for (Map.Entry<String, Object> entry : safeParams.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
