package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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


    public static Boolean insertarNinio(Ninio nuevoNinio) {
      Connection conexion = Database.getInstance().getConnection();
      try {
         conexion.setAutoCommit(false);
      } catch (SQLException e) {
         return false;
      }

      Boolean resul = true;
      PreparedStatement ps;

      try {
         ps = conexion.prepareStatement("INSERT INTO persona (nombre, apellido, dni) VALUES (?, ?, ?)");
         ps.setString(1, nuevoNinio.nombre);
         ps.setString(2, nuevoNinio.apellido);
         ps.setString(3, nuevoNinio.dni);

         ps.executeUpdate();

         ps = conexion.prepareStatement("INSERT INTO nino (dni, fecha_nacimiento, telefono) VALUES (?, ?, ?)");
         ps.setString(1, nuevoNinio.dni);
         ps.setString(2, nuevoNinio.fechaNacimiento);
         ps.setString(3, nuevoNinio.telefono);

         ps.executeUpdate();

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

    public static Boolean eliminarNinio(String dni) {
      Connection conexion = Database.getInstance().getConnection();

      try {
         conexion.setAutoCommit(false);
      } catch (SQLException e) {
         return false;
      }

      int sum = 0;

      // en realidad hay que hacerlo de otra forma.. no falla, si no existiera el campo.. asique siempre devuelve true..
      // hay que ver el resultado de las columnas afectadas
        try {
            PreparedStatement ps = conexion
                .prepareStatement(
                    "DELETE FROM nino WHERE nino.dni= ? "
                );

            ps.setString(1, dni);
            sum += ps.executeUpdate();

            ps = conexion
                .prepareStatement(
                    "DELETE FROM persona WHERE persona.dni= ?"
                );

            ps.setString(1, dni);

            sum += ps.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {

              try {
                 conexion.rollback();
              } catch (SQLException e1) { }

        } finally {
           try {
              conexion.setAutoCommit(true);
           } catch (SQLException e) {     }
        }

        return (sum == 2) ? true : false;
    }
}
