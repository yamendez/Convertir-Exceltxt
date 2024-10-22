package CRUDTablas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewTabla extends JPanel{
    private JPanel mainPanel;
    private JTabbedPane tabPanel;
    private JTable table1;
    private JPanel tabListar;
    private JPanel panelButtons;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSave;
    private JPanel panelForm;
    private JTextField txtNombre;
    private JTextField txtCampNum;
    private JButton btnCancelar;
    private JButton btnClear;
    private File archivo;
    private FileWriter escribir = null;
    private DefaultTableModel model;
    private int lastId, selectedRow;
    private String[][] data;
    private boolean isSelected;
    private String[] columna;


    public ViewTabla() {
        mainPanel.setPreferredSize(new Dimension(400,330));
        this.add(mainPanel);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(txtNombre.getText().isEmpty() || txtCampNum.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Debe llenar los campos.",
                            "Campo Vacio", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if(lastId == 0) {
                        lastId++;
                        model.insertRow(0, new Object[]{lastId, txtNombre.getText(), txtCampNum.getText()});
                    } else {
                        model.addRow(new Object[]{++lastId, txtNombre.getText(), txtCampNum.getText()});
                    }
                    limpiar();
                }
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "Desea guardar los cambios.", "Guardar", JOptionPane.YES_NO_OPTION);

                if(opcion == 0) {
                    int cantRow = table1.getRowCount();
                    String[][] newTabla = new String[cantRow][table1.getColumnCount()];
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        for (int j = 0; j < table1.getColumnCount(); j++) {
                            newTabla[i][j] = table1.getValueAt(i, j).toString();

                        }
                    }
                    String direccion = archivo.toURI().toString();
                    Path path = Paths.get(System.getProperty("user.dir"),"Tablas.txt");

                    URI uri = null;
                    try {
                        uri = new URI(direccion);
                        URL url = uri.toURL();
                        /*if(archivo.canRead()){
                            System.out.println("se puede leer.");
                            System.out.println("archivo: " + archivo.getAbsolutePath());
                            System.out.println(new File("Tablas.txt").getAbsolutePath());
                        } else{
                            System.out.println("no se puede leer.");
                        }*/

                        new EscribirTabla(archivo, escribir, newTabla).escribir();

                    } catch (URISyntaxException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null,"Se guardo la informacion correctamente.",
                            "Informacion Guardada",JOptionPane.INFORMATION_MESSAGE);
                }
                limpiar();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(table1.getSelectedRow() == -1)) {
                    table1.setValueAt(txtNombre.getText(), selectedRow, 1);
                    table1.setValueAt(txtCampNum.getText(), selectedRow, 2);
                } else {
                    JOptionPane.showMessageDialog(null,"Debe seleccionar una fila",
                            "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                selectedRow = table1.getSelectedRow();

                if(e.getClickCount() == 2){
                    selectedRow = table1.getSelectedRow();

                    txtNombre.setText(String.valueOf(table1.getValueAt(selectedRow,1)));
                    txtCampNum.setText(String.valueOf(table1.getValueAt(selectedRow,2)));

                    isSelected = false;
                    table1.clearSelection();
                }


            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(table1.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila",
                            "Seleccione", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    model.removeRow(selectedRow);
                    table1.setModel(model);
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,"Desea eliminar los cambios?",
                        "Cancelar", JOptionPane.YES_NO_OPTION);
                if(opcion == 0){
                    String direccion = archivo.toURI().toString();
                    try {
                        URI uri = new URI(direccion);
                        URL url = uri.toURL();
                        data = new LeerTabla(archivo, new File(url.toURI())).Leer();
                        model = new DefaultTableModel(data, columna){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        table1.setModel(model);
                        table1.getColumnModel().getColumn(0).setMinWidth(35);
                        table1.getColumnModel().getColumn(0).setMaxWidth(35);
                    } catch (MalformedURLException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
    }

//    public static void main(String[] args){
//        JFrame frame = new JFrame("CRUD Tablas");
//        frame.setContentPane(new ViewTabla().mainPanel);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setSize(400, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }

    private void createUIComponents() throws IOException, URISyntaxException {
        // TODO: place custom component creation code here
        columna = new String[]{"ID", "Nombre", "Campos Numericos"};
        archivo = new File(System.getProperty("user.dir"),"Tablas.txt");
        model = new DefaultTableModel();
        isSelected = false;

        if(archivo.exists()){
            if(archivo.length() == 0){
                table1 = new JTable();
                model.addColumn("ID");
                model.addColumn("Nombre");
                model.addColumn("Campos Numericos");
                table1.setModel(model);
                table1.getColumnModel().getColumn(0).setMinWidth(35);
                table1.getColumnModel().getColumn(0).setMaxWidth(35);


            } else {
                String direccion = archivo.toURI().toString();

                URI uri = new URI(direccion);
                URL url = uri.toURL();

                lastId = 0;

                data = new LeerTabla(archivo, new File(url.toURI())).Leer();
                model = new DefaultTableModel(data, columna) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table1 = new JTable(model);
                table1.getColumnModel().getColumn(0).setMinWidth(35);
                table1.getColumnModel().getColumn(0).setMaxWidth(35);

                lastId = Integer.parseInt(data[data.length - 1][0]);

            }
        } else {
            escribir = new FileWriter(archivo, false);
        }
    }

    public void limpiar(){
        txtNombre.setText("");
        txtCampNum.setText("");
    }
}
