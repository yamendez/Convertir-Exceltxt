package ConvertirTabla;

import Excepciones.ValoresNulosException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h1>LeerExcel</h1>
 * La clase LeerExcel contiene los métodos que se encargan de leer el archivo Excel
 * @author Yailiana Mendez
 */

public class LeerExcel {

    private Workbook workbook;
    private final File fileRead;
    private File fileWrite;
    private String[][] tabla;
    private String nomTabla, campNumeros;
    private boolean checkbox, insertRB, deleteRB;

    public LeerExcel(File fileread) {
        this.fileRead = fileread;
    }

    /**
     * Este constructor de LeerExcel se llamará si el archivo es un csv.
     * @param fwr El archivo de texto que se va a crear
     * @param nomTabla El nombre de la tabla que se usara en el SQL
     * @param campNum Los campos numericos que contiene la tabla
     * @param checkbox Sí es <code>true</code> se agregan los campos al SQL
     * @param insertRB Si es <code>true</code> la sentencia SQL es para agregar los datos
     * @param deleteRB Si es <code>true</code> la sentencia SQL es para eliminar los datos
     * @param fileread El archivo de texto que se va a leer
     */
    public LeerExcel(File fwr, String nomTabla, String campNum,
                     boolean checkbox, boolean insertRB, boolean deleteRB, File fileread) {
        this.fileWrite = fwr;
        this.fileRead = fileread;
        this.nomTabla = nomTabla;
        this.campNumeros = campNum;
        this.checkbox = checkbox;
        this.insertRB = insertRB;
        this.deleteRB = deleteRB;
    }

    /**
     * Este constructor de LeerExcel se llamara si el archivo es .xls o .xlsx
     * @param archivo El archivo que se va a crear
     * @param workbook El archivo Excel que se va a leer
     * @param nombreTabla El nombre de la tabla que se usara en el SQL
     * @param camposNum Los campos numericos que contiene la tabla
     * @param atributos Sí es <code>true</code> se agregan los campos al SQL
     * @param insertar Si es <code>true </code> la sentencia SQL es para agregar los datos
     * @param eliminar Si es <code>true</code> la sentencia SQL es para eliminar los datos
     */
    public LeerExcel(File archivo, Workbook workbook, String nombreTabla, String camposNum,
                   boolean atributos, boolean insertar, boolean eliminar) {
        this.fileRead = archivo;
        this.workbook = workbook;
        this.nomTabla = nombreTabla;
        this.campNumeros = camposNum;
        this.checkbox = atributos;
        this.insertRB = insertar;
        this.deleteRB = eliminar;
    }


    // Lee un archivo xls o xlsx
    /**
     * Este método leer el archivo Excel(.xls, xlsx), guarda los datos en una matriz, pasa
     * los parametros recibidos a la clase <code>EscribirArchivo</code> y llama al método <code>escribir()</code>.
     * @throws IOException Si hubo un error al leer el archivo Excel
     * @throws ValoresNulosException Si hay valores nulos en la matriz
     */
    public void leerXls() throws IOException, ValoresNulosException{
        Sheet sheet = workbook.getSheetAt(0);

        List<String[]> filas = new ArrayList<>();
        StringBuilder data= new StringBuilder();

        // se obtienen las filas y columnas de la hoja
        for (Row r: sheet) {
            for (Cell c : r) {
                // si em primer campo esta vacio, salta a la siguiente fila
                if (c.getColumnIndex() == 0 && c.toString().isBlank()) {
                    break;
                }

                // agrega la celda al builder
                data.append(c).append(",");
            }

            // si el builder tiene datos, se agrega a la lista de filas
            if (!data.toString().isBlank()) {
                filas.add(data.toString().split(","));
                data.setLength(0);
            }
        }

        tabla = filas.toArray(new String[0][]);

        // Se llama al método Escribir
        new EscribirArchivo(fileRead, tabla, nomTabla, campNumeros, checkbox, insertRB, deleteRB).escribir();
        workbook.close();

    }

    // Lee el csv separado por comas

    /**
     * Llama al método <code>readSeparador(String separador)</code>, lee el archivo Excel(.csv), pasa
     * los parametros recibidos a la clase <code>EscribirArchivo</code> y llama al método <code>escribir()</code>.
     */
    public void leerCsv(){
        try {
            readSeparador(",");
            EscribirArchivo ea = new EscribirArchivo(fileWrite, tabla, nomTabla,
                    campNumeros, checkbox, insertRB, deleteRB);
            ea.escribir();

        } catch (IOException | ValoresNulosException e) {
            throw new RuntimeException(e);
        }
    }

    // Leer el archivo de texto que contiene el nombre de las tablas y los campos numericos

    /**
     * Llama al método <code>readSeparador(String separador)</code> y lee el archivo de texto
     * que contiene los nombres de las tablas y sus campos numericos.
     * @return Una matriz de tipo <code>String</code>
     */
    public String[][] leerTablas(){
        try {
            tabla = readSeparador("\\|");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tabla;
    }

    // Obtiene el separador que divide los datos del archivo

    /**
     * Lee un archivo y obtiene los datos
     * @param separador Caracter por el que están separados los datos
     * @return Devuelve una matriz det tipo <code>String</code>
     * @throws IOException Si hubo un error al leer el archivo
     */
    public String[][] readSeparador(String separador) throws IOException {

        List<String[]> filas = new ArrayList<>();
        String[] data;

        try (FileReader fr = new FileReader(fileRead);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {

                // separa los campos
                data = linea.split(separador);

                // verifica que no esten vacios
                if (!(data.length == 0) && !data[0].isBlank()) {
                    filas.add(data);
                }
            }
            // convierte filas en una matriz
            tabla = filas.toArray(new String[0][]);

        }
        return tabla;
    }

}
