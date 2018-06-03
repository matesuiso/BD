package db;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class Database {

    private static Database instance;

    private Connection connection;

    private Database() {
        Properties props = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("./database.properties");
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Error al obtener la configuración de la base de datos.");
            System.exit(0);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException e) {}
            }
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                "jdbc:mysql://" +
                props.getProperty("host") + ":" +
                props.getProperty("port") + "/" +
                props.getProperty("name") + "?useSSL=false",
                props.getProperty("user"),
                props.getProperty("password")
            );
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos.");
            System.exit(0);
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
