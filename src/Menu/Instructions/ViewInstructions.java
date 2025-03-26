package Menu.Instructions;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ViewInstructions extends JFrame{
    private JPanel mainPanel;
    private JTextPane txtpInstructions;
    private JButton btnTablasInstruc;
    private JButton btnExcelInstruc;

    public ViewInstructions() throws HeadlessException {
        this.setTitle("Instrucciones");
        this.setVisible(true);
        this.setContentPane(mainPanel);
        this.setSize(350, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Instrucciones");

        frame.setVisible(true);
        frame.setContentPane(new ViewInstructions().mainPanel);
        frame.setSize(350,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        txtpInstructions = new JTextPane();
        txtpInstructions.setContentType("text/html");
        HTMLDocument doc = (HTMLDocument) txtpInstructions.getDocument();
        txtpInstructions.setText("some text");
        URL helpURL = ViewInstructions.class.getResource("resources/text/tablas.html");
        try {
            txtpInstructions.setPage(helpURL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
