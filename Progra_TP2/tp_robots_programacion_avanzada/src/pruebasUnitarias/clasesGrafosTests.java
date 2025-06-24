package pruebasUnitarias;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import grafos.*;

public class clasesGrafosTests {
	
	private Nodo nodo1, nodo2, nodo3, nodo4, nodo5;
	private Arista arista1;
	private Grafo grafo1;

	@Before
	public void setUp() {
	    nodo1 = new Nodo(01, "Nodo de la Muerte");
	    nodo2 = new Nodo(02, "Nodo Yaviniano");
	    nodo3 = new Nodo(03, "Nodo Aldeereano");
	    nodo4 = new Nodo(04, "Nodo Corsuceano");
	    nodo5 = new Nodo(05, "Nodo Nabooeano");
	    
	    arista1 = new Arista(nodo2, -4);
	    
	    grafo1 = new Grafo(false);

	}


	@Test
	public void claseNodo() {
		assertEquals("Test1: El id del nodo no es el insertado",nodo1.getId(), 01);
		assertEquals("Test2: El Alias del nodo no es el insertado",nodo1.getAlias(), "Nodo de la Muerte");
	}
	

	@Test
	public void claseArista() {
		assertEquals("Test1: No esta devolviendo el Nodo Objetivo",arista1.getObjetivo(), nodo2);
		assertEquals("Test2: No esta devolviendo el peso inicial",arista1.getPeso(), -4, 0.001);
		arista1.setPeso(7.3);
		assertEquals("Test3: no funciona el metodo setPeso()",arista1.getPeso(), 7.3, 0.001);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void grafoNodoRepetido() {
	    grafo1.addNodo(nodo1);
	    grafo1.addNodo(nodo1); // debería lanzar la excepción
	}
	
	@Test
	public void grafoVecinos() {
		
		 
		//Testeo si funciona 
		grafo1.addNodo(nodo1);
		grafo1.addNodo(nodo2);
		grafo1.addNodo(nodo3);
		grafo1.addNodo(nodo4);
		grafo1.addArista(nodo2, nodo1, 5);
		grafo1.addArista(nodo1, nodo4, 10);
		grafo1.addArista(nodo3, nodo4, 50);
		
		Set<Nodo> nodosVecinos = grafo1.getVecinos(nodo1);
		
		
		assertTrue("No conecta bien con nodo2",nodosVecinos.contains(nodo2));
		  assertTrue("No conecta bien con nodo4",nodosVecinos.contains(nodo4));
		  assertEquals("El tamaño que devuelve no es correcto",2, nodosVecinos.size());
	      nodosVecinos = grafo1.getVecinos(nodo4);
	      assertFalse("nodo4 no deberia conectar con nodo2",nodosVecinos.contains(nodo2));
	      assertTrue("No conecta bien con nodo4",nodosVecinos.contains(nodo3));
	      assertTrue("No conecta bien con nodo4",nodosVecinos.contains(nodo1));
	      assertEquals("El tamaño que devuelve no es correcto",2, nodosVecinos.size());
		
	}
	
	@Test
	public void grafoGetNodos() {
		
		grafo1.addNodo(nodo1);
		grafo1.addNodo(nodo2);
		grafo1.addNodo(nodo3);
		grafo1.addNodo(nodo4);
		
		Set <Nodo> listaNodos = grafo1.getNodos();
		assertTrue("No contiene nodo1", listaNodos.contains(nodo1));
		assertTrue("No contiene nodo2", listaNodos.contains(nodo2));
		assertTrue("No contiene nodo3", listaNodos.contains(nodo3));
		assertTrue("No contiene nodo4", listaNodos.contains(nodo4));
		assertEquals("El tamaño que devuelve no es correcto",4, listaNodos.size());
	}
	
	@Test
	public void grafoGetArista() {
	    grafo1.addNodo(nodo1);
	    grafo1.addNodo(nodo2);
	    grafo1.addNodo(nodo3);
	    grafo1.addNodo(nodo4);
	    grafo1.addNodo(nodo5);

	    grafo1.addArista(nodo2, nodo1, 5);
	    grafo1.addArista(nodo5, nodo1, -5);
	    grafo1.addArista(nodo1, nodo4, 10);
	    grafo1.addArista(nodo3, nodo4, 50);

	    List<Arista> aristasNodo1 = grafo1.getAristas_deNodo(nodo1);

	    assertTrue("No contiene arista hacia nodo4", aristasNodo1.stream().anyMatch(arista -> arista.getObjetivo().equals(nodo4)));

	    List<Arista> aristasNodo2 = grafo1.getAristas_deNodo(nodo2);
	    assertTrue("No contiene arista hacia nodo1 desde nodo2", aristasNodo2.stream().anyMatch(arista -> arista.getObjetivo().equals(nodo1)));

	    List<Arista> aristasNodo5 = grafo1.getAristas_deNodo(nodo5);
	    assertTrue("No contiene arista hacia nodo1 desde nodo5", aristasNodo5.stream().anyMatch(arista -> arista.getObjetivo().equals(nodo1)));

	    List<Arista> aristasNodo3 = grafo1.getAristas_deNodo(nodo3);
	    assertTrue("No contiene arista hacia nodo4 desde nodo3", aristasNodo3.stream().anyMatch(arista -> arista.getObjetivo().equals(nodo4)));
	}


	
}
