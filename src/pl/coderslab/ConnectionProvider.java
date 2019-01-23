package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "coderslab";
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "workshop2";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getProperties());
    }

    private static String getUrl() {
        return String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE);
    }

    private static Properties getProperties() {
        final Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("useSSL", "true");
        properties.setProperty("verifyServerCertificate", "false");
        properties.setProperty("useJDBCCompliantTimezoneShift", "true");
        properties.setProperty("useLegacyDatetimeCode", "false");
        properties.setProperty("serverTimezone", "UTC");
        properties.setProperty("characterEncoding", "utf8");
        return properties;
    }
}
