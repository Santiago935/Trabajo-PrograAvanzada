package pruebasUnitarias;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import grafos.*;

public class clasesGrafosTests {
	
	private Nodo nodo1, nodo2, nodo3, nodo4, nodo5;
	private Grafo grafoNoDirigido, grafoDirigido;

	@Before
	public void setUp() {
	    nodo1 = new Nodo(01, "Nodo de la Muerte");
	    nodo2 = new Nodo(02, "Nodo Yaviniano");
	    nodo3 = new Nodo(03, "Nodo Aldeereano");
	    nodo4 = new Nodo(04, "Nodo Corsuceano");
	    nodo5 = new Nodo(05, "Nodo Nabooeano");
	    
	    
	    grafoNoDirigido = new Grafo(false);
        grafoDirigido = new Grafo(true);

	}



	@Test(expected = IllegalArgumentException.class)
    public void insertarNodoRepetido() {
        grafoNoDirigido.addNodo(nodo1);
        grafoNoDirigido.addNodo(nodo1); 
    }

    @Test
    public void grafoGetNodos() {
        grafoNoDirigido.addNodo(nodo1);
        grafoNoDirigido.addNodo(nodo2);
        grafoNoDirigido.addNodo(nodo3);

        Set<Nodo> listaNodos = grafoNoDirigido.getNodos();
        assertTrue("Test1: Deberia contener al nodo1", listaNodos.contains(nodo1));
        assertTrue("Test2: Deberia contener al nodo1", listaNodos.contains(nodo2));
        assertTrue("Test3: Deberia contener al nodo1", listaNodos.contains(nodo3));
        assertFalse("Test4: No debería contener nodo4", listaNodos.contains(nodo4));
        assertEquals("Test5: El tamaño que devuelve deberia ser 3", 3, listaNodos.size());
    }

    @Test
    public void gNoDirigidoGetVecinos() {
        grafoNoDirigido.addNodo(nodo1);
        grafoNoDirigido.addNodo(nodo2);
        grafoNoDirigido.addNodo(nodo3);
        grafoNoDirigido.addNodo(nodo4);
        grafoNoDirigido.addArista(nodo1, nodo2, 5);
        grafoNoDirigido.addArista(nodo1, nodo4, 10);

        Set<Nodo> vecinos1 = grafoNoDirigido.getVecinos(nodo1);
        assertEquals("Test1: El tamaño del grafo deberia ser 2", 2, vecinos1.size());
        assertTrue("Test2: nodo1 no conecta bien con nodo2", vecinos1.contains(nodo2));
        assertTrue("Test3: nodo1 no conecta bien con nodo4", vecinos1.contains(nodo4));

        Set<Nodo> vecinos2 = grafoNoDirigido.getVecinos(nodo2);
        assertEquals("Test4: El tamaño de vecinos de nodo2 no es correcto", 1, vecinos2.size());
        assertTrue("Test5: nodo2 no conecta bien con nodo1", vecinos2.contains(nodo1));
    }

    @Test
    public void gDirigidoGetVecinos() {
        grafoDirigido.addNodo(nodo1);
        grafoDirigido.addNodo(nodo2);
        grafoDirigido.addNodo(nodo4);
        grafoDirigido.addArista(nodo1, nodo2, 5);
        grafoDirigido.addArista(nodo1, nodo4, 10);

        Set<Nodo> vecinos1 = grafoDirigido.getVecinos(nodo1);
        assertEquals("Test1: El tamaño de vecinos de nodo1 no es correcto", 2, vecinos1.size());
        assertTrue("Test2: nodo1 deberia ser vecino de nodo2", vecinos1.contains(nodo2));
        assertTrue("Test3: nodo1 deberia ser vecino de nodo4", vecinos1.contains(nodo4));

        Set<Nodo> vecinos2 = grafoDirigido.getVecinos(nodo2);
        assertTrue("Test4: nodo2 no debería tener vecinos de salida ya que es un grafo dirigido", vecinos2.isEmpty());
    }
    
    @Test
    public void getVecinosDeNodoAislado() {
        grafoNoDirigido.addNodo(nodo1);
        grafoNoDirigido.addNodo(nodo2);
        grafoNoDirigido.addArista(nodo1, nodo2, 10);
        

        grafoNoDirigido.addNodo(nodo3);
        Set<Nodo> vecinos3 = grafoNoDirigido.getVecinos(nodo3);
        assertTrue("Test1: Un nodo aislado no debe tener vecinos", vecinos3.isEmpty());
    }

    @Test
    public void getVecinosDeNodoInexistente() {
        grafoNoDirigido.addNodo(nodo1);
        Set<Nodo> vecinos5 = grafoNoDirigido.getVecinos(nodo5);
        assertTrue("Test1: Un nodo que no existe en el grafo no debe tener vecinos", vecinos5.isEmpty());
    }


    @Test
    public void gNoDirigidoGetAristasdeNodo() {
        grafoNoDirigido.addNodo(nodo1);
        grafoNoDirigido.addNodo(nodo2);
        grafoNoDirigido.addNodo(nodo4);
        grafoNoDirigido.addArista(nodo1, nodo2, 5);
        grafoNoDirigido.addArista(nodo1, nodo4, 10);

        List<Arista> aristas1 = grafoNoDirigido.getAristas_deNodo(nodo1);
        assertEquals("Test1: El número de aristas de nodo1 es incorrecto", 2, aristas1.size());
        Set<Integer> idsDestino = aristas1.stream().map(a -> a.getObjetivo().getId()).collect(Collectors.toSet());
        assertTrue("Test2: Falta arista de nodo1 a nodo2", idsDestino.contains(nodo2.getId()));
        assertTrue("Test3: Falta arista de nodo1 a nodo4", idsDestino.contains(nodo4.getId()));
    }
	
}
