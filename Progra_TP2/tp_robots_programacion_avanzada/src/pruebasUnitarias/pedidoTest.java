package pruebasUnitarias;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cofres.Cofre;
import cofres.CofreAlmacenamiento;
import cofres.CofreSolicitud;
import red.Coordenada;
import utiles.Item;
import utiles.Pedido;

public class pedidoTest {

    private Cofre cSolicitante;
    private Cofre cOfertante;
    private Item item;
    private int cantidad;
    private Pedido pedido;

    @Before
    public void setUp() {
        cSolicitante = new CofreSolicitud(0, 0, "solicitante1");
        cOfertante = new CofreAlmacenamiento(3, 4, "ofertante1");
        item = new Item(1, "Pocion", "Restaura vida");
        cantidad = 5;
        pedido = new Pedido(cSolicitante, cOfertante, item, cantidad);
    }

    @Test
    public void getCofreOrigen() {
    	assertEquals("El cofre de origen no es el esperado.", cSolicitante, pedido.getcOrigen());

    }

    @Test
    public void getCofreDestino() {

    	assertEquals("El cofre de destino no es el esperado.", pedido.getcSolicito(), cSolicitante);
    }

    @Test
    public void getItem() {
    	assertEquals("El item no es el esperado.", item, pedido.getItem());

    }

    @Test
    public void getCantidad() {
    	assertEquals("La cantidad no es la esperada.", cantidad, pedido.getCantidad());

    }

    @Test
    public void getDistanciaVacia() {
    	assertEquals("La distancia debería ser 5.0 inicialmente.", 5.0, pedido.getDistancia(), 0.001);

    }


    @Test
    public void atenderPedido() {

    	pedido.AtenderPedido(cOfertante);

        assertEquals("El cofre de destino no fue asignado correctamente.", cOfertante, pedido.getcDestino());
    }

    //Testeo si calcula bien la distancia (osea se la asigno bien)
    @Test
    public void getDistancia() {
    	pedido.AtenderPedido(cOfertante);

        double distanciaEsperada = Coordenada.distancia_eucladiana(cSolicitante.getCoordenada(), cOfertante.getCoordenada());
        assertEquals("La distancia no se calculó correctamente.", distanciaEsperada, pedido.getDistancia(), 0.001);

    }


    @Test
    public void pedidoNoSePisan() {
        pedido.AtenderPedido(cOfertante);
        Cofre otroOfertante = new CofreAlmacenamiento(5, 5, "otroOfertante");


        pedido.AtenderPedido(otroOfertante);

        assertEquals("El cofre de destino no debería cambiar si el pedido ya fue atendido.", cOfertante, pedido.getcDestino());
    }


    @Test
    public void testToString() {
        pedido.AtenderPedido(cOfertante);
        String expected = String.format("Pedido[origen=%s, destino=%s, item=%s, cantidad=%d, distancia=%.2f]",
                cSolicitante.getId(), cOfertante.getId(), item.getNombre(), cantidad, 5.00);

        assertEquals("El formato toString no es el esperado.", expected.replace(",", "."), pedido.toString().replace(",", "."));
    }
}