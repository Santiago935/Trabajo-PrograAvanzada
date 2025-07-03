package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cofres.CofreBufer;
import utiles.Item;

public class CofreBufferTest {

    private CofreBufer cofre;
    private Item itemSolicitado;
    private Item itemOfrecido;

    @Before
    public void setUp() {
        cofre = new CofreBufer(7, 7, "BUF-1");
        itemSolicitado = new Item(40, "Oro", "Metal precioso");
        itemOfrecido = new Item(41, "Plata", "Metal");


        cofre.guardarItem(itemOfrecido, 20);
    }


    @Test
    public void cuantoSolicita() {
        assertEquals(0, cofre.cuantoSolicita(itemSolicitado));
        cofre.solicitarItem(itemSolicitado, 15);
        assertEquals("La cantidad solicitada no es la correcta.", 15, cofre.cuantoSolicita(itemSolicitado));
    }

    @Test
    public void aceptarPedido() {
        cofre.solicitarItem(itemSolicitado, 25);
        cofre.aceptarPedido(itemSolicitado, 10);
        assertEquals("La solicitud debe reducirse tras aceptar un pedido parcial.", 15, cofre.cuantoSolicita(itemSolicitado));
        cofre.aceptarPedido(itemSolicitado, 15);
        assertEquals("La solicitud debe ser 0 tras completarse.", 0, cofre.cuantoSolicita(itemSolicitado));
    }


    @Test
    public void cuantoOfrece() {
        assertEquals("Inicialmente no debería ofrecer items.", 0, cofre.cuantoOfrece(itemOfrecido));
        assertTrue("Debería poder ofrecer items que tiene en stock.", cofre.agregarOferta(itemOfrecido, 10));
        assertEquals("La cantidad ofrecida no es la correcta.", 10, cofre.cuantoOfrece(itemOfrecido));
    }

    @Test
    public void agregarOfertaFallida() {
        assertFalse("No debería poder ofrecer más de lo que tiene.", cofre.agregarOferta(itemOfrecido, 25));
        assertEquals("La oferta no debe cambiar si falla.", 0, cofre.cuantoOfrece(itemOfrecido));
    }

    @Test
    public void reservarItem() {
        cofre.agregarOferta(itemOfrecido, 15);
        assertTrue("Debería poder reservar una cantidad ofrecida.", cofre.reservarItem(itemOfrecido, 10));
        assertEquals("La cantidad ofrecida debe disminuir tras la reserva.", 5, cofre.cuantoOfrece(itemOfrecido));
        assertFalse("No debería poder reservar más de lo ofrecido.", cofre.reservarItem(itemOfrecido, 10));
    }

    @Test
    public void comportamientoDual() {
        // Solicita Oro
        cofre.solicitarItem(itemSolicitado, 10);
        assertEquals(10, cofre.cuantoSolicita(itemSolicitado));

        // Ofrece Plata
        cofre.agregarOferta(itemOfrecido, 15);
        assertEquals(15, cofre.cuantoOfrece(itemOfrecido));

        // Verifica que no se mezclen
        assertEquals(0, cofre.cuantoOfrece(itemSolicitado));
        assertEquals(0, cofre.cuantoSolicita(itemOfrecido));

        // Acepta pedido de Oro
        cofre.aceptarPedido(itemSolicitado, 5);
        assertEquals(5, cofre.cuantoSolicita(itemSolicitado));

        // Reserva oferta de Plata
        cofre.reservarItem(itemOfrecido, 5);
        assertEquals(10, cofre.cuantoOfrece(itemOfrecido));

        // El stock interno de plata no debe cambiar
        assertEquals(20, cofre.consultarItem(itemOfrecido));
    }
}