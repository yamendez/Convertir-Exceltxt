package ConvertirTabla;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Excepciones.ValoresNulosException;

import javax.swing.*;

public class EscribirArchivo{

    private FileWriter fw;
    private File fileRead, fileWrite;
    private String[][] tabla;
    private String mensaje, nomTabla, campNumeros;
    private boolean checkbox, insertRB, deleteRB;
    private StringBuilder builder = new StringBuilder();

    public EscribirArchivo(File f, File fileWrite, String[][] tabla, String nomTabla,
                           String campNum, boolean checkbox, boolean insertRB, boolean deleteRB){
        this.fileRead = f;
        this.fileWrite = fileWrite;
        this.tabla = tabla;
        this.nomTabla = nomTabla;
        this.campNumeros = campNum;
        this.checkbox = checkbox;
        this.insertRB = insertRB;
        this.deleteRB = deleteRB;
    }

    public EscribirArchivo(File f, String[][] tabla, String nomTabla,
                           String campNum, boolean checkbox, boolean insertRB, boolean deleteRB){
        this.fileRead = f;
        this.tabla = tabla;
        this.nomTabla = nomTabla;
        this.campNumeros = campNum;
        this.checkbox = checkbox;
        this.insertRB = insertRB;
        this.deleteRB = deleteRB;
    }
    public void escribir() throws IOException, ValoresNulosException {
        fw = new FileWriter(fileRead, false);
        if(insertRB) {
            insertSQL();
        }else if (deleteRB) deleteSQL();
        fw.close();
    }

    // Se verifica si un campo es numerico
    public Boolean columnaExiste(int j, String[] columna){
        boolean b = false;
        for(String s : columna){
            if (Integer.parseInt(s) == j) {
                b = true;
                break;
            }
        }
        return b;
    }

    // Escribe los datos al txt en formato  INSERT SQL
    public void insertSQL() throws IOException, ValoresNulosException {

        int count = 0;
        String campos;
        String valores;

        // Separando los campos por comas
        for(String value : tabla[0]){
            builder.append(value).append(", ");
        }
        campos = String.valueOf(builder).substring(0, builder.length() -2);
        builder.delete(0, builder.length());

        String[] selecColumn = campNumeros.replace(" ", "").split(",");
        boolean columnVacia = campNumeros.isEmpty();

        // Crea un arreglo de campos numericos
        if(!columnVacia){
            int[] columna = new int[selecColumn.length];
            for(int i = 0; i < columna.length; i++){
                columna[i] = Integer.parseInt(selecColumn[i]) -1;
                selecColumn[i] = String.valueOf(columna[i]);
            }
        }

        for (int i = 1; i < tabla.length; i++){

            int cont = 0;

            for (int j = 0; j < tabla[0].length; j++){

                // Mensaje de error en caso de recibir un valor nulo
                if(tabla[i][j] == null){
                    tabla[i][j] = "";
                    /*String[] c = campos.split(",");
                    String m = "El archivo contiene valores nulos, revise la columna:" + c[j] +", fila: " + (i+1);

                    JOptionPane.showMessageDialog(null,m, "Valor nulo", JOptionPane.ERROR_MESSAGE);
                    throw new ValoresNulosException("El archivo contiene valores nulos");*/

                }


                // Elimina comillas dobles
                if(tabla[i][j].contains("\"")){ tabla[i][j] = tabla[i][j].replace("\"", "");}

                // Se colocan comillas simples si el valor es un string
                if(columnVacia) {
                    // Elimina espacios en blanco
                    tabla[i][j] = tabla[i][j].trim();
                    tabla[i][j] = tabla[i][j].isEmpty() ? " " : tabla[i][j];//linea agregada
                    builder.append("'").append(tabla[i][j]).append("'").append(", ");
                } else if(columnaExiste(cont, selecColumn)){
                    tabla[i][j] = tabla[i][j].isEmpty()? tabla[i][j] = "0": tabla[i][j];
                    builder.append(tabla[i][j]).append(", ");
                } else {
                    // Elimina espacios en blanco
                    tabla[i][j] = tabla[i][j].trim();
                    tabla[i][j] = tabla[i][j].isEmpty() ? " " : tabla[i][j];//linea agregada
                    builder.append("'").append(tabla[i][j]).append("'").append(", ");
                }
                cont++;
            }

            valores = String.valueOf(builder).substring(0, builder.length() -2);

            // Se agregan los nombres de los campos si el usuario selecciono el checkbox Atributos
            if(checkbox){
                mensaje = "insert into "+nomTabla+" ("+campos+")\nvalues (" + valores + ");\n\n";
            }
            else{
                mensaje = "insert into "+nomTabla+"\nvalues (" + valores + ");\n\n";
            }



            fw.write(mensaje.toUpperCase());
            builder.delete(0, builder.length());

            mensaje = "";
            count++;

            // Se agrega un commit cada 50 lineas o al final del archivo
            if(count == 50 || i == tabla.length-1){
                fw.write("COMMIT;\n\n");
                count = 0;
            }
        }

    }

    // Escribe los datos al txt en formato DELETE SQL
    public void deleteSQL() throws IOException{
        int count = 0;
        String valores;

        // Separando los campos numericos
        String[] selecColumn = campNumeros.replace(" ", "").split(",");
        boolean columnVacia = campNumeros.isEmpty();

        //Crea un arreglo de campos numericos
        if(!columnVacia){
            int[] columna = new int[selecColumn.length];
            for(int i = 0; i < columna.length; i++){
                columna[i] = Integer.parseInt(selecColumn[i]) -1;
                selecColumn[i] = String.valueOf(columna[i]);
            }
        }

        // Concatenando los atributos y los valores
        for (int i = 1; i < tabla.length; i++) {

            int cont = 0;

            for(int j = 0; j < tabla[0].length; j++) {
                if(tabla[i][j] == null){
                    tabla[i][j] = "";
                }
                if (columnVacia) {
                    // Elimina espacios en blanco
                    tabla[i][j] = tabla[i][j].trim();
                    tabla[i][j] = tabla[i][j].isEmpty()? " ": tabla[i][j];
                    builder.append(tabla[0][j]).append("='".concat(tabla[i][j].concat("' and ")));
                } else if (columnaExiste(cont, selecColumn)) {
                    tabla[i][j] = tabla[i][j].isEmpty()? "0": tabla[i][j];
                    builder.append(tabla[0][j]).append("=".concat(tabla[i][j].concat(" and ")));
                } else {
                    // Elimina espacios en blanco
                    tabla[i][j] = tabla[i][j].trim();
                    tabla[i][j] = tabla[i][j].isEmpty()? " ": tabla[i][j];
                    builder.append(tabla[0][j]).append("='".concat(tabla[i][j].concat("' and ")));
                }
                cont ++;
            }

            valores = String.valueOf(builder).substring(0, builder.length() -5);


            mensaje = "delete from "+nomTabla+
                    "\nwhere " + valores + ";\n\n";


            fw.write(mensaje.toUpperCase());
            builder.delete(0, builder.length());

            mensaje = "";
            count++;

            // Se agrega un commit cada 50 lineas o al final del archivo
            if(count == 50 || i == tabla.length-1){
                fw.write("COMMIT;\n\n");
                count = 0;
            }
        }

    }

}
