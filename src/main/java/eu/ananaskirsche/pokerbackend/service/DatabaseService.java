package eu.ananaskirsche.pokerbackend.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    public static Connection getConnection() throws SQLException {
        String filePath = PropertiesService.getEnv().get("DB_FILE", "data/poker.db");
        return DriverManager.getConnection("jdbc:sqlite:%s".formatted(filePath));
    }
}
