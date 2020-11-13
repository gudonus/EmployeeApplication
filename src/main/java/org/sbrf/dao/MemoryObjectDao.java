package org.sbrf.dao;

import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryObjectDao implements ObjectDao<Employee> {

    private HashMap<Long, Employee> employees;
    private HashMap<Long, Function> functions;

    public MemoryObjectDao() {
        employees = new HashMap<Long, Employee>();
        functions = new HashMap<Long, Function>();

        functions.put(Long.valueOf(1), new Function(1, "Engineer"));
        functions.put(Long.valueOf(2), new Function(2, "Main Engineer"));
        functions.put(Long.valueOf(3), new Function(3, "Analytic"));
        functions.put(Long.valueOf(4), new Function(4, "DevOps Engineer"));
        functions.put(Long.valueOf(5), new Function(5, "Tester"));
        functions.put(Long.valueOf(6), new Function(6, "Main Tester"));
    }

    @Override
    public void add(Employee employee) throws AddObjectException {
        try {
            if (!employee.isValid())
                throw new AddObjectException("MemoryObjectDao: Not all fields were completed!");

            employee.setId(employee.hashCode());
            employees.put(Long.valueOf(employee.getId()), employee);
        } catch(Exception exception) {
            throw new AddObjectException(exception.getMessage());
        }
    }

    @Override
    public List<Employee> getAll() throws GetObjectException {
        try {
            List<Employee> allEmployees = new ArrayList<Employee>(this.employees.values());
            return allEmployees;
        } catch(Exception exception) {
            throw new GetObjectException("MemoryObjectDao: getAll: " + exception.getMessage());
        }
    }

    @Override
    public List<Function> getAllFunctions() throws GetObjectException {
        try {
            List<Function> allFunctions = new ArrayList<Function>(functions.values());
            return allFunctions;
        } catch(Exception exception) {
            throw new GetObjectException("MemoryObjectDao: getAllFunctions: " + exception.getMessage());
        }
    }

    @Override
    public Employee get(FilterDao filter) {
        return employees.get(filter.getId());
    }

    @Override
    public void delete(FilterDao filter) throws DeleteObjectException {
        try {
            employees.remove(filter.getId());
        } catch(Exception exception) {
            throw new DeleteObjectException("MemoryObjectDao: delete: " + exception.getMessage());
        }

    }

    @Override
    public void update(Employee employee) throws UpdateObjectException {
        try {
            employees.replace(employee.getId(), employee);
        } catch(Exception exception) {
            throw new UpdateObjectException("MemoryObjectDao: Не удается обновить объект: " + exception.getMessage());
        }
    }
}
