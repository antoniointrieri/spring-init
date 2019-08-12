package it.nautilor.spring.repository;

import it.nautilor.spring.model.Entity;
import it.nautilor.spring.params.EntityQueryParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityRepository<T extends Entity, P extends EntityQueryParams> {

    @Autowired
    private MongoTemplate mongoTemplate;



    private Class<T> clazz;

    public AbstractEntityRepository(Class<T> clazz) { this.clazz = clazz; }

    public T save(T entity) { return mongoTemplate.save(entity); }

    public T delete(T entity) { mongoTemplate.remove(entity);return entity; }

    public List<T> findAll() { return mongoTemplate.findAll(clazz); }

    public Optional<T> findById(String id) { return Optional.ofNullable(mongoTemplate.findById(id, clazz)); }

    public List<T> findByIds(List<String> ids) {
        Query qb = new Query();
        ids.parallelStream().forEach(id -> qb.addCriteria(Criteria.where("id").is(id)));
        return mongoTemplate.find(qb, clazz);
    }

    public List<T> search(P params) {
        Query qb = createQuery(params);
        qb.with(PageRequest.of(params.getPage(), params.getPageSize(), Sort.by("creationDate")));
        return mongoTemplate.find(qb, clazz);
    }

    public int count(P params) {
        Query qb = createQuery(params);
        return (int)mongoTemplate.count(qb, clazz);
    }

    private Query createQuery(P params) {
        Query qb = new Query();
        if (StringUtils.isNotBlank(params.getId())) {
            qb.addCriteria(Criteria.where("id").is(params.getId()));
        }
        return addParams(qb, params);
    }

    abstract Query addParams(Query qb, P params);
}
