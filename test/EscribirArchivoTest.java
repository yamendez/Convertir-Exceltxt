import ConvertirTabla.EscribirArchivo;
import Excepciones.ValoresNulosException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EscribirArchivoTest {
    @Test
    void Test(){
        String[][] tabla ={
                {"Identificador", "Nombre", "Apellidos", "telefono", "email", "sueldo"},
                {"1", "Maria", "Remen", "123", "maria@gmail.com", "1000"},
                {"2", "David", "Allos", "222", "david@gmail.com", "500"},
                {"3", "Carlos", "Caritas", "777", "carlos@gmail.com", "100"},
                {"4", "Luisa", "Vitz", "888", "luisa@gmail.com", "300"},
                {"5", "Carlos", "Guzman", "999", "carlos@gmail.com", "200"},
                {"6", "Ramon", "Cespedes", "290", "ramon@gmail.com", "150"}
        };

        EscribirArchivo ea = new EscribirArchivo(new File("D:\\Cursos\\Java\\HojaPruebatxt.csv"),//HojaPruebatxt.csv
                new File("D:\\Cursos\\Java\\PRUEBA TXT A TXT.txt"), tabla, "prueba1", "1, 4, 6", true, false, true);
        try {
            ea.escribir();
        } catch (IOException | ValoresNulosException e) {
            throw new RuntimeException(e);
        }
        File actual = new File("D:\\Cursos\\Java\\PRUEBA TXT A TXT.txt");

        assertTrue(actual.exists());
    }
}