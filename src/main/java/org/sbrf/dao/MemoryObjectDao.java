package org.sbrf.dao;

import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.exception.*;

import java.util.HashMap;
import java.util.List;

public class MemoryObjectDao implements ObjectDao {

    private HashMap<Long, Employee> employees;
    private HashMap<Long, Function> functions;

    public MemoryObjectDao() {
        employees = new HashMap<Long, Employee>();
        functions = new HashMap<Long, Function>();
    }

    @Override
    public void add(Employee employee) throws CannotAddObjectException {
        try {
            employees.put(employee.getId(), employee);
        } catch(Exception exception) {
            throw new CannotAddObjectException(exception.getMessage());
        }
    }

    @Override
    public List<Employee> getAll() throws GetAllObjectException {
        throw new GetAllObjectException("MemoryDao: getAll function is not implemented yet!");
    }

    @Override
    public List<Function> getAllFunctions() throws GetAllFunctionObjectException {
        throw new GetAllFunctionObjectException("MemoryDao: getAll function is not implemented yet!");
    }

    @Override
    public Employee get(long objectId) {
        return employees.get(objectId);
    }

    @Override
    public void delete(long objectId) throws CannotDeleteObjectException {
        employees.remove(objectId);
    }

    @Override
    public void update(Employee employee) throws CannotUpdateObjectException {
        try {
            employees.replace(employee.getId(), employee);
        } catch(Exception exception) {
            throw new CannotUpdateObjectException("Не удается обновить объект: " + exception.getMessage());
        }
    }
}
