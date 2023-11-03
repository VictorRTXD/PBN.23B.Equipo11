package excelread;

import org.apache.poi.ss.usermodel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExcelRead extends JFrame{
    static private JTable table;
    static private DefaultTableModel tableModel;

    public ExcelRead() {
        JFrame frame = new JFrame("Ejemplo JTable");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el modelo de la tabla con columnas
        String[] columnNames = {"Etiqueta", "codop", "operando", "key", "peso", "direccionamiento", "postbyte", "contloc"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Crear la tabla con el modelo
        table = new JTable(tableModel);

        // Agregar la tabla a un JScrollPane para hacerla desplazable
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setSize(500, 300);
        frame.setVisible(true);
    }
    
    static ArrayList<LineaInstruccion> instruccion = new ArrayList<LineaInstruccion>();
    static ArrayList<LineaInstruccion> origenRel = new ArrayList<LineaInstruccion>();
    static ArrayList<LineaInstruccion> destinoRel = new ArrayList<LineaInstruccion>();
    
    static int contador = 0;
    public static void main(String[] args) throws IOException {
        String flag = "y";

        while (flag.equals("y")) {
            System.out.println("oprime 0 para default o escribe el nombre del archivo");
            Scanner sc = new Scanner(System.in);
            flag = sc.next();
            String fileName;

            if (flag.equals("0"))
                fileName = "P1ASM.asm";
            else
                fileName = flag;

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
                                instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0, "0", "0", "0"));}
                        
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
                        instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0, "0", "0", "0"));}
                        
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
                        instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0, "0", "0", "0"));}
                        
                    }else if(matcherEtiquetaCodigoOperacion.matches()){
                        String etiqueta = matcherEtiquetaCodigoOperacion.group(1);
                        String codigoOperacion = matcherEtiquetaCodigoOperacion.group(2);
                        String operando = "-";
                        //instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, null));
                        if(etiqueta.length()>8 || codigoOperacion.length()>5){
                            System.out.println("Exceso de caracteres en: "+linea);
                            System.out.println("");
                        }else{
                            instruccion.add(new LineaInstruccion(etiqueta, codigoOperacion, operando, null, 0, "0", "0", "0"));}
                        } else {
                        // No coincide con ninguna de las expresiones regulares
                        System.out.println("Error de Sintaxis: " + linea);
                        System.out.println("");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            System.out.println("ETIQUETA CODOP OPERANDO KEY PESO ADDRESS FORM_POSTBYTE");
            
            mostrarArray(); 
            SwingUtilities.invokeLater(() -> {
                ExcelRead ventana = new ExcelRead();
                ventana.mostrarArray2();
            });
        
            // proyecto 3
            calcularContloc();
            insertarDatosList();
            insertarDatosTabism();
            
            //proyecto 4
            calcularRel();

            System.out.println("quieres continuar? y/n");
            flag = sc.next();
        }
    }
    
    public void mostrarArray2() {
        // Simulación de datos con un bucle for
        for (int i=0; i<=instruccion.size()-1; i++) {
            contador = i;
            
            Object[] rowData = {
                instruccion.get(i).etiqueta,
                instruccion.get(i).codop,
                instruccion.get(i).operando,
                instruccion.get(i).key,
                String.valueOf(instruccion.get(i).peso),
                instruccion.get(i).direc,
                instruccion.get(i).postByte,
                instruccion.get(i).contloc
            };

            tableModel.addRow(rowData);
        }
    }
    
    static void mostrarArray(){
    for(int i=0; i<=instruccion.size()-1; i++){
        contador = i;
        System.out.print(instruccion.get(i).getEtiqueta()+"  "+instruccion.get(i).getCodop()+"  "+instruccion.get(i).getOperando()+ "  " + " " + instruccion.get(i).contloc);
        notacion(instruccion.get(i).getCodop(), instruccion.get(i).getOperando());
        System.out.println("");
        }
    }
    
    
    
