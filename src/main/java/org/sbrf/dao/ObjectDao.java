package org.sbrf.dao;

import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.exception.*;

import java.util.List;

public interface ObjectDao extends Dao<Employee> {
    @Override
    default void add(Employee object) throws CannotAddObjectException {
    }

    @Override
    default List<Employee> getAll() throws GetAllObjectException {
        return null;
    }

    @Override
    default Employee get(long objectId) {
        return null;
    }

    @Override
    default void delete(long objectId) throws CannotDeleteObjectException {

    }

    @Override
    default void update(Employee object) throws CannotUpdateObjectException {

    }

    List<Function> getAllFunctions() throws GetAllFunctionObjectException;
}
