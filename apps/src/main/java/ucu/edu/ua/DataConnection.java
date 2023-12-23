package ucu.edu.ua;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.Getter;
import lombok.SneakyThrows;

public class DataConnection {
    private static String JDBC_DRIVER = "org.sqlite.JDBC";
    private static String DATABASE_URL = "jdbc:sqlite:C:/sqlite/database/redrig";

    private static DataConnection instance;

    @Getter
    private final Connection connection;

    @SneakyThrows
    private DataConnection() {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DATABASE_URL);
    }

    public static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        }
        return instance;
    }

    @SneakyThrows
    public void closeConnection() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
