package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cofres.CofreSolicitud;
import utiles.Item;

public class CofreSolicitudTest {

    private CofreSolicitud cofre;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {
        cofre = new CofreSolicitud(2, 3, "SOL-1");
        item1 = new Item(10, "Manzana", "Fruta");
        item2 = new Item(11, "Pan", "Comida");
    }

    @Test
    public void solicitarItem() {
        assertEquals(0, cofre.cuantoSolicita(item1));
        cofre.solicitarItem(item1, 5);
        assertEquals("La cantidad solicitada no es la correcta.", 5, cofre.cuantoSolicita(item1));
        cofre.solicitarItem(item1, 10);
        assertEquals("La cantidad solicitada debe acumularse.", 15, cofre.cuantoSolicita(item1));
    }

    @Test
    public void aceptarPedidoParcial() {
        cofre.solicitarItem(item1, 20);
        cofre.aceptarPedido(item1, 8);
        assertEquals("La cantidad solicitada debe reducirse tras un pedido parcial.", 12, cofre.cuantoSolicita(item1));
        assertTrue("El item todavía debe ser necesitado.", cofre.necesita(item1));
    }

    @Test
    public void aceptarPedidoCompleto() {
        cofre.solicitarItem(item1, 20);
        cofre.aceptarPedido(item1, 20);
        assertEquals("La cantidad solicitada debe ser 0 tras un pedido completo.", 0, cofre.cuantoSolicita(item1));
        assertFalse("El item ya no debe ser necesitado si se completó el pedido.", cofre.necesita(item1));
    }

    @Test
    public void aceptarPedidoExcedente() {
        cofre.solicitarItem(item1, 20);
        cofre.aceptarPedido(item1, 25); // Se aceptan más de los solicitados
        assertEquals("La cantidad solicitada debe ser 0 si el pedido se satisface con exceso.", 0, cofre.cuantoSolicita(item1));
        assertFalse("El item ya no debe ser necesitado si se completó el pedido.", cofre.necesita(item1));
    }

    @Test
    public void necesita() {
        assertFalse("No debería necesitar un item no solicitado.", cofre.necesita(item1));
        cofre.solicitarItem(item1, 1);
        assertTrue("Debería necesitar un item solicitado.", cofre.necesita(item1));
    }

    @Test
    public void getPedidos() {
        cofre.solicitarItem(item1, 10);
        cofre.solicitarItem(item2, 5);
        assertEquals("El mapa de pedidos no tiene el tamaño esperado.", 2, cofre.getPedidos().size());
        assertTrue("El mapa de pedidos debe contener el item1.", cofre.getPedidos().containsKey(item1));
        assertEquals("La cantidad para item1 en el mapa no es la correcta.", 10, (int) cofre.getPedidos().get(item1));
    }
}