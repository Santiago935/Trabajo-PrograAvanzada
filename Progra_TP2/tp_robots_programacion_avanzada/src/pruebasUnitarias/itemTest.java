package pruebasUnitarias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utiles.Item;



public class itemTest {

    private Item item;
    private final int id = 1;
    private final String nombre = "Destornillador sonico";
    private final String desc = "La herramienta del Doctor";

    @Before
    public void setUp() {
        item = new Item(id, nombre, desc);
    }

    @Test
    public void getId() {
        assertEquals("Test1: El Id del item no es el correcto.", 1, item.getId());}

    @Test
    public void getNombre() {
    	assertEquals("Test1: El nombre del item no es el correcto.", "Destornillador sonico", item.getNombre());
    }

    @Test
    public void getDescripcion() {
        assertEquals("Test1: La descripción del item no es el correcto", "La herramienta del Doctor", item.getDesc());
    }

    @Test
    public void testToString() {
        String expected = String.format("Item[id=1, nombre=Destornillador sonico]");
        assertEquals("El formato de toString no es el esperado.", expected, item.toString());
    }
}