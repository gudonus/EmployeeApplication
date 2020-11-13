package org.sbrf.dao;

import org.sbrf.exception.*;

import java.util.List;

// реализовать на основе мап - хранение в памяти
public interface Dao<T> {

    void add(T object) throws CannotAddObjectException;

    List<T> getAll() throws GetAllObjectException;

    T get(FilterDao filter) throws NotFoundObjectException; // фильтр-интерфейс реализован для объекта ...

    void delete(FilterDao filter) throws CannotDeleteObjectException;

    void update(T object) throws CannotUpdateObjectException;
}
