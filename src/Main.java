import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Main {
    public static void main(String[] args) {
        String fileName = "P1ASM.asm";

        /*
        br sirve para leer el contenido del archivo y el while es para leerlo todo usando br.readLIne() y almacenamos cada linea en la variable linea */
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            System.out.println("Contenido original del archivo:");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea); // Muestra la línea original
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
