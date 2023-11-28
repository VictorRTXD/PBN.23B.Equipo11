/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excelread;

/**
 *
 * @author victorrtxd
 */
import java.util.ArrayList;


public class Srecord {

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public Srecord(String tipo, String conteo, String addr, String checksum, ArrayList<String> data) {
        this.tipo = tipo;
        this.conteo = conteo;
        this.addr = addr;
        this.checksum = checksum;
        this.data = data;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConteo() {
        return conteo;
    }

    public void setConteo(String conteo) {
        this.conteo = conteo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    
    String tipo, conteo, addr, checksum;
    ArrayList<String> data;
    
    
}
