package com.company.service.helper.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariDataSourceFactory {
    public DataSource create() {
        String dsn = "jdbc:postgresql://localhost:5432/phone_book";
        String user = "postgres";
        String password = "0000";
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dsn);
        config.setUsername(user);
        config.setPassword(password);
        config.setMinimumIdle(4);
        config.setMaximumPoolSize(8);
        return new HikariDataSource(config);
    }
}
