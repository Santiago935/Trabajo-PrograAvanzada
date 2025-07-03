package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cofres.CofreProvisionPasiva;
import utiles.Item;

public class CofreProvisionPasivaTest {

    private CofreProvisionPasiva cofre;
    private Item item;

    @Before
    public void setUp() {
        cofre = new CofreProvisionPasiva(8, 8, "PAS-1");
        item = new Item(20, "Trigo", "Cereal");
    }

    @Test
    public void almacenarYCuantoOfrece() {
        assertEquals("Inicialmente no debería ofrecer nada.", 0, cofre.cuantoOfrece(item));
        cofre.almacenar(item, 100);
        assertEquals("La cantidad ofrecida no es correcta.", 100, cofre.cuantoOfrece(item));
        cofre.almacenar(item, 50);
        assertEquals("La cantidad ofrecida debe acumularse.", 150, cofre.cuantoOfrece(item));
    }

    @Test
    public void reservarItemExitoso() {
        cofre.almacenar(item, 100);
        assertTrue("Debería poder reservar una cantidad menor al stock.", cofre.reservarItem(item, 40));
        assertEquals("El stock ofrecido debe disminuir tras la reserva.", 60, cofre.cuantoOfrece(item));
    }

    @Test
    public void reservarItemFallido() {
        cofre.almacenar(item, 30);
        assertFalse("No debería poder reservar una cantidad mayor al stock.", cofre.reservarItem(item, 50));
        assertEquals("El stock ofrecido no debe cambiar si la reserva falla.", 30, cofre.cuantoOfrece(item));
    }

    @Test
    public void reservarItemInexistente() {
        assertFalse("No debería poder reservar un item que no existe.", cofre.reservarItem(item, 10));
    }
}