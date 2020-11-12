package org.sbrf.dao;

import org.sbrf.exception.CannotAddObjectException;
import org.sbrf.exception.CannotDeleteObjectException;
import org.sbrf.exception.CannotUpdateObjectException;
import org.sbrf.exception.GetAllObjectException;

import java.util.List;

// реализовать на основе мап - хранение в памяти
public interface Dao<T> {

    void add(T object) throws CannotAddObjectException;

    List<T> getAll() throws GetAllObjectException;
    T get(long objectId); // фильтр-интерфейс реализован для объекта ...

    void delete(long objectId) throws CannotDeleteObjectException;

    void update(T object) throws CannotUpdateObjectException;
}
