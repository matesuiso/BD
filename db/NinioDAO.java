package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static Boolean insertarNinio(Ninio nuevoNinio) {
      Connection conexion = Database.getInstance().getConnection();
      try {
         conexion.setAutoCommit(false);
      } catch (SQLException e) { }
      Boolean resul = true;
      PreparedStatement ps;

      try {
         ps = conexion.prepareStatement("INSERT INTO persona (nombre, apellido, dni) VALUES (?, ?, ?)");
         ps.setString(1, nuevoNinio.nombre);
         ps.setString(2, nuevoNinio.apellido);
         ps.setString(3, nuevoNinio.dni);

         ps.executeQuery();


         ps = conexion.prepareStatement("INSERT INTO nino (dni, telefono, fecha_nacimiento) VALUES (?, ?, ?)");
         ps.setString(1, nuevoNinio.dni);
         ps.setString(2, nuevoNinio.telefono);
         ps.setString(3, nuevoNinio.fechaNacimiento);

         ps.executeQuery();

         conexion.commit();
      } catch (SQLException e) {
         try {
            conexion.rollback();
         } catch (SQLException e1) { }
         resul = false;
      } finally {
         try {
            conexion.setAutoCommit(true);
         } catch (SQLException e) { }
      }

      return resul;
   }

}
