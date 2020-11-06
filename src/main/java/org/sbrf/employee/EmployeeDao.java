package org.sbrf.employee;

import org.sbrf.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements UserDao<Employee> {

    public void add(Employee employee) {

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

    public void delete(Employee employee) {

    }
}
