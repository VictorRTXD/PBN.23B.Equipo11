/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelread;

import org.apache.poi.ss.usermodel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelRead {
    static ArrayList<LineaInstruccion> instruccion = new ArrayList<LineaInstruccion>();
    static int contador = 0;
    public static void main(String[] args) throws IOException {
        String fileName = "P1ASM.asm";

        // ** Expresiones regulares

        String regexComentario = "^\\s*;.*$";

        // Expresión regular para identificar código de operación y operando
        String regexCodigoOperacionOperando = "^\\s*([Aa.-Zz]+)\\s+(.*?)$";

        // Expresión regular para identificar etiquetas, códigos de operación y operandos
        String regexCodigo = "^\\s*([a-zA-Z0-9]+):\\s*([Aa.-Zz]+)\\s+(.*?)$"; 

        //Expresión regular para identificar solo etiqueta y código de operación
        String regexEtiquetaCodigoOperacion = "^\\s*([a-zA-Z0-9]+):\\s*([Aa.-Zz]+)";

        //Un solo codigo de operacion
        String regexCodigoOperacion = "^\\s*([Aa.-Zz]+)";

        // Compilar las expresiones regulares
        Pattern patComentario = Pattern.compile(regexComentario);
        Pattern patCodigo = Pattern.compile(regexCodigo);
        Pattern patCodigoOperacionOperando = Pattern.compile(regexCodigoOperacionOperando);
        Pattern patCodigoOperacion = Pattern.compile(regexCodigoOperacion);
        Pattern patEtiquetaCodigoOperacion = Pattern.compile(regexEtiquetaCodigoOperacion);

        /*
        br sirve para leer el contenido del archivo y el while es para leerlo todo usando br.readLIne() y almacenamos cada linea en la variable linea */
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            System.out.println("Contenido original del archivo:");
            while ((linea = br.readLine()) != null){
                Matcher matcherComentario = patComentario.matcher(linea);
                Matcher matcherCodigo = patCodigo.matcher(linea);
                Matcher matcherCodigoOperacionOperando = patCodigoOperacionOperando.matcher(linea);
                Matcher matcherCodigoOperacion= patCodigoOperacion.matcher(linea);
                Matcher matcherEtiquetaCodigoOperacion = patEtiquetaCodigoOperacion.matcher(linea);
                
                //String etiqueta=null, codigoOperacion=null, operando=null;
    
                if (matcherComentario.matches()) {
                    if (linea.length () > 80){
                        System.out.println("Exceso de caracteres en: "+linea);
                    } else {
                    // Es un comentario
                        System.out.println("Comentario: " + linea);
                    }
                } else if (matcherCodigo.matches()) {
                    // Es una línea de código con etiqueta, código de operación y operando
                    String etiqueta = matcherCodigo.group(1);
                    String codigoOperacion = matcherCodigo.group(2);
                    String operando = matcherCodigo.group(3);
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                        if(etiqueta.length()>8 || codigoOperacion.length()>5){
                            System.out.println("Exceso de caracteres en: "+linea);
                        }else{
                            instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0));}
                    
                } else if(matcherCodigoOperacionOperando.matches()) {
                    // Es una línea con código de operación y operando
                    String etiqueta = "-";
                    String codigoOperacion = matcherCodigoOperacionOperando.group(1);
                    String operando = matcherCodigoOperacionOperando.group(2);
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                    if(codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                    instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0));}
                    
                } else if(matcherCodigoOperacion.matches()) {
                    // Es una línea con código de operación y operando
                    String etiqueta = "-";
                    String codigoOperacion = matcherCodigoOperacion.group(1);
                    String operando = "-";
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                    if(codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                    instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0));}
                    
                }else if(matcherEtiquetaCodigoOperacion.matches()){
                    String etiqueta = matcherEtiquetaCodigoOperacion.group(1);
                    String codigoOperacion = matcherEtiquetaCodigoOperacion.group(2);
                    String operando = "-";
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                    if(etiqueta.length()>8 || codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                        instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0));}
                    } else {
                    // No coincide con ninguna de las expresiones regulares
                    System.out.println("Error de Sintaxis: " + linea);
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ETIQUETA CODOP OPERANDO KEY PESO ");
       mostrarArray(); 
    }
    
    static void mostrarArray(){
    for(int i=0; i<=instruccion.size()-1; i++){
        contador = i;
        System.out.print(instruccion.get(i).getEtiqueta()+"  "+instruccion.get(i).getCodop()+"  "+instruccion.get(i).getOperando() + "  ");
        notacion(instruccion.get(i).getCodop(), instruccion.get(i).getOperando());
        System.out.println("");
        System.out.println("----------------------");
        }
    }
    
    static void notacion(String codop, String notacion){
        String regexOpri="^#[@%\\$?][0-9A-F]+$|^#[0-9]+$";
        String regexOpra="^[@%\\$?][0-9A-F]+$|^[0-9]+$"; // \\$?
        String regexRel="^[a-zA-Z0-9]+$|^[ABDXY],[a-zA-Z0-9]+$|^[SP],[a-zA-Z0-9]+$";
        String regexOprx="^[-]*[@%\\$?$]*[0-9A-F]+,(X|Y|SP|PC)$|^[@%\\$?$]*[0-9A-F]+,[+-]*(X|Y|SP|PC)[+-]*$|^[ABD]+,(X|Y|SP|PC)$|^(\\[?[@%\\$?]*[0-9A-F]+,(X|Y|SP|PC))\\]?$|^(\\[?[Dd],(X|Y|SP|PC))\\]?|^,(X|Y|SP|PC)$";
        
         Pattern patOpri = Pattern.compile(regexOpri);
         Pattern patOpra = Pattern.compile(regexOpra);
         Pattern patRel = Pattern.compile(regexRel);
         Pattern patOprx = Pattern.compile(regexOprx);
         
         Matcher matcherOpri = patOpri.matcher(notacion);
         Matcher matcherOpra = patOpra.matcher(notacion);
         Matcher matcherRel = patRel.matcher(notacion);
         Matcher matcherOprx = patOprx.matcher(notacion);
         
         if(matcherOpri.matches()){
             char tem = notacion.charAt(1);//Crea un caracter para comparar
             if(Character.toString(tem).matches("%")){
                 String binario = notacion.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=255){
                     String key = "#opr8i";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     System.out.print(key + "  ");
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//Fin del binario
             }else if(Character.toString(tem).matches("@")){
                 String octal = notacion.substring(2);
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=255){
                     String key = "#opr8i";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     System.out.print(key + "  ");
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//in del octal
             }else if(Character.toString(tem).equals("$")){
                 String hexa = notacion.substring(2);
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=255){
                     String key = "#opr8i";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//Fin del octal
                }else{
                 String dec = notacion.substring(1);
                 int tamaño = Integer.parseInt(dec);
                 if(tamaño<=255){
                     String key = "#opr8i";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }
            }else if(matcherOpra.matches()){
            char tem = notacion.charAt(0);//Crea un caracter para comparar
             if(Character.toString(tem).matches("%")){
                 String binario = notacion.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=255){
                     String key = "opr8a";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     System.out.print(key + "  ");
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     System.out.print(key + "  ");
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//Fin del binario
             }else if(Character.toString(tem).matches("@")){
                 String octal = notacion.substring(1);
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=255){
                     String key = "opr8a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//in del octal
             }else if(Character.toString(tem).equals("$")){
                 String hexa = notacion.substring(1);
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=255){
                     String key = "opr8a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//Fin del octal
                }else{
                 int tamaño = Integer.parseInt(notacion);
                 if(tamaño<=255){
                     String key = "opr8a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }
            }else if(matcherRel.matches()){
               char tem = codop.charAt(0);
               char temp2 = notacion.charAt(0);
               if(Character.toString(tem).equals("B")){
                   String key = "rel8";
                   System.out.print(key + "  ");
                   comparadorExcel(instruccion.get(contador).getCodop(), key);
               }else if(Character.toString(tem).equals("L")){
                   String key = "rel16";
                   System.out.print(key + "  ");
                   comparadorExcel(instruccion.get(contador).getCodop(), key);
               }else if(Character.toString(temp2).matches("[A | B | D | X | Y | SP]")){
                   String key = "abdxys,rel9";
                   System.out.print(key + "  ");
                   comparadorExcel(instruccion.get(contador).getCodop(), key);
               }else{
                   String key = "Error";
                   System.out.print(key + "  ");
               }
            }else if(matcherOprx.matches()){
                char tem = notacion.charAt(0);
                char temp2 = notacion.charAt(1);
                if(Character.toString(tem).matches(", | [ABD]")){
                    String key = "oprx0_xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(Character.toString(tem).matches("\\[?")){
                    if(Character.toString(temp2).matches("D")){
                        String key = "[D,xysp]";
                        System.out.print(key + "  ");
                        comparadorExcel(instruccion.get(contador).getCodop(), key);
                    }else{
                        String key = "[oprx16,xysp]";
                        System.out.print(key + "  ");
                        comparadorExcel(instruccion.get(contador).getCodop(), key);
                    }
                }else if(Character.toString(tem).equals("-")){
                    separarOperandos(notacion);
                }else if(Character.toString(tem).matches("[0-9]")){
                    separarOperandos(notacion);
                }else if(Character.toString(tem).matches("[@%\\$?]")){
                    separarOperandos(notacion);
                }else{
                    System.out.println("Instruccion no valida");
                }
            }else{
                char tem = notacion.charAt(0);
                if(notacion.length() == 1 && Character.toString(tem).equals("-")){
                    String key = "-";
                    System.out.print(key + "  ");
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                    }
            }
    } 
    
    static void separarOperandos(String operandos){
        //String operandosOriginal = operandos;
        String[] aOperandos = operandos.split(",");
        String operando1 = aOperandos[0];
        String operando2 = aOperandos[1];
        
        int idx = operando2.length()-1;
        //Comparadores temporales que sirven para encontrar los símbolos de "+-" en un operando
        char tem = operando1.charAt(0);
        char temp3 = operando2.charAt(0);
        char temp4 = operando2.charAt(idx);
        
        if(Character.toString(tem).equals("-")||Character.toString(temp3).equals("-")||Character.toString(temp4).equals("-")){
              char temp2 = operando1.charAt(1);
             if(Character.toString(temp2).matches("%")){
                 String binario = operando1.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("@")){
                 String octal = operando1.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                System.out.print(key + "  ");
                }
            }else if(Character.toString(temp2).equals("$")){
                 String hexa = operando1.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                System.out.print(key + "  ");
                }
            }else if(Character.toString(temp2).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                    System.out.print(key + "  ");                
                }
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                }
        }else{
           if(Character.toString(tem).matches("%")){
                 String binario = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(tem).matches("@")){
                 String octal = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";}
            }else if(Character.toString(tem).equals("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                System.out.print(key + "  ");}
                 
            }else if(Character.toString(tem).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                }else{//Valor no valido
                     String key = "Error";
                System.out.print(key + "  ");}
                }else{
                    String key = "Error";
                    System.out.print(" " + key + "  ");
                    System.out.println("Instruccion no valida");
                }
            }
        }//Fin del método separar operandos
    
    static void comparadorExcel(String codop, String key) {
        try {
            // Ruta del archivo Excel
            String archivoExcel = "Salvation.Tabop.xlsx";

            // Crear un flujo de entrada para el archivo Excel
            FileInputStream fis = new FileInputStream(new File(archivoExcel));

            // Crear un libro de trabajo (workbook) a partir del archivo Excel
            Workbook workbook = WorkbookFactory.create(fis);

            // Obtener la hoja de Excel (supongamos que es la primera hoja)
            Sheet sheet = workbook.getSheetAt(1);
            
            // Recorrer las filas de la hoja
            for (Row row : sheet) {
                // Suponemos que las columnas de código de operación están en la columna 1 (cero indexado)
                Cell codopCell = row.getCell(0);
                String codopEnFila = codopCell.getStringCellValue(); // Asumiendo que es una cadena

                // Suponemos que las columnas de operandos están en la columna 2
                Cell keyCell = row.getCell(1);
                String keyEnFila = keyCell.getStringCellValue();

                // Compara codop y key con los valores deseados
                if (codop.equals(codopEnFila) && key.equals(keyEnFila)) {
                    // Suponemos que el peso total en bytes está en la columna 6
                    Cell pesoTotalCell = row.getCell(6);
                    Cell addrCell = row.getCell(3);
                    double pesoTotal = pesoTotalCell.getNumericCellValue();
                    System.out.print(" " + pesoTotal + "  ");
                    System.out.print(" " + addrCell + "  ");
                    // Puedes almacenar este valor o hacer lo que necesites con él
                    break; // Puedes romper el bucle una vez que encuentres la coincidencia deseada
                }
            } 
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
