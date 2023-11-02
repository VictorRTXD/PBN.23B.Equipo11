/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excelread;

import static excelread.ExcelRead.destinoRel;
import static excelread.ExcelRead.instruccion;
import static excelread.ExcelRead.origenRel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author macia
 */
public class test {
    static ArrayList<LineaInstruccion> instruccion2 = new ArrayList<LineaInstruccion>();
    static ArrayList<LineaInstruccion> origenRel = new ArrayList<LineaInstruccion>();
    static ArrayList<LineaInstruccion> destinoRel = new ArrayList<LineaInstruccion>();
    
    public static void main(String[] args) {
        instruccion2.add(new LineaInstruccion("dos", "BCLR", "$0F30,#45", "EXT", 4, "4046", "1D hh ll mm", "0")); //des
        instruccion2.add(new LineaInstruccion("-", "IBNE", "A,dos", "REL", 3, "404E", "04 lb rr", "0")); //ori
        instruccion2.add(new LineaInstruccion("-", "EORA", "6444,X", "IDX2", 4, "4051", "18 2D qq rr", "0")); 
        instruccion2.add(new LineaInstruccion("-", "END", "-", "-", 0, "4055", "-", "-")); //ori
        
        origenRel.add(new LineaInstruccion("-", "IBNE", "A,dos", "REL", 3, "404E", "04 lb rr", "0"));
        
        destinoRel.add(new LineaInstruccion("dos", "BCLR", "0F30,#45", "EXT", 4, "4046", "1D hh ll mm", "0"));
        
        calcularRel();
        
        System.out.println("*******************************************");
        System.out.println(instruccion2.get(0).postByte);
        System.out.println(instruccion2.get(1).postByte);
        System.out.println(instruccion2.get(2).postByte);
        System.out.println(instruccion2.get(3).postByte);
    }
    
    public static int complementoDos(int destino, int origen) {
        int suma = destino + (~origen + 1); // Realizar la resta en complemento a dos
        return suma & 0xFFFF; // Aplicar la máscara para asegurar que el resultado sea de 4 bytes
    }
    
    public static void calcularDesplazamiento(int valorOri, int valorDes, String resultadoHex) {
        if (valorOri < valorDes) { // si Destino es mayor que el oRIGEN
            resultadoHex = Integer.toHexString(valorDes - valorOri);
        } else if (valorOri > valorDes) { // origen es mayor que el destino
            int resultadoDecimal = complementoDos(valorDes, valorOri);

            resultadoHex = Integer.toHexString(resultadoDecimal);

           System.out.println("Resultado en hexadecimal: " + resultadoHex);
        } else { // si son iguales
            resultadoHex = "0000";
        }
    }
    
    static void calcularRel() {
        for (int i = 0; i < origenRel.size(); i++) {
            for (int j = 0; j < destinoRel.size(); j++) { //fors para comparar el contloc origen y destino
                int valorOri = Integer.parseInt(origenRel.get(i).contloc, 16);
                int valorDes = Integer.parseInt(destinoRel.get(j).contloc, 16);
                String resultadoHex = "";
                String registro = "";
                String operando = "";
                
                // Divide el el operando de origen con registro y etiqueta
                if (origenRel.get(i).operando.contains(",")) {
                    String[] partes = origenRel.get(i).operando.split(",");
                    registro = partes[0];
                    operando = partes[1];
                }
                
                if (origenRel.get(i).operando.equals(destinoRel.get(j).etiqueta)) { // si el operando origen y la etiqueta destino son iguales 
                    if (valorOri < valorDes) { // si Destino es mayor que el oRIGEN
                        resultadoHex = Integer.toHexString(valorDes - valorOri);
                    } else if (valorOri > valorDes) { // origen es mayor que el destino
                        int resultadoDecimal = complementoDos(valorDes, valorOri);

                        resultadoHex = Integer.toHexString(resultadoDecimal);

                        System.out.println("Resultado en hexadecimal: " + resultadoHex);
                    } else { // si son iguales
                        resultadoHex = "0000";
                    }
                    
                    colocarPostByteRelativo(i, resultadoHex, ""); //se va a la funcion para usarlo con su postbyte
                } else if (destinoRel.get(j).etiqueta.equals(operando)){ // si tiene coma y el operando origen y la etiqueta de destino son iguales
                    System.out.println("pig daddy");
                    String desplazamiento = "";
                    String lb = "";
                    System.out.println(origenRel.get(i).contloc);
                    
                    if (valorOri < valorDes) { // si Destino es mayor que el oRIGEN
                        desplazamiento = "Positivo";
                        lb = calcularLb(registro, i, desplazamiento);
                        System.out.println("soy lb" + lb);
                        
                        resultadoHex = Integer.toHexString(valorDes - valorOri);
                    } else if (valorOri > valorDes) { // origen es mayor que el destino
                        desplazamiento = "Negativo";
                        lb = calcularLb(registro, i, desplazamiento);
                        System.out.println("soy lb" + lb);
                        
                        int resultadoDecimal = complementoDos(valorDes, valorOri);
                        resultadoHex = Integer.toHexString(resultadoDecimal);
                    } else { // si son iguales
                        resultadoHex = "0000";
                    }
                    
                    colocarPostByteRelativo(i, resultadoHex, lb); //se va a la funcion para usarlo con su postbyte
                }
            }
        } 
    }
    
