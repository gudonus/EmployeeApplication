package org.sbrf.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    Boolean add(T object) throws Exception;

    List<T> getAll() throws SQLException;

    T get(long objectId);

    Boolean delete(long objectId);
}
