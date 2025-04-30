package ConvertirTabla;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Excepciones.ValoresNulosException;

public class EscribirArchivo{

    private FileWriter fw;
    private final File fileRead;
    private File fileWrite;
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
    public void insertSQL() throws IOException {

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
        createArrayTypeNumber(columnVacia, selecColumn);

        for (int i = 1; i < tabla.length; i++){

            int cont = 0;

            for (int j = 0; j < tabla[0].length; j++){

                // si un campo es nulo se le asigna el valor ""
                cellIsEmpty(i, j);
                //si es string se agregan comillas
                builder = addQuotesIfString(columnVacia, tabla, i, j, cont, selecColumn);

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
            count = getCount(count, i);
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
        createArrayTypeNumber(columnVacia, selecColumn);

        // Concatenando los atributos y los valores
        for (int i = 1; i < tabla.length; i++) {

            int cont = 0;

            for(int j = 0; j < tabla[0].length; j++) {
                cellIsEmpty(i, j);
                builder = addQuotesIfString(columnVacia, tabla ,i ,j ,cont , selecColumn);
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
            count = getCount(count, i);
        }

    }

    private StringBuilder addQuotesIfString(boolean columnaVacia, String[][] tabla, int i, int j,
                                   int cont, String[] selecColumn){
        if(columnaVacia) {
            // Elimina espacios en blanco
            tabla[i][j] = tabla[i][j].trim();
            tabla[i][j] = tabla[i][j].isEmpty()? " ": tabla[i][j];
            ifTypeNumberAddQuote(tabla, i, j);
        }
        else if (columnaExiste(cont, selecColumn)) {
            // No agrega comillas si los campos son numericos
            tabla[i][j] = tabla[i][j].isEmpty()? tabla[i][j] = "0": tabla[i][j];
            if(insertRB) {
                builder.append(tabla[i][j]).append(", ");
            }
            else {
                builder.append(tabla[0][j]).append("=".concat(tabla[i][j].concat(" and ")));
            }
        }
        else {
            // Elimina espacios en blanco
            tabla[i][j] = tabla[i][j].trim();
            tabla[i][j] = tabla[i][j].isEmpty()? " ": tabla[i][j];
            ifTypeNumberAddQuote(tabla, i, j);
        }
        return builder;
    }

    private void ifTypeNumberAddQuote(String[][] tabla, int i, int j) {
        if(insertRB) {
            builder.append("'").append(tabla[i][j]).append("'").append(", ");
        }
        else {
            builder.append(tabla[0][j]).append("='".concat(tabla[i][j].concat("' and ")));
        }
    }

    private static void createArrayTypeNumber(boolean columnVacia, String[] selecColumn) {
        if(!columnVacia){
            int[] columna = new int[selecColumn.length];
            for(int i = 0; i < columna.length; i++){
                columna[i] = Integer.parseInt(selecColumn[i]) -1;
                selecColumn[i] = String.valueOf(columna[i]);
            }
        }
    }

    private int getCount(int count, int i) throws IOException {
        if(count == 50 || i == tabla.length-1){
            fw.write("COMMIT;\n\n");
            count = 0;
        }
        return count;
    }

    private void cellIsEmpty(int i, int j) {
        if(tabla[i][j] == null){
            tabla[i][j] = "";
        }
    }

}
