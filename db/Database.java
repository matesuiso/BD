package db;

import java.sql.*;

public class Database {

    private static Database instance;

    private Connection connection;

    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/colonia_verano",
                "root",
                "root"
            );
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