static void notacion(String codop, String notacion){
        LineaInstruccion actual = instruccion.get(contador);
        String comparador;

        String regexOpri="^#[@%\\$?][0-9A-F]+$|^#[0-9]+$";
        String regexOpra="^[@%\\$?][0-9A-F]+$|^[0-9]+$"; // \\$?
        String regexRel="^[a-zA-Z0-9]+$|^[ABDXY],[a-zA-Z0-9]+$|^[SP],[a-zA-Z0-9]+$";
        String regexOprx="^[-]*[@%\\$?$]*[0-9A-F]+,(X|Y|SP|PC)$|^[@%\\$?$]*[0-9A-F]+,[+-]*(X|Y|SP|PC)[+-]*$|^[ABD]+,(X|Y|SP|PC)$|^(\\[?[@%\\$?]*[0-9A-F]+,(X|Y|SP|PC))\\]?$|^(\\[?[Dd],(X|Y|SP|PC))\\]?|^,(X|Y|SP|PC)$";
        String regexDirectiva=" ^DC.B$ | ^DC.W$ | ^DS.B$ | ^DS.W$ ";
        
         Pattern patOpri = Pattern.compile(regexOpri);
         Pattern patOpra = Pattern.compile(regexOpra);
         Pattern patRel = Pattern.compile(regexRel);
         Pattern patOprx = Pattern.compile(regexOprx);
         Pattern patDirectiva = Pattern.compile(regexDirectiva);
         
         Matcher matcherOpri = patOpri.matcher(notacion);
         Matcher matcherOpra = patOpra.matcher(notacion);
         Matcher matcherRel = patRel.matcher(notacion);
         Matcher matcherOprx = patOprx.matcher(notacion);
         Matcher matcherDirectiva = patDirectiva.matcher(codop);
         
         if(matcherDirectiva.matches()){
             comparador = "esDirectiva";
                char tem = notacion.charAt(0);//crea un caracter temporal para comparar

                if(codop.equals("DC.B")){//compara que el codop sea DC.B
                    if(Character.toString(tem).equals("\"")){//en caso de que inicie con comillas
                        String aux = notacion.replace("\"", ""); //elimina las comillas

                        int pesoBytes = aux.length(); //Saca la longitud del conjunto de elementos y le asigna un byte
                        double pesoDouble = (double) pesoBytes;
                        instruccion.get(contador).peso = pesoDouble; //atencion aqui ***************************************

                        System.out.print(pesoBytes + "  "); //imprimir para confirmar
                    }else{//Si no inicia con comillas
                        String[] elementos = notacion.split(",");//separa los elementos por comillas y los guarda en un arreglo
                        int pesoBytes = elementos.length;//el peso en bytes es igual al tamaño del arreglo

                        System.out.print(pesoBytes + "  ");//imprimir para confirmar
                    }
                }else if(codop.equals("DC.W")){//En todo caso confirma si el codop es un DC.W

                    if(Character.toString(tem).equals("\"")){//en caso de que inicie con comillas
                        String aux = notacion.replace("\"", "");//elimina las comillas

                        int pesoBytes = (aux.length()*2);//el peso es igual al doble del numero de elementos
                        double pesoDouble = (double) pesoBytes;
                        instruccion.get(contador).peso = pesoDouble;

                        System.out.print(pesoBytes + "  ");//imprimir para confirmar
                    }else{//si no inicia con comillas
                        String[] elementos = notacion.split(",");//separa los elementos por comillas y los guarda en un arreglo
                        int pesoBytes = (elementos.length*2);//el peso es igual a 2 veces la longitud del arreglo
                        double pesoDouble = (double) pesoBytes;
                        instruccion.get(contador).peso = pesoDouble;

                        System.out.print(pesoBytes + "  ");//imprimir para confirmar
                    }
                }else if(codop.equals("DS.B") && Character.toString(tem).matches("[1-9]")){//En todo caso confirma si el codop es un DS.B
                    int pesoBytes = Integer.parseInt(notacion);//el peso será igual al número específico del operando
                    double pesoDouble = (double) pesoBytes;
                    instruccion.get(contador).peso = pesoDouble;

                    System.out.print(pesoBytes + "  ");//imprimir para confirmar
                }else if(codop.equals("DS.W") && Character.toString(tem).matches("[1-9]")){//En todo caso confirma si el codop es un DS.W
                    int pesoBytes = (Integer.parseInt(notacion)*2);//el peso será igual a 2 veces el operando especificado
                    double pesoDouble = (double) pesoBytes;
                    instruccion.get(contador).peso = pesoDouble;

                    System.out.print(pesoBytes + "  ");//imprimir para confirmar
                }else{//caso de error
                    System.out.println("Directiva no valida");//mensaje de error
                }
         }else{
             if(matcherOpri.matches()){
             char tem = notacion.charAt(1);//Crea un caracter para comparar
             if(Character.toString(tem).matches("%")){
                 String binario = notacion.substring(2);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=255){
                     String key = "#opr8i";
                     comparador = "esInmediato8";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     comparador = "esInmediato16";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
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
                     comparador = "esInmediato8";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     comparador = "esInmediato16";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
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
                     comparador = "esInmediato8";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     comparador = "esInmediato16";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }//Fin del octal
                }else{
                 String dec = notacion.substring(1);
                 int tamaño = Integer.parseInt(dec);
                 if(tamaño<=255){
                    String key = "#opr8i";
                     comparador = "esInmediato8";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "#opr16i";
                     comparador = "esInmediato16";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }
            }else if(matcherOpra.matches()){
                comparador = "esDirExt";
            char tem = notacion.charAt(0);//Crea un caracter para comparar
             if(Character.toString(tem).matches("%")){
                 String binario = notacion.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=255){
                     String key = "#opr8a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
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
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
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
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
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
                     calcPostByte(comparador,actual,tamaño);
                 }else if(tamaño<=65535){
                     String key = "opr16a";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcPostByte(comparador,actual,tamaño);
                }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }
            }else if(matcherRel.matches()){
               comparador = "esRelativo";
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
                if(Character.toString(tem).matches("[ABD]")){
                    String[] aOperandos = notacion.split(",");
                    String operando1 = aOperandos[0];
                    String operando2 = aOperandos[1];
                    
                    String key = "oprx0_xysp";
                    String llave = "ABD";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    calcularXB(llave, actual, operando1, operando2);
                    
                }else if(Character.toString(tem).equals(",")){
                    String opr1= "0";
                    String opr2 = notacion.substring(1);
                    
                    String key = "oprx0_xysp";
                    String comparador2 =  "5b";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    calcularXB(comparador2, actual, opr1, opr2);
                    
                }else if(Character.toString(tem).matches("\\[?")){
                    if(Character.toString(temp2).matches("D")){
                        String[] aOperandos = notacion.split(",");
                        String operando2 = aOperandos[1].replace("]", "");
                        
                        String key = "[D,xysp]";
                        String comparador2 = "DIDX";
                        System.out.print(key + "  ");
                        
                        comparadorExcel(instruccion.get(contador).getCodop(), key);
                        calcularXB(comparador2, actual, "D", operando2);
                    }else{
                        String[] aOperandos = notacion.split(",");
                        String operando1= aOperandos[0].replace("[", "");
                        String operando2 = aOperandos[1].replace("]", "");
                        
                        String key = "[oprx16,xysp]";
                        String llave = "16IDX";
                        System.out.print(key + "  ");
                        comparadorExcel(instruccion.get(contador).getCodop(), key);
                        calcularXB(llave, actual, operando1, operando2);
                        
                    } //atencion aqui ****************************************************************************************
                }else if(Character.toString(tem).equals("-") || Character.toString(tem).matches("[0-9]") || Character.toString(tem).matches("[@%\\$?]")){
                    separarOperandos(notacion);
                }else{
                    System.out.println("Instruccion no valida");
                }
            }else{
                comparador = "esInherente";
                char tem = notacion.charAt(0);
                if(notacion.length() == 1 && Character.toString(tem).equals("-")){
                    String key = "-";
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    calcPostByte(comparador, actual, 0);
                    System.out.print(key + "  ");
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                    }
            }
         }
    }//fin de notación
    
  static void separarOperandos(String operandos){
        LineaInstruccion actual = instruccion.get(contador);
        //String operandosOriginal = operandos;
        String[] aOperandos = operandos.split(",");
        String operando1 = aOperandos[0];
        String operando2 = aOperandos[1];
        
        int idx = operando2.length()-1;
        //Comparadores temporales que sirven para encontrar los símbolos de "+-" en un operando
        char tem = operando1.charAt(0);
        char temp3 = operando2.charAt(0);
        char temp4 = operando2.charAt(idx);
        
        if(Character.toString(tem).equals("-")){
              char temp2 = operando1.charAt(1);
             if(Character.toString(temp2).matches("[0-9]")){
                String paso = operando1.substring(1);
                int tamaño = Integer.parseInt(paso);
                 if(tamaño<=16){
                     String key = "oprx0_xysp";
                     String comparador = "5b";
                     System.out.print(key + "  ");

                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, operando1, operando2);
                 }else if(tamaño<=256){
                     String key = "oprx9,xysp";
                     String comparador = "9b";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, operando1, operando2);
                     
                }else if(tamaño<=32768){
                    String key = "oprx16,xysp";
                    String comparador= "16b";
                    System.out.print(key + "  ");
                    
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    calcularXB(comparador, actual, operando1, operando2);
                }else{//Valor no valido
                     String key = "Error";
                    System.out.print(key + "  ");                
                }
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                }
        }else if(Character.toString(temp3).equals("-")){
            char temp2 = operando1.charAt(0);
             if(Character.toString(temp2).matches("%")){
                 String binario = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("@")){
                 String octal = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).equals("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                }
        }else if(Character.toString(temp4).equals("-")){
            char temp2 = operando1.charAt(0);
             if(Character.toString(temp2).matches("%")){
                 String binario = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("@")){
                 String octal = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).equals("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+tamaño;
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                } 
        }else if(Character.toString(temp3).equals("+")){
              char temp2 = operando1.charAt(0);
             if(Character.toString(temp2).matches("%")){
                 String binario = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("@")){
                 String octal = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).equals("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "pre";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
                }
        }else if(Character.toString(temp4).equals("+")){
            char temp2 = operando1.charAt(0);
             if(Character.toString(temp2).matches("%")){
                 String binario = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(binario, 2);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("@")){
                 String octal = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(octal, 8);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).equals("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else{//Valor no valido
                     String key = "Error";
                     System.out.print(key + "  ");
                 }
            }else if(Character.toString(temp2).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=8 && tamaño>= 1){
                     String key = "oprx0_xysp";
                     String comparador = "post";
                     System.out.print(key + "  ");
                     String opr1 = "-"+(tamaño-1);
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
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
                     String comparador= "5b";
                     String opr1 = ""+tamaño;
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     calcularXB(comparador, actual, opr1, operando2);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     
                     String opr1 = ""+tamaño;
                     String comparador= "9b";
                     calcularXB(comparador, actual, opr1, operando2);
                     
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    
                    String opr1 = ""+tamaño;
                     String comparador= "16b";
                     calcularXB(comparador, actual, opr1, operando2);
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
                     
                     String opr1 = ""+tamaño;
                     String comparador= "5b";
                     calcularXB(comparador, actual, opr1, operando2);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     String opr1 = ""+tamaño;
                     String comparador= "9b";
                     calcularXB(comparador, actual, opr1, operando2);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    String opr1 = ""+tamaño;
                     String comparador= "16b";
                     calcularXB(comparador, actual, opr1, operando2);
                }else{//Valor no valido
                     String key = "Error";}
            }else if(Character.toString(tem).equals("$")){
                 String hexa = operando1.substring(1);//Elimina caracteres no deseados
                 int tamaño = Integer.parseInt(hexa, 16);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     String opr1 = ""+tamaño;
                     String comparador= "5b";
                     calcularXB(comparador, actual, opr1, operando2);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     String opr1 = ""+tamaño;
                     String comparador= "9b";
                     calcularXB(comparador, actual, opr1, operando2);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    String opr1 = ""+tamaño;
                     String comparador= "16b";
                     calcularXB(comparador, actual, opr1, operando2);
                }else{//Valor no valido
                     String key = "Error";
                System.out.print(key + "  ");}
                 
            }else if(Character.toString(tem).matches("[0-9]")){
                int tamaño = Integer.parseInt(operando1);
                 if(tamaño<=15){
                     String key = "oprx0_xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     String opr1 = ""+tamaño;
                     String comparador= "5b";
                     calcularXB(comparador, actual, opr1, operando2);
                 }else if(tamaño<=255){
                     String key = "oprx9,xysp";
                     System.out.print(key + "  ");
                     comparadorExcel(instruccion.get(contador).getCodop(), key);
                     String opr1 = ""+tamaño;
                     String comparador= "9b";
                     calcularXB(comparador, actual, opr1, operando2);
                }else if(tamaño<=65535){
                    String key = "oprx16,xysp";
                    System.out.print(key + "  ");
                    comparadorExcel(instruccion.get(contador).getCodop(), key);
                    String opr1 = ""+tamaño;
                     String comparador= "16b";
                     calcularXB(comparador, actual, opr1, operando2);
                }else{//Valor no valido
                     String key = "Error";
                System.out.print(key + "  ");}
                }else{
                    String key = "Error";
                    System.out.print(key + "  ");
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

                    System.out.print(pesoTotal + "  ");
                    System.out.print(addrCell + "  ");

                    instruccion.get(contador).peso = pesoTotal;
                    instruccion.get(contador).direc = addrCell.toString();
                    instruccion.get(contador).key = key;

                    //cambios proyecto 4

                    Cell dircell = row.getCell(3);
                    String dirFila = dircell.getStringCellValue();

                    Cell postcell = row.getCell(4);
                    String postFila = postcell.getStringCellValue();

                    if (codop.equals(codopEnFila) && instruccion.get(contador).direc.equals(dirFila)) {
                        instruccion.get(contador).forma = postFila;
                        System.out.print(instruccion.get(contador).forma + "  ");
                    }
                    System.out.println("");
                    // Puedes almacenar este valor o hacer lo que necesites con él
                    break; // Puedes romper el bucle una vez que encuentres la coincidencia deseada
                }
            } 
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Datos: esta funcion analiza el tipo de registros, etiqueta, codop, operando y contloc de las instrucciones, 
     * muestra la etiqueta, codop y operando al inicio y luego analiza en un if el tipo de registro para saber como calcular el contloc
     * todo esto lo escribe en el archivo P1ASM1.lst
     */
    static void insertarDatosList(){
       try {
           FileOutputStream programList = new FileOutputStream("P1ASM1.lst");
           PrintStream p1List = new PrintStream(programList);
           String[] registros = {"DIR_INIC", "CONTLOC", "VALOR"};

           String org = instruccion.get(1).codop + " " + instruccion.get(1).operando;
           String regexOrg = "ORG \\$[0-9A-Fa-f]+";

           Pattern patternOrg = Pattern.compile(regexOrg);

           Matcher matcherOrg = patternOrg.matcher(org);
           
           p1List.println("ETIQUETA CODOP OPERANDO REGISTRO CONTLOC");

           for (int i = 1; i < instruccion.size(); i++) {
               p1List.print(instruccion.get(i).etiqueta + "  ");
               p1List.print(instruccion.get(i).codop + "  ");
               p1List.print(instruccion.get(i).operando + "  ");

               if (matcherOrg.find()) { // procesa la instruccion org
               // Se encontró una coincidencia
               String matchedText = matcherOrg.group();
               p1List.print(registros[0] + " ");
               String operator = matchedText.substring(4); // Para eliminar "ORG $"
               p1List.print(operator + " ");
               operator = matchedText.substring(5); // Para eliminar "ORG $"
               instruccion.get(0).contloc = operator;
               } else if (instruccion.get(i).codop.equals("EQU")) {
                   //p1List.print(instruccion.get(i).contloc);
                   p1List.print(registros[2] + " ");
                   p1List.print(instruccion.get(i).contloc + "  ");
               } else {
                   //p1List.print(instruccion.get(i).contloc);
                   p1List.print(registros[1] + " ");
                   p1List.print(instruccion.get(i).contloc + "  ");
               }
               p1List.println("");
               p1List.println("----------------");
           }
           p1List.close();
       }  catch (Exception e) {
           // TODO: handle exception
       }
   }

   /**
    * Datos: esta funcion cera el archivo tabism
    */
    static void insertarDatosTabism() {
        FileOutputStream programTabsim;
        HashMap<String, Boolean> validadorSimbolo = new HashMap<>();
        String [] Tipo = {"Relativa", "Absoluta"};

        try {
            programTabsim = new FileOutputStream("TABSIM.txt");
            PrintStream tabsim = new PrintStream(programTabsim);
            tabsim.println("SI TIPO TI ");
            //String [] Tipo = {"Relativa", "Absoluta"};
    
            for (int i = 2; i < instruccion.size(); i++) {
                contador = i;
                
                if (instruccion.get(i).key != null && (instruccion.get(i).key.contains("rel8") || instruccion.get(i).key.contains("rel16") || instruccion.get(i).key.contains("abdxys, rel9")))
                    origenRel.add(new LineaInstruccion("", instruccion.get(i).codop, instruccion.get(i).operando, null, 0, instruccion.get(1+i).contloc, "0", "0"));
                
                // Verifica si la etiqueta ya existe en el HashMap
                if (validadorSimbolo.containsKey(instruccion.get(contador).etiqueta)) {
                    // La etiqueta ya existe
                } else {
                    validadorSimbolo.put(instruccion.get(contador).etiqueta, true);
                    tabsim.print(instruccion.get(contador).etiqueta + " ");

                    if(instruccion.get(i).codop.equals("EQU")){
                    tabsim.print(Tipo[1] + " ");
                    } else {
                        tabsim.print(Tipo[0] + " ");
                    }

                    tabsim.print(instruccion.get(i).contloc + " ");
                    
                    if (instruccion.get(i).etiqueta.contains("-")) {
                    } else
                        destinoRel.add(new LineaInstruccion(instruccion.get(i).etiqueta, "", "", null, 0, instruccion.get(i).contloc, "0", "0"));
                    
                    tabsim.println("");
                    tabsim.println("-------------");
                }
            }
            tabsim.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    static void calcularContloc(){//inicio del método calcular contloc
        for(int i=0; i<=instruccion.size()-2; i++){//for para recorrer todo el arrayList
            
            int dirCont = i+1;//variable de apoyo
            LineaInstruccion auxiliar = instruccion.get(dirCont);//auxiliar nos da acceso al objeto que sigue 
            LineaInstruccion auxiliar2 = instruccion.get(i);//auxiliar 2 nos da acceso al objeto actual
           if(instruccion.get(i).codop.equals("ORG")){//Si nuestro codop es un ORG entra
               char tem = instruccion.get(i).operando.charAt(0);//variable temporal para comparar
               if(Character.toString(tem).matches("#")){//si el primer digito es un #
                   char tem2 = instruccion.get(i).operando.charAt(1);//variable temporal para comparar
                   if(Character.toString(tem2).matches("%")){//si el segundodigito es un %
                String binario = instruccion.get(i).operando.substring(2);//Elimina caracteres no deseados
                int numero = Integer.parseInt(binario, 2); //convierte el binario a decimal
                String contloc = Integer.toHexString(numero); //luego lo cambia a hexadecimal y lo guarda en un string
                auxiliar.setContloc(contloc); //guarda el contloc en la siguiente linea
              }else if(Character.toString(tem2).equals("@")){
                String octal = instruccion.get(i).operando.substring(2);//Elimina caracteres no deseados
                int numero = Integer.parseInt(octal, 8); //convierte el octal a decimal
                String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
                auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
              }else if(Character.toString(tem2).equals("$")){
                String hexa = instruccion.get(i).operando.substring(2);//Elimina caracteres no deseados
                auxiliar.setContloc(hexa); //guarda el valor en la siguiente linea
               }else{
                String decimal = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
                int numero = Integer.parseInt(decimal); //convierte el valor a tipo int
                String contloc = Integer.toHexString(numero); //luego lo convierte a hexadecimal y lo gurda en un string
                auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
               }
               }else{
                   if(Character.toString(tem).matches("%")){
                String binario = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
                int numero = Integer.parseInt(binario, 2);//convierte el binario a decimal
                String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
                auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
              }else if(Character.toString(tem).equals("@")){
                String octal = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
                int numero = Integer.parseInt(octal, 8);//convierte el octal a decimal
                String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
                auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
              }else if(Character.toString(tem).equals("$")){
                String hexa = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
                auxiliar.setContloc(hexa);//guarda el contloc en la siguiente linea
               }else{
                String decimal = instruccion.get(i).operando;//Elimina caracteres no deseados
                int numero = Integer.parseInt(decimal);//convierte el valor a tipo int
                String contloc = Integer.toHexString(numero);//luego lo convierte a hexadecimal y lo gurda en un string
                auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
               }
               }
       }else if(instruccion.get(i).codop.equals("EQU")){//si la instruccion es un EQU
        char tem = instruccion.get(i).operando.charAt(0);//variable temporal para comparar
        if(Character.toString(tem).matches("#")){//si el primer digito es un #
            char tem2 = instruccion.get(i).operando.charAt(1);//variable temporal para comparar
            if(Character.toString(tem2).matches("%")){//si el segundodigito es un %
         String binario = instruccion.get(i).operando.substring(2);//Elimina caracteres no deseados
         int numero = Integer.parseInt(binario, 2); //convierte el binario a decimal
         String contloc = Integer.toHexString(numero); //luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar.setContloc(contloc); //guarda el contloc en la siguiente linea
       }else if(Character.toString(tem2).equals("@")){
         String octal = instruccion.get(i).operando.substring(2);//Elimina caracteres no deseados
         int numero = Integer.parseInt(octal, 8); //convierte el octal a decimal
         String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
       }else if(Character.toString(tem2).equals("$")){
         String hexa = instruccion.get(i).operando.substring(2);//Elimina caracteres no deseados
         auxiliar.setContloc(hexa); //guarda el valor en la siguiente linea
        }else{
         String decimal = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         int numero = Integer.parseInt(decimal); //convierte el valor a tipo int
         String contloc = Integer.toHexString(numero); //luego lo convierte a hexadecimal y lo gurda en un string
         auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
        }
        }else{
            if(Character.toString(tem).matches("%")){
         String binario = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         int numero = Integer.parseInt(binario, 2);//convierte el binario a decimal
         String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
       }else if(Character.toString(tem).equals("@")){
         String octal = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         int numero = Integer.parseInt(octal, 8);//convierte el octal a decimal
         String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
       }else if(Character.toString(tem).equals("$")){
         String hexa = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         auxiliar.setContloc(hexa);//guarda el contloc en la siguiente linea
        }else{
         String decimal = instruccion.get(i).operando;//Elimina caracteres no deseados
         int numero = Integer.parseInt(decimal);//convierte el valor a tipo int
         String contloc = Integer.toHexString(numero);//luego lo convierte a hexadecimal y lo gurda en un string
         auxiliar.setContloc(contloc);//guarda el contloc en la siguiente linea
        }
        }
}else if(instruccion.get(i).codop.equals("EQU")){//si la instruccion es un EQU
    char tem = instruccion.get(i).operando.charAt(0);//variable temporal para comparar 
    if(Character.toString(tem).matches("%")){
         String nextCont = auxiliar2.getContloc(); //los convertimos a hexadecimal y los convertimos a string
         auxiliar.setContloc(nextCont);
         String binario = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         int numero = Integer.parseInt(binario, 2);//convierte el binario a decimal
         String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar2.setContloc(contloc);//guarda el contloc en la linea actual
       }else if(Character.toString(tem).equals("@")){
         String nextCont = auxiliar2.getContloc(); //los convertimos a hexadecimal y los convertimos a string
         auxiliar.setContloc(nextCont);
         String octal = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         int numero = Integer.parseInt(octal, 8);//convierte el octal a decimal
         String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar2.setContloc(contloc);//guarda el contloc en la linea actual
       }else if(Character.toString(tem).equals("$")){
         String nextCont = auxiliar2.getContloc(); //los convertimos a hexadecimal y los convertimos a string
         auxiliar.setContloc(nextCont);
         String hexa = instruccion.get(i).operando.substring(1);//Elimina caracteres no deseados
         auxiliar2.setContloc(hexa);//guarda el contloc en la linea actual
        }else{
         String nextCont = auxiliar2.getContloc(); //los convertimos a hexadecimal y los convertimos a string
         auxiliar.setContloc(nextCont);
         String decimal = instruccion.get(i).operando;//Elimina caracteres no deseados
         int numero = Integer.parseInt(decimal);//convierte el string a int decimal
         String contloc = Integer.toHexString(numero);//luego lo cambia a hexadecimal y lo guarda en un string
         auxiliar2.setContloc(contloc);//guarda el contloc en la linea actual
        }
       }else if (instruccion.get(i).codop.equals("END")){
               System.out.println("no hacer nada");    
       }else{
           int cont = Integer.parseInt(auxiliar2.getContloc(), 16);//conseguimos el contloc de la linea actual en decimal
           int peso = (int) auxiliar2.getPeso();//conseguimos el peso de la linea actual en decimal
           int suma = cont+peso;//simamos los valores
           String nextCont = Integer.toHexString(suma); //los convertimos a hexadecimal y los convertimos a string
           auxiliar.setContloc(nextCont); //guardamos el valor en el contloc de la linea siguiente
       }
   
     }
    }

    static void calcularXB(String comparador, LineaInstruccion actual, String valor, String opr2){
       int entero = Integer.valueOf(valor);
       String registro="";
       int n = 5, n2= 4;
       
       if(opr2.matches("[xX]") || opr2.equals("-X") || opr2.equals("X-") || opr2.equals("+X") || opr2.equals("X+")){
           registro="00";
       }else if(opr2.matches("[yY]")|| opr2.equals("-Y") || opr2.equals("Y-") || opr2.equals("+Y") || opr2.equals("Y+")){
           registro="01";
       }else if(opr2.matches("[sp | SP]") || opr2.equals("-SP") || opr2.equals("SP-") || opr2.equals("+SP") || opr2.equals("SP+")){
           registro="10";
       }else if(opr2.matches("[pc | PC]")){
           registro="11";
       }else{
           System.out.println("ERROR");
       }
       

        switch(comparador){
            case "5b":
                int valorE = Integer.valueOf(valor);
                String base= "rr0nnnnn";
                char signo = valor.charAt(0);
                String binario = Integer.toBinaryString(valorE);
                
                if(Character.toString(signo).equals("-")){
                    
                String bin2 = binario.substring(binario.length()-5);
                String paso1 = base.replace("rr", registro);
                String paso2 = paso1.replace("nnnnn", bin2);
                int decimal = Integer.parseInt(paso2, 2);
                String xb = Integer.toHexString(decimal);
                String cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                }else{
                
                String cadena = String.format("%" + n + "s", binario).replace(' ', '0');
                String paso1 = base.replace("rr", registro);
                String paso2 = paso1.replace("nnnnn", cadena);
                int decimal = Integer.parseInt(paso2, 2);
                String xb = "0"+Integer.toHexString(decimal);
                String cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                }
                
                break;
                
            case "9b":
                signo = valor.charAt(0);

                if(Character.toString(signo).equals("-")){
                    
                    base= "111rr000";
                    String paso1 = base.replace("rr", registro);
                    int decimal = Integer.parseInt(paso1, 2);
                    String xb = Integer.toHexString(decimal);
                    String cambio = actual.getForma().replace("xb", xb);
                    actual.setForma(cambio);
                    
                    String llave="idx9";
                    calcPostByte(llave, actual, entero);
                    
                }else{
                    
                    base= "111rr001";
                    String paso1 = base.replace("rr", registro);
                    int decimal = Integer.parseInt(paso1, 2);
                    String xb = Integer.toHexString(decimal);
                    String cambio = actual.getForma().replace("xb", xb);
                    actual.setForma(cambio);
                    
                    String llave="idx9";
                    calcPostByte(llave, actual, entero);
                }
                
                break;
        
            case "16b":
                
                base= "111rr010";
                String paso1 = base.replace("rr", registro);
                int decimal = Integer.parseInt(paso1, 2);
                String xb = Integer.toHexString(decimal);
                String cambio = actual.getForma().replace("xb", xb);
                actual.setForma(cambio);
                
                String llave="idx16";
                calcPostByte(llave, actual, entero);
                
                break;
            
            case "pre":
                
                base= "rr10nnnn";
                signo = valor.charAt(0);
                valorE = Integer.valueOf(valor);
                binario = Integer.toBinaryString(valorE);
                
                
                if(Character.toString(signo).equals("-")){
                    
                String bin2 = binario.substring(binario.length()-4);
                paso1 = base.replace("rr", registro);
                String paso2 = paso1.replace("nnnn", bin2);
                decimal = Integer.parseInt(paso2, 2);
                xb = Integer.toHexString(decimal);
                cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                }else{
                
                String cadena = String.format("%" + n2 + "s", binario).replace(' ', '0');
                paso1 = base.replace("rr", registro);
                String paso2 = paso1.replace("nnnn", cadena);
                decimal = Integer.parseInt(paso2, 2);
                xb = Integer.toHexString(decimal);
                cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                }
                
                break;
                
            case "post":
                
                base= "rr11nnnn";
                signo = valor.charAt(0);
                valorE = Integer.valueOf(valor);
                binario = Integer.toBinaryString(valorE);
                
                
                if(Character.toString(signo).equalsIgnoreCase("-")){
                    
                String bin2 = binario.substring(binario.length()-4);
                paso1 = base.replace("rr", registro);
                String paso2 = paso1.replace("nnnn", bin2);
                decimal = Integer.parseInt(paso2, 2);
                xb = Integer.toHexString(decimal);
                cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                }else{
                
                String cadena = String.format("%" + n2 + "s", binario).replace(' ', '0');
                paso1 = base.replace("rr", registro);
                String paso2 = paso1.replace("nnnn", cadena);
                decimal = Integer.parseInt(paso2, 2);
                xb = Integer.toHexString(decimal);
                cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                }
                
                break;
                
            case "ABD":
                
                if(valor.matches("[Aa]")){
                    
                    base= "111rr100";
                    paso1 = base.replace("rr", registro);
                    decimal = Integer.parseInt(paso1, 2);
                    xb = Integer.toHexString(decimal);
                    cambio = actual.getForma().replace("xb", xb);
                    actual.setPostByte(cambio);
                
                }else if(valor.matches("[Bb]")){
                
                    base= "111rr101";
                    paso1 = base.replace("rr", registro);
                    decimal = Integer.parseInt(paso1, 2);
                    xb = Integer.toHexString(decimal);
                    cambio = actual.getForma().replace("xb", xb);
                    actual.setPostByte(cambio);
                
                }else if(valor.matches("[Dd]")){
                    
                    base= "111rr110";
                    paso1 = base.replace("rr", registro);
                    decimal = Integer.parseInt(paso1, 2);
                    xb = Integer.toHexString(decimal);
                    cambio = actual.getForma().replace("xb", xb);
                    actual.setPostByte(cambio);
                
                }else{
                    System.out.println("ERROR");
                }
                
                break;
                
            case "DIDX":
                
                base= "111rr111";
                paso1 = base.replace("rr", registro);
                decimal = Integer.parseInt(paso1, 2);
                xb = Integer.toHexString(decimal);
                cambio = actual.getForma().replace("xb", xb);
                actual.setPostByte(cambio);
                
                break;
                
            case "16IDX":
                
                base= "111rr011";
                paso1 = base.replace("rr", registro);
                decimal = Integer.parseInt(paso1, 2);
                xb = Integer.toHexString(decimal);
                cambio = actual.getForma().replace("xb", xb);
                actual.setForma(cambio);
                
                llave="idx16";
                calcPostByte(llave, actual, entero);
                
                break;
                
        }//fin del switch
   
   }//fin de calcular xb

   static void calcPostByte(String comparador, LineaInstruccion actual, int valor){
         static void calcPostByte(String comparador, LineaInstruccion actual, int valor){
        String valorS = Integer.toString(valor);
        String hexa = Integer.toHexString(valor);
        switch(comparador){
            case "esInherente":// Si es inherente
                
                actual.setPostByte(actual.getForma());//el postbyte lo da la forma
                
            break;//break del caso inherente
            
            case "esInmediato8":
                
                if(hexa.length()==1){
                    String nuevo = "0"+hexa;
                    String cambio = actual.getForma().replace("ii", nuevo);
                    actual.setPostByte(cambio);
                    
                }else{
                    String cambio = actual.getForma().replace("ii", hexa);
                    actual.setPostByte(cambio);
                }
                
            break;// break es inmediato
            
            case "esInmediato16":
                
                if(hexa.length()==3){
                    
                    String nuevo = "0"+hexa;
                    String jj = nuevo.substring(2, 4);
                    String kk = nuevo.substring(0, 2);
                    
                    String cambio = actual.getForma().replace("jj", jj);
                    String cambio2 = cambio.replace("kk", kk);
                    
                    actual.setPostByte(cambio2);
                }else{
                    
                    String jj = hexa.substring(2, 4);
                    String kk = hexa.substring(0, 2);
                    
                    String cambio = actual.getForma().replace("jj", jj);
                    String cambio2 = cambio.replace("kk", kk);
                    
                    actual.setPostByte(cambio2);
                }
                
            break;

            case "esDirecto":// si el comparador es directo
                
                 if(hexa.length()==1){ //verifica la longitud del operando y si es = 1
                    String nuevo = "0"+hexa;// completa con 0 y lo guarda en nuevo
                    String cambio = actual.getForma().replace("dd", nuevo);// reemplaza los valores en la forma
                    actual.setPostByte(cambio);//guarda el cambio y lo agrega en el objeto parte postByte
                    
                }else{//si es par entonces
                    String cambio = actual.getForma().replace("dd", hexa);// reemplaza los valores en la forma
                    actual.setPostByte(cambio); //guarda el cambio y lo agrega en el objeto parte postByte
                }
                
                break;//Fin de los directos
                
            case "esExtendido"://si es extendido es porque el operando 16 bits
                
                if(hexa.length()==3){ //si tiene 3 caracteres solo
                    
                    String nuevo = "0"+hexa; //agrega un cero a la izquierda y lo guarda en nuevo
                    String hh = nuevo.substring(2, 4); //elimina los valores en low
                    String ll = nuevo.substring(0, 2); //elimina los valores e high
                    
                    String cambio = actual.getForma().replace("hh", hh); //reemplaza el hh y lo guarda en cambio
                    String cambio2 = cambio.replace("ll", ll); // ahora reemplaza el ll y lo guarda en cambio 2
                    
                    actual.setPostByte(cambio2); //cambio 2 es nuestro postByte asi que lo guardamos en el objeto
                    
                }else{
                    
                    String hh = hexa.substring(2, 4);
                    String ll = hexa.substring(0, 2);
                    
                    String cambio = actual.getForma().replace("hh", hh);
                    String cambio2 = cambio.replace("ll", ll);
                    
                    actual.setPostByte(cambio2);
                }
                
                break;//fin de los extendidos 
                
            case "idx9":
                char signo = valorS.charAt(0);
                
                if(Character.toString(signo).equals("-")){
                String hexa2 = hexa.substring(hexa.length()-2);
                
                String ff = hexa2;
                String cambio = actual.getForma().replace("ff", ff);
                
                actual.setPostByte(cambio);
                
                }else{
                    
                    String ff = hexa;
                    String cambio = actual.getForma().replace("ff", ff);
                
                    actual.setPostByte(cambio);
                }

                break;
                
            case "idx16":
                
                signo = valorS.charAt(0);
                
                if(Character.toString(signo).equals("-")){
                    String hexa2 = hexa.substring(hexa.length()-4);
                    
                    String ee = hexa2.substring(2, 4);
                    String ff = hexa2.substring(0, 2);
                
                    String cambio = actual.getForma().replace("ee", ee);
                    String cambio2 = cambio.replace("ff", ff);
                
                    actual.setPostByte(cambio2);
                }else{
                    if(hexa.length()==3){
                        String hexa2 = "0"+hexa;
                    
                        String ee = hexa2.substring(2, 4);
                        String ff = hexa2.substring(0, 2);
                
                        String cambio = actual.getForma().replace("ee", ee);
                        String cambio2 = cambio.replace("ff", ff);
                
                        actual.setPostByte(cambio2);
                    }else{
                        String ee = hexa.substring(2, 4);
                        String ff = hexa.substring(0, 2);
                
                        String cambio = actual.getForma().replace("ee", ee);
                        String cambio2 = cambio.replace("ff", ff);
                
                        actual.setPostByte(cambio2);
                    }

                }

                break;
            /*case "esRelativo":
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
                calcularRel();
            break;
            */
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
    
    public static int complementoDos(int destino, int origen) {
        int suma = destino + (~origen + 1); // Realizar la resta en complemento a dos
        return suma & 0xFFFF; // Aplicar la máscara para asegurar que el resultado sea de 4 bytes
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
                    String desplazamiento = "";
                    String lb = "";
                    
                    if (valorOri < valorDes) { // si Destino es mayor que el oRIGEN
                        desplazamiento = "Positivo";
                        lb = calcularLb(registro, i, desplazamiento);
                        
                        resultadoHex = Integer.toHexString(valorDes - valorOri);
                    } else if (valorOri > valorDes) { // origen es mayor que el destino
                        desplazamiento = "Negativo";
                        lb = calcularLb(registro, i, desplazamiento);
                        
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
        for (i = 1; i < instruccion.size(); i++) {
            if (instruccion.get(i-1).contloc.equals(origenRel.get(indiceOrigen).contloc)) { // se planea recorrer instruccion donde coincide con contloc origen
                int auxiliarPeso = (int) instruccion.get(i-2).peso; // Actualiza el atributo del elemento encontrado.
                
                if (auxiliarPeso == 2) { // es de 8 bits //******************************************************************** aqui es dependiente de la existencia de la forma posbyte
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
    
    static void funcionCortadora(int i, String resultado, int auxiliarPeso, String lb) {
        String firstThreeCharacters = "";
        if (auxiliarPeso == 4) {
            firstThreeCharacters = instruccion.get(i-2).forma.substring(0, 6);
            String modifiedString = firstThreeCharacters.concat(resultado);
            instruccion.get(i-2).postByte =  modifiedString;
        } else if (auxiliarPeso == 2) {
            firstThreeCharacters = instruccion.get(i-2).forma.substring(0, 3);
            String modifiedString = firstThreeCharacters.concat(resultado);
            instruccion.get(i-2).postByte =  modifiedString;
        } else if (auxiliarPeso == 3) {
            firstThreeCharacters = instruccion.get(i-2).forma.substring(0, 3);
            String modifiedString = firstThreeCharacters + " " + lb + " " + resultado;
            instruccion.get(i-2).postByte =  modifiedString;
        }
    }
}
