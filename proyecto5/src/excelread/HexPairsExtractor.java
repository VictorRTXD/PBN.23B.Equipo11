/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package excelread;

public class HexPairsExtractor {

    // Función para convertir un string hexadecimal a decimal
    public static int hexadecimalADecimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    // Función para convertir un decimal a su representación hexadecimal
    public static String decimalAHexadecimal(int decimal) {
        return Integer.toHexString(decimal).toUpperCase();
    }

    // Función para calcular el complemento a uno de un número decimal
    public static int complementoUno(int decimal) {
        return ~decimal & 0xFF; // Aplicamos la máscara para obtener solo los 8 bits
    }

    public static void main(String[] args) {
        String hexadecimalCompleto = "f821"; // El número hexadecimal completo
        
        // Extraer los dos últimos caracteres del string hexadecimal
        String dosUltimosCaracteres = hexadecimalCompleto.substring(hexadecimalCompleto.length() - 2);
        
        // Convertir los dos últimos caracteres a decimal
        int decimal = hexadecimalADecimal(dosUltimosCaracteres);
        
        // Calcular el complemento a uno del número decimal
        int complemento = complementoUno(decimal);
        
        // Convertir el resultado de vuelta a hexadecimal
        String resultadoHexadecimal = decimalAHexadecimal(complemento);
        
        // Mostrar el resultado
        System.out.println("Número hexadecimal inicial (2 últimos caracteres): " + dosUltimosCaracteres);
        System.out.println("Complemento a uno en hexadecimal: " + resultadoHexadecimal);
    }
}



// HexPairsExtractor