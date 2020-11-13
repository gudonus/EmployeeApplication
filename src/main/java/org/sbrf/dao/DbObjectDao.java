package org.sbrf.dao;

import org.apache.log4j.Logger;
import org.sbrf.db.DbConnectionManager;
import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.exception.CannotAddObjectException;
import org.sbrf.exception.CannotDeleteObjectException;
import org.sbrf.exception.CannotUpdateObjectException;
import org.sbrf.exception.GetAllFunctionObjectException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbObjectDao implements ObjectDao {

    private final DbConnectionManager dbConnectionManager;

    private final Logger logger = Logger.getLogger(DbObjectDao.class);

    public DbObjectDao() throws SQLException, ClassNotFoundException {

        this.dbConnectionManager = new DbConnectionManager(
                DbConnectionManager.DATABASE_URL,
                DbConnectionManager.DATABASE_USER,
                DbConnectionManager.DATABASE_PASS
        );
    }

    @Override
    public void add(Employee employee) throws CannotAddObjectException {
        Connection connection = dbConnectionManager.getConnection();

        if (!employee.isValid())
            throw new CannotAddObjectException("DbObjectDao: Not all fields were completed!");

        try {
            Long PersonDataId = createPersonData(employee);

            String sqlQuery = "insert into Employees(ID, FirstName, SurName, StartDate, EndDate, FireDate, FunctionID, PersonDataID) " +
                    "values (?, ?, ?, sysdate, null, null, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(employee.hashCode()));
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getSurName());
            preparedStatement.setString(4, employee.getFunction());
            preparedStatement.setString(5, String.valueOf(PersonDataId));

            if (preparedStatement.executeUpdate() != 1)
                throw new CannotAddObjectException("DbObjectDao: Was not added employee in the DataBase!");

            preparedStatement.close();
        } catch (Exception exception) {
            logger.error("\tDbObjectDao: update: On DB insert: " + exception.toString());
            throw new CannotAddObjectException("DbObjectDao: Database exception during updating the employee! " + exception.toString());
        }
    }

    @Override
    public void update(Employee employee) throws CannotUpdateObjectException {
        Connection connection = dbConnectionManager.getConnection();

        if (!employee.isValid())
            throw new CannotUpdateObjectException("DbObjectDao: Not all fields were completed!");

        try {
            Long PersonDataId = createPersonData(employee);

            String sqlQuery =
                    "update Employees SET FirstName = ?, SurName = ?, FunctionID = ?, PersonDataID = ?" +
                    "where ID = " + String.valueOf(employee.getId());
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getSurName());
            preparedStatement.setString(3, employee.getFunction());
            preparedStatement.setString(4, String.valueOf(PersonDataId));

            if (preparedStatement.executeUpdate() != 1)
                throw new CannotUpdateObjectException("DbObjectDao: Was not updated employee into the DataBase!");

            preparedStatement.close();
        } catch (Exception exception) {
            logger.error("\tDbObjectDao: update: On DB insert: " + exception.toString());
            throw new CannotUpdateObjectException("DbObjectDao: Database exception during updating the employee! " + exception.toString());
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        Connection connection = dbConnectionManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "select emp.ID, emp.FirstName, emp.SurName, func.Name as function, pd.Address, pd.Phone\n" +
                    " from Employees emp\n" +
                    " left join Functions func on func.ID = emp.FunctionID\n" +
                    " left join PersonDatas pd on pd.ID = emp.PersonDataID\n";
            ResultSet employeeSet = statement.executeQuery(sqlQuery);

            while (employeeSet.next()) {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(employeeSet.getString("id")));
                employee.setFirstName(employeeSet.getString("firstName"));
                employee.setSurName(employeeSet.getString("surName"));
                employee.setFunction(employeeSet.getString("function"));
                employee.setAddress(employeeSet.getString("address"));
                employee.setPhone(employeeSet.getString("phone"));
                employees.add(employee);

            }
            statement.close();
        } catch (Exception exception) {
            logger.error("\tDbObjectDao: getAll: On DB connect: " + exception.toString());
        }

        return employees;
    }

    @Override
    public List<Function> getAllFunctions() throws GetAllFunctionObjectException {
        Connection connection = dbConnectionManager.getConnection();

        List<Function> functions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet dBfunctions = statement.executeQuery("select * from functions");

            while (dBfunctions.next()) {
                Function function = new Function();
                function.setId(dBfunctions.getLong("id"));
                function.setName(dBfunctions.getString("name"));

                functions.add(function);
            }
            statement.close();
        } catch (SQLException exception) {
            throw new GetAllFunctionObjectException("DbObjectDao: getAll: " + exception.toString());
        }

        return functions;
    }

    @Override
    public Employee get(FilterDao filter) {
        Connection connection = dbConnectionManager.getConnection();

        Employee employee = new Employee();
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "select emp.ID, emp.FirstName, emp.SurName, func.ID as function, pd.Address, pd.Phone\n" +
                    " from Employees emp\n" +
                    " left join Functions func on func.ID = emp.FunctionID\n" +
                    " left join PersonDatas pd on pd.ID = emp.PersonDataID\n" +
                    " where emp.ID = " + filter.getId();
            ResultSet employeeSet = statement.executeQuery(sqlQuery);

            while (employeeSet.next()) {
                employee.setId(Integer.parseInt(employeeSet.getString("id")));
                employee.setFirstName(employeeSet.getString("firstName"));
                employee.setSurName(employeeSet.getString("surName"));
                employee.setFunction(employeeSet.getString("function"));
                employee.setAddress(employeeSet.getString("address"));
                employee.setPhone(employeeSet.getString("phone"));
            }
            statement.close();
        } catch (Exception exception) {
            logger.error("\tDbObjectDao: get: On DB connect: " + exception.toString());
        }

        return employee;
    }

    @Override
    public void delete(FilterDao filter) throws CannotDeleteObjectException {
        Connection connection = dbConnectionManager.getConnection();
        try {
            String sqlQuery = "delete from Employees where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(filter.getId()));
            if (preparedStatement.executeUpdate() != 1)
                throw new CannotDeleteObjectException("DbDao->delete: ИД не найден. ");

            preparedStatement.close();
        } catch (Exception exception) {
            logger.error("\tDbObjectDao: delete: On DB insert: " + exception.toString());
            throw new CannotDeleteObjectException("DbObjectDao: delete: " + exception.getMessage());
        }
    }

    private Long createPersonData(Employee employee) {
        Connection connection = dbConnectionManager.getConnection();

        long PersonDataId = employee.hashCode();
        try {

            String sqlQuery = "insert into PersonDatas(ID, EmployeeID, DataDate, Phone, Address) " +
                    "values (?, ?, sysdate, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(PersonDataId));
            preparedStatement.setString(2, String.valueOf(employee.getId()));
            preparedStatement.setString(3, employee.getPhone());
            preparedStatement.setString(4, employee.getAddress());

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();

            preparedStatement.close();
        } catch (Exception exception) {
            logger.error("\tDbObjectDao: add: On DB insert: " + exception.toString());
        }

        return PersonDataId;
    }
}
