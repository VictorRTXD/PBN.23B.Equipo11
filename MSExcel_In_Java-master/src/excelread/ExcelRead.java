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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelRead {

    public static void main(String[] args) {
        try {
            
            String fileName = "P1ASM.asm";
            XSSFWorkbook workbook = new XSSFWorkbook();

            // Crear una hoja en el libro
            XSSFSheet sheet = workbook.createSheet("Datos Procesados");

            // Crear una fila para encabezados
            XSSFRow headerRow = sheet.createRow(0);
            XSSFCell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("ETQ");
            XSSFCell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("CODOP");
            XSSFCell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("OPR");
            XSSFCell headerCell4 = headerRow.createCell(3);
            headerCell4.setCellValue("ADDR");
            XSSFCell headerCell5 = headerRow.createCell(4);
            headerCell5.setCellValue("SIZE");
            
            
            int rowNum = 1;
            
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
                while ((linea = br.readLine()) != null) {
                    Matcher matcherComentario = patComentario.matcher(linea);
                    Matcher matcherCodigo = patCodigo.matcher(linea);
                    Matcher matcherCodigoOperacionOperando = patCodigoOperacionOperando.matcher(linea);
                    Matcher matcherCodigoOperacion= patCodigoOperacion.matcher(linea);
                    Matcher matcherEtiquetaCodigoOperacion = patEtiquetaCodigoOperacion.matcher(linea);

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
                                System.out.println("");
                            //estructura excel
                            
                            rowNum = excelRows(sheet, rowNum,  etiqueta, codigoOperacion, operando);
                            }

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
                        System.out.println("");
                        rowNum = excelRows(sheet, rowNum,  etiqueta, codigoOperacion, operando);
                        }

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
                        System.out.println("");
                        rowNum = excelRows(sheet, rowNum,  etiqueta, codigoOperacion, operando);
                        }

                    }else if(matcherEtiquetaCodigoOperacion.matches()){
                        String etiqueta = matcherEtiquetaCodigoOperacion.group(1);
                        String codigoOperacion = matcherEtiquetaCodigoOperacion.group(2);
                        String operando = null;
                        if(etiqueta.length()>8 || codigoOperacion.length()>5){
                            System.out.println("Exceso de caracteres en: "+linea);
                            System.out.println("");
                        }else{
                            System.out.println("Etiqueta: "+etiqueta);
                            System.out.println("Código de Operación: " + codigoOperacion);
                            System.out.println("Operando/s: "+operando);
                            System.out.println("");
                        rowNum = excelRows(sheet, rowNum,  etiqueta, codigoOperacion, operando);
                        }
                        } else {
                        // No coincide con ninguna de las expresiones regulares
                        System.out.println("Error de Sintaxis: " + linea);
                        System.out.println("");
                    }
                }
                try (FileOutputStream fileOut = new FileOutputStream("datos_procesados.xlsx")) {
                workbook.write(fileOut);
                }
                // Cerrar el libro
                workbook.close();
                System.out.println("Datos procesados guardados en datos_procesados.xlsx");
            
                
            }  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        static int excelRows(XSSFSheet sheet, int rowNum, String etiqueta, String codigoOperacion, String operando) {
        rowNum = rowNum + 1;
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell1 = row.createCell(0);
        cell1.setCellValue(etiqueta);
        XSSFCell cell2 = row.createCell(1);
        cell2.setCellValue(codigoOperacion);
        XSSFCell cell3 = row.createCell(2);
        cell3.setCellValue(operando); 
        //XSSFCell cell4 = row.createCell(3);
        //cell4.setCellValue(addr);
        //XSSFCell cell5 = row.createCell(4);
        //cell5.setCellValue(bytes);
        
        return rowNum;
    }
}
