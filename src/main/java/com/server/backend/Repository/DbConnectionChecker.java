package com.server.backend.Repository;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DbConnectionChecker {

    private static final Logger logger =
            LoggerFactory.getLogger(DbConnectionChecker.class);

    private final DataSource dataSource;

    public DbConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void checkConnection() {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Connected successfully!");
            logger.info("Database: {}", 
            connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            logger.error("Database connection failed", e);
        }
    }
}