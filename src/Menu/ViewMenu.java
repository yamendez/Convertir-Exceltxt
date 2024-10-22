package Menu;

import javax.swing.*;

import CRUDTablas.ViewTabla;
import ConvertirTabla.ConvertirTablatxt;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class ViewMenu extends JFrame{
    private JTabbedPane tabMenu;
    private JPanel mainPanel;
    private JPanel vTabla;
    private JPanel conTablatxt;

    public ViewMenu() {
        tabMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(tabMenu.getSelectedIndex() == 1) {

                    conTablatxt = new ConvertirTablatxt();
                    tabMenu.addTab("Archivo Excel", null, conTablatxt, null);
                    tabMenu.remove(1);
                    //tabMenu.addTab("Archivo", null, conTablatxt,null);


                }
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new ViewMenu().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(450,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //ViewTabla tabla = new ViewTabla();
        //tabMenu.add(tabla);
    }

    private void createUIComponents() throws IOException, URISyntaxException {

        File archivo = new File(System.getProperty("user.dir"),"Tablas.txt");
        String direccion = archivo.toURI().toString();

        URI uri = new URI(direccion);
        URL url = uri.toURL();
        tabMenu = new JTabbedPane();
        if(archivo.exists()) {

            tabMenu.addTab("Tablas", null, new ViewTabla(), null);
            tabMenu.addTab("Archivo Excel", null, new ConvertirTablatxt(), null);

        } else {
            new FileWriter(archivo, false);

            JOptionPane.showMessageDialog(null, "Se creo el Archivo Tablas.txt debe ingresarle" +
                    " informacion.", "Archivo creado", JOptionPane.INFORMATION_MESSAGE);
            tabMenu.addTab("Tablas", null, new ViewTabla(), null);
            tabMenu.addTab("Archivo Excel", null, null, null);
        }
    }
}
