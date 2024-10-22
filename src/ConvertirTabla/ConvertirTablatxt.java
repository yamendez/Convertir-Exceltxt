package ConvertirTabla;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertirTablatxt extends JPanel{
    private JPanel panelMain;
    private JButton btnEjecutar;
    private JPanel panelArchivo;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JCheckBox chbAgreAtri;
    private JTextField txtNomTabla;
    private JTextField txtNumeros;
    private JPanel panelTabla;
    private JRadioButton rbInsert;
    private JRadioButton rbDelete;
    private JComboBox cmbTabla;
    private String direccion, nomTabla, campNumeros;
    private File directorio;
    String[][] dataCmb;

    /**
     * <h1>ConvertirTablatxt</h1>
     * El programa ConvertirTablatxt implementa una aplicacion que recibe un archivo Excel(.csv, .xls, xlsx),
     * y pasa los datos a un archivo de texto plano en formato SQL.
     *
     * @author Yailiana Mendez
     * @since 2024-09-06
     */

public ConvertirTablatxt() {
    this.add(panelMain);
    cmbTabla.addItemListener(e -> {

    });
    btnBuscar.addActionListener(e -> {
        JFileChooser fc = new JFileChooser();

        File dirActual = new File(System.getProperty("user.dir"));

        fc.setCurrentDirectory(dirActual);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel", "csv", "xls", "xlsx");

        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        int seleccion = fc.showOpenDialog(fc);

        if(seleccion == JFileChooser.APPROVE_OPTION){
            File fichero = fc.getSelectedFile();
            txtBuscar.setText(fichero.getAbsolutePath());
            direccion = fichero.toURI().toString();
            directorio = fichero.getParentFile();
        }
    });
    btnEjecutar.addActionListener(e -> {
        try {
            nomTabla = txtNomTabla.getText().toUpperCase();
            campNumeros = txtNumeros.getText();
            boolean checkbox = chbAgreAtri.isSelected();
            boolean insertRButton = rbInsert.isSelected();
            boolean deleteRButton = rbDelete.isSelected();

            Date d = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMdd");

            if (direccion == null || nomTabla.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los " +
                        "campos", "Error Campos Vacios", JOptionPane.ERROR_MESSAGE);
            } else {
                File file = new File(directorio, nomTabla + "-" + df.format(d) + ".txt");

                URI uri = new URI(direccion);
                URL url = uri.toURL();

                Workbook workbook = null;
                FileInputStream fileInputStream = new FileInputStream(new File(url.toURI()));

                if(direccion.endsWith(".xls") || direccion.endsWith(".xlsx")){
                    workbook = WorkbookFactory.create(fileInputStream);
                    new LeerExcel(file,workbook, nomTabla, campNumeros, checkbox, insertRButton, deleteRButton).leerXls();
                }
                else if(direccion.endsWith(".csv")) {

                    new LeerExcel(file, nomTabla, campNumeros, checkbox, insertRButton, deleteRButton,
                            new File(url.toURI())).Leer();

                    System.gc();
                }

                JOptionPane.showMessageDialog(null, "Operacion realizada correctamente",
                        "Mensaje", JOptionPane.INFORMATION_MESSAGE);

                if (workbook != null) workbook.close();


                txtBuscar.setText("");
                txtNomTabla.setText("");
                txtNumeros.setText("");

            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
    });

    rbDelete.addChangeListener(e -> {
        AbstractButton boton = (AbstractButton) e.getSource();

        ButtonModel bmodel = boton.getModel();
        chbAgreAtri.setEnabled(!bmodel.isSelected());
    });
    cmbTabla.addActionListener(e -> {
        int indexCmb = cmbTabla.getSelectedIndex();
        String nombre = dataCmb[indexCmb][1];
        String campos = dataCmb[indexCmb][2];
        txtNomTabla.setText(nombre);
        txtNumeros.setText(campos);
    });
}

    private void createUIComponents() throws IOException {
        // TODO: place custom component creation code here
        File file = new File(System.getProperty("user.dir"), "Tablas.txt");
        if(!file.exists()){
            new FileWriter(file);
            cmbTabla = new JComboBox<>();
        } else{
            if(file.length() == 0){
                cmbTabla = new JComboBox<>();
            } else {
                dataCmb = new LeerExcel(file).leerTablas();
                String[] items = new String[dataCmb.length];

                for (int i = 0; i < dataCmb.length; i++) {
                    for (int j = 0; j < dataCmb[0].length; j++) {
                        if (j == 1) {
                            items[i] = dataCmb[i][j];
                        }
                    }
                }
                cmbTabla = new JComboBox<>(items);
            }
        }

    }
}
