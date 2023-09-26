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
            //estructura de los 2 archivos
            FileInputStream archivo1 = new FileInputStream("archivo.xlsx"); // Reemplaza con la ruta de tu archivo Excel
            FileInputStream archivo2 = new FileInputStream("Salvation.Tabop.xlsx");
            
            Workbook libro1 = new XSSFWorkbook(archivo1);
            Workbook libro2 = new XSSFWorkbook(archivo2);
            Sheet sheet1 = libro1.getSheetAt(0);
            Sheet sheet2 = libro2.getSheetAt(0);
            
            StringBuilder excelData1 = new StringBuilder();
            StringBuilder excelData2 = new StringBuilder();
            
            //lectura primer excel
            for (Row row : sheet1) {
                for (Cell cell : row) {
                    excelData1.append(cell.toString()).append("\t"); // Agrega un tabulador entre las celdas
                }
                excelData1.append("\n"); // Agrega un salto de línea al final de cada fila
            }
            
            libro1.close();
            archivo1.close();

            String excelDataAsString1 = excelData1.toString();
            System.out.println("Contenido del Excel como String:");
            System.out.println(excelDataAsString1);
            
            //lectura segundo excel
            for (Row row : sheet2) {
                for (Cell cell : row) {
                    excelData2.append(cell.toString()).append("\t"); // Agrega un tabulador entre las celdas
                }
                excelData2.append("\n"); // Agrega un salto de línea al final de cada fila
            }
            
            libro2.close();
            archivo2.close();

            String excelDataAsString2 = excelData2.toString();
            System.out.println("Contenido del Excel como String:");
            System.out.println(excelDataAsString2);
            
             
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // funcion para crear nuevas filas de info, 
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
