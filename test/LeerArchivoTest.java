import ConvertirTabla.LeerExcel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;


class LeerArchivoTest {

    //@Test
    void test(){
        LeerExcel leer = new LeerExcel(new File("D:\\Cursos\\Java\\HojaPruebatxt.csv"));
        //String[][] actual = leer.getTabla();
        String[][] expected ={
                {"Identificador", "Nombre", "Apellidos", "telefono", "email", "sueldo"},
                {"1", "Maria", "Remen", "123", "maria@gmail.com", "1000"},
                {"2", "David", "Allos", "222", "david@gmail.com", "500"},
                {"3", "Carlos", "Caritas", "777", "carlos@gmail.com", "100"},
                {"4", "Luisa", "Vitz", "888", "luisa@gmail.com", "300"},
                {"5", "Carlos", "Guzman", "999", "carlos@gmail.com", "200"},
                {"6", "Ramon", "Cespedes", "290", "ramon@gmail.com", "150"}
        };

//        String[][] expected ={
//                {"Nombre", "Apellido", "Cedula"},
//                {"Sancho","Panza","123"},
//                {"Gonzalo","Rodriguez","132"},
//                {"Carla","Mota","213"},
//                {"Jose","Josh","231"}
//        };

        //Assertions.assertArrayEquals(expected, actual);

    }

}