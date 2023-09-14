import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    public static void main(String[] args) {
        String fileName = "P1ASM.asm";

        // ** Expresiones regulares

        String regexComentario = "^\\s*;.*$";

        // Expresión regular para identificar código de operación y operando
        String regexCodigoOperacionOperando = "^\\s+([Aa.-Zz]+)\\s+(.*?)$";

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
                    if (linea.length () > 80){
                        System.out.println("Exceso de caracteres en: "+linea);
                    } else {
                    // Es un comentario
                        System.out.println("Comentario: " + linea);
                        System.out.println("");}
                } else if (matcherCodigo.matches()) {
                    // Es una línea de código con etiqueta, código de operación y operando
                    String etiqueta = matcherCodigo.group(1);
                    String codigoOperacion = matcherCodigo.group(2);
                    String operando = matcherCodigo.group(3);
                        if(etiqueta.length()>8 || codigoOperacion.length()>5){
                            System.out.println("Exceso de caracteres en: "+linea);
                        }else{
                            System.out.println("Etiqueta: " + etiqueta);
                            System.out.println("Código de Operación: " + codigoOperacion);
                            System.out.println("Operando: " + operando);
                            System.out.println("");}
                    
                } else if(matcherCodigoOperacionOperando.matches()) {
                    // Es una línea con código de operación y operando
                    String etiqueta = null;
                    String codigoOperacion = matcherCodigoOperacionOperando.group(1);
                    String operando = matcherCodigoOperacionOperando.group(2);
                    if(codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                    System.out.println("Etiqueta: "+etiqueta);
                    System.out.println("Código de Operación: " + codigoOperacion);
                    System.out.println("Operando/s: " + operando);
                    System.out.println("");}
                    
                } else if(matcherCodigoOperacion.matches()) {
                    // Es una línea con código de operación y operando
                    String etiqueta = null;
                    String codigoOperacion = matcherCodigoOperacion.group(1);
                    String operando = null;
                    if(codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                    System.out.println("Etiqueta: "+etiqueta);
                    System.out.println("Código de Operación: " + codigoOperacion);
                    System.out.println("Operando/s: "+operando);
                    System.out.println("");}
                    
                } else {
                    // No coincide con ninguna de las expresiones regulares
                    System.out.println("Error de Sintaxis: " + linea);
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
