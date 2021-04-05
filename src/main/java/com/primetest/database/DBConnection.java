package com.primetest.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnection {

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "0000");
    }
}

