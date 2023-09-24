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
import java.util.Iterator;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExcelRead {

    public static void main(String[] args) {
        try {
            // Ruta al archivo de entrada Excel
            String inputFile = "mi_archivo.xlsx";
            // Ruta al archivo de salida Excel para documentación
            String outputFile = "output.xlsx";

            FileInputStream fileInputStream = new FileInputStream(new File(inputFile));
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Crea un nuevo archivo Excel para la documentación
            XSSFWorkbook documentationWorkbook = new XSSFWorkbook();
            XSSFSheet documentationSheet = documentationWorkbook.createSheet("Documentación");

            int rowIndex = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                XSSFRow documentationRow = documentationSheet.createRow(rowIndex);

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    XSSFCell documentationCell = documentationRow.createCell(cellIndex);

                    // Copiar el valor de la celda del archivo de entrada al archivo de documentación
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            documentationCell.setCellValue(cell.getNumericCellValue());
                            break;
                        case STRING:
                            documentationCell.setCellValue(cell.getStringCellValue());
                            break;
                        // Añadir más casos según sea necesario para otros tipos de celdas
                    }

                    // Si deseas aplicar un formato específico a las celdas en el archivo de documentación, puedes hacerlo aquí.
                    // Por ejemplo, para cambiar el formato de las celdas a negrita:
                    XSSFCellStyle style = documentationWorkbook.createCellStyle();
                    XSSFFont font = documentationWorkbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    documentationCell.setCellStyle(style);

                    cellIndex++;
                }

                rowIndex++;
            }

            // Guarda el archivo de documentación
            FileOutputStream documentationOutputStream = new FileOutputStream(outputFile);
            documentationWorkbook.write(documentationOutputStream);
            documentationOutputStream.close();

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
