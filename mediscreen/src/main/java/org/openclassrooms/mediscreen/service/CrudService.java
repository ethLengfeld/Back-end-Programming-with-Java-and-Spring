package org.openclassrooms.mediscreen.service;

import java.util.List;

public interface CrudService<T> {

    void addOrUpdate(T value);

    T read(Long id);

    List<T> readAll();

    void delete(T value);
}
