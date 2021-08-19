package org.jazzteam.eltay.gasimov.repository;

import java.util.List;

public interface GeneralRepository<T> {
    T save(T toSave);

    void delete(Long idForDelete);

    List<T> findAll();

    T findOne(Long idForSearch);

    T update(T update);
}