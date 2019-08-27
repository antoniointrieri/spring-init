package it.nautilor.spring.repository;

import it.nautilor.spring.model.Entity;
import it.nautilor.spring.params.EntityQueryParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityRepository<T extends Entity, P extends EntityQueryParams> {

    @Autowired
    private EntityManager entityManager;


    private Class<T> clazz;

    public AbstractEntityRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T delete(T entity) {
        entityManager.remove(entity);
        return entity;
    }

    public T refresh(T entity) {
        entityManager.refresh(entity);
        return entity;
    }

    public List<T> findAll(P params) {
        return entityManager.createQuery(createQuery(null))
                .setFirstResult(params.getOffset())
                .setMaxResults(params.getLimit())
                .getResultList();
    }

    public Optional<T> findById(String id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = root = query.from(clazz);
        query.select(root);
        query.where(builder.equal(root.get("id"), id));
        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }

    public List<T> findByIds(List<String> ids, P params) {
        CriteriaQuery<T> query = createQuery(null);
        Root<T> root = root = query.from(clazz);
        query.where(root.get("id").in(ids));
        return entityManager.createQuery(query)
                .setFirstResult(params.getOffset())
                .setMaxResults(params.getLimit())
                .getResultList();
    }

    public List<T> search(P params) {
        return entityManager.createQuery(createQuery(params))
                .setFirstResult(params.getOffset())
                .setMaxResults(params.getLimit())
                .getResultList();
    }

    public int count(P params) {
        return entityManager.createQuery(createQuery(params))
                .getMaxResults();
    }

    private CriteriaQuery<T> createQuery(P params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = root = query.from(clazz);
        query.select(root);
        if (params != null) {
            if (StringUtils.isNoneBlank(params.getId())) {
                query.where(builder.equal(root.get("id"), params.getId()));
            }
        }
        addParams(builder, query, root, params);
        return query;

    }

    abstract CriteriaQuery<T> addParams(CriteriaBuilder builder, CriteriaQuery query, Root<T> root, P params);
}
