package it.nautilor.spring.helper;

import it.nautilor.spring.exception.EntityNotFoundException;
import it.nautilor.spring.model.Entity;
import it.nautilor.spring.params.EntityQueryParams;
import it.nautilor.spring.repository.AbstractEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class EntityHelper<T extends Entity, P extends EntityQueryParams, R extends AbstractEntityRepository<T, P>> {

    @Autowired
    R repository;

    private Class<T> clazz;

    public EntityHelper(Class<T> clazz) { this.clazz = clazz; }

    public List<T> search(P params) { return repository.search(params); }

    public T findbyId(String id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, clazz)); }

    public int count (P params) { return repository.count(params); }

    public T save(T entity) { return repository.save(entity); }

    public T update(T entity, String id) {
        T e = findbyId(id);
        entity.setId(e.getId());
        entity.setCreationDate(e.getCreationDate());
        return save(entity);
    }

    public T delete(String id) { return repository.delete(findbyId(id)); }
}
