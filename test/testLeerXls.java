import ConvertirTabla.LeerExcel;
import Excepciones.ValoresNulosException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testLeerXls {
    @Test
    public void leerXlsx() throws IOException, URISyntaxException, ValoresNulosException {
        String direccion = null;
        File directorio = null;
        JFileChooser fc = new JFileChooser();

        File dirActual = new File(System.getProperty("user.dir"));

        fc.setCurrentDirectory(dirActual);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel", "csv", "xls", "xlsx");

        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        int seleccion = fc.showOpenDialog(fc);

        if(seleccion == JFileChooser.APPROVE_OPTION){
            File fichero = fc.getSelectedFile();
            direccion = fichero.toURI().toString();
            directorio = fichero.getParentFile();
        }
        String nomTabla = "testPrueba";
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        //String direccion = "D:\\Cursos\\Java\\Prueba2.xlsx";
        File file = new File(directorio, nomTabla + "-" + df.format(d) + ".txt");

        URI uri = new URI(direccion);
        URL url = uri.toURL();
        FileInputStream fileInputStream = new FileInputStream(new File(url.toURI()));

        Workbook workbook = new XSSFWorkbook(fileInputStream);
        System.out.println("direccion="+direccion+", directorio="+directorio);

        new LeerExcel(file, workbook, nomTabla, "3", true, true, false).leerXls();
    }
}
