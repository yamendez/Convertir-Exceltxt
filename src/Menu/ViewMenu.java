package Menu;

import javax.swing.*;

import CRUDTablas.ViewTabla;
import ConvertirTabla.ConvertirTablatxt;
import Menu.Instructions.ViewInstructions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static JMenuBar menuBar;
    private static JMenu jMenu;
    private static JMenuItem jmItemInstruc, jmItemAbout;

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
        //jmItemInstruc.addActionListener(e -> JOptionPane.showMessageDialog(null, "Prueba instrucciones", "Mensaje", JOptionPane.PLAIN_MESSAGE));
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Menu");
        menuBar = new JMenuBar();
        jMenu = new JMenu("Ayuda");
        jmItemInstruc = new JMenuItem("Instrucciones");
        jmItemInstruc.addActionListener( e -> new ViewInstructions().setVisible(true));
        jmItemAbout = new JMenuItem("Acerca");
        jMenu.add(jmItemInstruc);
        jMenu.add(jmItemAbout);
        menuBar.add(jMenu);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(new ViewMenu().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(450,440);
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
//        menuBar = new JMenuBar();
//        jMenu = new JMenu("Ayuda");
//        jmItemInstruc = new JMenuItem("Instrucciones");
//        jmItemAbout = new JMenuItem("Acerca");
//        jMenu.add(jmItemInstruc);
//        jMenu.add(jmItemAbout);
//        menuBar.add(jMenu);

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
