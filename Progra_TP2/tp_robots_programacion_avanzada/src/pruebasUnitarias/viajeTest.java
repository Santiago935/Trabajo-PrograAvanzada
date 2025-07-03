package pruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cofres.CofreAlmacenamiento;
import cofres.CofreSolicitud;
import grafos.Nodo;
import utiles.Item;
import utiles.Pedido;
import utiles.Viaje;

public class viajeTest {

    private Nodo destino;
    private Pedido pedido;
    private Viaje viajeIda;

    @Before
    public void setUp() {
        destino = new Nodo(1, "Destino-1");
        Item item = new Item(1, "Item", "Desc");
        pedido = new Pedido(new CofreSolicitud(0,0,"S1"), new CofreAlmacenamiento(1,1,"A1"), item, 10);
        // Viaje de ida con 5 pasos y 50.0 de batería al final
        viajeIda = new Viaje(destino, 5, 50.0, pedido, true);
    }

    @Test
    public void constructorYGetters() {
        assertEquals("El destino no es el esperado.", destino, viajeIda.getDestino());
        assertEquals("Los pasos restantes no son los esperados.", 5, viajeIda.getPasosRestantes());
        assertEquals("La batería final no es la esperada.", 50.0, viajeIda.getBateriaFinal(), 0.001);
        assertEquals("El pedido asociado no es el esperado.", pedido, viajeIda.getPedido());
        assertTrue("El viaje debería ser de ida.", viajeIda.isIda());
    }

    @Test
    public void avanzar() {
        assertEquals(5, viajeIda.getPasosRestantes());
        viajeIda.avanzar();
        assertEquals("Los pasos restantes deben disminuir en 1.", 4, viajeIda.getPasosRestantes());
        viajeIda.avanzar();
        viajeIda.avanzar();
        assertEquals("Los pasos restantes deben seguir disminuyendo.", 2, viajeIda.getPasosRestantes());
    }

    @Test
    public void llego() {
        assertFalse("No debería haber llegado al inicio.", viajeIda.llego());
        
        viajeIda.avanzar(); // 4
        viajeIda.avanzar(); // 3
        viajeIda.avanzar(); // 2
        viajeIda.avanzar(); // 1
        assertFalse("Todavía no debería haber llegado.", viajeIda.llego());
        
        viajeIda.avanzar(); // 0
        assertTrue("Debería haber llegado cuando los pasos son 0.", viajeIda.llego());

        viajeIda.avanzar(); // -1
        assertTrue("Debería considerarse llegado si los pasos son negativos.", viajeIda.llego());
    }
}