package main;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class Main {

    /**
     * @param args the command line arguments
     */
    static ArrayList<LineaInstruccion> instruccion = new ArrayList<LineaInstruccion>();
    public static void main(String[] args) {
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
                    String etiqueta = null;
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
                    String etiqueta = null;
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
       mostrarArray(); 
    }
    
    static void mostrarArray(){
    for(int i=0; i<=instruccion.size()-1; i++){
        System.out.println(instruccion.get(i).getEtiqueta()+" "+instruccion.get(i).getCodop()+" "+instruccion.get(i).getOperando());
        }
    }
    
    
    
    }
