package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.PreparedStatement;

public class NinioDAO {

    public static List<Ninio> obtenerNiniosConColonias() {
        try {
            ResultSet resultSet = Database
                .getInstance()
                .getConnection()
                .prepareStatement(
                    "SELECT n.*, p.*, GROUP_CONCAT(nombre_asoc) AS colonias FROM nino n JOIN persona p ON n.dni = p.dni JOIN asiste a ON a.dni = n.dni JOIN colonia c ON a.cod_colonia = c.cod_colonia GROUP BY n.dni ORDER BY p.apellido ASC, p.nombre ASC;"
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

                ninio.colonias = resultSet.getString("colonias");

                result.add(ninio);
            }

            return result;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }


    public static Ninio buscarNinio(String sbdni) {
        try {
            PreparedStatement ps = Database
                .getInstance()
                .getConnection()
                .prepareStatement(
                    "SELECT * FROM nino n JOIN persona p ON n.dni = p.dni WHERE n.dni=?"
                );
            ps.setString(1, sbdni);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return new Ninio(
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getString("fecha_nacimiento")
                ); 
            else
                return null;    
        } catch (SQLException e) {
            return null;
        }
    }
}
