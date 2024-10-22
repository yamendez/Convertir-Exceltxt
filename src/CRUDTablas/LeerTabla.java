package CRUDTablas;

import java.io.*;

public class LeerTabla {
    private File fileRead, fileWrite;
    private String[][] tabla;
    private String nomTabla, campNumeros;

    public LeerTabla(File file, File fileRead) {
        this.fileWrite = file;
        this.fileRead = fileRead;
    }

    public String[][] Leer(){
        try{
            FileReader fr = new FileReader(fileRead);
            BufferedReader br = new BufferedReader(fr);

            br.mark(100000000);
            String linea = br.readLine();


            String[] data;
            int num_filas = 0;
            int num_column = 0;
            data = linea.split("\\|");

            // Numero de columnas
            for(String value: data){
                num_column++;
            }

            // Número de filas
            while(linea != null){
                linea = br.readLine();
                num_filas++;
            }

            tabla = new String[num_filas][num_column];

            br.reset();
            linea = br.readLine();
            data = linea.split("\\|");
            int i = 0;

            // Guardando los datos en un arreglo
            while (linea != null) {
                data = linea.split("\\|");

                for (int j = 0; j < tabla[0].length; j++) {
                    tabla[i][j] = data[j];
                }

                linea = br.readLine();
                i++;
            }

            return tabla;

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
