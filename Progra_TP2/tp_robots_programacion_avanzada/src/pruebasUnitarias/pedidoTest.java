package pruebasUnitarias;
import static org.junit.Assert.*;
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
        pedido = new Pedido(cSolicitante, null, item, cantidad);
    }
    
    @Test
    public void getCofreOrigen() {
    	assertEquals("El cofre de origen no es el esperado.", cSolicitante, pedido.getcOrigen());
    	
    }
    
    @Test
    public void getCofreDestino() {
    	
    	assertNull("El cofre de destino debería ser null inicialmente.", pedido.getcDestino());
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
    	assertEquals("La distancia debería ser 0.0 inicialmente.", 0.0, pedido.getDistancia(), 0.001);
    	
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
    public void compareTo() {
        pedido.AtenderPedido(cOfertante); 

        Cofre otroSolicitante = new CofreSolicitud(1, 1, "sol2");
        Cofre otroOfertante = new CofreAlmacenamiento(2, 2, "ofer2");
        Pedido otroPedido = new Pedido(otroSolicitante, null, item, 3);
        otroPedido.AtenderPedido(otroOfertante); 

        assertTrue("Test1: El primer pedido (distancia 5.0) debe ser mayor que el segundo (distancia 1.41).", pedido.compareTo(otroPedido) > 0);
        assertTrue("Test2: El segundo pedido (distancia 1.41) debe ser menor que el primero (distancia 5.0).", otroPedido.compareTo(pedido) < 0);
        
        Pedido pedidoIgual = new Pedido(cSolicitante, null, item, cantidad);
        pedidoIgual.AtenderPedido(cOfertante);
        assertEquals("Test3: Pedidos con la misma distancia deben ser iguales en comparación.", 0, pedido.compareTo(pedidoIgual));
    }

    @Test
    public void testToString() {
        pedido.AtenderPedido(cOfertante);
        String expected = String.format("Pedido[origen=%s, destino=%s, item=%s, cantidad=%d, distancia=%.2f]",
                cSolicitante.getId(), cOfertante.getId(), item.getNombre(), cantidad, 5.00);
       
        assertEquals("El formato toString no es el esperado.", expected.replace(",", "."), pedido.toString().replace(",", "."));
    }
}