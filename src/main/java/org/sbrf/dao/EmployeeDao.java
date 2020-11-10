package org.sbrf.dao;

import org.apache.log4j.Logger;
import org.sbrf.db.DbConnectionManager;
import org.sbrf.employee.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements Dao<Employee> {

    private DbConnectionManager dbConnectionManager;

    private Logger logger = Logger.getLogger(EmployeeDao.class);

    public EmployeeDao() throws SQLException, ClassNotFoundException {

        this.dbConnectionManager = new DbConnectionManager(
                DbConnectionManager.DATABASE_URL,
                DbConnectionManager.DATABASE_USER,
                DbConnectionManager.DATABASE_PASS
        );
    }

    private long getMaxId(String tableName) {
        long resultMaxId = 1;
        try {
            Connection connection = dbConnectionManager.getConnection();
            Statement statement = connection.createStatement();

            String sqlQuery = "select nvl(max(id),0)+1 as id from " + tableName;
            ResultSet employeeSet = statement.executeQuery(sqlQuery);
            employeeSet.next();
            resultMaxId = Integer.parseInt(employeeSet.getString("id"));

            statement.close();
        } catch (Exception exception) {
            logger.error("\tgetMaxId: On DB connect: " + exception.toString());
        }

        return resultMaxId;
    }

    public Boolean add(Employee employee) throws Exception {
        Connection connection = dbConnectionManager.getConnection();

        if (employee.isNull()) throw new Exception("No data to add to Employee!");
        try {
            Long PersonDataId = createPersonData(employee);

            String sqlQuery = "insert into Employees(ID, FirstName, SurName, StartDate, EndDate, FireDate, FunctionID, PersonDataID) " +
                    "values (?, ?, ?, sysdate, null, null, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(getMaxId("employees")));
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getSurName());
            preparedStatement.setString(4, employee.getFunction());
            preparedStatement.setString(5, String.valueOf(PersonDataId));

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();

            preparedStatement.close();
        } catch (Exception exception) {
            logger.error("\tadd: On DB insert: " + exception.toString());
        }

        return true;
    }

    private Long createPersonData(Employee employee) {
        Connection connection = dbConnectionManager.getConnection();

        Long PersonDataId = getMaxId("PersonDatas");
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
            logger.error("\tadd: On DB insert: " + exception.toString());
        }

        return PersonDataId;
    }

    public List getAll() {
        List<Employee> employees = new ArrayList<Employee>();

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
            logger.error("\tgetAll: On DB connect: " + exception.toString());
        }

        return employees;
    }

    public Employee get(long employeeId) {
        Connection connection = dbConnectionManager.getConnection();

        Employee employee = new Employee();
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "select * from employees where ID = " + String.valueOf(employeeId);
            ResultSet employeeSet = statement.executeQuery(sqlQuery);

            while (employeeSet.next()) {
                employee.setId(Integer.parseInt(employeeSet.getString("id")));
                employee.setFirstName(employeeSet.getString("firstName"));
                employee.setSurName(employeeSet.getString("surName"));
            }
            statement.close();
        } catch (Exception exception) {
            logger.error("\tget: On DB connect: " + exception.toString());
        }

        return employee;
    }

    public Boolean delete(long employeeId) {
        Connection connection = dbConnectionManager.getConnection();
        try {
            String sqlQuery = "delete from Employees where id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(employeeId));

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();

            preparedStatement.close();
        } catch (Exception exception) {
            logger.error("\tdelete: On DB insert: " + exception.toString());
        }

        return true;
    }
}
