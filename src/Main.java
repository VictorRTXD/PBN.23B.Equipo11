import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;

class Main {
    public static void main(String[] args) {
        String fileName = "P1ASM.asm";
        String lineaCompleta = "";

        /*
        br sirve para leer el contenido del archivo y el while es para leerlo todo usando br.readLIne() y almacenamos cada linea en la variable linea */
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            System.out.println("Contenido original del archivo:");
            while ((linea = br.readLine()) != null) {
                lineaCompleta += linea;
                System.out.println(linea); // Muestra la línea original
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(lineaCompleta);
    }
}

// "; comentario número 1 =Programación=\n" + //
//                 "ORG %00001111\n" + //
//                 "Et1: EQU $FFFF\n" + //
//                 "dos: LDAA @4732\n" + //
//                 "SWI\n" + //
//                 "DS.B %0011000011111100\n" + //
//                 "; comentario número 2 ¡Bajo!\n" + //
//                 "; comentario número 3 -Nivel- Tres: SWI\n" + //
//                 "END";
