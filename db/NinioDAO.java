package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class NinioDAO {

    public static List<Ninio> obtenerNinios() {
        try {
            ResultSet resultSet = Database
                .getInstance()
                .getConnection()
                .prepareStatement(
                    "SELECT * FROM nino n JOIN persona p ON n.dni = p.dni"
                )
                .executeQuery();

            List<Ninio> result = new ArrayList<>();

            while (resultSet.next()) {
                Ninio ninio = new Ninio(
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getString("fecha_nacimiento")
                );

                result.add(ninio);
            }

            return result;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }


    public static Ninio buscarNinio(String sbdni) {
        try {
            ResultSet resultSet = Database
                .getInstance()
                .getConnection()
                .prepareStatement(
                    "SELECT * FROM nino n JOIN persona p ON n.dni = p.dni WHERE n.dni=?", sbdni
                )
                .executeQuery();
            if (resultSet.next()) {
                return new Ninio(
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getString("fecha_nacimiento")
                ); 
                else {
                    return null;    
                }
        } catch (SQLException e) {
            return null;
        }
    }
}
