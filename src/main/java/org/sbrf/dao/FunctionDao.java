package org.sbrf.dao;

import org.sbrf.db.DbConnectionManager;
import org.sbrf.employee.Function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FunctionDao implements Dao<Function> {

    private DbConnectionManager dbConnectionManager;

    public FunctionDao() throws SQLException, ClassNotFoundException {
        this.dbConnectionManager = new DbConnectionManager(
                DbConnectionManager.DATABASE_URL,
                DbConnectionManager.DATABASE_USER,
                DbConnectionManager.DATABASE_PASS
        );
    }

    public Boolean add(Function employee) {
        return null;
    }

    public List<Function> getAll() {
        Connection connection = dbConnectionManager.getConnection();

        List<Function> functions = new ArrayList<Function>();
        try {
            Statement statement = connection.createStatement();
            ResultSet dBfunctions = statement.executeQuery("select * from functions");

            while (dBfunctions.next())
            {
                Function function = new Function();
                function.setId(dBfunctions.getLong("id"));
                function.setName(dBfunctions.getString("name"));

                functions.add(function);
            }
            statement.close();
        }
        catch (Exception exception) {
        }

        return functions;
    }

    public Function get(long employeeId) {
        return null;
    }

    public Boolean delete(long employeeId) {
        return null;
    }
}
