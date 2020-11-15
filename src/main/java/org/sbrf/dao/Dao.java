package org.sbrf.dao;

import org.sbrf.exception.*;

import java.util.List;

public interface Dao<T> {

    void add(T object) throws AddObjectException;

    List<T> getAll() throws GetObjectException;

    T get(FilterDao filter) throws NotFoundObjectException;

    void delete(FilterDao filter) throws DeleteObjectException;

    void update(T object) throws UpdateObjectException;
}
