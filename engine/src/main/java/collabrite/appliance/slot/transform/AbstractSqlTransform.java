package collabrite.appliance.slot.transform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import collabrite.appliance.DataTransform;

public abstract class AbstractSqlTransform implements DataTransform {

    private String databaseDriverName = null;
    private String jdbcURL = null;
    private String username = null, password = null;

    public void setDatabaseDriverName(String databaseDriver) {
        this.databaseDriverName = databaseDriver;
    }

    public void setJdbcURL(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract Object transform(Object transform) throws IOException;

    protected Connection getConnection() throws Exception {
        Class.forName(databaseDriverName);
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    protected void safeClose(Statement stmt) {
        if (stmt != null) {
            try {
                safeClose(stmt.getConnection());
                stmt.close();
            } catch (SQLException e) {
            }
        }
    }

    protected void safeClose(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}