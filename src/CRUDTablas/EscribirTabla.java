package CRUDTablas;

import java.awt.*;
import java.io.*;
import java.util.List;

public class EscribirTabla {
    private File archivo;
    private FileWriter fw;
    private String[][] tabla;
    private StringBuilder builder = new StringBuilder();

    public EscribirTabla(File archivo, FileWriter fw, String[][] tabla) {
        this.archivo = archivo;
        this.fw = fw;
        this.tabla = tabla;
    }

    public void escribir() throws IOException {
        fw = new FileWriter(archivo, false);
        PrintWriter pw = new PrintWriter(fw);
        addTabla();
        fw.close();

    }

    public void addTabla() throws IOException{

        for (String[] strings : tabla) {
            builder.append(strings[0]).append("|").append(strings[1]).append("|").append(strings[2]).append("\n");
        }

        fw.write(String.valueOf(builder));

    }
}
