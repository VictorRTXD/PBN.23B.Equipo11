/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelread;

/**
 *
 * @author Isaac
 */
public class LineaInstruccion {
   String etiqueta, codop, operando, direc, contloc, forma, postByte, key;
   double peso;

    public String getPostByte() {
        return postByte;
    }

    public void setPostByte(String postByte) {
        this.postByte = postByte;
    }
   

    public String getForma() {
    return forma;
}

public void setForma(String forma) {
    this.forma = forma;
}

    public String getContloc() {
        return contloc;
    }

    public void setContloc(String contloc) {
        this.contloc = contloc;
    }
   

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getCodop() {
        return codop;
    }

    public void setCodop(String codop) {
        this.codop = codop;
    }

    public String getOperando() {
        return operando;
    }

    public void setOperando(String operando) {
        this.operando = operando;
    }

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LineaInstruccion(String etiqueta, String codop, String operando, String direc, double peso, String contloc, String forma, String postByte) {
        this.etiqueta = etiqueta;
        this.codop = codop;
        this.operando = operando;
        this.direc = direc;
        this.peso = peso;
        this.contloc = contloc;
        this.forma = forma;
        this.postByte = postByte;
    }
}

/*



*/


