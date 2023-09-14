import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    public static void main(String[] args) {
        String fileName = "P1ASM.asm";
        String lineaCompleta = "";

        // ** Expresiones regulares

        String regexComentario = "^\\s*;.*$";

        // Expresión regular para identificar código de operación y operando
        String regexCodigoOperacionOperando = "^([A-Z]+)\\s+(.*?)$";

        // Expresión regular para identificar etiquetas, códigos de operación y operandos
        String regexCodigo = "^\\s*([a-zA-Z0-9]+):\\s*([A-Z]+)\\s+(.*?)$";

        //Un solo codigo de operacion
        String regexCodigoOperacion = "^\\s*([A-Za-z]+)";

        // Compilar las expresiones regulares
        Pattern patComentario = Pattern.compile(regexComentario);
        Pattern patCodigo = Pattern.compile(regexCodigo);
        Pattern patCodigoOperacionOperando = Pattern.compile(regexCodigoOperacionOperando);
        Pattern patCodigoOperacion = Pattern.compile(regexCodigoOperacion);

        

        /*
        br sirve para leer el contenido del archivo y el while es para leerlo todo usando br.readLIne() y almacenamos cada linea en la variable linea */
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            System.out.println("Contenido original del archivo:");
            while ((linea = br.readLine()) != null) {
                Matcher matcherComentario = patComentario.matcher(linea);
                Matcher matcherCodigo = patCodigo.matcher(linea);
                Matcher matcherCodigoOperacionOperando = patCodigoOperacionOperando.matcher(linea);
                Matcher matcherCodigoOperacion= patCodigoOperacion.matcher(linea);
    
                if (matcherComentario.matches()) {
                    // Es un comentario
                    if (linea.length() < 80) {
                        System.out.println("Comentario: " + linea);
                    } else {
                        System.out.println("no sirvo");
                    }
                } else if (matcherCodigo.matches()) {
                    // Es una línea de código con etiqueta, código de operación y operando
                    String etiqueta = matcherCodigo.group(1);
                    String codigoOperacion = matcherCodigo.group(2);
                    String operando = matcherCodigo.group(3);
    
                    System.out.println("Etiqueta: " + etiqueta);
                    System.out.println("Código de Operación: " + codigoOperacion);
                    System.out.println("Operando: " + operando);
                } else if(matcherCodigoOperacionOperando.matches()) {
                    // Es una línea con código de operación y operando
                    String codigoOperacion = matcherCodigoOperacionOperando.group(1);
                    String operando = matcherCodigoOperacionOperando.group(2);
    
                    System.out.println("Código de Operación: " + codigoOperacion);
                    System.out.println("Operando: " + operando);
                } else if(matcherCodigoOperacion.matches()) {
                    // Es una línea con código de operación y operando
                    String codigoOperacion = matcherCodigoOperacion.group(1);
    
                    System.out.println("Código de Operación: " + codigoOperacion);
                } else {
                    // No coincide con ninguna de las expresiones regulares
                    System.out.println("Línea no reconocida: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
