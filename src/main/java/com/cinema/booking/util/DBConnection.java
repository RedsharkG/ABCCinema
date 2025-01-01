package com.cinema.booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
<<<<<<< HEAD
import java.sql.SQLException;
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cinema_db";
    private static final String USER = "root";
<<<<<<< HEAD
    private static final String PASSWORD = "Sehara@27";

=======
    private static final String PASSWORD = "admin";
    
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
<<<<<<< HEAD
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException(
                    "Failed to connect to database. Please check if MySQL is running and credentials are correct.", e);
        }
=======
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
    }
}
