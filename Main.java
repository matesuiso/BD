import java.util.Scanner;
import db.Ninio;
import db.NinioDAO;
import java.sql.*;
import java.util.InputMismatchException;

public class Main {
   public static void main(String[] args) {
      int opc = 0;
      Scanner sc = new Scanner(System.in);

      while (!(opc == 9)) {
         System.out.println("");
         System.out.println("");
         System.out.println("-----------------------------------------------");
         System.out.println("Seleccione una operacion con un numero");
         System.out.println("");
         System.out.println("(1) Insertar un niño");
         System.out.println("(2) Buscar un niño");
         System.out.println("(3) Listar niños con las colonias que asisten");
         System.out.println("(4) Eliminar niño segun DNI");
         System.out.println("");
         System.out.println("(9) Salir");
         System.out.println("-----------------------------------------------");
         System.out.println("");

         try {
           opc = sc.nextInt();
         } catch (InputMismatchException e) {
           opc = 0;
         }

         switch (opc) {
            case 1:
              menuInsertar();
              break;
            case 2:
              menuBuscar();
              break;
            case 3:
              menuListar();
              break;
            case 4:
              menuEliminar();
              break;
            case 9:
              break;
            default:
              System.out.println("La opción ingresada es inválida.");
         }

      }  // end while
   }

   public static void menuInsertar() {
      System.out.println(" ");
      System.out.println(" ");
      Scanner sc = new Scanner(System.in);
      String nombre, dni, apellido, fecha, telefono;
      System.out.println("Inserte dni de niño");
      dni = sc.nextLine();
      System.out.println("Inserte nombre de niño");
      nombre = sc.nextLine();
      System.out.println("Inserte apellido de niño");
      apellido = sc.nextLine();
      System.out.println("Inserte telefono de niño");
      telefono = sc.nextLine();
      System.out.println("Inserte fecha de nacimiento de niño (AAAA-MM-DD)");
      fecha = sc.nextLine();
      System.out.println(" ");
      System.out.println(" ");

      if (NinioDAO.insertarNinio(
        new Ninio(
          nombre,
          apellido,
          dni,
          telefono,
          fecha
        )
      ))
      System.out.println(" ");
          System.out.println("El niño ha sido guardado con éxito.");
      else
          System.out.println("No se pudo guardar el niño.");

          System.out.println(" ");
   }

   public static void menuEliminar() {
      System.out.println(" ");
      System.out.println(" ");
      String dni;
      Scanner sc = new Scanner(System.in);
      System.out.println("Inserte dni de niño");
      dni = sc.nextLine();

      if (NinioDAO.eliminarNinio(dni))
         System.out.println("Niño borrado con exito!");
      else
         System.out.println("No se ha borrado ningun niño con el dni " + dni);
   }

   public static void menuBuscar() {
      Scanner sc = new Scanner(System.in);
      int dni;
      System.out.println(" ");
      System.out.println(" ");
      System.out.println("Inserte el dni del niño a buscar");
      dni = sc.nextInt();
      System.out.println(" ");

      Ninio n = NinioDAO.buscarNinio(String.valueOf(dni));
      if (n != null) {
        System.out.print(n.apellido);
        System.out.print(" ");
        System.out.print(n.nombre);
        System.out.print(" ");
        System.out.print(n.dni);
        System.out.print(" ");
        System.out.print(n.fechaNacimiento);
        System.out.println("");
      } else
        System.out.println("No se ha encontrado ningún niño con el dni " + dni);

        System.out.println(" ");
        System.out.println(" ");
   }

   public static void menuListar() {
      System.out.println(" ");
      System.out.println(" ");

      for (Ninio n : NinioDAO.obtenerNiniosConColonias()) {
          System.out.print("Apellido y nombre: ");
          System.out.print(n.apellido);
          System.out.print(" ");
          System.out.println(n.nombre);
          System.out.print("Colonias a las que asiste: ");
          System.out.print(n.colonias);
          System.out.println("");
          System.out.println("");
      }

      System.out.println("");
      System.out.println("");
   }

}
