/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelread;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelRead {
    public static void main(String[] args) {
        try {
            // Crear un nuevo libro de Excel
            Workbook workbook = new XSSFWorkbook();

            // Crear una nueva hoja en el libro
            Sheet sheet = workbook.createSheet("MiHoja");

            // Crear una fila
            Row row = sheet.createRow(0);

            // Crear una celda y establecer su valor
            Cell cell = row.createCell(0);
            cell.setCellValue("Hola, Excel!");

            // Guardar el libro de Excel en un archivo
            FileOutputStream fos = new FileOutputStream("mi_archivo.xlsx");
            workbook.write(fos);
            fos.close();
            System.out.println("Archivo Excel creado exitosamente.");

            // Leer el archivo Excel
            FileInputStream fis = new FileInputStream("mi_archivo.xlsx");
            workbook = new XSSFWorkbook(fis);
            Sheet readSheet = workbook.getSheetAt(0);
            Row readRow = readSheet.getRow(0);
            Cell readCell = readRow.getCell(0);
            System.out.println("Valor leído de Excel: " + readCell.getStringCellValue());

            // Modificar el valor en Excel
            readCell.setCellValue("¡Hola, Excel modificado!");
            fis.close();

            // Guardar los cambios
            FileOutputStream fos2 = new FileOutputStream("mi_archivo_modificado.xlsx");
            workbook.write(fos2);
            fos2.close();
            System.out.println("Archivo Excel modificado y guardado exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
