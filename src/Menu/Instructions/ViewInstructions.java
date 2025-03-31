package Menu.Instructions;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewInstructions extends JFrame{
    private JPanel mainPanel;
    private JTextPane txtpInstructions;
    private JButton btnTablasInstruc;
    private JButton btnExcelInstruc;
    private URL instructionHtml;

    public ViewInstructions() throws HeadlessException {
        this.setTitle("Instrucciones");
        this.setVisible(true);
        this.setContentPane(mainPanel);
        this.setSize(650, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnTablasInstruc.addActionListener(e -> {
            try {
                instructionHtml = ViewInstructions.class.getResource("resources/text/tablas.html");
                txtpInstructions.setPage(instructionHtml);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private void createUIComponents() throws IOException {
        // TODO: place custom component creation code here
        txtpInstructions = new JTextPane();
        txtpInstructions.setContentType("text/html");
        HTMLDocument doc = (HTMLDocument) txtpInstructions.getDocument();
        txtpInstructions.setPage(new URL(String.valueOf(ViewInstructions.class.getResource("resources/text/tablas.html"))));
//        txtpInstructions.setStyledDocument(doc);


    }
}
