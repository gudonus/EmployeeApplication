package org.sbrf.dao;

import java.util.List;

public interface UserDao<T> {

    Boolean add(T employee);

    List<T> getAll();

    T getById(long employeeId) ; // подумать

    Boolean delete(long employeeId);
}
