package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import grafos.AlgoritmosGrafos;
import grafos.AlgoritmosGrafos.Dijkstra_resultado;
import grafos.Grafo;
import grafos.Nodo;
public class algoritmoGrafosTest {

    private Nodo nodo1, nodo2, nodo3, nodo4;
    private Grafo grafoDirigido;

    @Before
    public void setUp() {
        nodo1 = new Nodo(1, "Coruscant");
        nodo2 = new Nodo(2, "Tatooine");
        nodo3 = new Nodo(3, "Alderaan");
        nodo4 = new Nodo(4, "Dagobah");

        grafoDirigido = new Grafo(true);
    }

	 @Test
	    public void dijkstraCaminoSimpleYReconstruccion() {
	        // Grafo: N1 --(10)--> N2 --(10)--> N3
	        grafoDirigido.addNodo(nodo1);
	        grafoDirigido.addNodo(nodo2);
	        grafoDirigido.addNodo(nodo3);
	        grafoDirigido.addArista(nodo1, nodo2, 10);
	        grafoDirigido.addArista(nodo2, nodo3, 10);

	        Dijkstra_resultado resultado = AlgoritmosGrafos.dijkstraConBateria(grafoDirigido, nodo1, 100, 100, Collections.emptySet());

	        assertEquals(20.0, resultado.getCostosMinimos().get(nodo3.getId()), 0.01);

	        List<Integer> camino = resultado.reconstruirCamino(nodo3.getId());
	        List<Integer> caminoEsperado = Arrays.asList(1, 2, 3);
	        assertEquals(caminoEsperado, camino);
	    }

	    @Test
	    public void dijkstra_nodoInalcanzablePorBateria() {
	        // Grafo: N1 --(costo/bateria 25)--> N2
	        grafoDirigido.addNodo(nodo1);
	        grafoDirigido.addNodo(nodo2);
	        grafoDirigido.addArista(nodo1, nodo2, 25);

	        // Batería inicial (20) es insuficiente para el viaje (25)
	        Dijkstra_resultado resultado = AlgoritmosGrafos.dijkstraConBateria(grafoDirigido, nodo1, 20, 100, Collections.emptySet());

	        assertEquals(Double.MAX_VALUE, resultado.getCostosMinimos().get(nodo2.getId()), 0.01);
	    }

	    @Test
	    public void dijkstra_caminoConRobopuerto() {
	        // Grafo: N1 --(25)--> N2 (Robopuerto) --(25)--> N3
	        grafoDirigido.addNodo(nodo1);
	        grafoDirigido.addNodo(nodo2);
	        grafoDirigido.addNodo(nodo3);
	        grafoDirigido.addArista(nodo1, nodo2, 25);
	        grafoDirigido.addArista(nodo2, nodo3, 25);

	        Set<Integer> robopuertos = new HashSet<>(Collections.singletonList(nodo2.getId()));

	        // Con batería inicial 30, puede llegar a N2. Allí recarga a 100 y puede continuar a N3.
	        Dijkstra_resultado resultado = AlgoritmosGrafos.dijkstraConBateria(grafoDirigido, nodo1, 30, 100, robopuertos);

	        assertEquals(50.0, resultado.getCostosMinimos().get(nodo3.getId()), 0.01);
	        assertEquals(30.0 - 25.0, resultado.getMejorBateriaConCostoMinimoDouble().get(nodo2.getId()), 0.01); // Llega a N2 con 5
	        // Sale de N2 con 100 (recargado), llega a N3 con 100 - 25 = 75
	        assertEquals(75.0, resultado.getMejorBateriaConCostoMinimoDouble().get(nodo3.getId()), 0.01);
	    }

	    @Test
	    public void dijkstra_mismoCostoDiferenteBateria() {
	        // Dos caminos a N4 con costo total 15, pero uno conserva más batería.
	        // Camino 1: N1 -> N2 -> N4. Costo 10+5=15. Gasto Batería 10+5=15.
	        // Camino 2: N1 -> N3 -> N4. Costo 10+5=15. Gasto Batería 2+5=7.
	        grafoDirigido.addNodo(nodo1);
	        grafoDirigido.addNodo(nodo2);
	        grafoDirigido.addNodo(nodo3);
	        grafoDirigido.addNodo(nodo4);
	        grafoDirigido.addArista(nodo1, nodo2, 10); // Gasta 10 de batería
	        grafoDirigido.addArista(nodo2, nodo4, 5);  // Gasta 5 de batería

	        grafoDirigido.addArista(nodo1, nodo3, 2); // Un camino más barato en batería
	        grafoDirigido.addArista(nodo3, nodo4, 13); // pero el costo total del camino es el mismo

	        Dijkstra_resultado resultado = AlgoritmosGrafos.dijkstraConBateria(grafoDirigido, nodo1, 20, 100, Collections.emptySet());

	        // El costo final a N4 debería ser el de N1->N3->N4
	        assertEquals(15.0, resultado.getCostosMinimos().get(nodo4.getId()), 0.01);
	        // La batería restante debería ser 20 - (2+13) = 5
	        assertEquals(5.0, resultado.getMejorBateriaConCostoMinimoDouble().get(nodo4.getId()), 0.01);

	        // El predecesor de N4 debe ser N3, que es la ruta que ahorra más batería.
	        List<Integer> camino = resultado.reconstruirCamino(nodo4.getId());
	        List<Integer> caminoEsperado = Arrays.asList(1, 3, 4);
	        assertEquals(caminoEsperado, camino);
	    }

	    @Test
	    public void dijkstra_sinCaminoPosible() {
	        // N1 y N2 están desconectados de N3
	        grafoDirigido.addNodo(nodo1);
	        grafoDirigido.addNodo(nodo2);
	        grafoDirigido.addNodo(nodo3);
	        grafoDirigido.addArista(nodo1, nodo2, 10);

	        Dijkstra_resultado resultado = AlgoritmosGrafos.dijkstraConBateria(grafoDirigido, nodo1, 100, 100, Collections.emptySet());

	        assertEquals(Double.MAX_VALUE, resultado.getCostosMinimos().get(nodo3.getId()), 0.01);
	        assertTrue(resultado.reconstruirCamino(nodo3.getId()).isEmpty());
	    }
	}






