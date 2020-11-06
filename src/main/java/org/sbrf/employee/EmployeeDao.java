package org.sbrf.employee;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.sbrf.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements UserDao<Employee> {

    private long getMaxId() {
        Statement statement = null;
        Connection connection = null;
        long resultMaxId = 0;

        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee");
            statement = connection.createStatement();

            String sqlQuery = "select max(id)+1 as id from employees ";
            ResultSet employeeSet = statement.executeQuery(sqlQuery);
            employeeSet.next();
            resultMaxId = Integer.parseInt(employeeSet.getString("id"));
        } catch (Exception exception) {
            System.out.println("On DB connect: " + exception.toString());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception exception) {
                System.out.println("On DB close: " + exception.toString());
            }
        }

        return resultMaxId;
    }

    public void add(Employee employee) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee");

            String sqlQuery = "insert into Employees(ID, FirstName, SurName, StartDate, EndDate, FireDate, FunctionID, PersonDataID) " +
                    "values (?, ?, ?, sysdate, null, null, 1, 1);";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(getMaxId()));
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getSurName());

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();
        } catch (Exception exception) {
            System.out.println("On DB insert: " + exception.toString());
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (Exception exception) {
                System.out.println("On DB insert close: " + exception.toString());
            }
        }
    }

    public List getAll() {
        List<Employee> employees = new ArrayList<Employee>();

        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee");

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
            System.out.println("On DB connect: " + exception.toString());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception exception) {
                System.out.println("On DB close: " + exception.toString());
            }
        }

        return employees;
    }

    public Employee getById(long employeeId) {
        Statement statement = null;
        Connection connection = null;

        Employee employee = new Employee();
        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee");

            statement = connection.createStatement();
            String sqlQuery = "select * from employees where ID = " + String.valueOf(employeeId);
            ResultSet employeeSet = statement.executeQuery(sqlQuery);

            while (employeeSet.next()) {
                employee.setId(Integer.parseInt(employeeSet.getString("id")));
                employee.setFirstName(employeeSet.getString("firstName"));
                employee.setSurName(employeeSet.getString("surName"));
            }
        } catch (Exception exception) {
            System.out.println("On DB connect: " + exception.toString());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception exception) {
                System.out.println("On DB close: " + exception.toString());
            }
        }

        return employee;
    }

    public void delete(long employeeId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApp\\src\\main\\db\\employee");

            String sqlQuery = "delete from Employees where id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(employeeId));

            if (preparedStatement.executeUpdate() != 1)
                throw new Exception();
        } catch (Exception exception) {
            System.out.println("On DB insert: " + exception.toString());
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (Exception exception) {
                System.out.println("On DB insert close: " + exception.toString());
            }
        }
    }
}
