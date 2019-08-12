package it.nautilor.spring.resource;

import it.nautilor.spring.helper.EntityHelper;
import it.nautilor.spring.model.Entity;
import it.nautilor.spring.params.EntityQueryParams;
import it.nautilor.spring.repository.AbstractEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

public abstract class EntityResource<T extends Entity, P extends EntityQueryParams, R extends AbstractEntityRepository<T, P>, H extends EntityHelper<T, P, R>> {

    @Autowired
    H helper;

    private Class<T> clazz;

    public EntityResource(Class<T> clazz) { this.clazz = clazz; }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getAll(@Valid P params) { return ResponseEntity.status(HttpStatus.OK).body(helper.search(params)); }

    @RequestMapping(value = "{id}" ,method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable String id) { return ResponseEntity.status(HttpStatus.OK).body(helper.findbyId(id)); }

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody @Valid T entity) { return ResponseEntity.status(HttpStatus.OK).body(helper.save(entity)); }

    @RequestMapping(value = "{id}" ,method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid T entity) { return ResponseEntity.status(HttpStatus.OK).body(helper.update(entity, id)); }

    @RequestMapping(value = "{id}/delete" ,method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable String id) { return ResponseEntity.status(HttpStatus.OK).body(helper.delete(id)); }

    @RequestMapping(value="/count", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity count(P params) { return ResponseEntity.status(HttpStatus.OK).body(helper.count(params)); }


}
