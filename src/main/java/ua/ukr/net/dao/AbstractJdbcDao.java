package ua.ukr.net.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class AbstractJdbcDao {
    private Connection connection = null;

    public Connection createConnection() throws SQLException {
        try {
            connection = new HikariDataSource(getHikariConfig()).getConnection();
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return connection;
   }

    private HikariConfig getHikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(null);
        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMaximumPoolSize(25);
        config.setAllowPoolSuspension(true);
        return config;
    }
}