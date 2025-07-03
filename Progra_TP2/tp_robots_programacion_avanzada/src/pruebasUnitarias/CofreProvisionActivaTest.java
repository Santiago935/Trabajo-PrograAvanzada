package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cofres.CofreProvisionActiva;
import utiles.Item;

public class CofreProvisionActivaTest {

    private CofreProvisionActiva cofre;
    private Item item;

    @Before
    public void setUp() {
        cofre = new CofreProvisionActiva(3, 4, "ACT-1");
        item = new Item(30, "Diamante", "Gema preciosa");

        // El cofre tiene 10 items en su almacenamiento interno
        cofre.guardarItem(item, 10);
    }

    @Test
    public void agregarOfertaExitosa() {
        assertEquals("Inicialmente no debería ofrecer nada.", 0, cofre.cuantoOfrece(item));
        assertTrue("Debería poder agregar una oferta si hay stock.", cofre.agregarOferta(item, 5));
        assertEquals("La cantidad ofrecida no es la esperada.", 5, cofre.cuantoOfrece(item));
        assertTrue("Debería poder agregar más a la oferta.", cofre.agregarOferta(item, 5));
        assertEquals("La cantidad ofrecida debe acumularse.", 10, cofre.cuantoOfrece(item));
    }

    @Test
    public void agregarOfertaFallida() {
        assertFalse("No debería poder ofrecer más de lo que tiene en stock.", cofre.agregarOferta(item, 15));
        assertEquals("La oferta no debe cambiar si falla.", 0, cofre.cuantoOfrece(item));

        cofre.agregarOferta(item, 8);
        assertFalse("No debería poder agregar a la oferta si excede el stock total.", cofre.agregarOferta(item, 3));
        assertEquals("La oferta no debe cambiar si la adición falla.", 8, cofre.cuantoOfrece(item));
    }

    @Test
    public void reservarItemExitoso() {
        cofre.agregarOferta(item, 8);
        assertTrue("Debería poder reservar una cantidad ofrecida.", cofre.reservarItem(item, 5));
        assertEquals("La cantidad ofrecida debe disminuir tras la reserva.", 3, cofre.cuantoOfrece(item));
        // El stock interno no debe cambiar
        assertEquals("El stock interno del cofre no debe cambiar al reservar.", 10, cofre.consultarItem(item));
    }

    @Test
    public void reservarItemFallido() {
        cofre.agregarOferta(item, 8);
        assertFalse("No debería poder reservar más de lo ofrecido.", cofre.reservarItem(item, 10));
        assertEquals("La cantidad ofrecida no debe cambiar si la reserva falla.", 8, cofre.cuantoOfrece(item));
    }
}