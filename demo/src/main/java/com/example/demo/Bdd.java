package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bdd {
private static final String URL =
"jdbc:postgresql://localhost:5432/base_site";
private static final String USER = "postgres";
private static final String PASSWORD = "user";
public static Connection getConnection() throws SQLException {
return DriverManager.getConnection(URL, USER, PASSWORD);
}
}

