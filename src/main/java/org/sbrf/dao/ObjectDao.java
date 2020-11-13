package org.sbrf.dao;

import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.exception.*;

import java.util.List;

public interface ObjectDao<T extends Employee> extends Dao<Employee> {
    List<Function> getAllFunctions() throws GetObjectException;
}
