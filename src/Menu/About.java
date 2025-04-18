package Menu;

import javax.swing.*;
import static Menu.ViewMenu.img;

public class About extends JFrame{
    private JPanel panelMain;
    private JLabel lblIcon;
    private JPanel panelContent;
    private ImageIcon img2 = new ImageIcon(About.class.getResource("logo/sql32px.png"));

    public About() {
        this.setTitle("Acerca De");
        this.setVisible(true);
        this.setContentPane(panelMain);
        this.setSize(400, 150);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(img2.getImage());
        this.lblIcon.setIcon(img);
        this.setLocationRelativeTo(null);

    }
}
