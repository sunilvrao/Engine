package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil { 

    private static final String PROPFILE = "db.properties";
    private static Connection con;
    
    static{
        try {
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream(PROPFILE);
            if (is == null)
                throw new IllegalStateException(PROPFILE + " not found");
            // We just load a custom properties or xml file
            Properties properties = new Properties();
            properties.load(is);

            // Load the Driver class.
            Class.forName(properties.getProperty("connection.class"));
            // If you are using any other database then load the right driver here.

            // Create the connection using the static getConnection method
            con = DriverManager.getConnection(properties.getProperty("connection.url"),
                    properties.getProperty("connection.username"), properties.getProperty("connection.password"));

            con.setAutoCommit(true);
 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    };
    public DBUtil() {
        
    }
    

    /**
     * Get the {@link Connection}
     *
     * @return
     */
    public Connection getConnection() {
        return con;
    }
}