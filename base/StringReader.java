import java.io.*;
import java.util.*;

public class StringReader {
    private ArrayList<String> lineas;//se guardan las lineas con cuerda
    
    public StringReader(String filePath) {
        lineas = new ArrayList<>();//aqui se almacenan las listas
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {//mientras hayan lineas que leer, las va guardadno a la lista
                lineas.add(line);
            }
            reader.close();
        } catch (IOException e) {//si hay un error al leer, solo se imrpime el mensaje 
            System.out.println("error al leer el archivo: " + filePath);
        }
    }

    public int length() {//devolucion de las cuentas que se guardasron 
        return lineas.size();
    }

    public String get(int index) {//devuleve la cuerda y su posicion
        return lineas.get(index);
    }
}