import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * testing
 */
public class testing {
    public static void main(String[] args) {
        String texto = "; comentario número 1 =Programación=\n" + //
                "ORG %00001111\n" + //
                "Et1: EQU $FFFF\n" + //
                "dos: LDAA @4732\n" + //
                "SWI\n" + //
                "DS.B %0011000011111100\n" + //
                "; comentario número 2 ¡Bajo!\n" + //
                "; comentario número 3 -Nivel- Tres: SWI\n" + //
                "END";

        // Expresión regular para identificar comentarios que comienzan con ";"
        String regexComentario = "^\\s*;.*$";

        // Expresión regular para identificar código de operación y operando
        String regexCodigoOperacionOperando = "^([A-Z]+)\\s+(.*?)$";

        // Expresión regular para identificar etiquetas, códigos de operación y operandos
        String regexCodigo = "^\\s*([a-z0-9]+):\\s*([A-Z]+)\\s+(.*?)$";

        //Un solo codigo de operacion
        String regexCodigoOperacion = "^\\s*([A-Z])";

        // Compilar las expresiones regulares
        Pattern patComentario = Pattern.compile(regexComentario);
        Pattern patCodigo = Pattern.compile(regexCodigo);
        Pattern patCodigoOperacionOperando = Pattern.compile(regexCodigoOperacionOperando);
        Pattern patCodigoOperacion = Pattern.compile(regexCodigoOperacion);

        // Dividir el texto en líneas
        String[] lineas = texto.split("\n");

        // Procesar cada línea
        for (String linea : lineas) {
            Matcher matcherComentario = patComentario.matcher(linea);
            Matcher matcherCodigo = patCodigo.matcher(linea);
            Matcher matcherCodigoOperacionOperando = patCodigoOperacionOperando.matcher(linea);
            Matcher matcherCodigoOperacion= patCodigoOperacion.matcher(linea);

            if (matcherComentario.matches()) {
                // Es un comentario
                System.out.println("Comentario: " + linea);
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
    }
}