    static void colocarPostByteRelativo(int indiceOrigen, String resultado, String lb) {
        int i;
        System.out.println(resultado);
        for (i = 1; i < instruccion2.size(); i++) {
            if (instruccion2.get(i-1).contloc.equals(origenRel.get(indiceOrigen).contloc)) { // se planea recorrer instruccion donde coincide con contloc origen
                int auxiliarPeso = (int) instruccion2.get(i-1).peso; // Actualiza el atributo del elemento encontrado.
                
                if (auxiliarPeso == 2) {
                    resultado = resultado.substring(2, 4);
                    funcionCortadora(i, resultado, auxiliarPeso, lb);
                } else if (auxiliarPeso == 4) { // es de 16 bits
                    funcionCortadora(i, resultado, auxiliarPeso, lb);
                } else if (auxiliarPeso == 3) {
                    resultado = resultado.substring(2, 4);
                    funcionCortadora(i, resultado, auxiliarPeso, lb);
                }
                break; // Puedes detener el bucle una vez que encuentres el elemento.
            }
        }
    }
    
    static void funcionCortadora(int i, String resultado, int auxiliarPeso, String lb) { //
        String firstThreeCharacters = "";
        System.out.println(auxiliarPeso);
        if (auxiliarPeso == 4) {
            firstThreeCharacters = instruccion2.get(i-1).forma.substring(0, 6);
            String modifiedString = firstThreeCharacters.concat(resultado);
            System.out.println("es " + modifiedString);
            instruccion2.get(i-1).postByte =  modifiedString;
        }    
        else if (auxiliarPeso == 2) {
            firstThreeCharacters = instruccion2.get(i-1).forma.substring(0, 3);
            String modifiedString = firstThreeCharacters.concat(resultado);
            System.out.println("es " + modifiedString);
            instruccion2.get(i-1).postByte =  modifiedString;
        }   
        else if (auxiliarPeso == 3) {
            System.out.println("ek esperado");
            firstThreeCharacters = instruccion2.get(i-1).forma.substring(0, 3);
            firstThreeCharacters += lb + resultado;
            instruccion2.get(i-1).postByte = firstThreeCharacters;
        }  
    }
    
    static String calcularLb(String registro, int indexOrigen, String desplazamiento) { // recuerda modificarla porque inicialmente era para xb
        try {
            // Ruta del archivo Excel
            String archivoExcel = "Salvation.Tabop.xlsx";

            // Crear un flujo de entrada para el archivo Excel
            FileInputStream fis = new FileInputStream(new File(archivoExcel));

            // Crear un libro de trabajo (workbook) a partir del archivo Excel
            Workbook workbook = WorkbookFactory.create(fis);

            // Obtener la hoja de Excel (supongamos que es la primera hoja)
            Sheet sheet = workbook.getSheetAt(4);
            
            // Recorrer las filas de la hoja
            for (Row row : sheet) {
                Cell codopCell = row.getCell(0);
                Cell registroExcel = row.getCell(1);
                Cell desplazamientoExcel = row.getCell(2);

                String codopEnFila = codopCell.getStringCellValue();
                String registroEnFila = registroExcel.getStringCellValue();
                String desplazamientoEnFila = desplazamientoExcel.getStringCellValue();

                if (codopEnFila.equals(origenRel.get(indexOrigen).codop) && registroEnFila.equals(registro) && desplazamientoEnFila.equals(desplazamiento)){ // compara con excel con tamano y operando
                    Cell lbCell = row.getCell(3);
                    return lbCell.toString();
                }
            } 
          } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
/*
    
    static void calcularLb(String temp1, String temp2, String valor, LineaInstruccion actual) { // recuerda modificarla porque inicialmente era para xb
        String operando = temp1 + ',' + temp2;
        Integer validador = Integer.parseInt(temp1);
        String size = "";
        try {
            // Ruta del archivo Excel
            String archivoExcel = "Salvation.Tabop.xlsx";

            // Crear un flujo de entrada para el archivo Excel
            FileInputStream fis = new FileInputStream(new File(archivoExcel));

            // Crear un libro de trabajo (workbook) a partir del archivo Excel
            Workbook workbook = WorkbookFactory.create(fis);

            // Obtener la hoja de Excel (supongamos que es la primera hoja)
            Sheet sheet = workbook.getSheetAt(2);
            
            // Recorrer las filas de la hoja
            for (Row row : sheet) {
                Cell operandoCell = row.getCell(0);
                Cell sizeExcel = row.getCell(1);
                Cell xb = row.getCell(2);

                String operandoEnFila = operandoCell.getStringCellValue();
                String sizeEnFila = sizeExcel.getStringCellValue();
                String xbEnFila = xb.getStringCellValue();

                // Compara codop y key con los valores deseados
                if (validador < -16 || validador > 15) { // verificar si es n
                    if (temp1.contains("-")) //compara signo
                        operando = "-n" + ',' + temp2;
                    else
                        operando = "n" + ',' + temp2;

                    if (actual.key.contains("oprx9,xysp")) //compara tamano
                        size = "9b const";
                    else if (actual.key.contains("oprx16,xysp"))
                        size = "16b const";

                    if (operandoEnFila.equals(operando) && sizeEnFila.equals(size)){ // compara con excel con tamano y operando
                        System.out.print(xbEnFila + "  ");
                        System.out.println(Integer.toHexString(Integer.parseInt(temp1)));
                        System.out.println("");
                        break; 
                    }
                } else if (operandoEnFila.equals(operando)){ // compara con operando
                    System.out.println(xbEnFila);
                    System.out.println(Integer.toHexString(Integer.parseInt(temp1)));
                    break; 
                } 
            } 
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    */