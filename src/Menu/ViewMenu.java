package Menu;

import javax.swing.*;

import CRUDTablas.ViewTabla;
import ConvertirTabla.ConvertirTablatxt;
import Menu.Instructions.ViewInstructions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class ViewMenu extends JFrame{
    private JTabbedPane tabMenu;
    private JPanel mainPanel;
    //private JPanel vTabla;
    private JPanel conTablatxt;
    public static final ImageIcon img = new ImageIcon(Objects.requireNonNull(ViewMenu.class.getResource("logo/sql32px.png")));


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
        JMenuBar menuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Ayuda");
        JMenuItem jmItemInstruc = new JMenuItem("Instrucciones");
        jmItemInstruc.addActionListener(e -> new ViewInstructions().setVisible(true));
        JMenuItem jmItemAbout = new JMenuItem("Acerca de");
        jmItemAbout.addActionListener(e -> new About().setVisible(true));
        jMenu.add(jmItemInstruc);
        jMenu.add(jmItemAbout);
        menuBar.add(jMenu);
        frame.setIconImage(img.getImage());
        frame.setJMenuBar(menuBar);
        frame.setTitle("Convertidor");
        frame.setContentPane(new ViewMenu().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(450,440);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //ViewTabla tabla = new ViewTabla();
        //tabMenu.add(tabla);
    }

    private void createUIComponents() throws IOException {

        File archivo = new File(System.getProperty("user.dir"),"Tablas.txt");

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
                    " información.", "Archivo creado", JOptionPane.INFORMATION_MESSAGE);
            tabMenu.addTab("Tablas", null, new ViewTabla(), null);
            tabMenu.addTab("Archivo Excel", null, null, null);
        }
    }
}
