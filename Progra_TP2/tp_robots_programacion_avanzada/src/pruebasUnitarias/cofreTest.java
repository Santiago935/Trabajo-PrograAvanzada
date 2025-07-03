package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cofres.CofreAlmacenamiento;
import red.Robopuerto;
import utiles.Item;



public class cofreTest {

    private CofreAlmacenamiento cofre;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {
        cofre = new CofreAlmacenamiento(10, 20, "C-001");
        item1 = new Item(1, "Madera", "Bloque de madera");
        item2 = new Item(2, "Piedra", "Bloque de piedra");

    }

    @Test
    public void getId() {
        assertEquals("El ID del cofre no es el esperado.", "C-001", cofre.getId());}
    @Test
    public void getCoordenadas() {
        assertEquals("La coordenada X no es la esperada.", 10, cofre.getCoordenada().getX());
        assertEquals("La coordenada Y no es la esperada.", 20, cofre.getCoordenada().getY());}
    @Test
    public void getNodoInicial() {
        assertNotNull("El nodo no debería ser null.", cofre.getNodo());}
    @Test
    public void getAliasNodo() {
        assertEquals("El alias del nodo no es el esperado.", "C-001", cofre.getNodo().getAlias());
    }

    @Test
    public void testGuardarYConsultarItem() {
    	assertEquals("Un item no guardado debería tener cantidad 0.", 0, cofre.consultarItem(item1));

        cofre.guardarItem(item1, 50);
        assertEquals("La cantidad del item1 no es la esperada.", 50, cofre.consultarItem(item1));

        cofre.guardarItem(item1, 20);
        assertEquals("La cantidad del item1 debería acumularse.", 70, cofre.consultarItem(item1));

        cofre.guardarItem(item2, 30);
        assertEquals("La cantidad del item2 no es la esperada.", 30, cofre.consultarItem(item2));
    }

    @Test
    public void tieneItem() {


        assertFalse("No debería tener un item no guardado.", cofre.tieneItem(item1));}

    @Test
    public void guardarItem() {
    	cofre.guardarItem(item1, 10);
        assertTrue("Debería existir el item.", cofre.tieneItem(item1));}

    @Test
    public void descontarItem() {
    	cofre.guardarItem(item1, 10);
    	cofre.descontarItem(item1, 10);
    	assertFalse("No debería tener un item si su stock es 0.", cofre.tieneItem(item1));
    }

    @Test
    public void descontarMasCantidad() {
    	cofre.guardarItem(item1, 10);
    	cofre.descontarItem(item1, 11);
    	assertEquals("Deberia seguir con la misma cantidad.", 10, cofre.consultarItem(item1));

    }

    @Test
    public void testSetRobopuertoMasCercano() {
        Robopuerto rp = new Robopuerto("RP-A", 1, 1, 5.0);
        double distancia = 15.5;

        cofre.setRp_mas_cercano(rp, distancia);

        assertEquals("El Robopuerto más cercano no es el esperado.", rp, cofre.getRp_mas_cercano());
        assertEquals("La distancia mínima al Robopuerto no es la esperada.", distancia, cofre.getDistanciaRP_minima(), 0.001);
    }
}