package org.sbrf.employee;

import org.apache.log4j.Logger;
import org.sbrf.dao.UserDao;
import org.sbrf.db.DbConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements UserDao<Employee> {

    static String DATABASE_URL  = "jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee";
    static String DATABASE_USER = "";
    static String DATABASE_PASS = "";

    private DbConnectionManager dbConnectionManager;

    private Logger logger = Logger.getLogger(EmployeeDao.class);

    public EmployeeDao() throws SQLException, ClassNotFoundException {
        this.dbConnectionManager = new DbConnectionManager(
                DATABASE_URL,
                DATABASE_USER,
                DATABASE_PASS
        );
    }

    private long getMaxId() {
        Statement statement = null;
        Connection connection = null;
        long resultMaxId = 0;

        try {
            connection = dbConnectionManager.getConnection() ;//DriverManager.getConnection("jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee");
            statement = connection.createStatement();

            String sqlQuery = "select max(id)+1 as id from employees ";
            ResultSet employeeSet = statement.executeQuery(sqlQuery);
            employeeSet.next();
            resultMaxId = Integer.parseInt(employeeSet.getString("id"));
        } catch (Exception exception) {
            logger.error("\tgetMaxId: On DB connect: " + exception.toString());
        } finally {
            try {
                statement.close();
            } catch (Exception exception) {
                logger.error("\tgetMaxId: On DB close: " + exception.toString());
            }
        }

        return resultMaxId;
    }

    public Boolean add(Employee employee) {
        PreparedStatement preparedStatement = null;
        Connection connection = dbConnectionManager.getConnection();

        try {
            String sqlQuery = "insert into Employees(ID, FirstName, SurName, StartDate, EndDate, FireDate, FunctionID, PersonDataID) " +
                    "values (?, ?, ?, sysdate, null, null, 1, 1);";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(getMaxId()));
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getSurName());

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();
        } catch (Exception exception) {
            logger.error("\tadd: On DB insert: " + exception.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception exception) {
                logger.error("\tadd: On DB insert close: " + exception.toString());
            }
        }

        return true;
    }

    public List getAll() {
        List<Employee> employees = new ArrayList<Employee>();

        Statement statement = null;
        Connection connection = dbConnectionManager.getConnection();

        try {
            statement = connection.createStatement();
            String sqlQuery = "select * from employees";
            ResultSet employeeSet = statement.executeQuery(sqlQuery);

            while (employeeSet.next()) {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(employeeSet.getString("id")));
                employee.setFirstName(employeeSet.getString("firstName"));
                employee.setSurName(employeeSet.getString("surName"));

                employees.add(employee);
            }
        } catch (Exception exception) {
            logger.error("\tgetAll: On DB connect: " + exception.toString());
        } finally {
            try {
                statement.close();
            } catch (Exception exception) {
                logger.error("\tgetAll:On DB close: " + exception.toString());
            }
        }

        return employees;
    }

    public Employee getById(long employeeId) {
        Statement statement = null;
        Connection connection = dbConnectionManager.getConnection();

        Employee employee = new Employee();
        try {
            statement = connection.createStatement();
            String sqlQuery = "select * from employees where ID = " + String.valueOf(employeeId);
            ResultSet employeeSet = statement.executeQuery(sqlQuery);

            while (employeeSet.next()) {
                employee.setId(Integer.parseInt(employeeSet.getString("id")));
                employee.setFirstName(employeeSet.getString("firstName"));
                employee.setSurName(employeeSet.getString("surName"));
            }
        } catch (Exception exception) {
            logger.error("\tgetById: On DB connect: " + exception.toString());
        } finally {
            try {
                statement.close();
            } catch (Exception exception) {
                logger.error("getById: On DB close: " + exception.toString());
            }
        }

        return employee;
    }

    public Boolean delete(long employeeId) {
        PreparedStatement preparedStatement = null;
        Connection connection = dbConnectionManager.getConnection();

        try {
            String sqlQuery = "delete from Employees where id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(employeeId));

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();
        } catch (Exception exception) {
            logger.error("\tdelete: On DB insert: " + exception.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception exception) {
                logger.error("\tdelete: On DB insert close: " + exception.toString());
            }
        }

        return true;
    }
}
