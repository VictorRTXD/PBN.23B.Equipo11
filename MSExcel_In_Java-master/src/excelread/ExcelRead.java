/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelread;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFFont;
import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class ExcelRead {
    static ArrayList<LineaInstruccion> instruccion = new ArrayList<LineaInstruccion>();

    public static void main(String[] args) {
        try {
            //estructura de los 2 archivos
            FileInputStream archivo1 = new FileInputStream("archivo.xlsx"); // Reemplaza con la ruta de tu archivo Excel
            FileInputStream archivo2 = new FileInputStream("Salvation.Tabop.xlsx");
            
            Workbook libro1 = new XSSFWorkbook(archivo1);
            Workbook libro2 = new XSSFWorkbook(archivo2);
            Sheet sheet1 = libro1.getSheetAt(0);
            Sheet sheet2 = libro2.getSheetAt(1);
            
            StringBuilder excelData1 = new StringBuilder();
            StringBuilder excelData2 = new StringBuilder();
            
            String excel1 = "";
            String excel2 = "";
            
            //lectura primer excel
            excel1 = lecturaExcel1(sheet1, excelData1, excel1);
            System.out.println(excel1);

            //lectura segundo excel
            excel2 = lecturaExcel1(sheet2, excelData2, excel2);
            //System.out.println(excel2);
            
           // ** chac
        String fileName = "archivo.xlsx";

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
        /*try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
                        System.out.println("");}
                } else if (matcherCodigo.matches()) {
                    // Es una línea de código con etiqueta, código de operación y operando
                    String etiqueta = matcherCodigo.group(1);
                    String codigoOperacion = matcherCodigo.group(2);
                    String operando = matcherCodigo.group(3);
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                        if(etiqueta.length()>8 || codigoOperacion.length()>5){
                            System.out.println("Exceso de caracteres en: "+linea);
                        }else{
                            instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    
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
                    instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    
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
                    instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    
                }else if(matcherEtiquetaCodigoOperacion.matches()){
                    String etiqueta = matcherEtiquetaCodigoOperacion.group(1);
                    String codigoOperacion = matcherEtiquetaCodigoOperacion.group(2);
                    String operando = "-";
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                    if(etiqueta.length()>8 || codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                        instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    } else {
                    // No coincide con ninguna de las expresiones regulares
                    System.out.println("Error de Sintaxis: " + linea);
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        
        String[] lineas = excel1.split("\n");
        for (String linea : lineas) {
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
                        System.out.println("");}
                } else if (matcherCodigo.matches()) {
                    // Es una línea de código con etiqueta, código de operación y operando
                    String etiqueta = matcherCodigo.group(1);
                    String codigoOperacion = matcherCodigo.group(2);
                    String operando = matcherCodigo.group(3);
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                        if(etiqueta.length()>8 || codigoOperacion.length()>5){
                            System.out.println("Exceso de caracteres en: "+linea);
                        }else{
                            instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    
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
                    instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    
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
                    instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    
                }else if(matcherEtiquetaCodigoOperacion.matches()){
                    String etiqueta = matcherEtiquetaCodigoOperacion.group(1);
                    String codigoOperacion = matcherEtiquetaCodigoOperacion.group(2);
                    String operando = "-";
                    //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                    if(etiqueta.length()>8 || codigoOperacion.length()>5){
                        System.out.println("Exceso de caracteres en: "+linea);
                        System.out.println("");
                    }else{
                        instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));}
                    } else {
                    // No coincide con ninguna de las expresiones regulares
                    System.out.println("Error de Sintaxis: " + linea);
                    System.out.println("");
                }
        }
       mostrarArray(); 
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // fin main
    
    static void mostrarArray(){
    for(int i=0; i<=instruccion.size()-1; i++){
        System.out.println(instruccion.get(i).getEtiqueta()+" "+instruccion.get(i).getCodop()+" "+instruccion.get(i).getOperando());
        notacion(instruccion.get(i).getCodop(), instruccion.get(i).getOperando());
        //separarOperandos(instruccion.get(i).getOperando());
        }
    
     for(int i=0; i<=instruccion.size()-1; i++){
        System.out.println(instruccion.get(i).getEtiqueta()+" "+instruccion.get(i).getCodop()+" "+instruccion.get(i).getOperando());
        notacion(instruccion.get(i).getCodop(), instruccion.get(i).getOperando());
        //separarOperandos(instruccion.get(i).getOperando());
        }
    }
    
    
    
    static void notacion(String codop, String notacion){
        String regexOpri="^#[@%$][0-9]+$|^[0-9]+$";
        String regexOpra="^[@%$][0-9]+$|^[0-9]+$";
        String regexRel="^[a-zA-Z0-9]+$|^[ABDXY | SP],[A-Za-z0-9]+$";
        String regexOprx="^[-]*[@%$]*[0-9]+,(X|Y|SP|PC)$|^[0-9]+,[+-]*(X|Y|SP|PC)[+-]*$|^[AaBbDd]+,(X|Y|SP|PC)$|^(\\[?[@%$]*[0-9]+,(X|Y|SP|PC))\\]?$|^(\\[?[Dd],(X|Y|SP|PC))\\]?|^,(X|Y|SP|PC)$";
        
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
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                }else{//Valor no valido
                     String key = "Error";
                 }//Fin del binario
             }else if(Character.toString(tem).matches("@")){
                 String octal = notacion.substring(2);
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=255){
                     String key = "#opr8i";
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                }else{//Valor no valido
                     String key = "Error";
                 }//in del octal
             }else if(Character.toString(tem).matches("$")){
                 String hexa = notacion.substring(2);
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=255){
                     String key = "#opr8i";
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                }else{//Valor no valido
                     String key = "Error";
                 }//Fin del octal
                }else{
                 int tamaño = Integer.parseInt(notacion);
                 if(tamaño<=255){
                     String key = "#opr8i";
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                }else{//Valor no valido
                     String key = "Error";
                 }
                }
            }else if(matcherOpra.matches()){
            char tem = notacion.charAt(0);//Crea un caracter para comparar
             if(Character.toString(tem).matches("%")){
                 String binario = notacion.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=255){
                     String key = "opr8a";
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                }else{//Valor no valido
                     String key = "Error";
                 }//Fin del binario
             }else if(Character.toString(tem).matches("@")){
                 String octal = notacion.substring(1);
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=255){
                     String key = "opr8a";
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                }else{//Valor no valido
                     String key = "Error";
                 }//in del octal
             }else if(Character.toString(tem).matches("$")){
                 String hexa = notacion.substring(1);
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=255){
                     String key = "opr8a";
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                }else{//Valor no valido
                     String key = "Error";
                 }//Fin del octal
                }else{
                 int tamaño = Integer.parseInt(notacion);
                 if(tamaño<=255){
                     String key = "opr8a";
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                }else{//Valor no valido
                     String key = "Error";
                 }
                }
            }else if(matcherRel.matches()){
               char tem = codop.charAt(0);
               char temp2 = notacion.charAt(0);
               if(Character.toString(tem).matches("B")){
                   String key = "rel8";
               }else if(Character.toString(tem).matches("L")){
                   String key = "rel16";
               }else if(Character.toString(temp2).matches("A | B | D | X | Y | SP")){
                   String key = "abdxys,rel9";
               }else{
                   String key = "Error";
               }
            }else if(matcherOprx.matches()){
                char tem = notacion.charAt(0);
                char temp2 = notacion.charAt(1);
                if(Character.toString(tem).matches(", | [ABD]")){
                    String key = "oprx0_xysp";
                }else if(Character.toString(tem).matches("\\[?")){
                    if(Character.toString(temp2).matches("D")){
                        String key = "[D,xysp]";
                    }else{
                        String key = "[oprx16,xysp]";
                    }
                }else if(Character.toString(tem).matches("- | [@%$] | [0-9]")){
                    separarOperandos(notacion);
                }
            }
    }//Fin del metodo notación
    
    static void separarOperandos(String operandos){
        //String operandosOriginal = operandos;
        String[] aOperandos = operandos.split(",");
        String operando1 = aOperandos[0];
        String operando2 = aOperandos[1];
        
        //Comparadores temporales que sirven para encontrar los símbolos de "+-" en un operando
        char tem = operando1.charAt(0);
        char temp2 = operando1.charAt(1);
        char temp3 = operando2.charAt(0);
        char temp4 = operando2.charAt(2);
        char temp5 = operando2.charAt(3);
        
        if(Character.toString(tem).matches("-")||Character.toString(temp3).matches("-")||Character.toString(temp4).matches("-")||Character.toString(temp5).matches("-")){
             if(Character.toString(temp2).matches("%")){
                 String binario = operando1.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";
                 }
            }else if(Character.toString(temp2).matches("@")){
                 String octal = operando1.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";}
            }else if(Character.toString(temp2).matches("$")){
                 String hexa = operando1.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";}
            }else if(Character.toString(temp2).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";}
                }else{
                    String key = "Error";
                }
        }else{
           if(Character.toString(tem).matches("%")){
                 String binario = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";
                 }
            }else if(Character.toString(tem).matches("@")){
                 String octal = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";}
            }else if(Character.toString(tem).matches("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";}
            }else if(Character.toString(tem).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                }else{//Valor no valido
                     String key = "Error";}
                }else{
                    String key = "Error";
                }
            }
        }//Fin del método separar operandos
    
    static String lecturaExcel1(Sheet sheet, StringBuilder excelData, String excel) {
        for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum() - 1; rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                        Cell cell = row.getCell(columnIndex);
                        if (cell != null) {
                            excelData.append(cell.toString()).append("\t");
                        }
                    }
                    excelData.append("\n"); // Salto de línea después de cada fila
                }
            }
                return excelData.toString();
    }
}