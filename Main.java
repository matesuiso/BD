import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      int opc = 0;
      Scanner sc = new Scanner(System.in);

      while (!(opc == 9)) {
         System.out.println("Seleccione una operacion con un numero");
         System.out.println("");
         System.out.println("(1) Insertar un ninio");
         System.out.println("(2) Eliminar un ninio");
         System.out.println("(3) Listar ninios con las colonias que asisten");
         System.out.println("");
         System.out.println("(9) Salir");
         System.out.println("");

         opc = sc.nextInt();

         if (opc == 1) {
            Main.insertar();
         }
         if (opc == 2) {
            Main.eliminar();
         }
         if (opc == 3) {
            Main.listar();
         }

      }  // end while
   }
}
