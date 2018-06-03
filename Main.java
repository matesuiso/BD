import java.util.Scanner;
import db.Ninio;
import db.NinioDAO;
import java.sql.*;

public class Main {
   public static void main(String[] args) {
      int opc = 0;
      Scanner sc = new Scanner(System.in);

      while (!(opc == 9)) {
         System.out.println("");
         System.out.println("");
         System.out.println("");
         System.out.println("Seleccione una operacion con un numero");
         System.out.println("");
         System.out.println("(1) Insertar un ninio");
         System.out.println("(2) Eliminar un ninio");
         System.out.println("(3) Listar ninios con las colonias que asisten");
         System.out.println("");
         System.out.println("(9) Salir");
         System.out.println("");

         opc = sc.nextInt();

         switch (opc) {
            case 1:
              menuInsertar();
              break;
            case 2:
              menuEliminar();
              break;
            case 3:
              menuListar();
              break;
         }

      }  // end while
   }

   public static void menuInsertar() {
      Scanner sc = new Scanner(System.in);
      String nombre, dni, apellido, fecha, telefono;
      System.out.println("Inserte nombre de ninio");
      nombre = sc.nextLine();
      System.out.println("Inserte apellido de ninio");
      apellido = sc.nextLine();
      System.out.println("Inserte telefono de ninio");
      telefono = sc.nextLine();
      System.out.println("Inserte fecha de nacimiento de ninio AAAA-MM-DD");
      fecha = sc.nextLine();
      System.out.println("Inserte dni de ninio");
      dni = sc.nextLine();

      System.out.println("");
      Ninio temporal = new Ninio(nombre, apellido, dni, telefono, fecha);
      System.out.println("Resultado de la insercion: " + NinioDAO.insertarNinio(temporal));

      System.out.println("");
      System.out.println("");
   }

   public static void menuEliminar() {
      Scanner sc = new Scanner(System.in);
      String dni;
      System.out.println("Inserte dni de ninio");
      dni = sc.nextLine();

      System.out.println("");
      System.out.println("Resultado de la eliminacion: " + NinioDAO.eliminarNinio(dni));

      System.out.println("");
      System.out.println("");
   }

   public static void menuListar() {
     System.out.println("");
     System.out.println("Apellido  |  Nombre  | Colonias a las que asiste");

      for (Ninio n : NinioDAO.obtenerNiniosConColonias()) {
          System.out.print(n.apellido);
          System.out.print(" ");
          System.out.print(n.nombre);
          System.out.print(" ");
          System.out.print(n.colonias);
          System.out.println("");
      }
      
      System.out.println("");
      System.out.println("");
   }

}
