package com.edutech.progressive.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private static final Properties props = new Properties();
    private static boolean initialized = false;

    private DatabaseConnectionManager() {}

    private static synchronized void init() {
        if (initialized) return;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) props.load(is);
        } catch (IOException ignored) {}
        String driver = firstNonEmpty(
                System.getProperty("db.driver"),
                System.getProperty("jdbc.driver"),
                System.getProperty("spring.datasource.driver-class-name"),
                props.getProperty("db.driver"),
                props.getProperty("jdbc.driver"),
                props.getProperty("spring.datasource.driver-class-name")
        );
        if (driver != null && !driver.isBlank()) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Driver not found: " + driver, e);
            }
        }
        initialized = true;
    }

    public static Connection getConnection() throws SQLException {
        if (!initialized) init();
        String url = firstNonEmpty(
                System.getProperty("db.url"),
                System.getProperty("jdbc.url"),
                System.getProperty("spring.datasource.url"),
                props.getProperty("db.url"),
                props.getProperty("jdbc.url"),
                props.getProperty("spring.datasource.url")
        );
        String user = firstNonEmpty(
                System.getProperty("db.username"),
                System.getProperty("jdbc.username"),
                System.getProperty("spring.datasource.username"),
                props.getProperty("db.username"),
                props.getProperty("jdbc.username"),
                props.getProperty("spring.datasource.username")
        );
        String pass = firstNonEmpty(
                System.getProperty("db.password"),
                System.getProperty("jdbc.password"),
                System.getProperty("spring.datasource.password"),
                props.getProperty("db.password"),
                props.getProperty("jdbc.password"),
                props.getProperty("spring.datasource.password")
        );
        if (url == null || url.isBlank()) throw new SQLException("Database URL not configured");
        if (user == null) user = "";
        if (pass == null) pass = "";
        return DriverManager.getConnection(url, user, pass);
    }

    private static String firstNonEmpty(String... c) {
        for (String s : c) if (s != null && !s.isBlank()) return s;
        return null;
    }
}