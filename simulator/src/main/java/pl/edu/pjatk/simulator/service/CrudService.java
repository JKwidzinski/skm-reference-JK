package pl.edu.pjatk.simulator.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.simulator.exception.BadRequestException;
import pl.edu.pjatk.simulator.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudService<T extends DbEntity> {
    JpaRepository<T, Long> repository;

    public CrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public T add(T obj) {
        return repository.save(obj);
    }

    public List<T> addAll(List<T> objList){ return repository.saveAll(objList);}

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Long> getIds() {
        return getAll().stream().map(obj -> obj.getId()).collect(Collectors.toList());
    }

    public T update(T obj) throws BadRequestException, NotFoundException {
        if (obj.getId() == null) throw new BadRequestException();
        var entity = getUpdatedEntity(obj);
        repository.save(entity);
        return entity;
    }

    public abstract T getUpdatedEntity(T obj) throws NotFoundException;

}