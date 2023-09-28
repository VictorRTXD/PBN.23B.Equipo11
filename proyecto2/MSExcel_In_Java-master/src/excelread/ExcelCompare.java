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

public class ExcelCompare {

    public static void main(String[] args) {
        try {
            //estructura de los 2 archivos
            FileInputStream archivo1 = new FileInputStream("archivo.xlsx"); // Reemplaza con la ruta de tu archivo Excel
            FileInputStream archivo2 = new FileInputStream("Salvation.Tabop.xlsx");
            
            Workbook libro1 = new XSSFWorkbook(archivo1);
            Workbook libro2 = new XSSFWorkbook(archivo2);
            
            Sheet sheet1 = libro1.getSheetAt(0);
            Sheet sheet2 = libro2.getSheetAt(1);
            // Obtener el número de filas en cada hoja
            int file1RC = sheet1.getPhysicalNumberOfRows();
            int file2RC = sheet2.getPhysicalNumberOfRows();
            
            StringBuilder excelData1 = new StringBuilder();
            StringBuilder excelData2 = new StringBuilder();
            
            int columnaDeseada = 1; // Cambia esto al número de columna que necesites
            
            List<String> data1 = readColumnData(sheet1, columnaDeseada);

            // Lee los datos de la primera columna del segundo archivo
            List<String> data2 = readColumnData(sheet2, columnaDeseada);
            
            StringBuilder excelContent = new StringBuilder();
            for (Row row : sheet1) {
                for (Cell cell : row) {
                    // Agrega el contenido de cada celda a la cadena de texto
                    excelContent.append(cell.toString());
                    excelContent.append("\t"); // Agrega un tabulador entre las celdas
                }
                excelContent.append("\n"); // Agrega un salto de línea al final de cada fila
            }
            String excelData = excelContent.toString();

            System.out.println(excelData);

            
            int filaInicio = 3;

            // Itera a través de las filas de ambos archivos
            for (int i = filaInicio; i < sheet1.getPhysicalNumberOfRows(); i++) {
                Row row1 = sheet1.getRow(i);
                String valueToCompare = row1.getCell(columnaDeseada).toString();

                for (Row row2 : sheet2) {
                    String value2 = row2.getCell(columnaDeseada).toString();

                    if (valueToCompare.equals(value2)) {
                        System.out.println("Fila del archivo 1: " + (row1.getRowNum() + 1) + " coincide con fila del archivo 2: " + (row2.getRowNum() + 1));
                        // Aquí puedes agregar la lógica que necesites cuando se encuentre una coincidencia
                    }
                }
            }
            
             // Itera a través de las filas de la hoja
            
           

            
             
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
        
        private static List<String> readColumnData(Sheet sheet, int columnIndex) {
        List<String> data = new ArrayList<>();
        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                data.add(cell.toString());
            }
        }
        return data;
    }

}
